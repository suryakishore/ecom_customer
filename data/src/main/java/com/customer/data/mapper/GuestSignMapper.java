package com.customer.data.mapper;

import static com.customer.data.utils.DataConstants.GROCERY_KEY;

import com.customer.data.preference.PreferenceManager;
import com.customer.data.utils.DataUtils;
import com.customer.domain.model.guestsignin.GuestSignInData;
import com.customer.domain.model.guestsignin.Token;
import com.customer.remote.http.model.response.guestSignIn.GuestLocation;
import com.customer.remote.http.model.response.guestSignIn.GuestSignInDetailsData;
import com.customer.remote.http.model.response.guestSignIn.StoreCategoryIdData;
import java.util.ArrayList;

public class GuestSignMapper {
  public GuestSignInData map(
      GuestSignInDetailsData guestSignInResponse, PreferenceManager preference) {
    preference.setGuestToken(guestSignInResponse.getToken().getAccessToken());
    Token token = new Token(guestSignInResponse.getToken().getAccessToken(),
        guestSignInResponse.getToken().getAccessExpiry(),
        guestSignInResponse.getToken().getRefreshToken());
    saveStoreCategory(guestSignInResponse.getStoreCategory(), preference);
    saveUserDetails(guestSignInResponse.getLocation(), preference);
    return new GuestSignInData(guestSignInResponse.getGoogleMapKeyMqtt(),
        guestSignInResponse.getType(), guestSignInResponse.getSid(), token);
  }

  private void saveUserDetails(GuestLocation guestLocation,
      PreferenceManager preference) {
    if (guestLocation != null) {
      preference.setLatLong(Double.parseDouble(guestLocation.getLatitude()),
          Double.parseDouble(guestLocation.getLongitude()));
    }
  }

  private void saveStoreCategory(ArrayList<StoreCategoryIdData> idDataArrayList,
      PreferenceManager preference) {
    if (!DataUtils.isEmptyArray(idDataArrayList)) {
      for (StoreCategoryIdData data : idDataArrayList) {
        if (data.getStoreCategoryName().contains(GROCERY_KEY)) {
          preference.setGrocerStoreId(data.getStoreCategoryId());
        } else {
          preference.setEcomStoreId(data.getStoreCategoryId());
          preference.setStoreCatId(data.getStoreCategoryId());
        }
      }
    }
  }
}
