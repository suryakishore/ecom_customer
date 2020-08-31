package com.customer.domain.model.getcart;

public class CartDeliveryData {

  private String deliveryFee;

  private String deliveryByDeliveryPartner;

  private String deliveryByFleetDriver;

  private String time;

  public CartDeliveryData(String deliveryFee, String deliveryByDeliveryPartner,
      String deliveryByFleetDriver, String time) {
    this.deliveryFee = deliveryFee;
    this.deliveryByDeliveryPartner = deliveryByDeliveryPartner;
    this.deliveryByFleetDriver = deliveryByFleetDriver;
    this.time = time;
  }

  public String getDeliveryFee() {
    return deliveryFee;
  }

  public void setDeliveryFee(String deliveryFee) {
    this.deliveryFee = deliveryFee;
  }

  public String getDeliveryByDeliveryPartner() {
    return deliveryByDeliveryPartner;
  }

  public void setDeliveryByDeliveryPartner(String deliveryByDeliveryPartner) {
    this.deliveryByDeliveryPartner = deliveryByDeliveryPartner;
  }

  public String getDeliveryByFleetDriver() {
    return deliveryByFleetDriver;
  }

  public void setDeliveryByFleetDriver(String deliveryByFleetDriver) {
    this.deliveryByFleetDriver = deliveryByFleetDriver;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }
}
