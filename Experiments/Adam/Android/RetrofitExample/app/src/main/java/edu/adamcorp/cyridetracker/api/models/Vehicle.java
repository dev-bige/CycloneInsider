
package edu.adamcorp.cyridetracker.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vehicle {

    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("APCPercentage")
    @Expose
    private Integer aPCPercentage;
    @SerializedName("RouteId")
    @Expose
    private Integer routeId;
    @SerializedName("PatternId")
    @Expose
    private Integer patternId;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("HasAPC")
    @Expose
    private Boolean hasAPC;
    @SerializedName("IconPrefix")
    @Expose
    private String iconPrefix;
    @SerializedName("DoorStatus")
    @Expose
    private Integer doorStatus;
    @SerializedName("Latitude")
    @Expose
    private Double latitude;
    @SerializedName("Longitude")
    @Expose
    private Double longitude;
    @SerializedName("Coordinate")
    @Expose
    private Coordinate coordinate;
    @SerializedName("Speed")
    @Expose
    private Integer speed;
    @SerializedName("Heading")
    @Expose
    private String heading;
    @SerializedName("Updated")
    @Expose
    private String updated;
    @SerializedName("UpdatedAgo")
    @Expose
    private String updatedAgo;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public Integer getAPCPercentage() {
        return aPCPercentage;
    }

    public void setAPCPercentage(Integer aPCPercentage) {
        this.aPCPercentage = aPCPercentage;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public Integer getPatternId() {
        return patternId;
    }

    public void setPatternId(Integer patternId) {
        this.patternId = patternId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getHasAPC() {
        return hasAPC;
    }

    public void setHasAPC(Boolean hasAPC) {
        this.hasAPC = hasAPC;
    }

    public String getIconPrefix() {
        return iconPrefix;
    }

    public void setIconPrefix(String iconPrefix) {
        this.iconPrefix = iconPrefix;
    }

    public Integer getDoorStatus() {
        return doorStatus;
    }

    public void setDoorStatus(Integer doorStatus) {
        this.doorStatus = doorStatus;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getUpdatedAgo() {
        return updatedAgo;
    }

    public void setUpdatedAgo(String updatedAgo) {
        this.updatedAgo = updatedAgo;
    }

}
