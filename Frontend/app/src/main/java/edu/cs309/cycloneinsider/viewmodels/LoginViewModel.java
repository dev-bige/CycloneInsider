package edu.cs309.cycloneinsider.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.models.LoginRequestModel;
import edu.cs309.cycloneinsider.viewmodels.responsemodels.LoginResponseModel;

public class LoginViewModel extends ViewModel {
    private CycloneInsiderService cycloneInsiderService;
    private MutableLiveData<LoginResponseModel> loginResponse = new MutableLiveData<>();

    @Inject
    public LoginViewModel(CycloneInsiderService cycloneInsiderService) {
        this.cycloneInsiderService = cycloneInsiderService;
    }

    public void login(String username, String password) {
        if (username.length() == 0 && password.length() == 0) { //Net ID and Password must be entered
            loginResponse.setValue(LoginResponseModel.error(R.string.login_no_id_password));
            return;
        } else if (username.length() == 0) { //Net ID must be entered
            loginResponse.setValue(LoginResponseModel.error(R.string.login_no_id));
            return;
        } else if (password.length() == 0) { //Password must be entered
            loginResponse.setValue(LoginResponseModel.error(R.string.login_no_password));
            return;
        }
        cycloneInsiderService.login(new LoginRequestModel(username, password)).map(LoginResponseModel::success).subscribe(loginResponse::postValue);
    }

    public LiveData<LoginResponseModel> getLoginResponse() {
        return loginResponse;
    }
}
