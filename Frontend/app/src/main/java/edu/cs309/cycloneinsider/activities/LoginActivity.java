package edu.cs309.cycloneinsider.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.models.LoginRequestModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class LoginActivity extends InsiderActivity {
    private static final String TAG = "LoginActivity";
    private Disposable loginSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);


        Button signUpButton = findViewById(R.id.sign_up);
        signUpButton.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));
    }

    @Override
    protected void onDestroy() {
        if (loginSub != null && !loginSub.isDisposed()) {
            loginSub.dispose();
        }
        super.onDestroy();
    }

    public void openMainPage(View view) {
        EditText netID = findViewById(R.id.net_id); //finds the user name in netID box by ID
        EditText password = findViewById(R.id.password); //finds the password in password box by ID
        TextView hiddenText = findViewById(R.id.hidden_text); //finds the hidden text box that communicates errors to user when entering wrong values
        hiddenText.setVisibility(View.GONE); //makes sure to have the text box INVISIBLE, unless a conditional statement triggers it to be VISIBLE
        String netIDString = netID.getText().toString(); //extracts the string of the netID
        String passwordString = password.getText().toString(); //extracts the string of the password
        String hiddenTextString = hiddenText.getText().toString(); //extracts the string value of hiddenText in order to set this value when users enter bad username/passwords

        if (netIDString.length() == 0 && passwordString.length() == 0) { //Net ID and Password must be entered
            hiddenText.setText(R.string.login_no_id_password);
            hiddenText.setVisibility(View.VISIBLE); //sets the error box to VISIBLE
            return;
        } else if (netIDString.length() == 0) { //Net ID must be entered
            hiddenText.setText(R.string.login_no_id);
            hiddenText.setVisibility(View.VISIBLE);
            return;
        } else if (passwordString.length() == 0) { //Password must be entered
            hiddenText.setText(R.string.login_no_password);
            hiddenText.setVisibility(View.VISIBLE);
            return;
        }
        findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);

        LoginRequestModel loginRequestModel = new LoginRequestModel();
        loginRequestModel.setPassword(passwordString);
        loginRequestModel.setUsername(netIDString);
        loginSub = login(getInsiderApplication().getApiService(), loginRequestModel)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(voidResponse -> {
                    //Successful login!
                    if (getInsiderApplication().getSession().isLoggedIn()) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    findViewById(R.id.progress_bar).setVisibility(View.GONE);
                }, error -> {
                    //Login error!
                    hiddenText.setText(R.string.login_invalid_login);
                    hiddenText.setVisibility(View.VISIBLE);

                    findViewById(R.id.progress_bar).setVisibility(View.GONE);
                });
    }

    public Observable<Response<Void>> login(CycloneInsiderService service, LoginRequestModel loginRequestModel) {
        return service
                .login(loginRequestModel);
    }
}
