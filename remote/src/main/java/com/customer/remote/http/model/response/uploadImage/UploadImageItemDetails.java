package com.customer.remote.http.model.response.uploadImage;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadImageItemDetails implements Parcelable {
  @SerializedName("imageUrl")
  @Expose
  private String imageUrl;

  protected UploadImageItemDetails(Parcel in) {
    imageUrl = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(imageUrl);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<UploadImageItemDetails> CREATOR =
      new Creator<UploadImageItemDetails>() {
        @Override
        public UploadImageItemDetails createFromParcel(Parcel in) {
          return new UploadImageItemDetails(in);
        }

        @Override
        public UploadImageItemDetails[] newArray(int size) {
          return new UploadImageItemDetails[size];
        }
      };

  public String getImageUrl ()
  {
    return imageUrl;
  }

  public void setImageUrl (String imageUrl)
  {
    this.imageUrl = imageUrl;
  }
}
