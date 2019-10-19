package edu.cs309.cycloneinsider.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.activities.adapters.CommentsListAdapter;
import edu.cs309.cycloneinsider.api.models.CommentModel;
import edu.cs309.cycloneinsider.api.models.CreateCommentRequestModel;
import edu.cs309.cycloneinsider.api.models.PostModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class PostDetailActivity extends InsiderActivity implements View.OnClickListener {
    private static final String TAG = "PostDetailActivity";
    private LinearLayoutManager layoutManager;
    private CompositeDisposable disposables = new CompositeDisposable();
    private CommentsListAdapter mAdapter;
    private TextView content, username;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Disposable updateListDisposable;

    @SuppressLint("CheckResult")
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.new_comment_button) {
            AlertDialog alertDialog = null;

            alertDialog = new MaterialAlertDialogBuilder(this)
                    .setTitle("Comment")
                    .setView(R.layout.dialog_comment_post)
                    .setPositiveButton("Comment", (dialogInterface, i) -> {
                        String comment = ((EditText) ((AlertDialog) dialogInterface).findViewById(R.id.comment_edit_text)).getText().toString();
                        getInsiderApplication().getApiService().createComment(getIntent().getStringExtra("POST_UUID"), new CreateCommentRequestModel(comment))
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(commentModelResponse -> {
                                    if (commentModelResponse.isSuccessful()) {
                                        updateList();
                                    }
                                }, error -> Log.e(TAG, "onClick: ", error));
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    })
                    .create();

            alertDialog.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.new_comment_button).setOnClickListener(this);

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
        updateList();
    }

    @Override
    protected void onDestroy() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateList() {
        if (this.updateListDisposable != null && !this.updateListDisposable.isDisposed()) {
            this.updateListDisposable.dispose();
            this.updateListDisposable = null;
        }

        this.updateListDisposable = getInsiderApplication()
                .getApiService()
                .getPostComments(getIntent().getStringExtra("POST_UUID"))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(commentsResponse -> {
                    if (commentsResponse.isSuccessful()) {
                        List<CommentModel> comments = commentsResponse.body();
                        mAdapter.updateList(comments);
                    }
                });
    }
}
