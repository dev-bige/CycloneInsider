package edu.cs309.cycloneinsider.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.models.PostModel;
import edu.cs309.cycloneinsider.fragments.FavoritePostFragment;
import edu.cs309.cycloneinsider.fragments.adapters.PostListRecyclerViewAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class FavoritePostActivity extends InsiderActivity {
    private Disposable subscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_post);

        final String[] user = null;

        // getting the current user to associate favorite posts with that user
        subscribe = getInsiderApplication()
                .getApiService()
                .currentUser()
                .subscribe(userResponse -> {
                    if (userResponse.isSuccessful()) {
                        user[0] = userResponse.body().getUuid();
                    }
                });

        // Make the fragment and pass the current user to the fragment to display the users favorite posts
        Fragment fragment = FavoritePostFragment.newInstance(user[0]);
        fragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
    }
}

