package edu.cs309.cycloneinsider.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.activities.adapters.CommentsListAdapter;
import edu.cs309.cycloneinsider.api.models.CommentModel;
import edu.cs309.cycloneinsider.api.models.CreateCommentRequestModel;
import edu.cs309.cycloneinsider.api.models.InsiderUserModel;
import edu.cs309.cycloneinsider.api.models.PostModel;
import edu.cs309.cycloneinsider.di.ViewModelFactory;
import edu.cs309.cycloneinsider.viewmodels.PostDetailViewModel;

public class PostDetailActivity extends InsiderActivity implements View.OnClickListener {
    private static final String TAG = "PostDetailActivity";
    @Inject
    ViewModelFactory viewModelFactory;
    @Inject
    CommentsListAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    private InsiderUserModel user;
    private TextView content, username;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PostDetailViewModel viewModel;
    private PostModel postResponse;
    private boolean canEditOrDelete;

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
                        viewModel.createComment(new CreateCommentRequestModel(comment));
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    })
                    .create();

            alertDialog.show();
        }
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PostDetailViewModel.class);
        super.onCreate(savedInstanceState);

        viewModel.setPostUUID(getIntent().getStringExtra("POST_UUID"));

        mAdapter.getOnEditCommentClicked().subscribe(commentModel -> {
            AlertDialog alertDialog = new MaterialAlertDialogBuilder(this)
                    .setTitle("Comment")
                    .setView(R.layout.dialog_comment_post)
                    .setPositiveButton("Comment", (dialogInterface, i) -> {
                        String comment = ((EditText) ((AlertDialog) dialogInterface).findViewById(R.id.comment_edit_text)).getText().toString();
                        commentModel.setComment(comment);
                        viewModel.updateComment(commentModel);
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    })
                    .create();
            alertDialog.show();
            ((EditText) alertDialog.findViewById(R.id.comment_edit_text)).setText(commentModel.getComment());
        });

        setContentView(R.layout.activity_post_detail);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(viewModel::refresh);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.new_comment_button).setOnClickListener(this);

        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        content = findViewById(R.id.post_detail_content);
        username = findViewById(R.id.post_detail_username);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        viewModel.getCommentsResponse().observe(this, commentsResponse -> {
            if (commentsResponse.isSuccessful()) {
                List<CommentModel> comments = commentsResponse.body();
                mAdapter.updateList(comments);
            }
            swipeRefreshLayout.setRefreshing(false);
        });
        viewModel.getPostDetailResponse().observe(this, postModelResponse -> {
            if (postModelResponse.isSuccessful()) {
                PostModel post = postModelResponse.body();
                collapsingToolbarLayout.setTitle(post.getTitle());
                content.setText(post.getContent());
                username.setText(post.getUser().username);
            }
            swipeRefreshLayout.setRefreshing(false);
        });
        viewModel.getCreateCommentsResponse().observe(this, voidResponse -> {
            if (voidResponse.isSuccessful()) {
                viewModel.refresh();
            }
        });

        viewModel.getCanEditorDelete().observe(this, postResponse -> {
            if (postResponse) {
                this.canEditOrDelete = postResponse;
                this.invalidateOptionsMenu();
            }
        });

        viewModel.hasEditOrDelete();
        viewModel.refresh();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(this.canEditOrDelete || viewModel.isUserAdmin()) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_post_options, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        if (item.getItemId() == R.id.menu_post_edit) {
            Intent intent = new Intent(this, EditPostActivity.class);
            intent.putExtra("POST_UUID", getIntent().getStringExtra("POST_UUID"));
            intent.putExtra("ROOM_UUID", getIntent().getStringExtra("ROOM_UUID"));
            startActivity(intent);
        }

        else if (item.getItemId() == R.id.menu_post_delete) {
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Delete this post?")

                    .setPositiveButton("Delete", (dialogInterface, i) -> {
                        if (getIntent().getStringExtra("ROOM_UUID") != null) {
                            // TODO open respected room
                        }
                        else {
                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        viewModel.deletePost(getIntent().getStringExtra("POST_UUID"));
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {

                    })
                    .create()
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }

}
