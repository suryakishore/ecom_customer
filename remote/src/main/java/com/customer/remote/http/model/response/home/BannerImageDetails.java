package com.customer.remote.http.model.response.home;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BannerImageDetails implements ValidItem, Parcelable {

  @SerializedName("imageMobile")
  @Expose
  private String imageMobile;

  @SerializedName("imageWeb")
  @Expose
  private String imageWeb;

  public BannerImageDetails(String imageMobile, String imageWeb) {
    this.imageMobile = imageMobile;
    this.imageWeb = imageWeb;
  }

  protected BannerImageDetails(Parcel in) {
    imageMobile = in.readString();
    imageWeb = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(imageMobile);
    dest.writeString(imageWeb);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<BannerImageDetails> CREATOR = new Creator<BannerImageDetails>() {
    @Override
    public BannerImageDetails createFromParcel(Parcel in) {
      return new BannerImageDetails(in);
    }

    @Override
    public BannerImageDetails[] newArray(int size) {
      return new BannerImageDetails[size];
    }
  };

  public String getImageMobile() {
    return imageMobile;
  }

  public void setImageMobile(String imageMobile) {
    this.imageMobile = imageMobile;
  }

  public String getImageWeb() {
    return imageWeb;
  }

  public void setImageWeb(String imageWeb) {
    this.imageWeb = imageWeb;
  }

  @Override
  public Boolean isValid() {
    return true;
  }
}
