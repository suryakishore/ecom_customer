package com.customer.remote.http.model.response.getcart;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartAttributesDetails implements Parcelable {

  @Expose
  @SerializedName("attrname")
  private String attrname;

  @Expose
  @SerializedName("value")
  private String value;

  public CartAttributesDetails(String attrname, String value) {
    this.attrname = attrname;
    this.value = value;
  }

  protected CartAttributesDetails(Parcel in) {
    attrname = in.readString();
    value = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(attrname);
    dest.writeString(value);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<CartAttributesDetails> CREATOR =
      new Creator<CartAttributesDetails>() {
        @Override
        public CartAttributesDetails createFromParcel(Parcel in) {
          return new CartAttributesDetails(in);
        }

        @Override
        public CartAttributesDetails[] newArray(int size) {
          return new CartAttributesDetails[size];
        }
      };

  public String getAttrname() {
    return attrname;
  }

  public void setAttrname(String attrname) {
    this.attrname = attrname;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
