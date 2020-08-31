package com.customer.remote.http.model.request;

import static com.customer.remote.http.RemoteConstants.BRAND_CODE;
import static com.customer.remote.http.RemoteConstants.BRAND_KEY;
import static com.customer.remote.http.RemoteConstants.BRAND_NAME;
import static com.customer.remote.http.RemoteConstants.CAT_NAME;
import static com.customer.remote.http.RemoteConstants.COLOR;
import static com.customer.remote.http.RemoteConstants.COLOR_CODE;
import static com.customer.remote.http.RemoteConstants.FILTER_HIGH_TO_LOW;
import static com.customer.remote.http.RemoteConstants.FILTER_LOW_TO_HIGH;
import static com.customer.remote.http.RemoteConstants.FILTER_PREFIX;
import static com.customer.remote.http.RemoteConstants.HIGH_TO_LOW;
import static com.customer.remote.http.RemoteConstants.LOW_TO_HIGH;
import static com.customer.remote.http.RemoteConstants.MAX_PRICE;
import static com.customer.remote.http.RemoteConstants.MIN_PRICE;
import static com.customer.remote.http.RemoteConstants.OFFER_ID_KEY;
import static com.customer.remote.http.RemoteConstants.PAGE;
import static com.customer.remote.http.RemoteConstants.POPULAR;
import static com.customer.remote.http.RemoteConstants.POPULAR_TYPE;
import static com.customer.remote.http.RemoteConstants.PRICE_CODE;
import static com.customer.remote.http.RemoteConstants.SEARCH_QUERY;
import static com.customer.remote.http.RemoteConstants.SIZE;
import static com.customer.remote.http.RemoteConstants.SIZE_CODE;
import static com.customer.remote.http.RemoteConstants.SORT_KEY;
import static com.customer.remote.http.RemoteConstants.SUB_CAT_NAME;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArrayMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class MapToRequestQueryParamMapper implements Parcelable {
  public static final Creator<MapToRequestQueryParamMapper> CREATOR =
      new Creator<MapToRequestQueryParamMapper>() {
        @Override
        public MapToRequestQueryParamMapper createFromParcel(Parcel in) {
          return new MapToRequestQueryParamMapper(in);
        }

        @Override
        public MapToRequestQueryParamMapper[] newArray(int size) {
          return new MapToRequestQueryParamMapper[size];
        }
      };

  protected MapToRequestQueryParamMapper(Parcel in) {
  }

  @SuppressLint("NewApi")
  public static ArrayMap<String, String> mapToQueryParamMapper(
      CategoryQueryParameterRequest request, int sortType) {
    ArrayMap<String, String> reqBody = new ArrayMap<>();
    if (request.getFilterMap() != null && request.getFilterMap().size() > 0) {
      for (Map.Entry<Integer, Set<String>> entry : request.getFilterMap().entrySet()) {
        int key = entry.getKey();
        Set<String> values = entry.getValue();
        if (key == COLOR_CODE && values != null && values.size() > 0) {
          reqBody.put(COLOR, mSetToString(values));
        } else if (key == BRAND_CODE && values != null && values.size() > 0) {
          reqBody.put(BRAND_KEY, mSetToString(values));
        } else if (key == SIZE_CODE && values != null && values.size() > 0) {
          reqBody.put(SIZE, mSetToString(values));
        } else if (key == PRICE_CODE) {
          if (values != null) {
            ArrayList<String> list = new ArrayList<>(values);
            reqBody.put(MIN_PRICE, list.get(1));
            reqBody.put(MAX_PRICE, list.get(2));
          }
        } else if (values != null && values.size() > 0) {
          reqBody.put(FILTER_PREFIX + key, mSetToString(values));
        }
      }
    }
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
    if (!TextUtils.isEmpty(request.getPage())) {
      reqBody.put(PAGE, request.getPage());
    }
    return reqBody;
  }

  private static String mSetToString(Set<String> mList) {
    String mValue = "";
    if (mList != null && mList.size() > 0) {
      for (String mStock : mList) {
        mValue = TextUtils.isEmpty(mValue) ? mValue + mStock : mValue + "," + mStock;
      }
    }
    return mValue;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
  }
}
