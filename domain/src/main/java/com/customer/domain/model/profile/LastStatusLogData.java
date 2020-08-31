package com.customer.domain.model.profile;

public class LastStatusLogData {

  private String actionByUserId;

  private String actionByUserType;

  private String action;

  private String status;

  private String timestamp;

  public LastStatusLogData(String actionByUserId, String actionByUserType, String action,
      String status, String timestamp) {
    this.actionByUserId = actionByUserId;
    this.actionByUserType = actionByUserType;
    this.action = action;
    this.status = status;
    this.timestamp = timestamp;
  }

  public String getActionByUserId() {
    return actionByUserId;
  }

  public void setActionByUserId(String actionByUserId) {
    this.actionByUserId = actionByUserId;
  }

  public String getActionByUserType() {
    return actionByUserType;
  }

  public void setActionByUserType(String actionByUserType) {
    this.actionByUserType = actionByUserType;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }
}
