package com.customer.remote.http.model.response.recentsearch;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecentSearchSuggestion implements Parcelable {
  @Expose
  @SerializedName("timestamp")
  private String timestamp;

  @Expose
  @SerializedName("searchText")
  private String searchText;

  @Expose
  @SerializedName("searchIn")
  private String searchIn;

  public RecentSearchSuggestion(String timestamp, String searchText, String searchIn) {
    this.timestamp = timestamp;
    this.searchText = searchText;
    this.searchIn = searchIn;
  }

  protected RecentSearchSuggestion(Parcel in) {
    timestamp = in.readString();
    searchText = in.readString();
    searchIn = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(timestamp);
    dest.writeString(searchText);
    dest.writeString(searchIn);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<RecentSearchSuggestion> CREATOR =
      new Creator<RecentSearchSuggestion>() {
        @Override
        public RecentSearchSuggestion createFromParcel(Parcel in) {
          return new RecentSearchSuggestion(in);
        }

        @Override
        public RecentSearchSuggestion[] newArray(int size) {
          return new RecentSearchSuggestion[size];
        }
      };

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public String getSearchText() {
    return searchText;
  }

  public void setSearchText(String searchText) {
    this.searchText = searchText;
  }

  public String getSearchIn() {
    return searchIn;
  }

  public void setSearchIn(String searchIn) {
    this.searchIn = searchIn;
  }
}
