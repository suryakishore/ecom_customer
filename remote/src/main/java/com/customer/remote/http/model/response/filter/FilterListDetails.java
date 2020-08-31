package com.customer.remote.http.model.response.filter;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class FilterListDetails implements ValidItem, Parcelable {

  @SerializedName("name")
  @Expose
  private String name;

  @SerializedName("currency")
  @Expose
  private String currency;

  @SerializedName("currencySymbol")
  @Expose
  private String currencySymbol;

  @SerializedName("selType")
  @Expose
  private int selType;

  @SerializedName("level")
  @Expose
  private int level;

  @SerializedName("filterType")
  @Expose
  private int filterType;

  @SerializedName("data")
  @Expose
  private ArrayList<FilterListDataDetails> data;

  public FilterListDetails(String name, String currency,
      String currencySymbol, int selType, int level,
      ArrayList<FilterListDataDetails> data, int filterType) {
    this.name = name;
    this.currency = currency;
    this.currencySymbol = currencySymbol;
    this.selType = selType;
    this.level = level;
    this.data = data;
    this.filterType = filterType;
  }

  protected FilterListDetails(Parcel in) {
    name = in.readString();
    currency = in.readString();
    currencySymbol = in.readString();
    selType = in.readInt();
    level = in.readInt();
    filterType = in.readInt();
    data = in.createTypedArrayList(FilterListDataDetails.CREATOR);
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
    dest.writeString(currency);
    dest.writeString(currencySymbol);
    dest.writeInt(selType);
    dest.writeInt(level);
    dest.writeInt(filterType);
    dest.writeTypedList(data);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<FilterListDetails> CREATOR = new Creator<FilterListDetails>() {
    @Override
    public FilterListDetails createFromParcel(Parcel in) {
      return new FilterListDetails(in);
    }

    @Override
    public FilterListDetails[] newArray(int size) {
      return new FilterListDetails[size];
    }
  };

  public int getFilterType() {
    return filterType;
  }

  public void setFilterType(int filterType) {
    this.filterType = filterType;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getCurrencySymbol() {
    return currencySymbol;
  }

  public void setCurrencySymbol(String currencySymbol) {
    this.currencySymbol = currencySymbol;
  }

  public int getSelType() {
    return selType;
  }

  public void setSelType(int selType) {
    this.selType = selType;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public ArrayList<FilterListDataDetails> getData() {
    return data;
  }

  public void setData(ArrayList<FilterListDataDetails> data) {
    this.data = data;
  }

  @Override
  public Boolean isValid() {
    return true;
  }
}
