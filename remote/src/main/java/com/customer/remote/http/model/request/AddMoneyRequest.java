package com.customer.remote.http.model.request;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddMoneyRequest implements Parcelable {

  @Expose
  @SerializedName("cardId")
  private String cardId;

  @Expose
  @SerializedName("currency")
  private String currency;

  @Expose
  @SerializedName("amount")
  private String amount;

  public AddMoneyRequest(String cardId, String currency,String amount) {
    this.cardId = cardId;
    this.currency = currency;
    this.amount=amount;
  }

  protected AddMoneyRequest(Parcel in) {
    cardId = in.readString();
    currency = in.readString();
    amount = in.readString();
  }

  public static final Creator<AddMoneyRequest> CREATOR = new Creator<AddMoneyRequest>() {
    @Override
    public AddMoneyRequest createFromParcel(Parcel in) {
      return new AddMoneyRequest(in);
    }

    @Override
    public AddMoneyRequest[] newArray(int size) {
      return new AddMoneyRequest[size];
    }
  };

  public String getCardId() {
    return cardId;
  }

  public void setCardId(String cardId) {
    this.cardId = cardId;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(cardId);
    dest.writeString(currency);
    dest.writeString(amount);
  }
}
