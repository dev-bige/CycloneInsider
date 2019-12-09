package edu.cs309.cycloneinsider.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.models.InsiderUserModel;
import retrofit2.Response;

public class UserListViewModel extends ViewModel {
    CycloneInsiderService insiderService;

    MutableLiveData<Response<List<InsiderUserModel>>> reponseUsers = new MutableLiveData<>();
    private String roomId = null;

    @Inject
    public UserListViewModel(CycloneInsiderService insiderService) {
        this.insiderService = insiderService;
    }

    public LiveData<Response<List<InsiderUserModel>>> getReponseUsers() {
        return reponseUsers;
    }


    public void refresh() {
        insiderService.findUsers(roomId).subscribe(reponseUsers::postValue);
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
