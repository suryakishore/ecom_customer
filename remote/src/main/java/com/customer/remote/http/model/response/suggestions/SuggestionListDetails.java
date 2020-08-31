package com.customer.remote.http.model.response.suggestions;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class SuggestionListDetails implements ValidItem, Parcelable {
  @SerializedName("data")
  @Expose
  private ArrayList<SuggestionItemDetails> data;

  @SerializedName("message")
  @Expose
  private String message;

  public SuggestionListDetails(
      ArrayList<SuggestionItemDetails> data, String message) {
    this.data = data;
    this.message = message;
  }

  protected SuggestionListDetails(Parcel in) {
    data = in.createTypedArrayList(SuggestionItemDetails.CREATOR);
    message = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeTypedList(data);
    dest.writeString(message);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<SuggestionListDetails> CREATOR =
      new Creator<SuggestionListDetails>() {
        @Override
        public SuggestionListDetails createFromParcel(Parcel in) {
          return new SuggestionListDetails(in);
        }

        @Override
        public SuggestionListDetails[] newArray(int size) {
          return new SuggestionListDetails[size];
        }
      };

  public ArrayList<SuggestionItemDetails> getData() {
    return data;
  }

  public void setData(
      ArrayList<SuggestionItemDetails> data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public Boolean isValid() {
    return true;
  }
}
