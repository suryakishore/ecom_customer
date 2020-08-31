package com.customer.data.mapper;

import android.text.TextUtils;
import com.customer.data.preference.PreferenceManager;
import com.customer.data.repository.observable.UserDataObservable;
import com.customer.data.utils.DataUtils;
import com.customer.domain.model.UserData;
import com.customer.domain.model.profile.LastStatusLogData;
import com.customer.domain.model.profile.MmjCardData;
import com.customer.domain.model.profile.MobileDevicesData;
import com.customer.domain.model.profile.ProfileData;
import com.customer.domain.model.profile.WalletData;
import com.customer.domain.model.signup.LocationData;
import com.customer.remote.http.model.response.profile.LastStatusLogDetails;
import com.customer.remote.http.model.response.profile.MobileDevicesDetails;
import com.customer.remote.http.model.response.profile.ProfileDetails;
import java.util.ArrayList;

public class ProfileDetMapper {
  public ProfileData mapper(ProfileDetails details, PreferenceManager preference) {

    if (!TextUtils.isEmpty(details.getName())) {
      UserData data = new UserData(details.getName(),
          details.getEmail(), details.getMobile(),
          details.getCountryCode(), details.getProfilePic(), details.getCity(),
          details.getRegion(), details.getCountry());
      preference.setUserDetails(data);
      UserDataObservable.getInstance().postData(data);
    }
    MobileDevicesData mobileDevicesData = null;
    if (details.getMobileDevices() != null) {
      MobileDevicesDetails mobileDevices = details.getMobileDevices();
      mobileDevicesData = new MobileDevicesData(mobileDevices.getCurrentlyActive(),
          mobileDevices.getDeviceType(), mobileDevices.getLastLogin(),
          mobileDevices.getAppVersion(),
          mobileDevices.getLastTimestamp(), mobileDevices.getBrowserVersion(),
          mobileDevices.getBrowserName(), mobileDevices.getDeviceTypeMsg(),
          mobileDevices.getDeviceOsVersion(),
          mobileDevices.getLastISOdate(), mobileDevices.getDeviceId(),
          mobileDevices.getPushToken());
    }

    LocationData locationData = null;
    if (details.getCoordinates() != null) {
      locationData = new LocationData(details.getCoordinates().getLatitude(),
          details.getCoordinates().getLongitude());
    }

    MmjCardData mmjCardData = null;
    if (details.getMmjCard() != null) {
      mmjCardData = new MmjCardData(details.getMmjCard().getVerified(),
          details.getMmjCard().getUrl());
    }
    WalletData walletData = null;
    if (details.getWallet() != null) {
      walletData = new WalletData(details.getWallet().getSoftLimitHit(),
          details.getWallet().getBalance(), details.getWallet().getBlocked(),
          details.getWallet().getHardLimitHit(), details.getWallet().getHardLimit(),
          details.getWallet().getSoftLimit());
    }
    return new ProfileData(details.getGoogleId(), details.getLastName(),
        details.getZipCode(), details.getCountry(), details.getLastLogin(),
        details.getCreatedISOdate(), details.getCity(),
        convertToStatusData(details.getStatusLogs()),
        details.getLoginType(), mmjCardData, details.getMobileVerified(), details.getMqttTopic(),
        details.getCityId(), details.getPassword(), details.getCountryCode(),
        details.getUserTypeText(),
        details.getSocialMediaId(), convertToLastStatData(details.getLastStatusLog()),
        details.getTermsAndCondition(), details.getGuestToken(),
        details.getEmail(), details.getSeqId(), walletData,
        details.getRegistrationDateTimeStamp(),
        details.getFacebookId(), details.getProfilePic(), details.getCreatedTimestamp(),
        details.getRegisteredFromCity(),
        details.getMobile(), locationData, details.getDateOfBirth(), details.getFcmTopic(),
        details.getFirstName(), details.getCreatedDate(), details.getStatusMsg(), details.getName(),
        mobileDevicesData,
        details.get_id(), details.getUserType(), details.getRegion(), details.getZendeskId(),
        details.getRegistrationDateIso(),
        details.getStatus());
  }

  private LastStatusLogData convertToLastStatData(LastStatusLogDetails details) {
    return new LastStatusLogData(details.getActionByUserId(),
        details.getActionByUserType(),
        details.getAction(), details.getStatus(), details.getTimestamp());
  }

  private ArrayList<LastStatusLogData> convertToStatusData(
      ArrayList<LastStatusLogDetails> detailsList) {
    ArrayList<LastStatusLogData> listData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(detailsList)) {
      for (LastStatusLogDetails details : detailsList) {

        listData.add(convertToLastStatData(details));
      }

    }
    return listData;
  }

}
