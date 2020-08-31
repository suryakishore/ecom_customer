package com.customer.domain.model.cancelreasons;

public class CancelReasonsItemData {
  private String reason;
  private String reasonMsg;
  private String customerUnavailable;
  private String reasonId;
  private String id;
  private String reAttemptAllowed;
  private String returnToStore;
  private boolean isChecked;

  public CancelReasonsItemData(String reason, String reasonMsg, String customerUnavailable,
      String reasonId, String id, String reAttemptAllowed, String returnToStore) {
    this.reason = reason;
    this.reasonMsg = reasonMsg;
    this.customerUnavailable = customerUnavailable;
    this.reasonId = reasonId;
    this.id = id;
    this.reAttemptAllowed = reAttemptAllowed;
    this.returnToStore = returnToStore;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public String getReasonMsg() {
    return reasonMsg;
  }

  public void setReasonMsg(String reasonMsg) {
    this.reasonMsg = reasonMsg;
  }

  public String getCustomerUnavailable() {
    return customerUnavailable;
  }

  public void setCustomerUnavailable(String customerUnavailable) {
    this.customerUnavailable = customerUnavailable;
  }

  public String getReasonId() {
    return reasonId;
  }

  public void setReasonId(String reasonId) {
    this.reasonId = reasonId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getReAttemptAllowed() {
    return reAttemptAllowed;
  }

  public void setReAttemptAllowed(String reAttemptAllowed) {
    this.reAttemptAllowed = reAttemptAllowed;
  }

  public String getReturnToStore() {
    return returnToStore;
  }

  public void setReturnToStore(String returnToStore) {
    this.returnToStore = returnToStore;
  }

  public boolean isChecked() {
    return isChecked;
  }

  public void setChecked(boolean checked) {
    isChecked = checked;
  }

  @Override
  public boolean equals(Object obj) {
    CancelReasonsItemData cancelReasonsItemData = (CancelReasonsItemData) obj;
    return this.isChecked == cancelReasonsItemData.isChecked();
  }
}
