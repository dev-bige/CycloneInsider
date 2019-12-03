package edu.cs309.cycloneinsider.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.models.RoomMembershipModel;
import edu.cs309.cycloneinsider.api.models.RoomModel;
import retrofit2.Response;

/**
 * Join room View model
 */
public class JoinRoomViewModel extends ViewModel {
    private CycloneInsiderService cycloneInsiderService;
    private MutableLiveData<Response<List<RoomModel>>> roomModelResponse = new MutableLiveData<>();
    private MutableLiveData<Response<RoomMembershipModel>> roomMembershipResponse = new MutableLiveData<>();

    @Inject
    public JoinRoomViewModel(CycloneInsiderService cycloneInsiderService) {
        this.cycloneInsiderService = cycloneInsiderService;
    }

    /**
     * Refresh list of rooms
     */
    public void refresh() {
        cycloneInsiderService.getAllRooms().subscribe(roomModelResponse::postValue);
    }


    /**
     * Join a room based on the uuid
     * @param uuid the uuid of the room
     */
    public void joinRoom(String uuid) {
        cycloneInsiderService.joinRoom(uuid).subscribe(roomMembershipResponse::postValue);
    }

    public LiveData<Response<List<RoomModel>>> getRoomModelResponse() {
        return roomModelResponse;
    }

    public LiveData<Response<RoomMembershipModel>> getRoomMembershipResponse() {
        return roomMembershipResponse;
    }
}
