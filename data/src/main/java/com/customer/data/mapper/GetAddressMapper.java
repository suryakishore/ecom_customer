package com.customer.data.mapper;

import com.customer.data.repository.observable.DefaultAddressObservable;
import com.customer.data.utils.DataUtils;
import com.customer.domain.model.getaddress.AddressData;
import com.customer.domain.model.getaddress.AddressListItemData;
import com.customer.remote.http.model.response.getaddress.AddressDetails;
import com.customer.remote.http.model.response.getaddress.AddressListItemDetails;
import com.data.cache.DatabaseManager;
import com.data.cache.entities.UserAddress;
import java.util.ArrayList;

public class GetAddressMapper {
  public AddressData mapper(AddressDetails details) {
    return new AddressData(convertToAddressListData(details.getData()), details.getMessage());
  }

  private ArrayList<AddressListItemData> convertToAddressListData(
      ArrayList<AddressListItemDetails> detailsList) {
    ArrayList<AddressListItemData> listData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(detailsList)) {
      for (AddressListItemDetails details : detailsList) {
        listData.add(CommonMapper.convertToAddressData(details));
      }
    }
    return listData;
  }

  public void insertToDatabase(AddressDetails details, DatabaseManager databaseManager) {
    if (details != null) {
      databaseManager.address().insertAddresses(dbMapper(details.getData()));
    }
  }

  private ArrayList<UserAddress> dbMapper(
      ArrayList<AddressListItemDetails> detailsList) {
    ArrayList<UserAddress> listData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(detailsList)) {
      for (AddressListItemDetails details : detailsList) {
        if (details.isDefaultAd()) {
          DefaultAddressObservable.getInstance().postData(
              new AddressListItemData(details.get_id()));
        }
        UserAddress itemData = new UserAddress(details.get_id(), details.getMobileNumberCode(),
            details.getCountry(), details.getPincode(), details.getCity(),
            details.getMobileNumber(),
            details.getFlatNumber(), details.getLatitude(), details.getAlternatePhoneCode(),
            details.getAlternatePhone(),
            details.getLocality(), details.getPlaceId(), details.getCreatedTimeStamp(),
            details.getUserId(), details.getCreatedIsoDate(),
            details.getName(), details.getAddLine1(), details.getAddLine2(), details.getState(),
            details.getUserType(),
            details.getLandmark(), details.getTaggedAs(), details.getTagged(),
            details.isDefaultAd() ? "1" : "0", details.getLongitude(),details.getCityId(),details.getCountryId());
        listData.add(itemData);
      }
    }
    return listData;
  }
}
