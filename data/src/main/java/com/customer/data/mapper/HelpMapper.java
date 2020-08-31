package com.customer.data.mapper;

import com.customer.data.utils.DataUtils;
import com.customer.domain.model.help.HelpItemData;
import com.customer.domain.model.help.HelpListData;
import com.customer.domain.model.help.HelpSubCatListData;
import com.customer.remote.http.model.response.help.HelpItemDetails;
import com.customer.remote.http.model.response.help.HelpListDetails;
import com.customer.remote.http.model.response.help.HelpSubCatListDetails;
import java.util.ArrayList;

public class HelpMapper {
  public HelpListData mapper(HelpListDetails categoryDetails) {
    return new HelpListData(
        convertToLanguageItemData(categoryDetails.getData()), categoryDetails.getMessage()
    );
  }

  private ArrayList<HelpItemData> convertToLanguageItemData(
      ArrayList<HelpItemDetails> lanItemDetailsArrayList) {
    ArrayList<HelpItemData> lanDetailsArrayList = new ArrayList<>();
    if (!DataUtils.isEmptyArray(lanItemDetailsArrayList)) {
      for (HelpItemDetails details : lanItemDetailsArrayList) {
        HelpItemData data = new HelpItemData(details.getDesc(),
            convertToSubCategoryData(details.getSubcat()), details.getLink(), details.getName());
        lanDetailsArrayList.add(data);
      }
    }
    return lanDetailsArrayList;
  }

  private ArrayList<HelpSubCatListData> convertToSubCategoryData(
      ArrayList<HelpSubCatListDetails> lanItemDetailsArrayList) {
    ArrayList<HelpSubCatListData> lanDetailsArrayList = new ArrayList<>();
    if (!DataUtils.isEmptyArray(lanItemDetailsArrayList)) {
      for (HelpSubCatListDetails details : lanItemDetailsArrayList) {
        HelpSubCatListData data = new HelpSubCatListData(details.getDesc(), details.getLink(),
            details.getName());
        lanDetailsArrayList.add(data);
      }
    }
    return lanDetailsArrayList;
  }
}
