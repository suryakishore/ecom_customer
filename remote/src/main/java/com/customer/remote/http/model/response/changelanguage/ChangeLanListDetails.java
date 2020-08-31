package com.customer.remote.http.model.response.changelanguage;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.model.response.tracking.TrackingItemDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class ChangeLanListDetails implements Parcelable {
  @SerializedName("data")
  @Expose
  private ArrayList<ChangeLanItemDetails> data;
  @SerializedName("message")
  @Expose
  private String message;

  public ChangeLanListDetails(
      ArrayList<ChangeLanItemDetails> data, String message) {
    this.data = data;
    this.message = message;
  }

  protected ChangeLanListDetails(Parcel in) {
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

  public static final Creator<ChangeLanListDetails> CREATOR = new Creator<ChangeLanListDetails>() {
    @Override
    public ChangeLanListDetails createFromParcel(Parcel in) {
      return new ChangeLanListDetails(in);
    }

    @Override
    public ChangeLanListDetails[] newArray(int size) {
      return new ChangeLanListDetails[size];
    }
  };

  public ArrayList<ChangeLanItemDetails> getData ()
  {
    return data;
  }

  public void setData (ArrayList<ChangeLanItemDetails> data)
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
