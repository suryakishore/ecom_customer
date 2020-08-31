package com.customer.remote.http.model.response.generatetoken;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.model.response.tracking.TrackingListOrderStatusData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenerateTokenListDetails implements Parcelable{
  @SerializedName("data")
  @Expose
  private GenerateTokenItemDetails data;
  @SerializedName("message")
  @Expose
  private String message;

  public GenerateTokenListDetails(GenerateTokenItemDetails data, String message) {
    this.data = data;
    this.message = message;
  }

  protected GenerateTokenListDetails(Parcel in) {
    data = in.readParcelable(GenerateTokenItemDetails.class.getClassLoader());
    message = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeParcelable(data, flags);
    dest.writeString(message);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<GenerateTokenListDetails> CREATOR = new Creator<GenerateTokenListDetails>() {
    @Override
    public GenerateTokenListDetails createFromParcel(Parcel in) {
      return new GenerateTokenListDetails(in);
    }

    @Override
    public GenerateTokenListDetails[] newArray(int size) {
      return new GenerateTokenListDetails[size];
    }
  };

  public GenerateTokenItemDetails getData() {
    return data;
  }

  public void setData(GenerateTokenItemDetails data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
