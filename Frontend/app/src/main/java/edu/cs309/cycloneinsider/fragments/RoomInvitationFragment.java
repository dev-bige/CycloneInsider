package edu.cs309.cycloneinsider.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.activities.InsiderActivity;
import edu.cs309.cycloneinsider.activities.MainActivity;
import edu.cs309.cycloneinsider.api.UserStateService;
import edu.cs309.cycloneinsider.api.models.RoomModel;
import edu.cs309.cycloneinsider.di.ViewModelFactory;
import edu.cs309.cycloneinsider.fragments.adapters.RoomListRecyclerViewAdapter;
import edu.cs309.cycloneinsider.viewmodels.RoomInvitationViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class RoomInvitationFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RoomListRecyclerViewAdapter roomListRecyclerViewAdapter;
    private LinearLayoutManager layoutManager;
    private Disposable onClickSubscription;

    @Inject
    ViewModelFactory viewModelFactory;
    @Inject
    UserStateService userStateService;
    private RoomInvitationViewModel roomInvitationViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        if (onClickSubscription!= null && !onClickSubscription.isDisposed()) {
            onClickSubscription.dispose();
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_room_invite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        roomInvitationViewModel = ViewModelProviders.of(this, viewModelFactory).get(RoomInvitationViewModel.class);
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(roomInvitationViewModel::refresh);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        roomListRecyclerViewAdapter = new RoomListRecyclerViewAdapter();
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(roomListRecyclerViewAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        roomInvitationViewModel.getPendingInvites().observe(this, listResponse -> {
            if (listResponse.isSuccessful()) {
                List<RoomModel> roomModelList = listResponse.body();
                roomListRecyclerViewAdapter.updateList(roomModelList);
            }
            swipeRefreshLayout.setRefreshing(false);
        });

        roomInvitationViewModel.getJoinRoomMembership().observe(this, roomMembershipModelResponse -> {
            this.userStateService.refreshMemberships(() -> {
                ((MainActivity) getActivity()).selectRoom(roomMembershipModelResponse.body().getRoom().getUuid());
            });
        });

        onClickSubscription = roomListRecyclerViewAdapter.getItemClicks().subscribe(roomModel -> {
           new MaterialAlertDialogBuilder(getContext())
                   .setTitle("Join " + roomModel.name + "?")

                   .setPositiveButton("Accept", (dialogInterface, i) -> {
                            roomInvitationViewModel.joinRoom(roomModel.uuid);
                   })
                   .setNegativeButton("Deny", ((dialogInterface, i) -> {

                   }))
                   .setNeutralButton("Cancel", (dialogInterface, i) -> {

                   })
                   .create()
                   .show();
        });

        roomInvitationViewModel.refresh();
    }
}
