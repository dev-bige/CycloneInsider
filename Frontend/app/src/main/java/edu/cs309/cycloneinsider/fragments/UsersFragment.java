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

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.di.ViewModelFactory;
import edu.cs309.cycloneinsider.fragments.adapters.UserListRecyclerViewAdapter;
import edu.cs309.cycloneinsider.viewmodels.UserListViewModel;

public class UsersFragment extends Fragment {
    @Inject
    ViewModelFactory viewModelFactory;
    private UserListViewModel userListViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Inject
    UserListRecyclerViewAdapter userListRecyclerAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.new_post_button).setVisibility(View.GONE);
        userListViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserListViewModel.class);
        userListRecyclerAdapter.setCanDelete(userListViewModel::canDelete);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(userListViewModel::refresh);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userListRecyclerAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        userListViewModel.getReponseUsers().observe(this, listResponse -> {
            if (listResponse.isSuccessful()) {
                userListRecyclerAdapter.updateList(listResponse.body());
            }
            swipeRefreshLayout.setRefreshing(false);
        });

        if(getArguments() != null) {
            userListViewModel.setRoomId(getArguments().getString("ROOM_ID"));
        }
        userListViewModel.refresh();
    }
}
