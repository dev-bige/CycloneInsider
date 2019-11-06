package edu.cs309.cycloneinsider.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.models.LoginRequestModel;
import edu.cs309.cycloneinsider.viewmodels.responsemodels.LoginResponseModel;

/**
 * View model that is being injected into the Login Activity Class
 */
public class LoginViewModel extends ViewModel {
    private CycloneInsiderService cycloneInsiderService;
    private MutableLiveData<LoginResponseModel> loginResponse = new MutableLiveData<>();

    @Inject
    public LoginViewModel(CycloneInsiderService cycloneInsiderService) {
        this.cycloneInsiderService = cycloneInsiderService;
    }

    /**
     * Logic that checks to be sure that the user trying to login is entering both a password and username
     * If the response is successful it will make a login API call
     *
     * @param loginRequestModel a object that is created based on the user input in the respected
     *                          text fields of the login activity
     */
    public void login(LoginRequestModel loginRequestModel) {
        if (loginRequestModel.username.length() == 0 && loginRequestModel.password.length() == 0) { //Net ID and Password must be entered
            loginResponse.setValue(LoginResponseModel.error(R.string.login_no_id_password));
            return;
        } else if (loginRequestModel.username.length() == 0) { //Net ID must be entered
            loginResponse.setValue(LoginResponseModel.error(R.string.login_no_id));
            return;
        } else if (loginRequestModel.password.length() == 0) { //Password must be entered
            loginResponse.setValue(LoginResponseModel.error(R.string.login_no_password));
            return;
        }
        cycloneInsiderService.login(loginRequestModel).map(LoginResponseModel::success).subscribe(loginResponse::postValue);
    }

    public LiveData<LoginResponseModel> getLoginResponse() {
        return loginResponse;
    }
}
