package com.customer.remote.http.model.response.changelanguage;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeLanItemDetails implements Parcelable {
  @SerializedName("langCode")
  @Expose
  private String langCode;
  @SerializedName("lan_name")
  @Expose
  private String lan_name;

  protected ChangeLanItemDetails(Parcel in) {
    langCode = in.readString();
    lan_name = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(langCode);
    dest.writeString(lan_name);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<ChangeLanItemDetails> CREATOR = new Creator<ChangeLanItemDetails>() {
    @Override
    public ChangeLanItemDetails createFromParcel(Parcel in) {
      return new ChangeLanItemDetails(in);
    }

    @Override
    public ChangeLanItemDetails[] newArray(int size) {
      return new ChangeLanItemDetails[size];
    }
  };

  public String getLanguageCode() {
    return langCode;
  }

  public void setLanguageCode(String langCode) {
    this.langCode = langCode;
  }

  public String getLanguageName() {
    return lan_name;
  }

  public void setLanguageName(String lan_name) {
    this.lan_name = lan_name;
  }



}
