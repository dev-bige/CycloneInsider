package edu.cs309.cycloneinsider.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.activities.InsiderActivity;
import edu.cs309.cycloneinsider.activities.PostDetailActivity;
import edu.cs309.cycloneinsider.api.models.PostModel;
import edu.cs309.cycloneinsider.fragments.adapters.PostListRecyclerViewAdapter;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class MyPostListFragment extends Fragment {
    public static final String USER_UUID = "USER_UUID";
    private String userUUID;
    private Disposable disposables;
    private LinearLayoutManager layoutManager;
    private PostListRecyclerViewAdapter mAdapter;
    private Disposable myPostClicks;
    private TextView noPost;

    public static MyPostListFragment newInstance(String roomUUID) {
        MyPostListFragment myPostListFragment = new MyPostListFragment();
        Bundle args = new Bundle();
        args.putString(USER_UUID, roomUUID);
        myPostListFragment.setArguments(args);
        return myPostListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userUUID = getArguments().getString(USER_UUID);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_list, container, false);
    }

    @Override
    public void onDestroy() {
        if (disposables != null && !disposables.isDisposed()) {
            disposables.dispose();
        }
        if (myPostClicks != null && !myPostClicks.isDisposed()) {
            myPostClicks.dispose();
        }
        super.onDestroy();
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new PostListRecyclerViewAdapter();
        recyclerView.setAdapter(mAdapter);

        Observable<Response<List<PostModel>>> myPostList = null;

        myPostList = ((InsiderActivity) getActivity())
                .getInsiderApplication()
                .getApiService()
                .getMyPost(getActivity().getIntent().getStringExtra("USER_UUID"))
                .observeOn(AndroidSchedulers.mainThread());

        disposables = myPostList.subscribe(myPostModelResponse -> {
            if (myPostModelResponse.isSuccessful()) {
                List<PostModel> myPostModel = myPostModelResponse.body();
                mAdapter.updateList(myPostModel);
            }
            else {
                noPost.setText("No posts to show!");
            }
        });

        myPostClicks = mAdapter.getItemClicks().subscribe(item -> {
            Intent intent = new Intent(getActivity(), PostDetailActivity.class);
            intent.putExtra("POST_UUID", item.getUuid());
            startActivity(intent);
        });
    }
}
