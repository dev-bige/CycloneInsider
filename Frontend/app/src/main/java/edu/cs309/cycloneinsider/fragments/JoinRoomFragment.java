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

import java.util.List;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.activities.InsiderActivity;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_join_room, container, false);
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
    }

    @Override
    public void onDestroy() {
        if (!roomsListSubscription.isDisposed()) {
            roomsListSubscription.dispose();
        }
        super.onDestroy();
    }
}
