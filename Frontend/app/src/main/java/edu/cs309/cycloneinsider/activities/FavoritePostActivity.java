package edu.cs309.cycloneinsider.activities;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.models.PostModel;
import edu.cs309.cycloneinsider.fragments.adapters.PostListRecyclerViewAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class FavoritePostActivity extends InsiderActivity  {
    LinearLayoutManager layoutManager;
    PostListRecyclerViewAdapter mAdapter;
    TextView post, room;
    private List<PostModel> listFavPostModel = new ArrayList<>();
    private CompositeDisposable disposables = new CompositeDisposable();
    private Disposable clicks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_post);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        post = findViewById(R.id.list_item_post_title);
        room = findViewById(R.id.list_item_room_title);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        disposables.add(getInsiderApplication()
                .getApiService()
                .getFavoritePost()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(favPostModelResponse -> {
                    if (favPostModelResponse.isSuccessful()) {
                        listFavPostModel = favPostModelResponse.body();
                        mAdapter.updateList(listFavPostModel);
                    }
                }
        ));

        clicks = mAdapter.getItemClicks().subscribe(item -> {
            Intent intent = new Intent(this, PostDetailActivity.class);
            intent.putExtra("POST_UUID", item.getUuid());
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
        if (clicks != null && !clicks.isDisposed()) {
            clicks.dispose();
        }
        super.onDestroy();
    }
}
