package edu.cs309.cycloneinsider.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.activities.CreatePostActivity;
import edu.cs309.cycloneinsider.activities.InviteActivity;
import edu.cs309.cycloneinsider.activities.PostDetailActivity;
import edu.cs309.cycloneinsider.api.models.PostModel;
import edu.cs309.cycloneinsider.di.ViewModelFactory;
import edu.cs309.cycloneinsider.fragments.adapters.PostListRecyclerViewAdapter;
import edu.cs309.cycloneinsider.viewmodels.PostListViewModel;
import io.reactivex.disposables.Disposable;

public class PostListFragment extends Fragment {
    public static final String ROOM_UUID = "ROOM_UUID";
    @Inject
    ViewModelFactory viewModelFactory;
    private String roomUUID;
    private LinearLayoutManager layoutManager;
    private PostListRecyclerViewAdapter mAdapter;
    private Disposable postClicks;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PostListViewModel viewModel;

    public static PostListFragment newInstance(String roomUuid) {
        PostListFragment postListFragment = new PostListFragment();
        Bundle args = new Bundle();
        args.putString(ROOM_UUID, roomUuid);
        postListFragment.setArguments(args);
        return postListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
        roomUUID = getArguments().getString(ROOM_UUID);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_list, container, false);
    }

    @Override
    public void onDestroy() {
        if (postClicks != null && !postClicks.isDisposed()) {
            postClicks.dispose();
        }
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_post_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_post_list_invite) {
            Intent intent = new Intent(getActivity(), InviteActivity.class);
            intent.putExtra("ROOM_UUID", roomUUID);
            this.getActivity().startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(PostListViewModel.class);
        viewModel.setRoomUUID(roomUUID);
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(viewModel::refresh);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new PostListRecyclerViewAdapter();
        recyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);


        getView().findViewById(R.id.new_post_button).setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), CreatePostActivity.class);
            intent.putExtra("ROOM_UUID", roomUUID);
            startActivity(intent);
        });


        postClicks = mAdapter.getItemClicks().subscribe(item -> {
            Intent intent = new Intent(getActivity(), PostDetailActivity.class);
            intent.putExtra("POST_UUID", item.getUuid());
            startActivity(intent);
        });
        viewModel.getPostListResponse().observe(this, listResponse -> {
            if (listResponse.isSuccessful()) {
                List<PostModel> posts = listResponse.body();

                mAdapter.updateList(posts);
            }
            swipeRefreshLayout.setRefreshing(false);
        });
        viewModel.getCanCreateInvite().observe(this, this::setHasOptionsMenu);

        viewModel.refresh();
    }
}
