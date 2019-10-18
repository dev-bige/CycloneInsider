package edu.cs309.cycloneinsider.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.List;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.activities.adapters.CommentsListAdapter;
import edu.cs309.cycloneinsider.api.models.CommentModel;
import edu.cs309.cycloneinsider.api.models.PostModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class PostDetailActivity extends InsiderActivity {
    private LinearLayoutManager layoutManager;
    private CompositeDisposable disposables = new CompositeDisposable();
    private CommentsListAdapter mAdapter;
    private TextView content, username;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        content = findViewById(R.id.post_detail_content);
        username = findViewById(R.id.post_detail_username);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        disposables.add(getInsiderApplication()
                .getApiService()
                .getPost(getIntent().getStringExtra("POST_UUID"))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(postModelResponse -> {
                    if (postModelResponse.isSuccessful()) {
                        PostModel post = postModelResponse.body();
                        collapsingToolbarLayout.setTitle(post.getTitle());
                        content.setText(post.getContent());
                        username.setText(post.getUser().username);
                    }
                }));

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new CommentsListAdapter();
        recyclerView.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        disposables.add(getInsiderApplication()
                .getApiService()
                .getPostComments(getIntent().getStringExtra("POST_UUID"))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(commentsResponse -> {
                    if (commentsResponse.isSuccessful()) {
                        List<CommentModel> comments = commentsResponse.body();
                        mAdapter.updateList(comments);
                    }
                }));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
        super.onDestroy();
    }
}
