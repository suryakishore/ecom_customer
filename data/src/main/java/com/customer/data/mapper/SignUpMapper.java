package com.customer.data.mapper;

import android.text.TextUtils;
import com.customer.data.preference.PreferenceManager;
import com.customer.data.repository.observable.UserDataObservable;
import com.customer.data.repository.observable.WalletAmtObservable;
import com.customer.domain.model.UserData;
import com.customer.domain.model.guestsignin.Token;
import com.customer.domain.model.signup.LocationData;
import com.customer.domain.model.signup.SignUpData;
import com.customer.remote.http.model.response.signUp.SignUpDetails;

public class SignUpMapper {
  public SignUpData map(SignUpDetails signUpDetails, PreferenceManager preference) {
    if (signUpDetails.getToken() != null && !TextUtils.isEmpty(
        signUpDetails.getToken().getAccessToken())) {
      preference.setIsLoggedIn(true);
      preference.setRefreshToken(signUpDetails.getToken().getRefreshToken());
      preference.setToken(signUpDetails.getUserId(), signUpDetails.getToken().getAccessToken());
    }
    if (!TextUtils.isEmpty(signUpDetails.getUserId())) {
      UserData data = new UserData(signUpDetails.getName(),
          signUpDetails.getEmail(), signUpDetails.getMobile(),
          signUpDetails.getCountryCode(), signUpDetails.getProfilePic(), signUpDetails.getCity(),
          signUpDetails.getRegion(), signUpDetails.getCountry());
      preference.setUserDetails(data);
      preference.setUserId(signUpDetails.getUserId());
      WalletAmtObservable.getInstance().emit(true);
      UserDataObservable.getInstance().postData(data);
      preference.setReferalCode(signUpDetails.getReferralCode());
    }
    Token token = null;
    if (signUpDetails.getToken() != null) {
      token = new Token(signUpDetails.getToken().getAccessToken(),
          signUpDetails.getToken().getAccessExpiry(),
          signUpDetails.getToken().getRefreshToken());
    }
    if (signUpDetails.getCurrencyCode() != null) {
      preference.setCurrencyCode(signUpDetails.getCurrencyCode());
    }
    if (signUpDetails.getCurrencySymbol() != null) {
      preference.setCurrencySymbol(signUpDetails.getCurrencySymbol());
    }
    LocationData locationData = null;
    if (signUpDetails.getLocation() != null) {
      locationData = new LocationData(signUpDetails.getLocation().getLat(),
          signUpDetails.getLocation().getLongi());
    }
    return new SignUpData(signUpDetails.getCountry(), signUpDetails.getStatusMsg(),
        signUpDetails.getCity(), signUpDetails.getName(), signUpDetails.getMobile(), locationData,
        signUpDetails.getRegion(), signUpDetails.getUserId(), signUpDetails.getEmail(), token,
        "", "");
  }
}
