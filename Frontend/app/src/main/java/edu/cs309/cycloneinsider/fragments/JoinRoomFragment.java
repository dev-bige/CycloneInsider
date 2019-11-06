package edu.cs309.cycloneinsider.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.activities.MainActivity;
import edu.cs309.cycloneinsider.api.models.RoomModel;
import edu.cs309.cycloneinsider.di.ViewModelFactory;
import edu.cs309.cycloneinsider.fragments.adapters.RoomListRecyclerViewAdapter;
import edu.cs309.cycloneinsider.viewmodels.JoinRoomViewModel;
import io.reactivex.disposables.Disposable;

public class JoinRoomFragment extends DaggerFragment {
    @Inject
    public ViewModelFactory viewModelFactory;
    private LinearLayoutManager layoutManager;
    private RoomListRecyclerViewAdapter mAdapter;
    private Disposable onClickSubscription;
    private JoinRoomViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_join_room, container, false);
    }

    @Override
    public void onDestroy() {
        if (!onClickSubscription.isDisposed()) {
            onClickSubscription.dispose();
        }
        super.onDestroy();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(JoinRoomViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new RoomListRecyclerViewAdapter();
        recyclerView.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        viewModel.getRoomModelResponse().observe(this, roomsResponse -> {
            if (roomsResponse.isSuccessful()) {
                List<RoomModel> roomsList = roomsResponse.body();
                mAdapter.updateList(roomsList);
            }
        });

        viewModel.getRoomMembershipResponse().observe(this, roomMembershipModelResponse -> {
            if (roomMembershipModelResponse.isSuccessful()) {
                ((MainActivity) getActivity()).loadRooms(() -> {
                    ((MainActivity) getActivity()).selectRoom(roomMembershipModelResponse.body().getRoom().uuid);
                });
            }
        });

        onClickSubscription = mAdapter.getItemClicks().subscribe(roomModel -> {
            new MaterialAlertDialogBuilder(getContext())
                    .setTitle("Join room?")
                    .setMessage("You are about to join a room. This action can be undone in the room settings")
                    .setPositiveButton("Join", (dialogInterface, i) -> {
                        viewModel.joinRoom(roomModel.uuid);
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {

                    })
                    .create()
                    .show();
        });

        viewModel.refresh();
    }
}
