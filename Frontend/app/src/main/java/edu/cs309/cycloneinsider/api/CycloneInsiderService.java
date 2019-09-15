package edu.cs309.cycloneinsider.api;


import edu.cs309.cycloneinsider.api.models.LoginRequestModel;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CycloneInsiderService {
    @POST("login")
    Observable<Void> login(@Body LoginRequestModel loginRequestModel);
}
