
package edu.adamcorp.cyridetracker.api.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RouteDetail {

    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("ArrivalsEnabled")
    @Expose
    private Boolean arrivalsEnabled;
    @SerializedName("DisplayName")
    @Expose
    private String displayName;
    @SerializedName("CustomerID")
    @Expose
    private Integer customerID;
    @SerializedName("DirectionStops")
    @Expose
    private Object directionStops;
    @SerializedName("Points")
    @Expose
    private Object points;
    @SerializedName("Color")
    @Expose
    private String color;
    @SerializedName("TextColor")
    @Expose
    private String textColor;
    @SerializedName("ArrivalsShowVehicleNames")
    @Expose
    private Boolean arrivalsShowVehicleNames;
    @SerializedName("IsHeadway")
    @Expose
    private Boolean isHeadway;
    @SerializedName("ShowLine")
    @Expose
    private Boolean showLine;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("ShortName")
    @Expose
    private String shortName;
    @SerializedName("RegionIDs")
    @Expose
    private List<Object> regionIDs = null;
    @SerializedName("ForwardDirectionName")
    @Expose
    private Object forwardDirectionName;
    @SerializedName("BackwardDirectionName")
    @Expose
    private Object backwardDirectionName;
    @SerializedName("NumberOfVehicles")
    @Expose
    private Integer numberOfVehicles;
    @SerializedName("Patterns")
    @Expose
    private List<Pattern> patterns = null;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public Boolean getArrivalsEnabled() {
        return arrivalsEnabled;
    }

    public void setArrivalsEnabled(Boolean arrivalsEnabled) {
        this.arrivalsEnabled = arrivalsEnabled;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public Object getDirectionStops() {
        return directionStops;
    }

    public void setDirectionStops(Object directionStops) {
        this.directionStops = directionStops;
    }

    public Object getPoints() {
        return points;
    }

    public void setPoints(Object points) {
        this.points = points;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public Boolean getArrivalsShowVehicleNames() {
        return arrivalsShowVehicleNames;
    }

    public void setArrivalsShowVehicleNames(Boolean arrivalsShowVehicleNames) {
        this.arrivalsShowVehicleNames = arrivalsShowVehicleNames;
    }

    public Boolean getIsHeadway() {
        return isHeadway;
    }

    public void setIsHeadway(Boolean isHeadway) {
        this.isHeadway = isHeadway;
    }

    public Boolean getShowLine() {
        return showLine;
    }

    public void setShowLine(Boolean showLine) {
        this.showLine = showLine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public List<Object> getRegionIDs() {
        return regionIDs;
    }

    public void setRegionIDs(List<Object> regionIDs) {
        this.regionIDs = regionIDs;
    }

    public Object getForwardDirectionName() {
        return forwardDirectionName;
    }

    public void setForwardDirectionName(Object forwardDirectionName) {
        this.forwardDirectionName = forwardDirectionName;
    }

    public Object getBackwardDirectionName() {
        return backwardDirectionName;
    }

    public void setBackwardDirectionName(Object backwardDirectionName) {
        this.backwardDirectionName = backwardDirectionName;
    }

    public Integer getNumberOfVehicles() {
        return numberOfVehicles;
    }

    public void setNumberOfVehicles(Integer numberOfVehicles) {
        this.numberOfVehicles = numberOfVehicles;
    }

    public List<Pattern> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<Pattern> patterns) {
        this.patterns = patterns;
    }

}
