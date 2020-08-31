package com.customer.remote.http.model.response.brands;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.model.response.newHome.CategoryDetails;
import com.customer.remote.http.model.response.tracking.TrackingItemDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class AllBrandsListDetails implements Parcelable {
  @SerializedName("penCount")
  @Expose
  private int penCount;
  @SerializedName("data")
  @Expose
  private ArrayList<CategoryDetails> data;
  @SerializedName("message")
  @Expose
  private String message;

  public AllBrandsListDetails(
      ArrayList<CategoryDetails> data, String message) {
    this.data = data;
    this.message = message;
  }

  protected AllBrandsListDetails(Parcel in) {
    penCount = in.readInt();
    data = in.createTypedArrayList(CategoryDetails.CREATOR);
    message = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(penCount);
    dest.writeTypedList(data);
    dest.writeString(message);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<AllBrandsListDetails> CREATOR = new Creator<AllBrandsListDetails>() {
    @Override
    public AllBrandsListDetails createFromParcel(Parcel in) {
      return new AllBrandsListDetails(in);
    }

    @Override
    public AllBrandsListDetails[] newArray(int size) {
      return new AllBrandsListDetails[size];
    }
  };

  public ArrayList<CategoryDetails> getData ()
  {
    return data;
  }

  public void setData (ArrayList<CategoryDetails> data)
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

  public int getPenCount() {
    return penCount;
  }

  public void setPenCount(int penCount) {
    this.penCount = penCount;
  }
}
