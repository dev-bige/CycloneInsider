package edu.cs309.cycloneinsider.activities;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.models.InsiderUserModel;
import edu.cs309.cycloneinsider.api.models.PostModel;
import edu.cs309.cycloneinsider.fragments.MyPostListFragment;
import edu.cs309.cycloneinsider.fragments.adapters.PostListRecyclerViewAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MyPostActivity extends InsiderActivity {
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);

        String[] UUID = new String[1];

        disposable =
                (getInsiderApplication()
                .getApiService()
                .currentUser()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(insiderUserModelResponse -> {
                    if (insiderUserModelResponse.isSuccessful()) {
                        UUID[0] = insiderUserModelResponse.body().getUuid();
                    }
                }));

        Fragment fragment = MyPostListFragment.newInstance(UUID[0]);
        fragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();


    }
}
