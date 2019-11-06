package edu.cs309.cycloneinsider.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.models.CommentModel;
import edu.cs309.cycloneinsider.api.models.CreateCommentRequestModel;
import edu.cs309.cycloneinsider.api.models.PostModel;
import retrofit2.Response;

/**
 * A view model used in the Post Detail Activity
 * Takes the user to the information about the post they are wanting to view
 */
public class PostDetailViewModel extends ViewModel {
    private CycloneInsiderService cycloneInsiderService;
    private MutableLiveData<Response<Void>> createCommentsResponse = new MutableLiveData<>();
    private MutableLiveData<Response<List<CommentModel>>> commentsResponse = new MutableLiveData<>();
    private MutableLiveData<Response<PostModel>> postDetailResposne = new MutableLiveData<>();
    private String postUUID;


    @Inject
    public PostDetailViewModel(CycloneInsiderService cycloneInsiderService) {
        this.cycloneInsiderService = cycloneInsiderService;
    }

    /**
     * API call for creating a new comment on the respected post
     *
     * @param createCommentRequestModel A model that is created when a user is wanting to make a comment on the post
     */
    public void createComment(CreateCommentRequestModel createCommentRequestModel) {
        cycloneInsiderService.createComment(postUUID, createCommentRequestModel).subscribe(createCommentsResponse::postValue);
    }

    /**
     * API calls that gets both the comments of the specified post and the post itself when a user goes to
     * the information about a post
     */
    public void refresh() {
        cycloneInsiderService.getPostComments(postUUID).subscribe(commentsResponse::postValue);
        cycloneInsiderService.getPost(postUUID).subscribe(postDetailResposne::postValue);
    }

    public void setPostUUID(String postUUID) {
        this.postUUID = postUUID;
    }

    public MutableLiveData<Response<Void>> getCreateCommentsResponse() {
        return createCommentsResponse;
    }

    public MutableLiveData<Response<List<CommentModel>>> getCommentsResponse() {
        return commentsResponse;
    }

    public MutableLiveData<Response<PostModel>> getPostDetailResponse() {
        return postDetailResposne;
    }
}
