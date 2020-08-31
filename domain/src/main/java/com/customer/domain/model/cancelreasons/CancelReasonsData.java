package com.customer.domain.model.cancelreasons;

import java.util.ArrayList;

public class CancelReasonsData {
  private ArrayList<CancelReasonsItemData> reasonData;
  private String totalCount;

  public CancelReasonsData(
      ArrayList<CancelReasonsItemData> reasonData, String totalCount) {
    this.reasonData = reasonData;
    this.totalCount = totalCount;
  }

  public ArrayList<CancelReasonsItemData> getReasonData() {
    return reasonData;
  }

  public void setReasonData(ArrayList<CancelReasonsItemData> reasonData) {
    this.reasonData = reasonData;
  }

  public String getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(String totalCount) {
    this.totalCount = totalCount;
  }
}
