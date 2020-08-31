package com.customer.remote.http.model.response.filter;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class FilterDetails implements ValidItem, Parcelable {
  @SerializedName("currency")
  @Expose
  private String currency;

  @SerializedName("unitId")
  @Expose
  private String unitId;

  @SerializedName("message")
  @Expose
  private String message;

  @SerializedName("filters")
  @Expose
  private ArrayList<FilterListDetails> filters;

  public FilterDetails(String currency, String unitId, String message,
      ArrayList<FilterListDetails> filters) {
    this.currency = currency;
    this.unitId = unitId;
    this.message = message;
    this.filters = filters;
  }

  protected FilterDetails(Parcel in) {
    currency = in.readString();
    unitId = in.readString();
    message = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(currency);
    dest.writeString(unitId);
    dest.writeString(message);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<FilterDetails> CREATOR = new Creator<FilterDetails>() {
    @Override
    public FilterDetails createFromParcel(Parcel in) {
      return new FilterDetails(in);
    }

    @Override
    public FilterDetails[] newArray(int size) {
      return new FilterDetails[size];
    }
  };

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getUnitId() {
    return unitId;
  }

  public void setUnitId(String unitId) {
    this.unitId = unitId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ArrayList<FilterListDetails> getFilters() {
    return filters;
  }

  public void setFilters(ArrayList<FilterListDetails> filters) {
    this.filters = filters;
  }

  @Override
  public Boolean isValid() {
    return true;
  }
}
