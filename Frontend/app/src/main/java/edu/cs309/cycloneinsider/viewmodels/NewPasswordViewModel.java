package edu.cs309.cycloneinsider.viewmodels;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.textfield.TextInputEditText;

import javax.inject.Inject;

import edu.cs309.cycloneinsider.R;
import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.models.NewPasswordRequestModel;
import edu.cs309.cycloneinsider.viewmodels.responsemodels.NewPasswordResponseModel;

public class NewPasswordViewModel extends ViewModel {
    private CycloneInsiderService cycloneInsiderService;
    private MutableLiveData<NewPasswordResponseModel> newpasswordResponse = new MutableLiveData<>();

    @Inject
    public NewPasswordViewModel(CycloneInsiderService cycloneInsiderService) {
        this.cycloneInsiderService = cycloneInsiderService;
    }

    public void changePassword(NewPasswordRequestModel newPasswordRequestModel) {



        if (newPasswordRequestModel.oldPassword.length() == 0 || newPasswordRequestModel.newPassword.length() == 0 || newPasswordRequestModel.newPassword2.length() == 0) {
            newpasswordResponse.setValue(NewPasswordResponseModel.error(R.string.password_all_boxes));
            return;

        }

        if (!newPasswordRequestModel.newPassword.equals(newPasswordRequestModel.newPassword2)) {
            newpasswordResponse.setValue(NewPasswordResponseModel.error(R.string.password_not_equal));
            return;

        }

        cycloneInsiderService.changePassword(newPasswordRequestModel).map(NewPasswordResponseModel::success).subscribe(newpasswordResponse::postValue);
        return;

    }

    public LiveData<NewPasswordResponseModel> getNewPasswordResponse() {
        return newpasswordResponse;
    }

}
