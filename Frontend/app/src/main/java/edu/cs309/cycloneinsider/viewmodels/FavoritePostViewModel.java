package edu.cs309.cycloneinsider.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.models.FavoritePostModel;
import edu.cs309.cycloneinsider.api.models.PostModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import retrofit2.Response;

/**
 * Class that is injected into the favorite post fragment. Deals with the new data being accessed
 */
public class FavoritePostViewModel extends ViewModel {
    private final MutableLiveData<Response<List<PostModel>>> favPostResponse = new MutableLiveData<>();
    private CycloneInsiderService cycloneInsiderService;

    @Inject
    public FavoritePostViewModel(CycloneInsiderService cycloneInsiderService) {
        this.cycloneInsiderService = cycloneInsiderService;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    /**
     * Is called when either the user goes into the fragment or pulls down on the screen to refresh the
     * activity
     * Maps the FavoritePostModel response to the PostModel response to be called by the adapter class
     */
    public void refresh() {
        Observable<Response<List<FavoritePostModel>>> observable = null;

        observable = cycloneInsiderService.getFavoritePost();
        observable.filter(Response::isSuccessful)
                .map(Response::body)
                .concatMap(Observable::fromIterable)
                .map(FavoritePostModel::getPost)
                .toList()
                .map(Response::success)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(favPostResponse::postValue);


    }

    public LiveData<Response<List<PostModel>>> getFavoritePostResponse() {
        return favPostResponse;
    }
}
