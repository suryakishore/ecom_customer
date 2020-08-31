package com.customer.remote.http.model.response.home;

import android.os.Parcel;
import android.os.Parcelable;
import com.customer.remote.http.core.ValidItem;
import com.customer.remote.http.model.response.common.OffersDataDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class OfferDetails implements ValidItem, Parcelable {

  @SerializedName("data")
  @Expose
  private ArrayList<OffersDataDetails> data;

  @SerializedName("message")
  @Expose
  private String message;

  @SerializedName("type")
  @Expose
  private int type;

  public OfferDetails(ArrayList<OffersDataDetails> data, String message, int type) {
    this.data = data;
    this.message = message;
    this.type = type;
  }

  protected OfferDetails(Parcel in) {
    data = in.createTypedArrayList(OffersDataDetails.CREATOR);
    message = in.readString();
    type = in.readInt();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeTypedList(data);
    dest.writeString(message);
    dest.writeInt(type);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<OfferDetails> CREATOR = new Creator<OfferDetails>() {
    @Override
    public OfferDetails createFromParcel(Parcel in) {
      return new OfferDetails(in);
    }

    @Override
    public OfferDetails[] newArray(int size) {
      return new OfferDetails[size];
    }
  };

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public ArrayList<OffersDataDetails> getData() {
    return data;
  }

  public void setData(ArrayList<OffersDataDetails> data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public Boolean isValid() {
    return true;
  }
}
