package com.customer.remote.http.model.request;

import static com.customer.remote.http.RemoteConstants.BRAND_TYPE;
import static com.customer.remote.http.RemoteConstants.OFFER_ID;
import static com.customer.remote.http.RemoteConstants.SEARCH_TYPE;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Set;

public class CategoryQueryParameterRequest  implements Parcelable {

  private int level;
  private String catName;
  private String subCatName;
  private String subSubCatName;
  private String brandName;
  private String searchQuery;
  private HashMap<Integer, Set<String>> filterMap;
  private int sortType;
  private String offerId;
  private int type;
  private String name;
  private boolean isFilter;
  private String mInText;
  private String page;
  public CategoryQueryParameterRequest(int type, String name, String inText) {
    if (type == OFFER_ID) {
      this.offerId = name;
    } else if (type == BRAND_TYPE) {
      this.brandName = name;
    } else if (type == SEARCH_TYPE) {
      this.searchQuery = name;
      this.mInText = inText;
    }
  }

  public CategoryQueryParameterRequest(HashMap<Integer, Set<String>> filterMap,
      String catName, int sortType,String page) {
    this.catName = catName;
    this.filterMap = filterMap;
    this.sortType = sortType;
    this.page=page;
  }

  public CategoryQueryParameterRequest(String catName, String subCatName,
      HashMap<Integer, Set<String>> filterMap, int sortType,String page) {
    this.catName = catName;
    this.subCatName = subCatName;
    this.filterMap = filterMap;
    this.sortType = sortType;
    this.page=page;
  }

  public CategoryQueryParameterRequest(String catName, String subCatName,String subSubCatName,
      HashMap<Integer, Set<String>> filterMap, int sortType,String page) {
    this.catName = catName;
    this.subCatName = subCatName;
    this.filterMap = filterMap;
    this.sortType = sortType;
    this.page=page;
  }

  public CategoryQueryParameterRequest(String searchQuery,
      HashMap<Integer, Set<String>> filterMap, int sortType,String page) {
    this.searchQuery = searchQuery;
    this.filterMap = filterMap;
    this.sortType = sortType;
    this.page=page;
  }

  public CategoryQueryParameterRequest(
      HashMap<Integer, Set<String>> filterMap, int sortType,String page) {
    this.filterMap = filterMap;
    this.sortType = sortType;
    this.page=page;
  }

  public CategoryQueryParameterRequest(String searchQuery, int sortType,String page) {
    this.searchQuery = searchQuery;
    this.sortType = sortType;
    this.page=page;
  }

  public CategoryQueryParameterRequest(String catName, String subCatName, String subSubCatName,
      int sortType,String page) {
    this.catName = catName;
    this.subCatName = subCatName;
    this.subSubCatName = subSubCatName;
    this.sortType = sortType;
    this.page=page;
  }

  public CategoryQueryParameterRequest(int level, String catName, String subCatName,
      String subSubCatName, int sortType) {
    this.level = level;
    this.catName = catName;
    this.subCatName = subCatName;
    this.subSubCatName = subSubCatName;
    this.sortType = sortType;
  }

  public CategoryQueryParameterRequest(String searchQuery) {
    this.searchQuery = searchQuery;
  }

  public CategoryQueryParameterRequest(int level, String brandName, int sortType) {
    this.level = level;
    this.brandName = brandName;
    this.sortType = sortType;
  }

  public CategoryQueryParameterRequest(int level, String catName, String subCatName,
      String subSubCatName, String brandName, int sortType,String page) {
    this.level = level;
    this.catName = catName;
    this.subCatName = subCatName;
    this.subSubCatName = subSubCatName;
    this.brandName = brandName;
    this.sortType = sortType;
    this.page=page;
  }

  protected CategoryQueryParameterRequest(Parcel in) {
    level = in.readInt();
    catName = in.readString();
    subCatName = in.readString();
    subSubCatName = in.readString();
    brandName = in.readString();
    searchQuery = in.readString();
    sortType = in.readInt();
    offerId = in.readString();
    type = in.readInt();
    name = in.readString();
    isFilter = in.readByte() != 0;
    mInText = in.readString();
    page = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(level);
    dest.writeString(catName);
    dest.writeString(subCatName);
    dest.writeString(subSubCatName);
    dest.writeString(brandName);
    dest.writeString(searchQuery);
    dest.writeInt(sortType);
    dest.writeString(offerId);
    dest.writeInt(type);
    dest.writeString(name);
    dest.writeByte((byte) (isFilter ? 1 : 0));
    dest.writeString(mInText);
    dest.writeString(page);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<CategoryQueryParameterRequest> CREATOR =
      new Creator<CategoryQueryParameterRequest>() {
        @Override
        public CategoryQueryParameterRequest createFromParcel(Parcel in) {
          return new CategoryQueryParameterRequest(in);
        }

        @Override
        public CategoryQueryParameterRequest[] newArray(int size) {
          return new CategoryQueryParameterRequest[size];
        }
      };

  public String getInText() {
    return mInText;
  }

  public void setInText(String inText) {
    mInText = inText;
  }

  public boolean isFilter() {
    return isFilter;
  }

  public void setFilter(boolean filter) {
    isFilter = filter;
  }

  public String getOfferId() {
    return offerId;
  }

  public void setOfferId(String offerId) {
    this.offerId = offerId;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public HashMap<Integer, Set<String>> getFilterMap() {
    return filterMap;
  }

  public void setFilterMap(HashMap<Integer, Set<String>> filterMap) {
    this.filterMap = filterMap;
  }

  public String getSearchQuery() {
    return searchQuery;
  }

  public void setSearchQuery(String searchQuery) {
    this.searchQuery = searchQuery;
  }

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

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public String getPage() {
    return page;
  }
}
