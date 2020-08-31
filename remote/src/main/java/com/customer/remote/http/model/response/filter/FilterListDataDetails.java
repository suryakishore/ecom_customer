package com.customer.remote.http.model.response.filter;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class FilterListDataDetails implements ValidItem, Parcelable {

  @SerializedName("name")
  @Expose
  private String name;

  @SerializedName("rgb")
  @Expose
  private String rgb;

  @SerializedName("selType")
  @Expose
  private int selType;

  @SerializedName("maxPrice")
  @Expose
  private double maxPrice;

  @SerializedName("minPrice")
  @Expose
  private double minPrice;

  @SerializedName("data")
  @Expose
  private ArrayList<String> data;

  public FilterListDataDetails(String name, String rgb, int selType, ArrayList<String> data,
      double maxPrice, double minPrice) {
    this.name = name;
    this.rgb = rgb;
    this.selType = selType;
    this.data = data;
  }

  protected FilterListDataDetails(Parcel in) {
    name = in.readString();
    rgb = in.readString();
    selType = in.readInt();
    maxPrice = in.readDouble();
    minPrice = in.readDouble();
    data = in.createStringArrayList();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
    dest.writeString(rgb);
    dest.writeInt(selType);
    dest.writeDouble(maxPrice);
    dest.writeDouble(minPrice);
    dest.writeStringList(data);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<FilterListDataDetails> CREATOR =
      new Creator<FilterListDataDetails>() {
        @Override
        public FilterListDataDetails createFromParcel(Parcel in) {
          return new FilterListDataDetails(in);
        }

        @Override
        public FilterListDataDetails[] newArray(int size) {
          return new FilterListDataDetails[size];
        }
      };

  public double getMaxPrice() {
    return maxPrice;
  }

  public void setMaxPrice(double maxPrice) {
    this.maxPrice = maxPrice;
  }

  public double getMinPrice() {
    return minPrice;
  }

  public void setMinPrice(double minPrice) {
    this.minPrice = minPrice;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRgb() {
    return rgb;
  }

  public void setRgb(String rgb) {
    this.rgb = rgb;
  }

  public int getSelType() {
    return selType;
  }

  public void setSelType(int selType) {
    this.selType = selType;
  }

  public ArrayList<String> getData() {
    return data;
  }

  public void setData(ArrayList<String> data) {
    this.data = data;
  }

  @Override
  public Boolean isValid() {
    return true;
  }
}
