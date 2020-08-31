package com.customer.data.mapper;

import com.customer.data.utils.DataUtils;
import com.customer.domain.model.promocode.PromoCodeData;
import com.customer.domain.model.promocode.PromoCodeDes;
import com.customer.domain.model.promocode.PromoCodeDetails;
import com.customer.remote.http.model.response.promocode.PromoCodeListData;
import com.customer.remote.http.model.response.promocode.PromoCodeListDes;
import com.customer.remote.http.model.response.promocode.PromoCodeListDetails;
import java.util.ArrayList;

public class PromoCodeMapper {

  public PromoCodeDetails mapper(PromoCodeListDetails categoryDetails) {
    return new PromoCodeDetails(
        convertToPromoCodeItemData(categoryDetails.getData()), categoryDetails.getMessage()
    );
  }

  private ArrayList<PromoCodeData> convertToPromoCodeItemData(
      ArrayList<PromoCodeListData> lanItemDetailsArrayList) {
    ArrayList<PromoCodeData> lanDetailsArrayList = new ArrayList<>();
    if (!DataUtils.isEmptyArray(lanItemDetailsArrayList)) {
      for (PromoCodeListData details : lanItemDetailsArrayList) {
        PromoCodeData data = new PromoCodeData(details.getStart_time(),details.getCode(),details.getPromo_id(),details.getEnd_time(),
            convertToPromoCodeData(details.getDescription()),details.getTitle());
        lanDetailsArrayList.add(data);
      }
    }
    return lanDetailsArrayList;
  }



  private ArrayList<PromoCodeDes> convertToPromoCodeData(
      ArrayList<PromoCodeListDes> lanItemDetailsArrayList) {
    ArrayList<PromoCodeDes> lanDetailsArrayList = new ArrayList<>();
    if (!DataUtils.isEmptyArray(lanItemDetailsArrayList)) {
      for (PromoCodeListDes details : lanItemDetailsArrayList) {
        PromoCodeDes data = new PromoCodeDes(details.getContain());
        lanDetailsArrayList.add(data);
      }
    }
    return lanDetailsArrayList;
  }

}
