package com.customer.domain.model.changelan;

public class ChangeLanDetails  {

  private String langCode;
  private String lanName;

  public ChangeLanDetails(String langCode, String lanName) {
    this.langCode = langCode;
    this.lanName = lanName;
  }

  public String getLanguageCode() {
    return langCode;
  }

  public void setLanguageCode(String langCode) {
    this.langCode = langCode;
  }

  public String getLanguageName() {
    return lanName;
  }

  public void setLanguageName(String lanName) {
    this.lanName = lanName;
  }
}
