package com.customer.remote.http.model.response.help;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by dell on 13-Apr-18.
 */

public class HelpSubCatListDetails implements Parcelable {
  @SerializedName("desc")
  @Expose
  private String desc;
  @SerializedName("link")
  @Expose
  private String link;
  @SerializedName("name")
  @Expose
  private String name;

  protected HelpSubCatListDetails(Parcel in) {
    desc = in.readString();
    link = in.readString();
    name = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(desc);
    dest.writeString(link);
    dest.writeString(name);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<HelpSubCatListDetails> CREATOR =
      new Creator<HelpSubCatListDetails>() {
        @Override
        public HelpSubCatListDetails createFromParcel(Parcel in) {
          return new HelpSubCatListDetails(in);
        }

        @Override
        public HelpSubCatListDetails[] newArray(int size) {
          return new HelpSubCatListDetails[size];
        }
      };

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
