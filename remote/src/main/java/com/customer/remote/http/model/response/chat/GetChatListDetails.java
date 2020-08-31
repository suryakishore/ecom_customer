package com.customer.remote.http.model.response.chat;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class GetChatListDetails implements Parcelable {
  @SerializedName("data")
  @Expose
  private ArrayList<GetChatItemDetails> data;
  @SerializedName("message")
  @Expose
  private String message;

  public GetChatListDetails(
      ArrayList<GetChatItemDetails> data, String message) {
    this.data = data;
    this.message = message;
  }

  protected GetChatListDetails(Parcel in) {
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

  public static final Creator<GetChatListDetails> CREATOR = new Creator<GetChatListDetails>() {
    @Override
    public GetChatListDetails createFromParcel(Parcel in) {
      return new GetChatListDetails(in);
    }

    @Override
    public GetChatListDetails[] newArray(int size) {
      return new GetChatListDetails[size];
    }
  };

  public ArrayList<GetChatItemDetails> getData ()
  {
    return data;
  }

  public void setData (ArrayList<GetChatItemDetails> data)
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
