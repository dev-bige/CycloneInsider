package edu.adamcorp.cyridetracker;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import edu.adamcorp.cyridetracker.api.MyRideService;
import edu.adamcorp.cyridetracker.api.models.Waypoint;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private final String routeId = "4531";
    private final String stopId = "3920";
    private GoogleMap mMap;
    private MyRideService myRideService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.mycyride.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myRideService = retrofit.create(MyRideService.class);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        myRideService.waypoints(routeId).observeOn(AndroidSchedulers.mainThread()).subscribe(waypoints -> {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (List<Waypoint> waypoints2 : waypoints) {
                PolylineOptions polylineOptions = new PolylineOptions();
                for (Waypoint waypoint : waypoints2) {
                    builder.include(new LatLng(waypoint.getLatitude(), waypoint.getLongitude()));
                    polylineOptions.add(new LatLng(waypoint.getLatitude(), waypoint.getLongitude()));
                }
                mMap.addPolyline(polylineOptions);
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 0));
        });
    }
}
