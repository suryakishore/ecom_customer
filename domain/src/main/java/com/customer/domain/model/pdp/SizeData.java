package com.customer.domain.model.pdp;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class SizeData implements Parcelable {
  public static final Creator<SizeData> CREATOR = new Creator<SizeData>() {
    @Override
    public SizeData createFromParcel(Parcel in) {
      return new SizeData(in);
    }

    @Override
    public SizeData[] newArray(int size) {
      return new SizeData[size];
    }
  };
  private String rgb;
  private String image;
  private ArrayList<String> size;
  private String sizeString;
  private boolean isPrimary;
  private String childProductId;
  private String name;
  private String unitId;

  public SizeData(String rgb, String image, ArrayList<String> size, boolean isPrimary,
      String childProductId, String name, String unitId) {
    this.rgb = rgb;
    this.image = image;
    this.size = size;
    this.isPrimary = isPrimary;
    this.childProductId = childProductId;
    this.name = name;
    this.unitId = unitId;
  }

  public SizeData(String rgb, String image, String sizeString, boolean isPrimary,
      String childProductId, String name, String unitId) {
    this.rgb = rgb;
    this.image = image;
    this.sizeString = sizeString;
    this.isPrimary = isPrimary;
    this.childProductId = childProductId;
    this.name = name;
    this.unitId = unitId;
  }

  public SizeData(String sizeString, boolean isPrimary, String childProductId) {
    this.sizeString = sizeString;
    this.isPrimary = isPrimary;
    this.childProductId = childProductId;
  }

  public SizeData(ArrayList<String> size, boolean isPrimary, String childProductId) {
    this.size = size;
    this.isPrimary = isPrimary;
    this.childProductId = childProductId;
  }

  protected SizeData(Parcel in) {
    rgb = in.readString();
    image = in.readString();
    //if (in.readByte() == 0x01) {
    // size = new ArrayList<String>();
    // in.readList(size, String.class.getClassLoader());
    /*} else {
      size = null;
    }*/
    sizeString = in.readString();
    isPrimary = in.readBoolean();
    childProductId = in.readString();
    name = in.readString();
    unitId = in.readString();
  }

  public String getRgb() {
    return rgb;
  }

  public void setRgb(String rgb) {
    this.rgb = rgb;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public ArrayList<String> getSize() {
    return size;
  }

  public void setSize(ArrayList<String> size) {
    this.size = size;
  }

  public boolean getIsPrimary() {
    return isPrimary;
  }

  public void setIsPrimary(boolean isPrimary) {
    this.isPrimary = isPrimary;
  }

  public String getChildProductId() {
    return childProductId;
  }

  public void setChildProductId(String childProductId) {
    this.childProductId = childProductId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUnitId() {
    return unitId;
  }

  public void setUnitId(String unitId) {
    this.unitId = unitId;
  }

  public String getSizeString() {
    return sizeString;
  }

  public void setSizeString(String sizeString) {
    this.sizeString = sizeString;
  }

  public boolean isPrimary() {
    return isPrimary;
  }

  public void setPrimary(boolean primary) {
    isPrimary = primary;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int i) {
    dest.writeString(rgb);
    dest.writeString(image);
    dest.writeString(sizeString);
    dest.writeBoolean(isPrimary);
    dest.writeString(childProductId);
    dest.writeString(name);
    dest.writeString(unitId);
  }

}
