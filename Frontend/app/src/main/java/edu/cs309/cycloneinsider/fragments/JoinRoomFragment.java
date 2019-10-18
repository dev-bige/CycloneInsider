package edu.cs309.cycloneinsider.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.activities.InsiderActivity;
import edu.cs309.cycloneinsider.activities.MainActivity;
import edu.cs309.cycloneinsider.api.models.RoomModel;
import edu.cs309.cycloneinsider.fragments.adapters.RoomListRecyclerViewAdapter;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class JoinRoomFragment extends Fragment {
    private Disposable roomsListSubscription;
    private LinearLayoutManager layoutManager;
    private RoomListRecyclerViewAdapter mAdapter;
    private Disposable onClickSubscription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_join_room, container, false);
    }

    @Override
    public void onDestroy() {
        if (!roomsListSubscription.isDisposed()) {
            roomsListSubscription.dispose();
        }

        if (!onClickSubscription.isDisposed()) {
            onClickSubscription.dispose();
        }
        super.onDestroy();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RoomListRecyclerViewAdapter();
        recyclerView.setAdapter(mAdapter);

        Observable<Response<List<RoomModel>>> allRooms = ((InsiderActivity) getActivity())
                .getInsiderApplication()
                .getApiService()
                .getAllRooms()
                .observeOn(AndroidSchedulers.mainThread());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        roomsListSubscription = allRooms.subscribe(roomsResponse -> {
            if (roomsResponse.isSuccessful()) {
                List<RoomModel> roomsList = roomsResponse.body();
                mAdapter.updateList(roomsList);
            }
        });

        onClickSubscription = mAdapter.getItemClicks().subscribe(roomModel -> {
            new MaterialAlertDialogBuilder(getContext())
                    .setTitle("Join room?")
                    .setMessage("You are about to join a room. This action can be undone in the room settings")
                    .setPositiveButton("Join", (dialogInterface, i) -> {
                        Disposable subscribe = ((InsiderActivity) getActivity())
                                .getInsiderApplication()
                                .getApiService()
                                .joinRoom(roomModel.uuid)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(roomMembershipModelResponse -> {
                                    if (roomMembershipModelResponse.isSuccessful()) {
                                        ((MainActivity) getActivity()).loadRooms(() -> {
                                            ((MainActivity) getActivity()).selectRoom(roomModel.uuid);
                                        });
                                    }
                                });
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {

                    })
                    .create()
                    .show();
        });
    }
}
