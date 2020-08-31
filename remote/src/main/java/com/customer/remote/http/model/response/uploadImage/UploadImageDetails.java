package com.customer.remote.http.model.response.uploadImage;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadImageDetails implements Parcelable {
  @SerializedName("data")
  @Expose
  private UploadImageItemDetails data;
  @SerializedName("message")
  @Expose
  private String message;

  protected UploadImageDetails(Parcel in) {
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

  public static final Creator<UploadImageDetails> CREATOR = new Creator<UploadImageDetails>() {
    @Override
    public UploadImageDetails createFromParcel(Parcel in) {
      return new UploadImageDetails(in);
    }

    @Override
    public UploadImageDetails[] newArray(int size) {
      return new UploadImageDetails[size];
    }
  };

  public UploadImageItemDetails getData ()
  {
    return data;
  }

  public void setData (UploadImageItemDetails data)
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
