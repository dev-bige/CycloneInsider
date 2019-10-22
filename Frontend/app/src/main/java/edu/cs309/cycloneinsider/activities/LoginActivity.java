package edu.cs309.cycloneinsider.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.Session;
import edu.cs309.cycloneinsider.api.models.LoginRequestModel;
import edu.cs309.cycloneinsider.di.ViewModelFactory;
import edu.cs309.cycloneinsider.viewmodels.LoginViewModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class LoginActivity extends InsiderActivity {
    private static final String TAG = "LoginActivity";
    @Inject
    ViewModelFactory viewModelFactory;
    @Inject
    Session session;
    private Disposable loginSub;
    private LoginViewModel viewModel;
    private EditText netID, password;
    private TextView hiddenText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        netID = findViewById(R.id.net_id); //finds the user name in netID box by ID
        password = findViewById(R.id.password); //finds the password in password box by ID
        hiddenText = findViewById(R.id.hidden_text); //finds the hidden text box that communicates errors to user when entering wrong values
        hiddenText.setVisibility(View.GONE);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);
        findViewById(R.id.sign_up).setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));

        viewModel.getLoginResponse().observe(this, loginResponseModel -> {
            findViewById(R.id.progress_bar).setVisibility(View.GONE);
            if (loginResponseModel.isError()) {
                hiddenText.setVisibility(View.VISIBLE);
                hiddenText.setText(loginResponseModel.getStringError());
            } else if (session.isLoggedIn()) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (loginSub != null && !loginSub.isDisposed()) {
            loginSub.dispose();
        }
        super.onDestroy();
    }

    public void openMainPage(View view) {
        String netIDString = netID.getText().toString();
        String passwordString = password.getText().toString();
        findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        hiddenText.setVisibility(View.GONE);
        viewModel.login(netIDString, passwordString);
    }

    public Observable<Response<Void>> login(CycloneInsiderService service, LoginRequestModel loginRequestModel) {
        return service
                .login(loginRequestModel);
    }
}
