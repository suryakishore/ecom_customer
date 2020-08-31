package com.customer.remote.http.model.response.home;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseDetails implements ValidItem , Parcelable {

  @SerializedName("bannerImage")
  @Expose
  private String bannerImage;

  @SerializedName("name")
  @Expose
  private String name;

  @SerializedName("logo")
  @Expose
  private String logo;

  @SerializedName("imageWeb")
  @Expose
  private String imageWeb;

  @SerializedName("id")
  @Expose
  private String id;

  @SerializedName("productCount")
  @Expose
  private String productCount;

  public ResponseDetails(String bannerImage, String name, String logo, String imageWeb, String id,
      String productCount) {
    this.bannerImage = bannerImage;
    this.name = name;
    this.logo = logo;
    this.imageWeb = imageWeb;
    this.id = id;
    this.productCount = productCount;
  }

  protected ResponseDetails(Parcel in) {
    bannerImage = in.readString();
    name = in.readString();
    logo = in.readString();
    imageWeb = in.readString();
    id = in.readString();
    productCount = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(bannerImage);
    dest.writeString(name);
    dest.writeString(logo);
    dest.writeString(imageWeb);
    dest.writeString(id);
    dest.writeString(productCount);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<ResponseDetails> CREATOR = new Creator<ResponseDetails>() {
    @Override
    public ResponseDetails createFromParcel(Parcel in) {
      return new ResponseDetails(in);
    }

    @Override
    public ResponseDetails[] newArray(int size) {
      return new ResponseDetails[size];
    }
  };

  public String getBannerImage() {
    return bannerImage;
  }

  public void setBannerImage(String bannerImage) {
    this.bannerImage = bannerImage;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLogo() {
    return logo;
  }

  public void setLogo(String logo) {
    this.logo = logo;
  }

  public String getImageWeb() {
    return imageWeb;
  }

  public void setImageWeb(String imageWeb) {
    this.imageWeb = imageWeb;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getProductCount() {
    return productCount;
  }

  public void setProductCount(String productCount) {
    this.productCount = productCount;
  }

  @Override
  public Boolean isValid() {
    return true;
  }
}
