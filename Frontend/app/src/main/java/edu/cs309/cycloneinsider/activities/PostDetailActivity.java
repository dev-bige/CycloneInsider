package edu.cs309.cycloneinsider.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.models.PostModel;
import edu.cs309.cycloneinsider.fragments.adapters.RoomListRecyclerViewAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class PostDetailActivity extends InsiderActivity {

    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        TextView title = findViewById(R.id.post_detail_title);
        TextView content = findViewById(R.id.post_detail_content);
        TextView username = findViewById(R.id.post_detail_username);

        getInsiderApplication().getApiService().getPost(getIntent().getStringExtra("POST_UUID")).observeOn(AndroidSchedulers.mainThread()).subscribe(postModelResponse -> {
            if(postModelResponse.isSuccessful()) {
                PostModel post = postModelResponse.body();
                title.setText(post.getTitle());
                content.setText(post.getContent());
                username.setText(post.getUser().username);
            }
        });

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }
}
