package edu.cs309.cycloneinsider.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

import javax.inject.Inject;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.models.PostCreateRequestModel;
import edu.cs309.cycloneinsider.di.ViewModelFactory;
import edu.cs309.cycloneinsider.viewmodels.CreatePostViewModel;
import io.reactivex.disposables.Disposable;

public class CreatePostActivity extends InsiderActivity {
    @Inject
    ViewModelFactory viewModelFactory;
    private Disposable subscribe;
    private CreatePostViewModel createPostViewModel;

    public void createPost(View view) {
        EditText postTitle = findViewById(R.id.post_title); //finds the post title by ID
        EditText initialText = findViewById(R.id.poster_comment); //finds the initial comment from poster by ID
        String title = postTitle.getText().toString(); //title of thread as a string
        TextView hiddenText = findViewById(R.id.hidden_thread_box); //gets the hidden text box by ID
        String hidden = hiddenText.getText().toString(); //gets the hidden text box as a string
        hiddenText.setVisibility(View.INVISIBLE); //initially has hidden text box invisible
        String initialComments = initialText.getText().toString(); //posters initial comments as a string
        String[] titleWords = title.split(" ", 0); //splits up all of the words in the title by spaces
        String[] initialCommentsWords = initialComments.split(" ", 0); //splits up all of the initial comments by spaces

        if (title.length() == 0 && initialComments.length() == 0) { //poster must enter a title and have an initial comment
            hiddenText.setText("Need to enter a title and place an initial comment");
            hiddenText.setVisibility(View.VISIBLE);
            return;
        }

        if (title.length() == 0) { //poster must enter a title
            hiddenText.setText("Need to enter a title");
            hiddenText.setVisibility(View.VISIBLE);
            return;
        }

        if (initialComments.length() == 0) { //poster needs to have an initial comment
            hiddenText.setText("Need to enter an initial comment");
            hiddenText.setVisibility(View.VISIBLE);
            return;
        }

        int counterTitle = 0;
        int counterComment = 0;

        for (int i = 0; i < title.length(); i++) { //loop finds out if the title is only made up of spaces (this is not allowed)
            if (title.charAt(i) != ' ') {
                break;
            }
            counterTitle++;
        }

        if (counterTitle == title.length()) { //poster needs to enter at least one character
            hiddenText.setText("Need to enter at least one character for title");
            hiddenText.setVisibility(View.VISIBLE);
            return;
        }

        for (int i = 0; i < initialComments.length(); i++) { //loop finds out if the initial comments section is only filled with spaces
            if (initialComments.charAt(i) != ' ') {
                break;
            }
            counterComment++;

        }

        if (counterComment == initialComments.length()) { //poster needs to enter at least
            hiddenText.setText("Need to enter at least one character for the initial comment section");
            hiddenText.setVisibility(View.VISIBLE);
            return;
        }


        PostCreateRequestModel postCreateRequestModel = new PostCreateRequestModel();
        postCreateRequestModel.content = initialComments;
        postCreateRequestModel.title = title;
        postCreateRequestModel.tags = new ArrayList<>();

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

    @Override
    protected void onDestroy() {
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
        }
        super.onDestroy();
    }

}