package com.customer.remote.http.model.response.walletEstimate;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.model.response.tracking.TrackingItemDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class WalletEstimateListDetails implements Parcelable {
  @SerializedName("data")
  @Expose
  private EstimateItemDetails data;
  @SerializedName("message")
  @Expose
  private String message;

  public WalletEstimateListDetails(
      EstimateItemDetails data, String message) {
    this.data = data;
    this.message = message;
  }

  protected WalletEstimateListDetails(Parcel in) {
    data = in.readParcelable(EstimateItemDetails.class.getClassLoader());
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

  public static final Creator<WalletEstimateListDetails> CREATOR =
      new Creator<WalletEstimateListDetails>() {
        @Override
        public WalletEstimateListDetails createFromParcel(Parcel in) {
          return new WalletEstimateListDetails(in);
        }

        @Override
        public WalletEstimateListDetails[] newArray(int size) {
          return new WalletEstimateListDetails[size];
        }
      };

  public EstimateItemDetails getData ()
  {
    return data;
  }

  public void setData (EstimateItemDetails data)
  {
    this.data = data;
  }

  public String getMessage ()
  {
    return message;
  }

  public void setMessage (String message)
  {
    this.message = message;
  }

}
