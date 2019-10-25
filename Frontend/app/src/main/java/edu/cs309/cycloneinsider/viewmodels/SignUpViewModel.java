package edu.cs309.cycloneinsider.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.models.SignUpRequestModel;
import edu.cs309.cycloneinsider.viewmodels.responsemodels.SignUpResponseModel;

public class SignUpViewModel extends ViewModel {
    private CycloneInsiderService service;
    private MutableLiveData<SignUpResponseModel> signUpResponse = new MutableLiveData<>();

    @Inject
    public SignUpViewModel(CycloneInsiderService service) {
        this.service = service;
    }

    public void signUp(SignUpRequestModel signUpRequestResponse) {
        if (!checkName(signUpRequestResponse.firstName)) {
            signUpResponse.setValue(SignUpResponseModel.error(R.string.error_sign_up_only_one_name));
            return;
        }
        else if (signUpRequestResponse.firstName.length() == 0) {
            signUpResponse.setValue(SignUpResponseModel.error(R.string.error_name_length));
            return;
        }
        else if (!checkName(signUpRequestResponse.lastName)) {
            signUpResponse.setValue(SignUpResponseModel.error(R.string.error_sign_up_only_one_name));
            return;
        }
        else if (signUpRequestResponse.lastName.length() == 0) {
            signUpResponse.setValue(SignUpResponseModel.error(R.string.error_name_length));
            return;
        }
        else if (!validPassword(signUpRequestResponse.password)) {
            signUpResponse.setValue(SignUpResponseModel.error(R.string.error_invalid_password));
            return;
        }
        else if (signUpRequestResponse.password.length() == 0) {
            signUpResponse.setValue(SignUpResponseModel.error(R.string.error_password_length));
            return;
        }
        service.signUp(signUpRequestResponse).map(SignUpResponseModel::success).subscribe(signUpResponse::postValue);
    }

    public LiveData<SignUpResponseModel> signUp() {
        return signUpResponse;
    }

    /**
     * Password
     * -must contain uppercase
     * -must contain a number
     * -must be equal to or more than 8 characters
     * -returns true if valid password
     **/
    public boolean validPassword(String userPassword) {
        boolean hasUppercase = !userPassword.equals(userPassword.toLowerCase());
        boolean correctLength = userPassword.length() >= 8;
        boolean containsNumber = false;

        for (int i = 0; i < userPassword.length(); i++) {
            containsNumber = Character.isDigit(userPassword.charAt(i));
        }

        return hasUppercase && correctLength && containsNumber;
    }

    /**
     * checks to make sure only one word is entered but checking for a space
     *
     * @param userEntry
     * @return true if name is valid
     */
    public boolean checkName(String userEntry) {
        for (int i = 0; i < userEntry.length(); i++) {
            if (userEntry.charAt(i) == ' ') {
                return false;
            }
        }
        return true;
    }

}
