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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.activities.InsiderActivity;
import edu.cs309.cycloneinsider.activities.PostDetailActivity;
import edu.cs309.cycloneinsider.api.models.FavoritePostModel;
import edu.cs309.cycloneinsider.api.models.PostModel;
import edu.cs309.cycloneinsider.fragments.adapters.PostListRecyclerViewAdapter;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class FavoritePostFragment extends Fragment {
    public static String USER_UUID = "USER_UUID";
    private String userUUID;
    private Disposable favPostSub, favPostClicks;
    private LinearLayoutManager layoutManager;
    private PostListRecyclerViewAdapter postListRecyclerViewAdapter;
    private TextView room, post;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_list, container, false);
    }

    @Override
    public void onDestroy() {
        if (favPostSub != null && !favPostSub.isDisposed()) {
            favPostSub.dispose();
        }
        if (favPostClicks != null && !favPostClicks.isDisposed()) {
            favPostClicks.dispose();
        }
        super.onDestroy();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        post = view.findViewById(R.id.list_item_post_title);
        room = view.findViewById(R.id.list_item_room_title);

        postListRecyclerViewAdapter = new PostListRecyclerViewAdapter();
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(postListRecyclerViewAdapter);

        Observable<Response<List<FavoritePostModel>>> favPostObservable = null;

        favPostObservable = ((InsiderActivity) getActivity())
                .getInsiderApplication()
                .getApiService()
                .getFavoritePost();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        favPostSub = favPostObservable
                .filter(Response::isSuccessful)
                .map(Response::body)
                .concatMap(Observable::fromIterable)
                .map(FavoritePostModel::getPost)
                .toList()
                .map(Response::success)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(favPostResponse -> {
                    if (favPostResponse.isSuccessful()) {
                        List<PostModel> favPostModelList = favPostResponse.body();
                        postListRecyclerViewAdapter.updateList(favPostModelList);
                    }
                });


        favPostClicks = postListRecyclerViewAdapter.getItemClicks().subscribe(item -> {
            Intent intent = new Intent(getActivity(), PostDetailActivity.class);
            intent.putExtra("POST_UUID", item.getUuid());
            startActivity(intent);
        });
    }
}
