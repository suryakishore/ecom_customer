package com.customer.remote.http.model.request;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Set;

public class ApplyFilterRequest implements Parcelable {
  private String catName;
  private String subCatName;
  private String subSubCatName;
  private String brandName;
  private String offerId;
  private String searchQuery;
  private int sortType;
  private HashMap<Integer, Set<String>> filterMap;

  public ApplyFilterRequest(HashMap<Integer, Set<String>> filterMap, String catName, int sortType) {
    this.catName = catName;
    this.filterMap = filterMap;
    this.sortType = sortType;
  }

  public ApplyFilterRequest(String catName, String subCatName,
      HashMap<Integer, Set<String>> filterMap, int sortType) {
    this.catName = catName;
    this.subCatName = subCatName;
    this.filterMap = filterMap;
    this.sortType = sortType;
  }

  public ApplyFilterRequest(String catName, String subCatName, String subSubCatName,
      HashMap<Integer, Set<String>> filterMap, int sortType) {

    this.catName = catName;
    this.subCatName = subCatName;
    this.subSubCatName = subSubCatName;
    this.filterMap = filterMap;
    this.sortType = sortType;
  }

  public ApplyFilterRequest(String searchQuery, HashMap<Integer, Set<String>> filterMap,
      int sortType) {
    this.searchQuery = searchQuery;
    this.filterMap = filterMap;
    this.sortType = sortType;
  }

  public ApplyFilterRequest(int level, String brandName, HashMap<Integer, Set<String>> filterMap,
      int sortType) {
    this.brandName = brandName;
    this.filterMap = filterMap;
    this.sortType = sortType;
  }

  public ApplyFilterRequest(String offerId, int level, HashMap<Integer, Set<String>> filterMap,
      int sortType) {
    this.offerId = offerId;
    this.filterMap = filterMap;
    this.sortType = sortType;
  }

  protected ApplyFilterRequest(Parcel in) {
    catName = in.readString();
    subCatName = in.readString();
    subSubCatName = in.readString();
    brandName = in.readString();
    offerId = in.readString();
    searchQuery = in.readString();
    sortType = in.readInt();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(catName);
    dest.writeString(subCatName);
    dest.writeString(subSubCatName);
    dest.writeString(brandName);
    dest.writeString(offerId);
    dest.writeString(searchQuery);
    dest.writeInt(sortType);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<ApplyFilterRequest> CREATOR = new Creator<ApplyFilterRequest>() {
    @Override
    public ApplyFilterRequest createFromParcel(Parcel in) {
      return new ApplyFilterRequest(in);
    }

    @Override
    public ApplyFilterRequest[] newArray(int size) {
      return new ApplyFilterRequest[size];
    }
  };

  public int getSortType() {
    return sortType;
  }

  public void setSortType(int sortType) {
    this.sortType = sortType;
  }

  public String getCatName() {
    return catName;
  }

  public void setCatName(String catName) {
    this.catName = catName;
  }

  public String getSubCatName() {
    return subCatName;
  }

  public void setSubCatName(String subCatName) {
    this.subCatName = subCatName;
  }

  public String getSubSubCatName() {
    return subSubCatName;
  }

  public void setSubSubCatName(String subSubCatName) {
    this.subSubCatName = subSubCatName;
  }

  public String getBrandName() {
    return brandName;
  }

  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }

  public String getOfferId() {
    return offerId;
  }

  public void setOfferId(String offerId) {
    this.offerId = offerId;
  }

  public String getSearchQuery() {
    return searchQuery;
  }

  public void setSearchQuery(String searchQuery) {
    this.searchQuery = searchQuery;
  }

  public HashMap<Integer, Set<String>> getFilterMap() {
    return filterMap;
  }

  public void setFilterMap(HashMap<Integer, Set<String>> filterMap) {
    this.filterMap = filterMap;
  }
}
