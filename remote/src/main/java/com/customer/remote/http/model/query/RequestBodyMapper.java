package com.customer.remote.http.model.query;

import static com.customer.remote.http.RemoteConstants.BRAND_NAME;
import static com.customer.remote.http.RemoteConstants.CAT_NAME;
import static com.customer.remote.http.RemoteConstants.FILTER_HIGH_TO_LOW;
import static com.customer.remote.http.RemoteConstants.FILTER_LOW_TO_HIGH;
import static com.customer.remote.http.RemoteConstants.HIGH_TO_LOW;
import static com.customer.remote.http.RemoteConstants.LOW_TO_HIGH;
import static com.customer.remote.http.RemoteConstants.PAGE;
import static com.customer.remote.http.RemoteConstants.POPULAR;
import static com.customer.remote.http.RemoteConstants.POPULAR_TYPE;
import static com.customer.remote.http.RemoteConstants.SEARCH_QUERY;
import static com.customer.remote.http.RemoteConstants.SORT_KEY;
import static com.customer.remote.http.RemoteConstants.SUB_CAT_NAME;
import static com.customer.remote.http.RemoteConstants.SUB_SUB_CAT_NAME;

import android.text.TextUtils;
import android.util.ArrayMap;
import com.customer.remote.http.model.request.CategoryQueryParameterRequest;

public class RequestBodyMapper {

  /**
   * This method is using to map request value to query params for API
   *
   * @param request  request value
   * @param sortType type of sort
   * @return returns HashMap that converted from request values to query param
   */
  public static ArrayMap<String, String> getBodyMapper(CategoryQueryParameterRequest request,
      int sortType) {

    ArrayMap<String, String> getProductReq = null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
      getProductReq = new ArrayMap<>();
    }
    if (getProductReq != null) {

      if (!TextUtils.isEmpty(request.getCatName())) {
        getProductReq.put(CAT_NAME, request.getCatName());
      }

      if (!TextUtils.isEmpty(request.getSubCatName())) {
        getProductReq.put(SUB_CAT_NAME, request.getSubCatName());
      }

      if (!TextUtils.isEmpty(request.getSubSubCatName())) {
        getProductReq.put(SUB_SUB_CAT_NAME, request.getSubSubCatName());
      }

      if (!TextUtils.isEmpty(request.getBrandName())) {
        getProductReq.put(BRAND_NAME, request.getBrandName());
      }

      if (!TextUtils.isEmpty(request.getSearchQuery())) {
        getProductReq.put(SEARCH_QUERY, request.getSearchQuery());
      }
      if (!TextUtils.isEmpty(request.getPage())) {
        getProductReq.put(PAGE, request.getPage());
      }

      /*condition to add sort type value to query */
      if (sortType == LOW_TO_HIGH) {
        getProductReq.put(SORT_KEY, FILTER_LOW_TO_HIGH);
      } else if (sortType == HIGH_TO_LOW) {
        getProductReq.put(SORT_KEY, FILTER_HIGH_TO_LOW);
      } else if (sortType == POPULAR_TYPE) {
        getProductReq.put(SORT_KEY, POPULAR);
      }
    }
    return getProductReq;
  }

  public static String getSearchType(CategoryQueryParameterRequest request) {

    String searchType = "100";    //Default searchType value

    if (!TextUtils.isEmpty(request.getCatName())) {
      searchType = "1";           //searchType value for category flow in PLP page
    }
    if (!TextUtils.isEmpty(request.getSubCatName())) {
      searchType = "2";           //searchType value for subCategory flow in PLP page
    }
    if (!TextUtils.isEmpty(request.getSubSubCatName())) {
      searchType = "3";           //searchType value for subSubCategory flow in PLP page
    }
    if (!TextUtils.isEmpty(request.getSearchQuery())) {
      searchType = "4";           //searchType value for Search flow in PLP page
    }

    return searchType;
  }
}
