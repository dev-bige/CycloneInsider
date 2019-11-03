package edu.cs309.cycloneinsider.viewmodels;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.models.InsiderUserModel;
import retrofit2.Response;

public class InviteViewModel extends ViewModel {
    private CycloneInsiderService cycloneInsiderService;
    private MutableLiveData<Response<InsiderUserModel>> findUserResponse = new MutableLiveData<>();

    @Inject
    public InviteViewModel(CycloneInsiderService cycloneInsiderService) {
        this.cycloneInsiderService = cycloneInsiderService;
    }

    @SuppressLint("CheckResult")
    public void findUser(String username) {
        this.cycloneInsiderService.findUser(username).subscribe(findUserResponse::postValue);
    }

    public void invite(String userUuid) {

    }

    public LiveData<Response<InsiderUserModel>> getFindUserResponse() {
        return findUserResponse;
    }
}
