package com.customer.remote.http.model.response.notification;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class NotificationDetails implements Parcelable {
  @SerializedName("data")
  @Expose
  private ArrayList<NotificationData> data;
  @SerializedName("total_count")
  @Expose
  private String total_count;
  @SerializedName("message")
  @Expose
  private String message;

  protected NotificationDetails(Parcel in) {
    data = in.createTypedArrayList(NotificationData.CREATOR);
    total_count = in.readString();
    message = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeTypedList(data);
    dest.writeString(total_count);
    dest.writeString(message);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<NotificationDetails> CREATOR = new Creator<NotificationDetails>() {
    @Override
    public NotificationDetails createFromParcel(Parcel in) {
      return new NotificationDetails(in);
    }

    @Override
    public NotificationDetails[] newArray(int size) {
      return new NotificationDetails[size];
    }
  };

  public ArrayList<NotificationData> getData ()
  {
    return data;
  }

  public void setData (ArrayList<NotificationData> data)
  {
    this.data = data;
  }

  public String getTotal_count ()
  {
    return total_count;
  }

  public void setTotal_count (String total_count)
  {
    this.total_count = total_count;
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
