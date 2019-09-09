package edu.adamcorp.cyridetracker.api;

import java.util.List;

import edu.adamcorp.cyridetracker.api.models.Direction;
import edu.adamcorp.cyridetracker.api.models.RouteDetail;
import edu.adamcorp.cyridetracker.api.models.Waypoint;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MyRideService {
    @GET("Region/0/Routes")
    Observable<List<RouteDetail>> routes();

    @GET("Route/{route}")
    Observable<RouteDetail> route(@Path("route") String route);

    @GET("Route/{route}/Directions")
    Observable<List<Direction>> directions(@Path("route") String route);

    @GET("Route/{route}/Waypoints")
    Observable<List<List<Waypoint>>> waypoints(@Path("route") String route);
}
