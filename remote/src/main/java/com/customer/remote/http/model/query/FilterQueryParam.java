package com.customer.remote.http.model.query;

import static com.customer.remote.http.RemoteConstants.BRAND_CODE;
import static com.customer.remote.http.RemoteConstants.BRAND_KEY;
import static com.customer.remote.http.RemoteConstants.BRAND_NAME;
import static com.customer.remote.http.RemoteConstants.CAT_NAME;
import static com.customer.remote.http.RemoteConstants.COLOR;
import static com.customer.remote.http.RemoteConstants.COLOR_CODE;
import static com.customer.remote.http.RemoteConstants.FILTER_HIGH_TO_LOW;
import static com.customer.remote.http.RemoteConstants.FILTER_LOW_TO_HIGH;
import static com.customer.remote.http.RemoteConstants.FILTER_PREFIX;
import static com.customer.remote.http.RemoteConstants.FIRST_POSITION;
import static com.customer.remote.http.RemoteConstants.HIGH_TO_LOW;
import static com.customer.remote.http.RemoteConstants.LOWER_BOUND;
import static com.customer.remote.http.RemoteConstants.LOW_TO_HIGH;
import static com.customer.remote.http.RemoteConstants.MAX_PRICE;
import static com.customer.remote.http.RemoteConstants.MIN_PRICE;
import static com.customer.remote.http.RemoteConstants.OFFER_ID_KEY;
import static com.customer.remote.http.RemoteConstants.POPULAR;
import static com.customer.remote.http.RemoteConstants.POPULAR_TYPE;
import static com.customer.remote.http.RemoteConstants.PRICE_CODE;
import static com.customer.remote.http.RemoteConstants.SEARCH_QUERY;
import static com.customer.remote.http.RemoteConstants.SIZE;
import static com.customer.remote.http.RemoteConstants.SIZE_CODE;
import static com.customer.remote.http.RemoteConstants.SORT_KEY;
import static com.customer.remote.http.RemoteConstants.SUB_CAT_NAME;

import android.text.TextUtils;
import android.util.ArrayMap;
import com.customer.remote.http.model.request.ApplyFilterRequest;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class FilterQueryParam {

  /**
   * This method is using to map request value to query params for API
   *
   * @param request  request value
   * @param sortType type of sort
   * @return returns HashMap that converted from request values to query param
   */
  public static ArrayMap<String, String> mapToQueryParamMapper(
      ApplyFilterRequest request, int sortType) {

    ArrayMap<String, String> reqBody = null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
      reqBody = new ArrayMap<>();
    }

    if (request.getFilterMap() != null && reqBody != null
        && request.getFilterMap().size() > LOWER_BOUND) {

      for (Map.Entry<Integer, Set<String>> entry : request.getFilterMap().entrySet()) {

        int key = entry.getKey();
        Set<String> values = entry.getValue();

        if (key == COLOR_CODE && values != null && values.size() > LOWER_BOUND) {
          reqBody.put(COLOR, mSetToString(values));
        } else if (key == BRAND_CODE && values != null && values.size() > LOWER_BOUND) {
          reqBody.put(BRAND_KEY, mSetToString(values));
        } else if (key == SIZE_CODE && values != null && values.size() > LOWER_BOUND) {
          reqBody.put(SIZE, mSetToString(values));
        } else if (key == PRICE_CODE) {

          /*This condition is using to handle price filter query param*/
          if (values != null) {

            ArrayList<String> list = new ArrayList<>(values);
            ArrayList<Integer> tempList = new ArrayList<>();

            for (String val : list) {
              if (TextUtils.isDigitsOnly(val)) {
                tempList.add(Integer.parseInt(val));
              }
            }
            if (tempList.size() > LOWER_BOUND) {
              /*adding min price and max price based on condition*/
              reqBody.put(MIN_PRICE,
                  tempList.get(LOWER_BOUND) < tempList.get(FIRST_POSITION) ?
                      tempList.get(LOWER_BOUND) + "" : tempList.get(FIRST_POSITION) + "");
              reqBody.put(MAX_PRICE,
                  tempList.get(LOWER_BOUND) > tempList.get(FIRST_POSITION) ?
                      tempList.get(LOWER_BOUND) + "" : tempList.get(FIRST_POSITION) + "");
            }
          }
        } else if (values != null && values.size() > LOWER_BOUND) {
          reqBody.put(FILTER_PREFIX + key, mSetToString(values));
        }
      }
    }
    if (reqBody != null) {

      /*condition to add sort type value to query */
      if (sortType == LOW_TO_HIGH) {
        reqBody.put(SORT_KEY, FILTER_LOW_TO_HIGH);
      } else if (sortType == HIGH_TO_LOW) {
        reqBody.put(SORT_KEY, FILTER_HIGH_TO_LOW);
      } else if (sortType == POPULAR_TYPE) {
        reqBody.put(SORT_KEY, POPULAR);
      }

      if (!TextUtils.isEmpty(request.getCatName())) {
        reqBody.put(CAT_NAME, request.getCatName());
      }

      if (!TextUtils.isEmpty(request.getSubCatName())) {
        reqBody.put(SUB_CAT_NAME, request.getSubCatName());
      }

      if (!TextUtils.isEmpty(request.getSearchQuery())) {
        reqBody.put(SEARCH_QUERY, request.getSearchQuery());
      }

      if (!TextUtils.isEmpty(request.getBrandName())) {
        reqBody.put(BRAND_NAME, request.getBrandName());
      }

      if (!TextUtils.isEmpty(request.getOfferId())) {
        reqBody.put(OFFER_ID_KEY, request.getOfferId());
      }
    }
    return reqBody;
  }

  /**
   * This method is using to form query value
   *
   * @param mList query value list
   * @return comma separated query value
   */
  private static String mSetToString(Set<String> mList) {
    String mValue = "";
    if (mList != null && mList.size() > LOWER_BOUND) {
      for (String mStock : mList) {
        mValue = TextUtils.isEmpty(mValue) ? mStock : mValue + "," + mStock;
      }
    }
    return mValue;
  }
}
