
package edu.adamcorp.cyridetracker.api.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Direction {

    @SerializedName("IsSegment")
    @Expose
    private Boolean isSegment;
    @SerializedName("RouteID")
    @Expose
    private Integer routeID;
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Directionality")
    @Expose
    private String directionality;
    @SerializedName("Stops")
    @Expose
    private List<Stop> stops = null;

    public Boolean getIsSegment() {
        return isSegment;
    }

    public void setIsSegment(Boolean isSegment) {
        this.isSegment = isSegment;
    }

    public Integer getRouteID() {
        return routeID;
    }

    public void setRouteID(Integer routeID) {
        this.routeID = routeID;
    }

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirectionality() {
        return directionality;
    }

    public void setDirectionality(String directionality) {
        this.directionality = directionality;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

}
