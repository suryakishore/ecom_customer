package com.customer.domain.model.getcart;

import android.os.Parcel;
import android.os.Parcelable;

public class CartAttributesData implements Parcelable {
  private String attrname;

  private String value;

  public CartAttributesData(String attrname, String value) {
    this.attrname = attrname;
    this.value = value;
  }

  protected CartAttributesData(Parcel in) {
    attrname = in.readString();
    value = in.readString();
  }

  public static final Creator<CartAttributesData> CREATOR = new Creator<CartAttributesData>() {
    @Override
    public CartAttributesData createFromParcel(Parcel in) {
      return new CartAttributesData(in);
    }

    @Override
    public CartAttributesData[] newArray(int size) {
      return new CartAttributesData[size];
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

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(attrname);
    dest.writeString(value);
  }


}
