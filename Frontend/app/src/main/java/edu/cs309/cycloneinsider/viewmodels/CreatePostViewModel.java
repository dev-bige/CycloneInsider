package edu.cs309.cycloneinsider.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.common.base.Strings;

import javax.inject.Inject;

import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.models.PostCreateRequestModel;
import edu.cs309.cycloneinsider.api.models.PostModel;
import io.reactivex.Observable;
import retrofit2.Response;

/**
 * View model for creating a room
 */
public class CreatePostViewModel extends ViewModel {
    private static final String TAG = "CreatePostViewModel";
    MutableLiveData<Response<PostModel>> createPostModelResponse = new MutableLiveData<>();
    private CycloneInsiderService cycloneInsiderService;

    @Inject
    public CreatePostViewModel(CycloneInsiderService cycloneInsiderService) {
        this.cycloneInsiderService = cycloneInsiderService;
    }

    /**
     * Create the post to the server
     * @param postCreateRequestModel the post create request model
     * @param room_uuid if null, post to front page
     */
    public void createPost(PostCreateRequestModel postCreateRequestModel, String room_uuid) {
        Observable<Response<PostModel>> observable;
        if (Strings.isNullOrEmpty(room_uuid)) {
            observable = this.cycloneInsiderService.createFrontPagePost(postCreateRequestModel);
        } else {
            observable = this.cycloneInsiderService.createRoomPost(room_uuid, postCreateRequestModel);
        }
        observable.subscribe(this.createPostModelResponse::postValue, throwable -> Log.e(TAG, "createPost: ", throwable));
    }

    public MutableLiveData<Response<PostModel>> getCreatePostModelResponse() {
        return createPostModelResponse;
    }


}
