package edu.cs309.cycloneinsider.api;


import java.util.List;

import edu.cs309.cycloneinsider.api.models.PostCreateRequestModel;
import edu.cs309.cycloneinsider.api.models.LoginRequestModel;
import edu.cs309.cycloneinsider.api.models.MembershipModel;
import edu.cs309.cycloneinsider.api.models.PostModel;
import edu.cs309.cycloneinsider.api.models.RoomMembershipModel;
import edu.cs309.cycloneinsider.api.models.RoomModel;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface CycloneInsiderService {
    @POST("login")
    Observable<Response<Void>> login(@Body LoginRequestModel loginRequestModel);

    @GET("rooms/memberships")
    Observable<Response<List<MembershipModel>>> getMemberships();

    @GET("posts/front-page")
    Observable<Response<List<PostModel>>> getFrontPagePosts();

    @POST("rooms/{uuid}/join")
    Observable<Response<RoomMembershipModel>> joinRoom(@Path("uuid") String room_uuid);

    @GET("rooms/all")
    Observable<Response<List<RoomModel>>> getAllRooms();

    @POST("posts/front-page")
    Observable<Response<PostModel>> createFrontPagePost(@Body PostCreateRequestModel body);
}
