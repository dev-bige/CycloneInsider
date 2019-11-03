package edu.cs309.cycloneinsider.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.models.CreateRoomRequestModel;
import edu.cs309.cycloneinsider.api.models.RoomModel;
import retrofit2.Response;

/**
 * View model that is used for dependency injection within the create room activity.
 * Allows for the user to create a room.
 */
public class CreateRoomViewModel extends ViewModel {
    MutableLiveData<Response<RoomModel>> createRoomResponse = new MutableLiveData<>();
    private CycloneInsiderService cycloneInsiderService;

    @Inject
    public CreateRoomViewModel(CycloneInsiderService cycloneInsiderService) {
        this.cycloneInsiderService = cycloneInsiderService;
    }

    /**
     * Allows for a user to pass a createRoomRequestModel and then make an API call to make the respected room
     * @param createRoomRequestModel a object that stores certain fields related to the room that is being created
     */
    public void createRoom(CreateRoomRequestModel createRoomRequestModel) {
        cycloneInsiderService.createRoom(createRoomRequestModel).subscribe(createRoomResponse::postValue);
    }

    public LiveData<Response<RoomModel>> getCreateRoomResponse() {
        return createRoomResponse;
    }
}
