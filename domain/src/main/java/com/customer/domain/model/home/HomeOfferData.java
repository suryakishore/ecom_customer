package com.customer.domain.model.home;

public class HomeOfferData {
  private double discountValue;
  private String startDateTimeISO;
  private String offerName;
  private String mobimage;
  private String offerId;

  public HomeOfferData(double discountValue, String startDateTimeISO,
      String offerName, String mobimage, String offerId) {
    this.discountValue = discountValue;
    this.startDateTimeISO = startDateTimeISO;
    this.offerName = offerName;
    this.mobimage = mobimage;
    this.offerId = offerId;
  }

  public String getOfferId() {
    return offerId;
  }

  public void setOfferId(String offerId) {
    this.offerId = offerId;
  }

  public double getDiscountValue() {
    return discountValue;
  }

  public void setDiscountValue(double discountValue) {
    this.discountValue = discountValue;
  }

  public String getStartDateTimeISO() {
    return startDateTimeISO;
  }

  public void setStartDateTimeISO(String startDateTimeISO) {
    this.startDateTimeISO = startDateTimeISO;
  }

  public String getOfferName() {
    return offerName;
  }

  public void setOfferName(String offerName) {
    this.offerName = offerName;
  }

  public String getMobimage() {
    return mobimage;
  }

  public void setMobimage(String mobimage) {
    this.mobimage = mobimage;
  }
}
