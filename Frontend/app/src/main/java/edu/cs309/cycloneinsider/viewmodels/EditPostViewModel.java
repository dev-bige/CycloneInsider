package edu.cs309.cycloneinsider.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.models.PostModel;
import io.reactivex.Observable;
import retrofit2.Response;

public class EditPostViewModel extends ViewModel {
    MutableLiveData<Response<PostModel>> editPostModelResponse = new MutableLiveData<>();
    MutableLiveData<Response<PostModel>> returnPost = new MutableLiveData<>();
    private CycloneInsiderService cycloneInsiderService;

    @Inject
    public EditPostViewModel(CycloneInsiderService cycloneInsiderService) {
        this.cycloneInsiderService = cycloneInsiderService;
    }

    public void getPost(String post_uuid) {
        Observable<Response<PostModel>> observable;
        observable = this.cycloneInsiderService.getPost(post_uuid);
        observable.subscribe(this.returnPost::postValue);
    }

    public MutableLiveData<Response<PostModel>> getReturnPost() {
        return returnPost;
    }
}
