package edu.cs309.cycloneinsider.activities;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.jakewharton.rxbinding3.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.di.ViewModelFactory;
import edu.cs309.cycloneinsider.viewmodels.InviteViewModel;
import io.reactivex.disposables.CompositeDisposable;

public class InviteActivity extends InsiderActivity {

    @Inject
    ViewModelFactory viewModelFactory;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private InviteViewModel viewModel;
    private EditText usernameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(InviteViewModel.class);

        usernameEditText = findViewById(R.id.username_edit_text);

        compositeDisposable.add(RxTextView.textChanges(usernameEditText)
                //Only accept where we have 500ms at least between key presses
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(text -> viewModel.findUser(text.toString())));
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
