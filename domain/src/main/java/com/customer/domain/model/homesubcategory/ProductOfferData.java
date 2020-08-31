package com.customer.domain.model.homesubcategory;

import com.customer.domain.model.common.ImageData;

public class ProductOfferData {
  private String applicableOnStatus;

  private ImageData images;

  private String offerName;

  private long endDateTimeISO;

  private String endDateTime;

  private String globalClaimCount;

  private String startDateTimeISO;

  private String startDateTime;

  private String minimumPurchaseQty;

  private String perUserLimit;

  private String statusString;

  private String offerId;

  private int discountType;

  private String offerFor;

  private String discountValue;

  private String applicableOn;

  private String status;

  public ProductOfferData(String applicableOnStatus, ImageData images,
      String offerName, long endDateTimeISO, String endDateTime,
      String globalClaimCount, String startDateTimeISO, String startDateTime,
      String minimumPurchaseQty, String perUserLimit, String statusString,
      String offerId, int discountType, String offerFor,
      long discountValue, String applicableOn, String status) {
    this.applicableOnStatus = applicableOnStatus;
    this.images = images;
    this.offerName = offerName;
    this.endDateTimeISO = endDateTimeISO;
    this.endDateTime = endDateTime;
    this.globalClaimCount = globalClaimCount;
    this.startDateTimeISO = startDateTimeISO;
    this.startDateTime = startDateTime;
    this.minimumPurchaseQty = minimumPurchaseQty;
    this.perUserLimit = perUserLimit;
    this.statusString = statusString;
    this.offerId = offerId;
    this.discountType = discountType;
    this.offerFor = offerFor;
    this.discountValue = ((int) discountValue) + "";
    this.applicableOn = applicableOn;
    this.status = status;
  }

  public String getApplicableOnStatus() {
    return applicableOnStatus;
  }

  public void setApplicableOnStatus(String applicableOnStatus) {
    this.applicableOnStatus = applicableOnStatus;
  }

  public ImageData getImages() {
    return images;
  }

  public void setImages(ImageData images) {
    this.images = images;
  }

  public String getOfferName() {
    return offerName;
  }

  public void setOfferName(String offerName) {
    this.offerName = offerName;
  }

  public long getEndDateTimeISO() {
    return endDateTimeISO;
  }

  public void setEndDateTimeISO(long endDateTimeISO) {
    this.endDateTimeISO = endDateTimeISO;
  }

  public String getEndDateTime() {
    return endDateTime;
  }

  public void setEndDateTime(String endDateTime) {
    this.endDateTime = endDateTime;
  }

  public String getGlobalClaimCount() {
    return globalClaimCount;
  }

  public void setGlobalClaimCount(String globalClaimCount) {
    this.globalClaimCount = globalClaimCount;
  }

  public String getStartDateTimeISO() {
    return startDateTimeISO;
  }

  public void setStartDateTimeISO(String startDateTimeISO) {
    this.startDateTimeISO = startDateTimeISO;
  }

  public String getStartDateTime() {
    return startDateTime;
  }

  public void setStartDateTime(String startDateTime) {
    this.startDateTime = startDateTime;
  }

  public String getMinimumPurchaseQty() {
    return minimumPurchaseQty;
  }

  public void setMinimumPurchaseQty(String minimumPurchaseQty) {
    this.minimumPurchaseQty = minimumPurchaseQty;
  }

  public String getPerUserLimit() {
    return perUserLimit;
  }

  public void setPerUserLimit(String perUserLimit) {
    this.perUserLimit = perUserLimit;
  }

  public String getStatusString() {
    return statusString;
  }

  public void setStatusString(String statusString) {
    this.statusString = statusString;
  }

  public String getOfferId() {
    return offerId;
  }

  public void setOfferId(String offerId) {
    this.offerId = offerId;
  }

  public int getDiscountType() {
    return discountType;
  }

  public void setDiscountType(int discountType) {
    this.discountType = discountType;
  }

  public String getOfferFor() {
    return offerFor;
  }

  public void setOfferFor(String offerFor) {
    this.offerFor = offerFor;
  }

  public String getDiscountValue() {
    return discountValue;
  }

  public void setDiscountValue(String discountValue) {
    this.discountValue = discountValue;
  }

  public String getApplicableOn() {
    return applicableOn;
  }

  public void setApplicableOn(String applicableOn) {
    this.applicableOn = applicableOn;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
