package edu.cs309.cycloneinsider.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import edu.cs309.cycloneinsider.activities.InsiderActivity;
import edu.cs309.cycloneinsider.activities.PostDetailActivity;
import edu.cs309.cycloneinsider.api.models.PostModel;
import edu.cs309.cycloneinsider.di.ViewModelFactory;
import edu.cs309.cycloneinsider.fragments.adapters.PostListRecyclerViewAdapter;
import edu.cs309.cycloneinsider.viewmodels.MyPostListViewModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class MyPostListFragment extends Fragment {
    private Disposable myPostClicks;
    private LinearLayoutManager layoutManager;
    private PostListRecyclerViewAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    ViewModelFactory viewModelFactory;
    private MyPostListViewModel myPostListViewModel;

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
    public void onDestroy() {
        if (myPostClicks != null && !myPostClicks.isDisposed()) {
            myPostClicks.dispose();
        }
        super.onDestroy();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        myPostListViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(MyPostListViewModel.class);
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(myPostListViewModel::refresh);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        mAdapter = new PostListRecyclerViewAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        myPostListViewModel.getMyPostResponse().observe(this, listResponse -> {
            if (listResponse.isSuccessful()) {
                List<PostModel> myPostModelList = listResponse.body();
                mAdapter.updateList(myPostModelList);
            }
            swipeRefreshLayout.setRefreshing(false);
        });


        myPostClicks = mAdapter.getItemClicks().subscribe(item -> {
            Intent intent = new Intent(getActivity(), PostDetailActivity.class);
            intent.putExtra("POST_UUID", item.getUuid());
            startActivity(intent);
        });
        myPostListViewModel.refresh();
    }

//    public void refresh() {
//        if (myPostSub != null && !myPostSub.isDisposed()) {
//            myPostSub.dispose();
//        }
//        Observable<Response<List<PostModel>>> myPostListObservable = ((InsiderActivity) getActivity())
//                .getInsiderApplication()
//                .getApiService()
//                .getMyPosts();
//
//        myPostSub = myPostListObservable
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(myPostModelResponse -> {
//                    if (myPostModelResponse.isSuccessful()) {
//                        List<PostModel> myPostModelList = myPostModelResponse.body();
//                        mAdapter.updateList(myPostModelList);
//                    } else {
//                        noPost.setText("No Posts to show!");
//                    }
//                    swipeRefreshLayout.setRefreshing(false);
//                });
//    }
}
