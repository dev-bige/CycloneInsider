package edu.cs309.cycloneinsider.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.models.PostModel;
import io.reactivex.Observable;
import retrofit2.Response;

/**
 * Class that is injected into the MyPostListFragment
 */
public class MyPostListViewModel extends ViewModel {
    private final MutableLiveData<Response<List<PostModel>>> myPostListResponse = new MutableLiveData<>();
    private CycloneInsiderService cycloneInsiderService;

    @Inject
    public MyPostListViewModel(CycloneInsiderService cycloneInsiderService) {
        this.cycloneInsiderService = cycloneInsiderService;
    }

    @Override
    public void onCleared() {
        super.onCleared();
    }

    /**
     * Uses the API call .getMyPosts() which gets all the posts that the current user has posted
     */
    public void refresh() {
        Observable<Response<List<PostModel>>> observable = null;

        observable = cycloneInsiderService.getMyPosts();

        observable.subscribe(myPostListResponse::postValue);
    }

    public LiveData<Response<List<PostModel>>> getMyPostResponse() {
        return myPostListResponse;
    }
}
