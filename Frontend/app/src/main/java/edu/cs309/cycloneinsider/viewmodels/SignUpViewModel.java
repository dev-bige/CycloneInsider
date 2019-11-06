package edu.cs309.cycloneinsider.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.models.SignUpRequestModel;
import edu.cs309.cycloneinsider.viewmodels.responsemodels.SignUpResponseModel;

/**
 * A class that is used to for dependency injection for the sign up activity class
 * Methods for logic checking if a name or password is valid
 * If the sign up is successful makes a POST request to the server for the new user
 **/
public class SignUpViewModel extends ViewModel {
    private CycloneInsiderService service;
    private MutableLiveData<SignUpResponseModel> signUpResponse = new MutableLiveData<>();

    @Inject
    public SignUpViewModel(CycloneInsiderService service) {
        this.service = service;
    }

    /**
     * A function called in the sign up activity that does the logic checking for the password and name
     * the user entered.
     *
     * @param signUpRequestResponse a object of type SignUpRequestModel who checks sign up will be valid
     */
    public void signUp(SignUpRequestModel signUpRequestResponse) {
        if (!checkName(signUpRequestResponse.firstName)) {
            signUpResponse.setValue(SignUpResponseModel.error(R.string.error_sign_up_only_one_name));
            return;
        } else if (signUpRequestResponse.firstName.length() == 0) {
            signUpResponse.setValue(SignUpResponseModel.error(R.string.error_name_length));
            return;
        } else if (!checkName(signUpRequestResponse.lastName)) {
            signUpResponse.setValue(SignUpResponseModel.error(R.string.error_sign_up_only_one_name));
            return;
        } else if (signUpRequestResponse.lastName.length() == 0) {
            signUpResponse.setValue(SignUpResponseModel.error(R.string.error_name_length));
            return;
        } else if (!validPassword(signUpRequestResponse.password)) {
            signUpResponse.setValue(SignUpResponseModel.error(R.string.error_invalid_password));
            return;
        } else if (signUpRequestResponse.password.length() == 0) {
            signUpResponse.setValue(SignUpResponseModel.error(R.string.error_password_length));
            return;
        }
        service.signUp(signUpRequestResponse).map(SignUpResponseModel::success).subscribe(signUpResponse::postValue);
    }

    public LiveData<SignUpResponseModel> signUp() {
        return signUpResponse;
    }

    /**
     * @param userPassword a password that the user enters in the password field box
     *                     Password Criteria
     *                     -must contain uppercase
     *                     -must contain a number
     *                     -must be equal to or more than 8 characters
     * @returns true if valid password
     **/
    private boolean validPassword(String userPassword) {
        boolean hasUppercase = !userPassword.equals(userPassword.toLowerCase());
        boolean correctLength = userPassword.length() >= 8;
        boolean containsNumber = false;

        for (int i = 0; i < userPassword.length(); i++) {
            containsNumber = Character.isDigit(userPassword.charAt(i));
        }

        return hasUppercase && correctLength && containsNumber;
    }

    /**
     * @param userEntry a name that use enters in to the text field
     * @return true if name contains no spaces hence only one name is entered
     */
    private boolean checkName(String userEntry) {
        for (int i = 0; i < userEntry.length(); i++) {
            if (userEntry.charAt(i) == ' ') {
                return false;
            }
        }
        return true;
    }

}
