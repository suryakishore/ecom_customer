package com.customer.data.mapper;

import static com.customer.data.utils.DataConstants.LOWER_BOUND;
import static com.customer.data.utils.DataConstants.PRICE_TYPE;
import static com.customer.remote.http.RemoteConstants.MAX_ACCORDING_TO_ARRAY;
import static com.customer.remote.http.RemoteConstants.MAX_SIZE;

import android.text.TextUtils;
import com.customer.data.utils.DataUtils;
import com.customer.domain.model.filter.FilterData;
import com.customer.domain.model.filter.FilterList;
import com.customer.domain.model.filter.FilterListData;
import com.customer.remote.http.model.response.filter.FilterDetails;
import com.customer.remote.http.model.response.filter.FilterListDataDetails;
import com.customer.remote.http.model.response.filter.FilterListDetails;
import java.util.ArrayList;

public class GetFilterParamsMapper {

  public FilterData mapper(FilterDetails details) {

    return new FilterData(details.getCurrency(), details.getUnitId(), details.getMessage(),
        convertToFilterListData(details.getFilters()));
  }

  private ArrayList<FilterList> convertToFilterListData(ArrayList<FilterListDetails> listDetails) {

    ArrayList<FilterList> filterLists = new ArrayList<>();
    if (!DataUtils.isEmptyArray(listDetails)) {
      for (FilterListDetails details : listDetails) {

        ArrayList<FilterListData> subListData;
        if (!TextUtils.isEmpty(details.getName()) && details.getFilterType() == PRICE_TYPE
            && details.getData() != null && details.getData().size() > LOWER_BOUND) {
          subListData = convertToPrice(details.getData().get(LOWER_BOUND).getMinPrice(),
              details.getData().get(LOWER_BOUND).getMaxPrice(), details.getCurrencySymbol());
        } else {
          subListData = convertToListData(details.getData());
        }
        FilterList listData = new FilterList(details.getName(),
            details.getCurrency(), details.getCurrencySymbol(), details.getSelType(),
            details.getLevel(), subListData, details.getFilterType());
        /*This condition is using to filter empty subList*/
        if (!DataUtils.isEmptyArray(subListData)) {
          filterLists.add(listData);
        }
      }
    }
    return filterLists;
  }

  private ArrayList<FilterListData> convertToListData(
      ArrayList<FilterListDataDetails> listDataDetails) {

    ArrayList<FilterListData> listData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(listDataDetails)) {
      for (FilterListDataDetails dataDetails : listDataDetails) {
        FilterListData data = new FilterListData(dataDetails.getName(),
            dataDetails.getRgb(), dataDetails.getSelType(), dataDetails.getData());
        listData.add(data);
      }
    }
    return listData;
  }

  private ArrayList<FilterListData> convertToPrice(double minPrice, double maxPrice,
      String currencySymbol) {
    ArrayList<FilterListData> data = new ArrayList<>();
    int tempMax;
    int tempMin = (int) minPrice;
    int diff = (int) (maxPrice - minPrice);
    int step = diff / MAX_SIZE;
    for (int i = LOWER_BOUND; i < MAX_SIZE; i++) {
      tempMax = i == MAX_ACCORDING_TO_ARRAY ? (int) maxPrice : tempMin + step;
      data.add(
          new FilterListData(
              currencySymbol + " " + tempMin + " - " + currencySymbol + " " + tempMax,
              tempMin, tempMax));
      tempMin = tempMax;
    }
    return data;
  }

}
