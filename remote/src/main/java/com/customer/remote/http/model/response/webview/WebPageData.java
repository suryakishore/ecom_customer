package com.customer.remote.http.model.response.webview;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WebPageData implements Parcelable {

  @SerializedName("privacyObj")
  @Expose
  private String privacyObj;
  @SerializedName("_id")
  @Expose
  private String _id;
  @SerializedName("termsObj")
  @Expose
  private String termsObj;

  protected WebPageData(Parcel in) {
    privacyObj = in.readString();
    _id = in.readString();
    termsObj = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(privacyObj);
    dest.writeString(_id);
    dest.writeString(termsObj);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<WebPageData> CREATOR = new Creator<WebPageData>() {
    @Override
    public WebPageData createFromParcel(Parcel in) {
      return new WebPageData(in);
    }

    @Override
    public WebPageData[] newArray(int size) {
      return new WebPageData[size];
    }
  };

  public String getPrivacyObj() {
    return privacyObj;
  }

  public void setPrivacyObj(String privacyObj) {
    this.privacyObj = privacyObj;
  }

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public String getTermsObj() {
    return termsObj;
  }

  public void setTermsObj(String termsObj) {
    this.termsObj = termsObj;
  }
}
