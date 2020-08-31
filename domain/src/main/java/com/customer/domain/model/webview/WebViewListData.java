package com.customer.domain.model.webview;

public class WebViewListData {
  private String privacyObj;
  private String _id;
  private String termsObj;

  public WebViewListData(String privacyObj, String _id, String termsObj) {
    this.privacyObj = privacyObj;
    this._id = _id;
    this.termsObj = termsObj;
  }

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
