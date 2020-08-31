package com.customer.remote.http.model.response.pdp;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class AttributesDetails implements ValidItem , Parcelable {

  @SerializedName("name")
  @Expose
  private String name;

  @SerializedName("innerAttributes")
  @Expose
  private ArrayList<InnerAttributesDetails> innerAttributes;

  public AttributesDetails(String name, ArrayList<InnerAttributesDetails> innerAttributes) {
    this.name = name;
    this.innerAttributes = innerAttributes;
  }

  protected AttributesDetails(Parcel in) {
    name = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<AttributesDetails> CREATOR = new Creator<AttributesDetails>() {
    @Override
    public AttributesDetails createFromParcel(Parcel in) {
      return new AttributesDetails(in);
    }

    @Override
    public AttributesDetails[] newArray(int size) {
      return new AttributesDetails[size];
    }
  };

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ArrayList<InnerAttributesDetails> getInnerAttributes() {
    return innerAttributes;
  }

  public void setInnerAttributes(ArrayList<InnerAttributesDetails> innerAttributes) {
    this.innerAttributes = innerAttributes;
  }

  @Override
  public Boolean isValid() {
    return true;
  }
}
