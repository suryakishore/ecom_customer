package com.customer.remote.http.model.request;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionRequest implements Parcelable {
  public static final Creator<VersionRequest> CREATOR = new Creator<VersionRequest>() {
    @Override
    public VersionRequest createFromParcel(Parcel in) {
      return new VersionRequest(in);
    }

    @Override
    public VersionRequest[] newArray(int size) {
      return new VersionRequest[size];
    }
  };
  @SerializedName("type")
  @Expose
  private int type;
  @SerializedName("version")
  @Expose
  private String version;

  public VersionRequest(int type, String version) {
    this.type = type;
    this.version = version;
  }

  protected VersionRequest(Parcel in) {
    type = in.readInt();
    version = in.readString();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(type);
    dest.writeString(version);
  }
}
