package edu.cs309.cycloneinsider.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.common.base.Strings;

import java.util.List;

import javax.inject.Inject;

import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.UserStateService;
import edu.cs309.cycloneinsider.api.models.InsiderUserModel;
import edu.cs309.cycloneinsider.api.models.PostModel;
import edu.cs309.cycloneinsider.api.models.RoomModel;
import io.reactivex.Observable;
import retrofit2.Response;

/**
 * A view model that is injected into the Post List Fragment
 * Displays either the front page posts or the respected room posts
 */
public class PostListViewModel extends ViewModel {
    private final MutableLiveData<Response<List<PostModel>>> postListResponse = new MutableLiveData<>();
    private final MutableLiveData<Boolean> canCreateInvite = new MutableLiveData<>(false);
    private CycloneInsiderService cycloneInsiderService;
    private UserStateService userStateService;
    private String roomUUID;

    @Inject
    public PostListViewModel(CycloneInsiderService cycloneInsiderService, UserStateService userStateService) {
        this.cycloneInsiderService = cycloneInsiderService;
        this.userStateService = userStateService;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    /**
     * An API call to get the posts for a user
     * If the room UUID for a post is empty means that is being displayed on the front page of the application
     * Otherwise if there is a specified UUID then there is a call to get the posts of the specified room
     */
    public void refresh() {
        Observable<Response<List<PostModel>>> observable = null;
        if (Strings.isNullOrEmpty(this.getRoomUUID())) {
            observable = cycloneInsiderService.getFrontPagePosts();
        } else {
            observable = cycloneInsiderService.getRoomPosts(this.getRoomUUID());
        }
        observable.subscribe(postListResponse::postValue);
        hasInviteAccess();
    }

    private void hasInviteAccess() {
        if (Strings.isNullOrEmpty(roomUUID)) {
            return;
        }

        this.cycloneInsiderService.getRoom(roomUUID)
                .filter(Response::isSuccessful)
                .map(Response::body)
                .map(RoomModel::getCreator)
                .map(InsiderUserModel::getUuid)
                .map(uuid -> uuid.equals(userStateService.getUser().uuid))
                .subscribe(canCreateInvite::postValue);
    }

    public String getRoomUUID() {
        return roomUUID;
    }

    public void setRoomUUID(String roomUUID) {
        this.roomUUID = roomUUID;
    }

    public LiveData<Response<List<PostModel>>> getPostListResponse() {
        return postListResponse;
    }

    public LiveData<Boolean> getCanCreateInvite() {
        return canCreateInvite;
    }
}
