package edu.cs309.cycloneinsider.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.common.base.Strings;

import java.util.List;

import javax.inject.Inject;

import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.models.PostModel;
import io.reactivex.Observable;
import retrofit2.Response;

public class PostListViewModel extends ViewModel {
    private final MutableLiveData<Response<List<PostModel>>> postListResponse = new MutableLiveData<>();
    private CycloneInsiderService cycloneInsiderService;
    private String roomUUID;

    @Inject
    public PostListViewModel(CycloneInsiderService cycloneInsiderService) {
        this.cycloneInsiderService = cycloneInsiderService;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public void refresh() {
        Observable<Response<List<PostModel>>> observable = null;
        if (Strings.isNullOrEmpty(this.getRoomUUID())) {
            observable = cycloneInsiderService.getFrontPagePosts();
        } else {
            observable = cycloneInsiderService.getRoomPosts(this.getRoomUUID());
        }
        observable.subscribe(postListResponse::postValue);
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
}
