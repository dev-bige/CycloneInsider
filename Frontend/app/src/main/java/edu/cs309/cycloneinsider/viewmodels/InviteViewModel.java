package edu.cs309.cycloneinsider.viewmodels;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.common.base.Strings;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.UserStateService;
import edu.cs309.cycloneinsider.api.models.InsiderUserModel;
import edu.cs309.cycloneinsider.api.models.RoomMembershipModel;
import retrofit2.Response;

/**
 * A view model that is used within the Invite Activity
 * Allows for the user to invite other users to the room they are currently a part of
 */
public class InviteViewModel extends ViewModel {
    private CycloneInsiderService cycloneInsiderService;
    private UserStateService userStateService;
    private MutableLiveData<Response<InsiderUserModel>> findUserResponse = new MutableLiveData<>();
    private MutableLiveData<Response<RoomMembershipModel>> inviteResponse = new MutableLiveData<>();

    @Inject
    public InviteViewModel(CycloneInsiderService cycloneInsiderService, UserStateService userStateService) {
        this.cycloneInsiderService = cycloneInsiderService;
        this.userStateService = userStateService;
    }

    /**
     * A check to make sure that the user being invited already exists
     *
     * @param username username of the user trying to be invited\
     * @return error if any if not null
     */
    @SuppressLint("CheckResult")
    public String findUser(String username) {
        if (Strings.isNullOrEmpty(username)) {
            return null;
        }
        if (this.userStateService != null && this.userStateService
                .getUser()
                .getUsername()
                .toLowerCase()
                .equals(username.toLowerCase())) {
            return "You cannot invite yourself!";
        }
        this.cycloneInsiderService.findUser(username).subscribe(findUserResponse::postValue);
        return null;
    }

    public void invite(String roomUuid, String userUuid) {
        if (Strings.isNullOrEmpty(userUuid)) {
            return;
        }

        this.cycloneInsiderService.invite(roomUuid, userUuid).subscribe(inviteResponse::postValue);
    }

    public LiveData<Response<RoomMembershipModel>> getInviteResponse() {
        return inviteResponse;
    }

    public LiveData<Response<InsiderUserModel>> getFindUserResponse() {
        return findUserResponse;
    }
}
