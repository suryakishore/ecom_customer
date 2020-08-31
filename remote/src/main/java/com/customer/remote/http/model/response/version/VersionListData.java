package com.customer.remote.http.model.response.version;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionListData implements Parcelable {
  @SerializedName("mandatory")
  @Expose
  private Boolean mandatory;
  @SerializedName("version")
  @Expose
  private String version;

  public VersionListData() {
  }

  protected VersionListData(Parcel in) {
    byte tmpMandatory = in.readByte();
    mandatory = tmpMandatory == 0 ? null : tmpMandatory == 1;
    version = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeByte((byte) (mandatory == null ? 0 : mandatory ? 1 : 2));
    dest.writeString(version);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<VersionListData> CREATOR = new Creator<VersionListData>() {
    @Override
    public VersionListData createFromParcel(Parcel in) {
      return new VersionListData(in);
    }

    @Override
    public VersionListData[] newArray(int size) {
      return new VersionListData[size];
    }
  };

  public Boolean getMandatory() {
    return mandatory;
  }

  public void setMandatory(Boolean mandatory) {
    this.mandatory = mandatory;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }
}
