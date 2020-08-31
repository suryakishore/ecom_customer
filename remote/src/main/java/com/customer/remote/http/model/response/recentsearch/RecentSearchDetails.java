package com.customer.remote.http.model.response.recentsearch;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class RecentSearchDetails implements Parcelable
{
  @Expose
  @SerializedName("message")
  private String message;

  @Expose
  @SerializedName("totalCount")
  private int totalCount;

  @Expose
  @SerializedName("data")
  private ArrayList<RecentSearchSuggestion> data;

  public RecentSearchDetails(String message, int totalCount,
      ArrayList<RecentSearchSuggestion> data) {
    this.message = message;
    this.totalCount = totalCount;
    this.data = data;
  }

  protected RecentSearchDetails(Parcel in) {
    message = in.readString();
    totalCount = in.readInt();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(message);
    dest.writeInt(totalCount);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<RecentSearchDetails> CREATOR = new Creator<RecentSearchDetails>() {
    @Override
    public RecentSearchDetails createFromParcel(Parcel in) {
      return new RecentSearchDetails(in);
    }

    @Override
    public RecentSearchDetails[] newArray(int size) {
      return new RecentSearchDetails[size];
    }
  };

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }

  public ArrayList<RecentSearchSuggestion> getData() {
    return data;
  }

  public void setData(
      ArrayList<RecentSearchSuggestion> data) {
    this.data = data;
  }
}
