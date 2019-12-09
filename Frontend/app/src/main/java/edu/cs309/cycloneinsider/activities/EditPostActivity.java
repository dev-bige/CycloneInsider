package edu.cs309.cycloneinsider.activities;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import javax.inject.Inject;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.models.PostModel;
import edu.cs309.cycloneinsider.di.ViewModelFactory;
import edu.cs309.cycloneinsider.viewmodels.EditPostViewModel;

public class EditPostActivity extends InsiderActivity {
    @Inject
    EditPostViewModel editPostViewModel;
    @Inject
    ViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editPostViewModel = ViewModelProviders.of(this, viewModelFactory).get(EditPostViewModel.class);

        setContentView(R.layout.activity_create_post);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccentDark));

        EditText title = findViewById(R.id.post_title);
        EditText content = findViewById(R.id.poster_comment);


        Bundle roomBundle = getIntent().getExtras();
        Bundle postCreateRequestModelBundle  = getIntent().getExtras();

        String post_uuid = postCreateRequestModelBundle.getString("POST_UUID");
        String room_uuid = roomBundle.getString("ROOM_UUID");

        editPostViewModel.getPost(post_uuid);

        editPostViewModel.getReturnPost().observe(this, postModelResponse -> {
            PostModel post = postModelResponse.body();
            title.setText(post.getTitle(), TextView.BufferType.EDITABLE);
            content.setText(post.getContent(), TextView.BufferType.EDITABLE);
        });



//        createPostViewModel.getCreatePostModelResponse().observe(this, postModelResponse -> {
//            if (postModelResponse.isSuccessful()) {
//                //Handle 200 response
//                Intent intent = new Intent(this, PostDetailActivity.class);
//                intent.putExtra("POST_UUID", postModelResponse.body().getUuid());
//                startActivity(intent);
//                finish();
//            }
//        });


    }
}
