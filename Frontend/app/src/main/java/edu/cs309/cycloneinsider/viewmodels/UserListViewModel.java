package edu.cs309.cycloneinsider.viewmodels;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.UserStateService;
import edu.cs309.cycloneinsider.api.models.InsiderUserModel;
import edu.cs309.cycloneinsider.api.models.MembershipModel;
import retrofit2.Response;

public class UserListViewModel extends ViewModel {
    CycloneInsiderService insiderService;
    MutableLiveData<Response<List<InsiderUserModel>>> reponseUsers = new MutableLiveData<>();
    private UserStateService userStateService;
    private String roomId = null;

    @Inject
    public UserListViewModel(CycloneInsiderService insiderService, UserStateService userStateService) {
        this.insiderService = insiderService;
        this.userStateService = userStateService;
    }

    public LiveData<Response<List<InsiderUserModel>>> getReponseUsers() {
        return reponseUsers;
    }


    public void refresh() {
        insiderService.findUsers(roomId).subscribe(reponseUsers::postValue);
    }

    public boolean canDelete(InsiderUserModel user) {
        if (user.getUuid().equals(this.userStateService.getUser().getUuid())) {
            return false;
        }

        if (this.roomId != null) {
            MembershipModel membership = this.userStateService.getMembership(roomId);
            if (!membership.roomLevel.equals("USER")) {
                return true;
            }
        }

        return this.userStateService.isAdmin() && !(user.getAdmin() || user.getProfessor());
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    @SuppressLint("CheckResult")
    public void onDeletePressed(InsiderUserModel insiderUserModel) {
        if (roomId != null) {
            this.insiderService.kickUser(roomId, insiderUserModel.getUuid()).subscribe(response -> {
                this.refresh();
            });
        } else {
            this.insiderService.banUser(insiderUserModel.getUuid()).subscribe(response -> {
                this.refresh();
            });
        }
    }
}
