package com.customer.domain.model.chat;

import android.os.Parcel;
import java.util.ArrayList;

public class GetChatDetails {
  private ArrayList<GetChatData> data;
  private String message;

  public GetChatDetails(ArrayList<GetChatData> data, String message) {
    this.data = data;
    this.message = message;
  }

  protected GetChatDetails(Parcel in) {
    message = in.readString();
  }

  public ArrayList<GetChatData> getData() {
    return data;
  }

  public void setData(ArrayList<GetChatData> data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
