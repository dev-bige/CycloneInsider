package edu.cs309.cycloneinsider.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.lifecycle.ViewModelProviders;

import javax.inject.Inject;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.models.PostCreateRequestModel;
import edu.cs309.cycloneinsider.di.ViewModelFactory;
import edu.cs309.cycloneinsider.viewmodels.CreatePostViewModel;

public class EditPostActivity extends InsiderActivity {
    @Inject
    private CreatePostViewModel createPostViewModel;
    ViewModelFactory viewModelFactory;

    public void PostThread(View view) {
        PostCreateRequestModel postCreateRequestModel = new PostCreateRequestModel();

        Intent intentResponse = getIntent();

        Bundle extras = intentResponse.getExtras();

        if (extras != null) {
            postCreateRequestModel.title = extras.getString("title");
            postCreateRequestModel.content = extras.getString("content");
            postCreateRequestModel.tags = extras.getStringArrayList("tags");
        }

        this.createPostViewModel.createPost(postCreateRequestModel, getIntent().getStringExtra("ROOM_UUID"));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createPostViewModel = ViewModelProviders.of(this, viewModelFactory).get(CreatePostViewModel.class);

        setContentView(R.layout.activity_create_post);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccentDark));

        createPostViewModel.getCreatePostModelResponse().observe(this, postModelResponse -> {
            if (postModelResponse.isSuccessful()) {
                //Handle 200 response
                Intent intent = new Intent(this, PostDetailActivity.class);
                intent.putExtra("POST_UUID", postModelResponse.body().getUuid());
                startActivity(intent);
                finish();
            }
        });


    }
}
