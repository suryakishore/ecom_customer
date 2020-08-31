package com.customer.remote.http.model.response.getReasons;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class GetReasonsItemDetails implements Parcelable {
  @SerializedName("reasonData")
  @Expose
  private ArrayList<ReasonData> reasonData;
  @SerializedName("totalCount")
  @Expose
  private String totalCount;

  protected GetReasonsItemDetails(Parcel in) {
    totalCount = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(totalCount);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<GetReasonsItemDetails> CREATOR =
      new Creator<GetReasonsItemDetails>() {
        @Override
        public GetReasonsItemDetails createFromParcel(Parcel in) {
          return new GetReasonsItemDetails(in);
        }

        @Override
        public GetReasonsItemDetails[] newArray(int size) {
          return new GetReasonsItemDetails[size];
        }
      };

  public ArrayList<ReasonData> getReasonData ()
  {
    return reasonData;
  }

  public void setReasonData (ArrayList<ReasonData> reasonData)
  {
    this.reasonData = reasonData;
  }

  public String getTotalCount ()
  {
    return totalCount;
  }

  public void setTotalCount (String totalCount)
  {
    this.totalCount = totalCount;
  }
}
