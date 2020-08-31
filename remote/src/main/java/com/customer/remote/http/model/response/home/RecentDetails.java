package com.customer.remote.http.model.response.home;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;

public class RecentDetails implements ValidItem, Parcelable {

  public RecentDetails() {
  }

  protected RecentDetails(Parcel in) {
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<RecentDetails> CREATOR = new Creator<RecentDetails>() {
    @Override
    public RecentDetails createFromParcel(Parcel in) {
      return new RecentDetails(in);
    }

    @Override
    public RecentDetails[] newArray(int size) {
      return new RecentDetails[size];
    }
  };

  @Override
  public Boolean isValid() {
    return true;
  }
}
