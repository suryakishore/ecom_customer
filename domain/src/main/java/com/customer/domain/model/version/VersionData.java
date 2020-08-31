package com.customer.domain.model.version;

public class VersionData {

  private boolean mandatory;

  private String version;

  public VersionData(boolean mandatory, String version) {
    this.mandatory = mandatory;
    this.version = version;
  }

  public boolean isMandatory() {
    return mandatory;
  }

  public void setMandatory(boolean mandatory) {
    this.mandatory = mandatory;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }
}
