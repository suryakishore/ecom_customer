package com.customer.domain.model.notification;

public class NotificationListData {
  private String date;
  private String notificationTypeMsg;
  private String userTypeMsg;
  private String appName;
  private String topic;
  private String userType;
  private String notificationType;
  private String body;
  private String userName;
  private String title;
  private String masterOrderId;
  private String image;
  private String productOrderId;
  private String packageId;

  public NotificationListData(String date, String notificationTypeMsg, String userTypeMsg,
      String appName, String topic, String userType, String notificationType, String body,
      String image, String userName, String title, String masterOrderId, String productOrderId,
      String packageId) {
    this.date = date;
    this.notificationTypeMsg = notificationTypeMsg;
    this.userTypeMsg = userTypeMsg;
    this.appName = appName;
    this.topic = topic;
    this.userType = userType;
    this.notificationType = notificationType;
    this.body = body;
    this.image = image;
    this.userName = userName;
    this.title = title;
    this.masterOrderId = masterOrderId;
    this.productOrderId = productOrderId;
    this.packageId = packageId;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getNotificationTypeMsg() {
    return notificationTypeMsg;
  }

  public void setNotificationTypeMsg(String notificationTypeMsg) {
    this.notificationTypeMsg = notificationTypeMsg;
  }

  public String getUserTypeMsg() {
    return userTypeMsg;
  }

  public void setUserTypeMsg(String userTypeMsg) {
    this.userTypeMsg = userTypeMsg;
  }

  public String getAppName() {
    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }

  public String getNotificationType() {
    return notificationType;
  }

  public void setNotificationType(String notificationType) {
    this.notificationType = notificationType;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getMasterOrderId() {
    return masterOrderId;
  }

  public void setMasterOrderId(String masterOrderId) {
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
