package com.customer.remote.http.model.response.notification;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationData implements Parcelable {
  @SerializedName("date")
  @Expose
  private String date;
  @SerializedName("notificationTypeMsg")
  @Expose
  private String notificationTypeMsg;
  @SerializedName("userTypeMsg")
  @Expose
  private String userTypeMsg;
  @SerializedName("appName")
  @Expose
  private String appName;
  @SerializedName("topic")
  @Expose
  private String topic;
  @SerializedName("userType")
  @Expose
  private String userType;
  @SerializedName("notificationType")
  @Expose
  private String notificationType;
  @SerializedName("body")
  @Expose
  private String body;


  @SerializedName("image")
  @Expose
  private String image;

  @SerializedName("userName")
  @Expose
  private String userName;
  @SerializedName("title")
  @Expose
  private String title;
  @SerializedName("masterOrderId")
  @Expose
  private String masterOrderId;

  @SerializedName("productOrderId")
  @Expose
  private String productOrderId;

  @SerializedName("packageId")
  @Expose
  private String packageId;

  protected NotificationData(Parcel in) {
    date = in.readString();
    notificationTypeMsg = in.readString();
    userTypeMsg = in.readString();
    appName = in.readString();
    topic = in.readString();
    userType = in.readString();
    notificationType = in.readString();
    body = in.readString();
    image = in.readString();
    userName = in.readString();
    title = in.readString();
    masterOrderId = in.readString();
    productOrderId = in.readString();
    packageId = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(date);
    dest.writeString(notificationTypeMsg);
    dest.writeString(userTypeMsg);
    dest.writeString(appName);
    dest.writeString(topic);
    dest.writeString(userType);
    dest.writeString(notificationType);
    dest.writeString(body);
    dest.writeString(image);
    dest.writeString(userName);
    dest.writeString(title);
    dest.writeString(masterOrderId);
    dest.writeString(productOrderId);
    dest.writeString(packageId);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<NotificationData> CREATOR = new Creator<NotificationData>() {
    @Override
    public NotificationData createFromParcel(Parcel in) {
      return new NotificationData(in);
    }

    @Override
    public NotificationData[] newArray(int size) {
      return new NotificationData[size];
    }
  };

  public String getDate ()
  {
    return date;
  }

  public void setDate (String date)
  {
    this.date = date;
  }

  public String getNotificationTypeMsg ()
  {
    return notificationTypeMsg;
  }

  public void setNotificationTypeMsg (String notificationTypeMsg)
  {
    this.notificationTypeMsg = notificationTypeMsg;
  }

  public String getUserTypeMsg ()
  {
    return userTypeMsg;
  }

  public void setUserTypeMsg (String userTypeMsg)
  {
    this.userTypeMsg = userTypeMsg;
  }

  public String getAppName ()
  {
    return appName;
  }

  public void setAppName (String appName)
  {
    this.appName = appName;
  }

  public String getTopic ()
  {
    return topic;
  }

  public void setTopic (String topic)
  {
    this.topic = topic;
  }

  public String getUserType ()
  {
    return userType;
  }

  public void setUserType (String userType)
  {
    this.userType = userType;
  }

  public String getNotificationType ()
  {
    return notificationType;
  }

  public void setNotificationType (String notificationType)
  {
    this.notificationType = notificationType;
  }

  public String getBody ()
  {
    return body;
  }

  public void setBody (String body)
  {
    this.body = body;
  }

  public String getUserName ()
  {
    return userName;
  }

  public void setUserName (String userName)
  {
    this.userName = userName;
  }

  public String getTitle ()
  {
    return title;
  }

  public void setTitle (String title)
  {
    this.title = title;
  }

  public String getMasterOrderId ()
  {
    return masterOrderId;
  }

  public void setMasterOrderId (String masterOrderId)
  {
    this.masterOrderId = masterOrderId;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getProductOrderId() {
    return productOrderId;
  }

  public void setProductOrderId(String productOrderId) {
    this.productOrderId = productOrderId;
  }

  public String getPackageId() {
    return packageId;
  }

  public void setPackageId(String packageId) {
    this.packageId = packageId;
  }
}
