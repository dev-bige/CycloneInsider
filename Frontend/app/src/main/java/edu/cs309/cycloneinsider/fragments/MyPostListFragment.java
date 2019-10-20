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
import androidx.recyclerview.widget.DividerItemDecoration;
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
    private Disposable myPostSub, myPostClicks;
    private LinearLayoutManager layoutManager;
    private PostListRecyclerViewAdapter mAdapter;
    private TextView noPost;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_list, container, false);
    }

    @Override
    public void onDestroy() {
        if (myPostSub != null && !myPostSub.isDisposed()) {
            myPostSub.dispose();
        }
        if (myPostClicks != null && !myPostClicks.isDisposed()) {
            myPostClicks.dispose();
        }
        super.onDestroy();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        mAdapter = new PostListRecyclerViewAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        Observable<Response<List<PostModel>>> myPostListObservable = null;

        myPostListObservable = ((InsiderActivity) getActivity())
                .getInsiderApplication()
                .getApiService()
                .getMyPosts();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);


        myPostSub = myPostListObservable
                .filter(Response::isSuccessful)
                .map(Response::body)
                .concatMap(Observable::fromIterable)
                .toList()
                .map(Response::success)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(myPostModelResponse -> {
                    if (myPostModelResponse.isSuccessful()) {
                        List<PostModel> myPostModelList = myPostModelResponse.body();
                        mAdapter.updateList(myPostModelList);
                    }
                    else {
                        noPost.setText("No Posts to show!");
                    }
                });

        myPostClicks = mAdapter.getItemClicks().subscribe(item -> {
            Intent intent = new Intent(getActivity(), PostDetailActivity.class);
            intent.putExtra("POST_UUID", item.getUuid());
            startActivity(intent);
        });
    }
}
