
package edu.adamcorp.cyridetracker.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stop {

    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("Latitude")
    @Expose
    private Double latitude;
    @SerializedName("Longitude")
    @Expose
    private Double longitude;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("RtpiNumber")
    @Expose
    private Integer rtpiNumber;
    @SerializedName("ShowLabel")
    @Expose
    private Boolean showLabel;
    @SerializedName("ShowStopRtpiNumberLabel")
    @Expose
    private Boolean showStopRtpiNumberLabel;
    @SerializedName("ShowVehicleName")
    @Expose
    private Boolean showVehicleName;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRtpiNumber() {
        return rtpiNumber;
    }

    public void setRtpiNumber(Integer rtpiNumber) {
        this.rtpiNumber = rtpiNumber;
    }

    public Boolean getShowLabel() {
        return showLabel;
    }

    public void setShowLabel(Boolean showLabel) {
        this.showLabel = showLabel;
    }

    public Boolean getShowStopRtpiNumberLabel() {
        return showStopRtpiNumberLabel;
    }

    public void setShowStopRtpiNumberLabel(Boolean showStopRtpiNumberLabel) {
        this.showStopRtpiNumberLabel = showStopRtpiNumberLabel;
    }

    public Boolean getShowVehicleName() {
        return showVehicleName;
    }

    public void setShowVehicleName(Boolean showVehicleName) {
        this.showVehicleName = showVehicleName;
    }

}
