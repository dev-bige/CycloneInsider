package edu.cs309.cycloneinsider.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.models.RoomMembershipModel;
import edu.cs309.cycloneinsider.api.models.RoomModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import retrofit2.Response;

public class RoomInvitationViewModel extends ViewModel {
    private final MutableLiveData<Response<List<RoomModel>>> pendingInvites = new MutableLiveData<>();
    private final MutableLiveData<Response<RoomMembershipModel>> joinRoomMembership = new MutableLiveData<>();

    private CycloneInsiderService cycloneInsiderService;

    @Inject
    public RoomInvitationViewModel(CycloneInsiderService cycloneInsiderService) {
        this.cycloneInsiderService = cycloneInsiderService;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }


    /**
     * Retrieves a list of private rooms that the current user has been invited too
     */
    public void refresh() {
        Observable<Response<List<RoomMembershipModel>>> observable = null;

        observable = cycloneInsiderService.getPendingMemberships();
        observable.filter(Response::isSuccessful)
                .map(Response::body)
                .concatMap(Observable::fromIterable)
                .map(RoomMembershipModel::getRoom)
                .toList()
                .map(Response::success)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pendingInvites::postValue);

    }

    public void joinRoom(String roomUUID) {
        Observable<Response<RoomMembershipModel>> observable = null;
        observable = cycloneInsiderService.joinRoom(roomUUID);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(joinRoomMembership::postValue);
    }

    public LiveData<Response<List<RoomModel>>> getPendingInvites() {
        return pendingInvites;
    }

    public LiveData<Response<RoomMembershipModel>> getJoinRoomMembership() {
        return joinRoomMembership;
    }
}
