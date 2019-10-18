package edu.cs309.cycloneinsider.activities;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.activities.adapters.FavoritePostAdapter;
import edu.cs309.cycloneinsider.api.models.FavPostModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class FavoritePostActivity extends InsiderActivity implements FavoritePostAdapter.OnPostListener {
    LinearLayoutManager layoutManager;
    FavoritePostAdapter mAdapter;
    TextView post, room;
    private List<FavPostModel> listFavPostModel = new ArrayList<>();
    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_post);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        post = findViewById(R.id.list_item_post_title);
        room = findViewById(R.id.list_item_room_title);

        disposables.add(getInsiderApplication()
                .getApiService()
                .getFavoritePost()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(favPostModelResponse -> {
                    if (favPostModelResponse.isSuccessful()) {
                        listFavPostModel = favPostModelResponse.body();
                    }
                }
        ));

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new FavoritePostAdapter(listFavPostModel, this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
        super.onDestroy();
    }

    @Override
    public void OnPostListener(int position) {
        FavPostModel favPost = listFavPostModel.get(position);
        Intent intent = new Intent(this, PostDetailActivity.class);
        intent.putExtra("Favorite Post", (Parcelable) favPost);
        startActivity(intent);
    }
}
