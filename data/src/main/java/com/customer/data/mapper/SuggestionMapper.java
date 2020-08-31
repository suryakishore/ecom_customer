package com.customer.data.mapper;

import com.customer.data.utils.DataUtils;
import com.customer.domain.model.suggestion.SuggestionItemData;
import com.customer.domain.model.suggestion.SuggestionListData;
import com.customer.remote.http.model.response.suggestions.SuggestionItemDetails;
import com.customer.remote.http.model.response.suggestions.SuggestionListDetails;
import java.util.ArrayList;

public class SuggestionMapper {
  public SuggestionListData mapper(SuggestionListDetails details) {
    return new SuggestionListData(convertToSuggestionData(details.getData()), details.getMessage());
  }

  private ArrayList<SuggestionItemData> convertToSuggestionData(
      ArrayList<SuggestionItemDetails> detailsList) {

    ArrayList<SuggestionItemData> dataList = new ArrayList<>();
    if (!DataUtils.isEmptyArray(detailsList)) {
      for (SuggestionItemDetails details : detailsList) {

        SuggestionItemData data = new SuggestionItemData(
            CommonMapper.convertToImageData(details.getImages()),
            details.getStoreType(), details.getProductId(),details.getChildProductId(), details.getSubCatName(),
            details.getCurrencySymbol(), details.getProductName(),
            details.getSubSubCatName(), details.getCatName(), details.getCurrency(),
            details.getInSection(), details.isProduct(), details.getBrandTitle(),
            details.getSeqId());
        dataList.add(data);
      }
    }
    return dataList;
  }
}
