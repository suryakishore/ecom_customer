package com.customer.data.mapper;

import android.text.TextUtils;
import com.customer.domain.model.SignInData;
import com.customer.domain.model.guestsignin.Token;
import com.customer.domain.model.signin.IdentityCard;
import com.customer.domain.model.signup.LocationData;
import com.customer.remote.http.model.response.signIn.SignInDetailsData;

public class SignInMapper {

  public SignInData map(SignInDetailsData signInDetailsData) {
    if (!TextUtils.isEmpty(signInDetailsData.getAccessToken()) || !TextUtils.isEmpty(
        signInDetailsData.getOtpId())) {
      return new SignInData(signInDetailsData.getAccessToken(), signInDetailsData.getOtpId(),
          signInDetailsData.getOtpExpiryTime());
    } else {
      IdentityCard mmjCard = null;
      IdentityCard identityCard = null;
      Token token = null;
      if (signInDetailsData.getMmjCard() != null) {
        mmjCard = new IdentityCard(signInDetailsData.getMmjCard().getVerified(),
            signInDetailsData.getMmjCard().getUrl());
      }

      if (signInDetailsData.getIdentityCard() != null) {
        identityCard = new IdentityCard(signInDetailsData.getIdentityCard().getVerified(),
            signInDetailsData.getIdentityCard().getUrl());
      }
      if (signInDetailsData.getToken() != null) {
        token = new Token(signInDetailsData.getToken().getAccessToken(),
            signInDetailsData.getToken().getAccessExpiry(),
            signInDetailsData.getToken().getRefreshToken());
      }
      LocationData locationData = null;

      if (signInDetailsData.getLocation() != null) {
        locationData = new LocationData(signInDetailsData.getLocation().getLat(),
            signInDetailsData.getLocation().getLongi());
      }

      return new SignInData(signInDetailsData.getCountry(), signInDetailsData.getCity(),
          signInDetailsData.getGoogleMapKeyMqtt(), mmjCard,
          signInDetailsData.getMobile(), signInDetailsData.getFcmTopic(),
          signInDetailsData.getMqttTopic(),
          identityCard,
          signInDetailsData.getUserId(), token, signInDetailsData.getCountryCode(),
          signInDetailsData.getName(), locationData,
          signInDetailsData.getRegion(), signInDetailsData.getEmail(),
          signInDetailsData.getStatus(), signInDetailsData.getRequester_id(),signInDetailsData.getExpireTime()
      );
    }
  }
}
