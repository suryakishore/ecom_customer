package com.customer.domain.model.filter;

import java.util.ArrayList;

public class FilterData {

  private String currency;

  private String unitId;

  private String message;

  private ArrayList<FilterList> filters;

  public FilterData(String currency, String unitId, String message, ArrayList<FilterList> filters) {
    this.currency = currency;
    this.unitId = unitId;
    this.message = message;
    this.filters = filters;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getUnitId() {
    return unitId;
  }

  public void setUnitId(String unitId) {
    this.unitId = unitId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ArrayList<FilterList> getFilters() {
    return filters;
  }

  public void setFilters(ArrayList<FilterList> filters) {
    this.filters = filters;
  }
}
