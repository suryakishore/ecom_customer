package com.customer.data.mapper;

import com.customer.data.utils.DataUtils;
import com.customer.domain.model.changelan.ChangeLanData;
import com.customer.domain.model.changelan.ChangeLanDetails;
import com.customer.remote.http.model.response.changelanguage.ChangeLanItemDetails;
import com.customer.remote.http.model.response.changelanguage.ChangeLanListDetails;
import java.util.ArrayList;

public class ChangeLanguageMapper {
  public ChangeLanData mapper(ChangeLanListDetails categoryDetails) {
    return new ChangeLanData(
        convertToLanguageItemData(categoryDetails.getData()), categoryDetails.getMessage()
    );
  }

  private ArrayList<ChangeLanDetails> convertToLanguageItemData(
      ArrayList<ChangeLanItemDetails> lanItemDetailsArrayList) {
    ArrayList<ChangeLanDetails> lanDetailsArrayList = new ArrayList<>();
    if (!DataUtils.isEmptyArray(lanItemDetailsArrayList)) {
      for (ChangeLanItemDetails details : lanItemDetailsArrayList) {
        ChangeLanDetails data = new ChangeLanDetails(details.getLanguageCode(),
            details.getLanguageName());
        lanDetailsArrayList.add(data);
      }
    }
    return lanDetailsArrayList;
  }
}
