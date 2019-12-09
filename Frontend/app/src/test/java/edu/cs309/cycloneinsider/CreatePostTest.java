package edu.cs309.cycloneinsider;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;


import edu.cs309.cycloneinsider.api.CycloneInsiderService;
import edu.cs309.cycloneinsider.api.models.PostCreateRequestModel;
import edu.cs309.cycloneinsider.api.models.PostModel;
import edu.cs309.cycloneinsider.viewmodels.CreatePostViewModel;
import io.reactivex.Observable;
import retrofit2.Response;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CreatePostTest {
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testCreatePost() {
        CycloneInsiderService cycloneInsiderService = mock(CycloneInsiderService.class);
        CreatePostViewModel createPostViewModel = new CreatePostViewModel(cycloneInsiderService);
        PostCreateRequestModel postCreateRequestModel = new PostCreateRequestModel("Blah", null, "Blah blah");
        PostModel postModel = new PostModel();
        Response<PostModel> success = Response.success(postModel);
        Observable<Response<PostModel>> createPostResponpse = Observable.just(success);
        when(cycloneInsiderService.createFrontPagePost(postCreateRequestModel)).thenReturn(createPostResponpse);
        createPostViewModel.createPost(postCreateRequestModel, null);
        assertEquals(success, createPostViewModel.getCreatePostModelResponse().getValue());
    }

    @Test
    public void testCreatePostRoom() {
        CycloneInsiderService cycloneInsiderService = mock(CycloneInsiderService.class);
        CreatePostViewModel createPostViewModel = new CreatePostViewModel(cycloneInsiderService);
        PostCreateRequestModel postCreateRequestModel = new PostCreateRequestModel("Blah", null, "Blah blah");
        PostModel postModel = new PostModel();
        Response<PostModel> success = Response.success(postModel);
        Observable<Response<PostModel>> createPostResponpse = Observable.just(success);
        when(cycloneInsiderService.createRoomPost("room", postCreateRequestModel)).thenReturn(createPostResponpse);
        createPostViewModel.createPost(postCreateRequestModel, "room");
        assertEquals(success, createPostViewModel.getCreatePostModelResponse().getValue());
    }
}
