package com.customer.domain.model.getcart;

public class CartOfferData {
  private String offerType;
  private String offerId;
  private String offerValue;
  private String offerTitle;

  public CartOfferData(String offerType, String offerId, String offerValue,
      String offerTitle) {
    this.offerType = offerType;
    this.offerId = offerId;
    this.offerValue = offerValue;
    this.offerTitle = offerTitle;
  }

  public String getOfferType() {
    return offerType;
  }

  public void setOfferType(String offerType) {
    this.offerType = offerType;
  }

  public String getOfferId() {
    return offerId;
  }

  public void setOfferId(String offerId) {
    this.offerId = offerId;
  }

  public String getOfferValue() {
    return offerValue;
  }

  public void setOfferValue(String offerValue) {
    this.offerValue = offerValue;
  }

  public String getOfferTitle() {
    return offerTitle;
  }

  public void setOfferTitle(String offerTitle) {
    this.offerTitle = offerTitle;
  }
}
