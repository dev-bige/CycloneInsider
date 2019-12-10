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

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.activities.CreatePostActivity;
import edu.cs309.cycloneinsider.activities.InviteActivity;
import edu.cs309.cycloneinsider.activities.MainActivity;
import edu.cs309.cycloneinsider.activities.PostDetailActivity;
import edu.cs309.cycloneinsider.api.UserStateService;
import edu.cs309.cycloneinsider.api.models.PostModel;
import edu.cs309.cycloneinsider.di.ViewModelFactory;
import edu.cs309.cycloneinsider.fragments.adapters.PostListRecyclerViewAdapter;
import edu.cs309.cycloneinsider.viewmodels.PostListViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class PostListFragment extends Fragment {
    public static final String ROOM_UUID = "ROOM_UUID";
    @Inject
    ViewModelFactory viewModelFactory;
    @Inject
    PostListRecyclerViewAdapter mAdapter;
    @Inject
    UserStateService userStateService;
    private String roomUUID;
    private LinearLayoutManager layoutManager;
    private Disposable postClicks;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PostListViewModel viewModel;
    private Boolean canCreateInvite;

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
    public void onResume() {
        super.onResume();
        this.viewModel.refresh();
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
        if (canCreateInvite) {
            inflater.inflate(R.menu.menu_post_list, menu);
        }
        if(roomUUID != null) {
            inflater.inflate(R.menu.post_list_menu, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_post_list_invite) {
            Intent intent = new Intent(getActivity(), InviteActivity.class);
            intent.putExtra("ROOM_UUID", roomUUID);
            this.getActivity().startActivity(intent);
        }

        if(item.getItemId() == R.id.post_list_menu_users) {
            Fragment fragment = new UsersFragment();
            Bundle bundle = new Bundle();
            bundle.putString("ROOM_ID", roomUUID);
            fragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim).replace(R.id.container, fragment).commit();
        }

        if(item.getItemId() == R.id.menu_post_list_delete_room) {
            new MaterialAlertDialogBuilder(getContext())
                    .setTitle("Are you sure you want to delete this room?")
                    .setMessage("It's contents will be deleted and cannot be recovered")
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        this.viewModel.deleteRoom().observeOn(AndroidSchedulers.mainThread()).subscribe(response -> {
                            this.userStateService.refreshMemberships(() -> {
                                ((MainActivity) getActivity()).selectDrawerItem(R.id.nav_front_page);
                            });
                        });
                    })
                    .setNegativeButton("No", (dialogInterface, i) -> {})
                    .create().show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PostListViewModel.class);
        viewModel.setRoomUUID(roomUUID);
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(viewModel::refresh);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

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
        viewModel.getCanCreateInvite().observe(this, canCreateInvite -> {
            getActivity().invalidateOptionsMenu();
            this.canCreateInvite = canCreateInvite;
        });

        viewModel.refresh();
    }
}
