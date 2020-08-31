package com.customer.remote.http.model.response.newHome;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.model.response.common.ImagesDetails;
import com.customer.remote.http.model.response.pdp.OfferName;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CatOfferDetails implements Parcelable {
  @SerializedName("startDateTimeISO")
  @Expose
  private String startDateTimeISO;

  @SerializedName("applicableOnStatus")
  @Expose
  private String applicableOnStatus;

  @SerializedName("images")
  @Expose
  private ImagesDetails images;

  @SerializedName("offerName")
  @Expose
  private OfferName offerName;

  @SerializedName("mobimage")
  @Expose
  private String mobimage;

  @SerializedName("discountValue")
  @Expose
  private double discountValue;

  @SerializedName("endDateTimeISO")
  @Expose
  private long endDateTimeISO;

  @SerializedName("discountType")
  @Expose
  private int discountType;

  public CatOfferDetails(String startDateTimeISO, String applicableOnStatus,
      ImagesDetails images, OfferName offerName,
      String mobimage, double discountValue, long endDateTimeISO, int discountType) {
    this.startDateTimeISO = startDateTimeISO;
    this.applicableOnStatus = applicableOnStatus;
    this.images = images;
    this.offerName = offerName;
    this.mobimage = mobimage;
    this.discountValue = discountValue;
    this.endDateTimeISO = endDateTimeISO;
    this.discountType = discountType;
  }

  protected CatOfferDetails(Parcel in) {
    startDateTimeISO = in.readString();
    applicableOnStatus = in.readString();
    images = in.readParcelable(ImagesDetails.class.getClassLoader());
    mobimage = in.readString();
    discountValue = in.readDouble();
    endDateTimeISO = in.readLong();
    discountType = in.readInt();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(startDateTimeISO);
    dest.writeString(applicableOnStatus);
    dest.writeParcelable(images, flags);
    dest.writeString(mobimage);
    dest.writeDouble(discountValue);
    dest.writeLong(endDateTimeISO);
    dest.writeInt(discountType);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<CatOfferDetails> CREATOR = new Creator<CatOfferDetails>() {
    @Override
    public CatOfferDetails createFromParcel(Parcel in) {
      return new CatOfferDetails(in);
    }

    @Override
    public CatOfferDetails[] newArray(int size) {
      return new CatOfferDetails[size];
    }
  };

  public int getDiscountType() {
    return discountType;
  }

  public void setDiscountType(int discountType) {
    this.discountType = discountType;
  }

  public long getEndDateTimeISO() {
    return endDateTimeISO;
  }

  public void setEndDateTimeISO(long endDateTimeISO) {
    this.endDateTimeISO = endDateTimeISO;
  }

  public String getMobimage() {
    return mobimage;
  }

  public void setMobimage(String mobimage) {
    this.mobimage = mobimage;
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

  public String getApplicableOnStatus() {
    return applicableOnStatus;
  }

  public void setApplicableOnStatus(String applicableOnStatus) {
    this.applicableOnStatus = applicableOnStatus;
  }

  public ImagesDetails getImages() {
    return images;
  }

  public void setImages(ImagesDetails images) {
    this.images = images;
  }

  public OfferName getOfferName() {
    return offerName;
  }

  public void setOfferName(OfferName offerName) {
    this.offerName = offerName;
  }
}

