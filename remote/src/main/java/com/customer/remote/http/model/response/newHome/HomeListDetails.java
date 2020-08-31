package com.customer.remote.http.model.response.newHome;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class HomeListDetails implements ValidItem, Parcelable {

  @SerializedName("list")
  @Expose
  private ArrayList<ListDetails> list;

  @SerializedName("totalCatCount")
  @Expose
  private int totalCatCount;

  public HomeListDetails(
      ArrayList<ListDetails> list, int totalCatCount) {
    this.list = list;
    this.totalCatCount = totalCatCount;
  }

  protected HomeListDetails(Parcel in) {
    totalCatCount = in.readInt();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(totalCatCount);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<HomeListDetails> CREATOR = new Creator<HomeListDetails>() {
    @Override
    public HomeListDetails createFromParcel(Parcel in) {
      return new HomeListDetails(in);
    }

    @Override
    public HomeListDetails[] newArray(int size) {
      return new HomeListDetails[size];
    }
  };

  public int getTotalCatCount() {
    return totalCatCount;
  }

  public void setTotalCatCount(int totalCatCount) {
    this.totalCatCount = totalCatCount;
  }

  public ArrayList<ListDetails> getList() {
    return list;
  }

  public void setList(ArrayList<ListDetails> list) {
    this.list = list;
  }

  @Override
  public Boolean isValid() {
    return true;
  }
}
