package edu.cs309.cycloneinsider.fragments;

import android.content.Intent;
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
import edu.cs309.cycloneinsider.activities.CreatePostActivity;
import edu.cs309.cycloneinsider.activities.InsiderActivity;
import edu.cs309.cycloneinsider.activities.PostDetailActivity;
import edu.cs309.cycloneinsider.api.models.PostModel;
import edu.cs309.cycloneinsider.fragments.adapters.PostListRecyclerViewAdapter;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class PostListFragment extends Fragment {
    public static final String ROOM_UUID = "ROOM_UUID";
    private String roomUUID;
    private Disposable postSub;
    private LinearLayoutManager layoutManager;
    private PostListRecyclerViewAdapter mAdapter;
    private Disposable postClicks;

    public static PostListFragment newInstance(String roomUuid) {
        PostListFragment postListFragment = new PostListFragment();
        Bundle args = new Bundle();
        args.putString(ROOM_UUID, roomUuid);
        postListFragment.setArguments(args);
        return postListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roomUUID = getArguments().getString(ROOM_UUID);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new PostListRecyclerViewAdapter();
        recyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);


        getView().findViewById(R.id.new_post_button).setOnClickListener(view1 -> {
            startActivity(new Intent(getActivity(), CreatePostActivity.class));
        });
        Observable<Response<List<PostModel>>> postListObservable = null;
        //If the room uuid is null then we should get the front page posts.
        if (roomUUID == null) {
            postListObservable = ((InsiderActivity) getActivity())
                    .getInsiderApplication()
                    .getApiService()
                    .getFrontPagePosts()
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            //TODO GET POSTS FOR ROOM UUID... for now, just have an empty steam so we don't crash.
            postListObservable = Observable.empty();
        }

        postSub = postListObservable.subscribe(postsResponse -> {
            if (postsResponse.isSuccessful()) {
                List<PostModel> posts = postsResponse.body();

                mAdapter.updateList(posts);
            }
        });

        postClicks = mAdapter.getItemClicks().subscribe(item -> {
            Intent intent = new Intent(getActivity(), PostDetailActivity.class);
            intent.putExtra("POST_UUID", item.getUuid());
            startActivity(intent);
        });
    }

    @Override
    public void onDestroy() {
        if (postSub != null && !postSub.isDisposed()) {
            postSub.dispose();
        }
        if (postClicks != null && !postClicks.isDisposed()) {
            postSub.dispose();
        }
        super.onDestroy();
    }
}
