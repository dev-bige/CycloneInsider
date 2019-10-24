package edu.cs309.cycloneinsider.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.models.CreateRoomRequestModel;
import edu.cs309.cycloneinsider.api.models.RoomModel;
import retrofit2.Response;

public class CreateRoomViewModel extends ViewModel {
    MutableLiveData<Response<RoomModel>> createRoomResponse = new MutableLiveData<>();
    private CycloneInsiderService cycloneInsiderService;

    @Inject
    public CreateRoomViewModel(CycloneInsiderService cycloneInsiderService) {
        this.cycloneInsiderService = cycloneInsiderService;
    }

    public void createRoom(CreateRoomRequestModel createRoomRequestModel) {
        cycloneInsiderService.createRoom(createRoomRequestModel).subscribe(createRoomResponse::postValue);
    }

    public LiveData<Response<RoomModel>> getCreateRoomResponse() {
        return createRoomResponse;
    }
}
