package com.customer.domain.model.promocode;

import java.util.ArrayList;

public class PromoCodeData {
  private String mStartTime;
  private String code;
  private String promo_id;
  private String mEndTime;
  private ArrayList<PromoCodeDes> description;
  private String title;

  public PromoCodeData(String startTime, String code, String promo_id,String endTime,
      ArrayList<PromoCodeDes> description, String title) {
    this.mStartTime = startTime;
    this.code = code;
    this.promo_id=promo_id;
    this.mEndTime = endTime;
    this.description = description;
    this.title = title;
  }

  public String getStartTime() {
    return mStartTime;
  }

  public void setStartTime(String startTime) {
    this.mStartTime = startTime;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getEndTime() {
    return mEndTime;
  }

  public void setEndTime(String endTime) {
    this.mEndTime = endTime;
  }

  public ArrayList<PromoCodeDes> getDescription() {
    return description;
  }

  public void setDescription(ArrayList<PromoCodeDes> description) {
    this.description = description;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPromo_id() {
    return promo_id;
  }

  public void setPromo_id(String promo_id) {
    this.promo_id = promo_id;
  }
}
