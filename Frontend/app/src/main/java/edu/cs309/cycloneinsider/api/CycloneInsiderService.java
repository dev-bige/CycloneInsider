package edu.cs309.cycloneinsider.api;


import java.util.List;

import edu.cs309.cycloneinsider.api.models.CommentModel;
import edu.cs309.cycloneinsider.api.models.CreateCommentRequestModel;
import edu.cs309.cycloneinsider.api.models.CreateRoomRequestModel;
import edu.cs309.cycloneinsider.api.models.FavoritePostModel;
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

    @GET("users/current/favorite-posts")
    Observable<Response<List<FavoritePostModel>>> getFavoritePost();

    @POST("rooms/{uuid}/join")
    Observable<Response<RoomMembershipModel>> joinRoom(@Path("uuid") String room_uuid);

    @POST("login")
    Observable<Response<Void>> login(@Body LoginRequestModel loginRequestModel);

//    @POST("changePassword")
//    Observable<Response<Void>> changePassword(@Body NewPasswordRequestModel newPasswordRequestModel);

    @POST("/users/sign-up")
    Observable<Response<Void>> signUp(@Body SignUpRequestModel signUpRequestModel);

    @GET("users/current/users-posts")
    Observable<Response<List<PostModel>>> getMyPosts();

    @GET("/rooms/{uuid}/posts")
    Observable<Response<List<PostModel>>> getRoomPosts(@Path("uuid") String room_uuid);

    @POST("/rooms/{uuid}/posts")
    Observable<Response<PostModel>> createRoomPost(@Path("uuid") String room_uuid, @Body PostCreateRequestModel body);

    @POST("/posts/{postUuid}/favorite")
    Observable<Response<FavoritePostModel>> favoritePost(@Path("postUuid") String post_uuid);

    @DELETE("/posts/{postUuid}/favorite")
    Observable<Response<Void>> removeFavoritePost(@Path("postUuid") String post_uuid);

    @GET("/users/{username}/profile")
    Observable<Response<InsiderUserModel>> findUser(@Path("username") String username);

    @GET("/users/memberships/pending")
    Observable<Response<List<RoomMembershipModel>>> getPendingMemberships();

    @POST("/rooms/{roomUuid}/invite")
    Observable<Response<RoomMembershipModel>> invite(@Path("roomUuid") String roomUuid, @Query("userUuid") String userUuid);


    @GET("/users/memberships/pending-professors")
    Observable<Response<List<InsiderUserModel>>> getPendingProfessorMemberships();
}
