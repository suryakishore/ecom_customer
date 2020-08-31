package com.customer.remote.http.model.response.getReasons;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.model.response.getratable.RatableProducts;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class GetReasonsDetails implements Parcelable {
  @SerializedName("data")
  @Expose
  private GetReasonsItemDetails data;

  @SerializedName("message")
  @Expose
  private String message;

  public GetReasonsDetails(
      GetReasonsItemDetails data, String message) {
    this.data = data;
    this.message = message;
  }

  protected GetReasonsDetails(Parcel in) {
    message = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(message);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<GetReasonsDetails> CREATOR = new Creator<GetReasonsDetails>() {
    @Override
    public GetReasonsDetails createFromParcel(Parcel in) {
      return new GetReasonsDetails(in);
    }

    @Override
    public GetReasonsDetails[] newArray(int size) {
      return new GetReasonsDetails[size];
    }
  };

  public GetReasonsItemDetails getData() {
    return data;
  }

  public void setReasonsData(
      GetReasonsItemDetails data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
