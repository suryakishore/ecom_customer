package com.customer.domain.model.changelan;

import java.util.ArrayList;

public class ChangeLanData {
  private ArrayList<ChangeLanDetails> data;
  private String message;

  public ChangeLanData(
      ArrayList<ChangeLanDetails> data, String message) {
    this.data = data;
    this.message = message;
  }

  public ArrayList<ChangeLanDetails> getData() {
    return data;
  }

  public void setData(ArrayList<ChangeLanDetails> data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
