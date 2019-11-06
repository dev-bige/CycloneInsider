package edu.cs309.cycloneinsider.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import com.google.common.base.Strings;
import com.jakewharton.rxbinding3.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.models.InsiderUserModel;
import edu.cs309.cycloneinsider.di.ViewModelFactory;
import edu.cs309.cycloneinsider.viewmodels.InviteViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class InviteActivity extends InsiderActivity {

    @Inject
    ViewModelFactory viewModelFactory;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private InviteViewModel viewModel;
    private EditText usernameEditText;
    private TextView usernameTextView, fullNameTextView, errorText;
    private ProgressBar progressBar;
    private Button inviteButton;
    private View userDetails;
    private String inviteUserUUID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        progressBar = findViewById(R.id.invite_progress_bar);


        viewModel = ViewModelProviders.of(this, viewModelFactory).get(InviteViewModel.class);
        usernameEditText = findViewById(R.id.username_edit_text);
        usernameTextView = findViewById(R.id.invite_username);
        fullNameTextView = findViewById(R.id.invite_full_name);
        errorText = findViewById(R.id.error_text);
        inviteButton = findViewById(R.id.invite_button);
        userDetails = findViewById(R.id.user_details);

        inviteButton.setEnabled(false);

        compositeDisposable.add(RxTextView.textChanges(usernameEditText)
                //Only accept where we have 500ms at least between key presses
                .debounce(250, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .filter(charSequence -> !Strings.isNullOrEmpty(charSequence.toString()))
                .subscribe(text -> {
                    inviteUserUUID = null;
                    String error = viewModel.findUser(text.toString());
                    inviteButton.setEnabled(false);
                    userDetails.setVisibility(View.GONE);
                    if (!Strings.isNullOrEmpty(error)) {
                        errorText.setText(error);
                        errorText.setVisibility(View.VISIBLE);
                        return;
                    }


                    errorText.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                }));

        viewModel.getInviteResponse().observe(this, roomMembershipModelResponse -> {
            if(roomMembershipModelResponse.isSuccessful()) {
                finish();
            }
        });

        viewModel.getFindUserResponse().observe(this, insiderUserModelResponse -> {
            progressBar.setVisibility(View.GONE);
            if (insiderUserModelResponse.isSuccessful()) {
                inviteUserUUID = insiderUserModelResponse.body().getUuid();
                userDetails.setVisibility(View.VISIBLE);
                inviteButton.setEnabled(true);
                InsiderUserModel user = insiderUserModelResponse.body();
                usernameTextView.setText(user.getUsername());
                fullNameTextView.setText(user.getFullName());
            }
        });

        inviteButton.setOnClickListener(view -> viewModel.invite(getIntent().getStringExtra("ROOM_UUID"), inviteUserUUID));
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
