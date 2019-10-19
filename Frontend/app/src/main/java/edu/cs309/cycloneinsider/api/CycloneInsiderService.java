package edu.cs309.cycloneinsider.api;


import java.util.List;

import edu.cs309.cycloneinsider.api.models.CommentModel;
import edu.cs309.cycloneinsider.api.models.CreateCommentRequestModel;
import edu.cs309.cycloneinsider.api.models.CreateRoomRequestModel;
import edu.cs309.cycloneinsider.api.models.InsiderUserModel;
import edu.cs309.cycloneinsider.api.models.LoginRequestModel;
import edu.cs309.cycloneinsider.api.models.MembershipModel;
import edu.cs309.cycloneinsider.api.models.PostCreateRequestModel;
import edu.cs309.cycloneinsider.api.models.PostModel;
import edu.cs309.cycloneinsider.api.models.RoomMembershipModel;
import edu.cs309.cycloneinsider.api.models.RoomModel;
import edu.cs309.cycloneinsider.api.models.SignUpRequestModel;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface CycloneInsiderService {
    @POST("posts/{uuid}/comments")
    Observable<Response<Void>> createComment(@Path("uuid") String post_uuid, @Body CreateCommentRequestModel body);

    @POST("posts/front-page")
    Observable<Response<PostModel>> createFrontPagePost(@Body PostCreateRequestModel body);

    @POST("rooms")
    Observable<Response<RoomModel>> createRoom(@Body CreateRoomRequestModel body);

    @GET("users/current")
    Observable<Response<InsiderUserModel>> currentUser();

    @GET("rooms/all")
    Observable<Response<List<RoomModel>>> getAllRooms();

    @GET("posts/front-page")
    Observable<Response<List<PostModel>>> getFrontPagePosts();

    @GET("users/memberships")
    Observable<Response<List<MembershipModel>>> getMemberships();

    @GET("posts/{uuid}")
    Observable<Response<PostModel>> getPost(@Path("uuid") String post_uuid);

    @GET("posts/{uuid}/comments")
    Observable<Response<List<CommentModel>>> getPostComments(@Path("uuid") String post_uuid);

    @POST("rooms/{uuid}/join")
    Observable<Response<RoomMembershipModel>> joinRoom(@Path("uuid") String room_uuid);

    @GET("rooms/all")
    Observable<Response<List<RoomModel>>> getAllRooms();

    @POST("posts/front-page")
    Observable<Response<PostModel>> createFrontPagePost(@Body PostCreateRequestModel body);

    @POST("/users/sign-up")
    Observable<Response<SignUpRequestModel>> signUp(@Body SignUpRequestModel signUpRequestModel);
}
