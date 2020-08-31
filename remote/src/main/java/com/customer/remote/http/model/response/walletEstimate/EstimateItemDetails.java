package com.customer.remote.http.model.response.walletEstimate;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EstimateItemDetails  implements Parcelable {
  @SerializedName("estimateAmount")
  @Expose
  private String estimateAmount;


  public EstimateItemDetails(String estimateAmount) {
    this.estimateAmount = estimateAmount;

  }

  protected EstimateItemDetails(Parcel in) {
    estimateAmount = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(estimateAmount);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<EstimateItemDetails> CREATOR = new Creator<EstimateItemDetails>() {
    @Override
    public EstimateItemDetails createFromParcel(Parcel in) {
      return new EstimateItemDetails(in);
    }

    @Override
    public EstimateItemDetails[] newArray(int size) {
      return new EstimateItemDetails[size];
    }
  };

  public String getEstimateAmount() {
    return estimateAmount;
  }

  public void setEstimateAmount(String estimateAmount) {
    this.estimateAmount = estimateAmount;
  }
}
