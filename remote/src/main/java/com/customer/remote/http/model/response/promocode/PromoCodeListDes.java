package com.customer.remote.http.model.response.promocode;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PromoCodeListDes implements Parcelable {
  @SerializedName("contain")
  @Expose
  private String contain;

  protected PromoCodeListDes(Parcel in) {
    contain = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(contain);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<PromoCodeListDes> CREATOR = new Creator<PromoCodeListDes>() {
    @Override
    public PromoCodeListDes createFromParcel(Parcel in) {
      return new PromoCodeListDes(in);
    }

    @Override
    public PromoCodeListDes[] newArray(int size) {
      return new PromoCodeListDes[size];
    }
  };

  public String getContain ()
  {
    return contain;
  }

  public void setContain (String contain)
  {
    this.contain = contain;
  }

}
