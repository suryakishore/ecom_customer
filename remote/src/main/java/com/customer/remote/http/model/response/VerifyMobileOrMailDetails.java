package com.customer.remote.http.model.response;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;

public class VerifyMobileOrMailDetails implements ValidItem, Parcelable {
  protected VerifyMobileOrMailDetails(Parcel in) {
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<VerifyMobileOrMailDetails> CREATOR =
      new Creator<VerifyMobileOrMailDetails>() {
        @Override
        public VerifyMobileOrMailDetails createFromParcel(Parcel in) {
          return new VerifyMobileOrMailDetails(in);
        }

        @Override
        public VerifyMobileOrMailDetails[] newArray(int size) {
          return new VerifyMobileOrMailDetails[size];
        }
      };

  @Override
  public Boolean isValid() {
    return true;
  }
}
