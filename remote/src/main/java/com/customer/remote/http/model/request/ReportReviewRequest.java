package com.customer.remote.http.model.request;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportReviewRequest implements Parcelable {

  @SerializedName("reviewId")
  @Expose
  private String reviewId;

  public ReportReviewRequest(String reviewId) {
    this.reviewId = reviewId;
  }

  protected ReportReviewRequest(Parcel in) {
    reviewId = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(reviewId);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<ReportReviewRequest> CREATOR = new Creator<ReportReviewRequest>() {
    @Override
    public ReportReviewRequest createFromParcel(Parcel in) {
      return new ReportReviewRequest(in);
    }

    @Override
    public ReportReviewRequest[] newArray(int size) {
      return new ReportReviewRequest[size];
    }
  };

  public String getReviewId() {
    return reviewId;
  }

  public void setReviewId(String reviewId) {
    this.reviewId = reviewId;
  }
}
