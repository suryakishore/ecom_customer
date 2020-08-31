package com.customer.domain.model.signup;

public class LocationData {

  private String lat;

  private double latitude;

  private String longi;

  private double longitude;

  public LocationData(String lat, String longi) {
    this.lat = lat;
    this.longi = longi;
  }

  public LocationData(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public String getLat() {
    return lat;
  }

  public void setLat(String lat) {
    this.lat = lat;
  }

  public String getLongi() {
    return longi;
  }

  public void setLongi(String longi) {
    this.longi = longi;
  }
}
