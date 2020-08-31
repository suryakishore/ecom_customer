package com.customer.domain.model.orderdetails;

public class PartnerData {

  private String id;

  private String name;


  private String trackingId;

  public PartnerData(String id, String name, String trackingId) {
    this.id = id;
    this.name = name;
    this.trackingId = trackingId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTrackingId() {
    return trackingId;
  }

  public void setTrackingId(String trackingId) {
    this.trackingId = trackingId;
  }
}
