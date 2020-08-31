package com.customer.fivecanale.manageaddress;

import static com.customer.fivecanale.manageaddress.AddAddressUiAction.FINISH;
import static com.customer.fivecanale.util.EcomConstants.EDIT_TYPE;

import android.text.TextUtils;
import android.view.View;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.AddAddressUseCase;
import com.customer.domain.interactor.EditAddressUseCase;
import com.customer.domain.model.getaddress.AddressListItemData;
import com.customer.fivecanale.util.BackButtonClickListener;
import io.reactivex.observers.DisposableSingleObserver;
import javax.inject.Inject;

/*
 * Purpose – This class Holds All the Business logic of AddAddressActivity.
 * @author 3Embed
 * Created on Dec 10, 2019
 * Modified on
 */
public class AddAddressViewModel extends ViewModel implements BackButtonClickListener {
  public ObservableField<String> isDataFound = new ObservableField<>();
  public ObservableField<Boolean> isNameValid = new ObservableField<>(false);
  public ObservableField<Boolean> addressLine1Valid = new ObservableField<>(false);
  public ObservableField<Boolean> isPostalCodeValid = new ObservableField<>(false);
  public ObservableField<Boolean> isCountryValid = new ObservableField<>(false);
  public ObservableField<Boolean> isCityValid = new ObservableField<>(false);
  public ObservableField<Boolean> isAreaValid = new ObservableField<>(false);
  public ObservableField<Boolean> canActivateBtn = new ObservableField<>(false);
  private AddAddressUseCase mAddAddressUseCase;
  private UseCaseHandler mHandler;
  private EditAddressUseCase mEditAddressUseCase;
  private String mName, mAddressLine1, mAddressLine2, mPhoneNum, mArea, mStateName;
  private String mCity, mCountry, mPostalCode, mLandMark, mCountryCode, mAddressTypeVal;
  private int mAddressType;
  private String mLatitude, mLongitude;
  private boolean mIsPhoneNumValid;
  private AddressListItemData mAddressListItemData;
  private int mUpdateType;
  private MutableLiveData<AddAddressUiAction> mAddAddressUiAction = new MutableLiveData<>();

  @Inject
  public AddAddressViewModel(AddAddressUseCase addAddressUseCase,
      EditAddressUseCase editAddressUseCase,
      UseCaseHandler handler) {
    this.mAddAddressUseCase = addAddressUseCase;
    this.mEditAddressUseCase = editAddressUseCase;
    this.mHandler = handler;
  }

  /**
   * handles text changes of name
   */
  public void onTextChangeName(CharSequence s, int start, int before, int count) {
    mName = s.toString();
    if (isEditable()) {
      mAddressListItemData.setName(mName);
    }
    isNameValid.set(TextUtils.isEmpty(mName));
    validateAddressPage();
  }

  /**
   * handles text changes of mobNum
   */
  public void onTextChangeMobNum(CharSequence s, int start, int before, int count) {
    mPhoneNum = s.toString();
    if (isEditable()) {
      mAddressListItemData.setMobileNumber(mPhoneNum);
    }
    validateAddressPage();
  }

  /**
   * handles text changes of AddressLine1
   */
  public void onTextChangeAddressLineOne(CharSequence s, int start, int before, int count) {
    mAddressLine1 = s.toString();
    if (isEditable()) {
      mAddressListItemData.setAddLine1(mAddressLine1);
    }
    addressLine1Valid.set(TextUtils.isEmpty(mAddressLine1));
    validateAddressPage();
  }

  /**
   * handles text changes of AddressLine2
   */
  public void onTextChanged(CharSequence s, int start, int before, int count) {
    mAddressLine2 = s.toString();
    if (isEditable()) {
      mAddressListItemData.setAddLine2(mAddressLine2);
    }
    validateAddressPage();
  }

  /**
   * handles text changes of City
   */
  public void onTextChangeCity(CharSequence s, int start, int before, int count) {
    mCity = s.toString();
    if (isEditable()) {
      mAddressListItemData.setCity(mCity);
    }
    isCityValid.set(TextUtils.isEmpty(mCity));
    validateAddressPage();
  }

  /**
   * handles text changes of Country
   */
  public void onTextChangeCountry(CharSequence s, int start, int before, int count) {
    mCountry = s.toString();
    if (isEditable()) {
      mAddressListItemData.setCountry(mCountry);
    }
    isCountryValid.set(TextUtils.isEmpty(mCountry));

    validateAddressPage();
  }

  /**
   * handles text changes of Postal code
   */
  public void onTextChangePostalCode(CharSequence s, int start, int before, int count) {
    mPostalCode = s.toString();
    if (isEditable()) {
      mAddressListItemData.setPincode(mPostalCode);
    }
    isPostalCodeValid.set(TextUtils.isEmpty(mPostalCode));
    validateAddressPage();
  }

  /**
   * handles text changes of LandMark
   */
  public void onTextChangeLandMark(CharSequence s, int start, int before, int count) {
    mLandMark = s.toString();
    if (isEditable()) {
      mAddressListItemData.setLandmark(mLandMark);
    }
    validateAddressPage();
  }

  /**
   * handles text changes of Other Adress type
   */
  public void onTextChangeOtherAddress(CharSequence s, int start, int before, int count) {
    mAddressTypeVal = s.toString();
    if (isEditable()) {
      mAddressListItemData.setTaggedAs(mAddressTypeVal);
    }
    validateAddressPage();
  }

  /**
   * handles text changes of AreaName
   */
  public void onTextChangeAreaName(CharSequence s, int start, int before, int count) {
    mArea = s.toString();
    if (isEditable()) {
      mAddressListItemData.setLocality(mArea);
    }
    isAreaValid.set(TextUtils.isEmpty(mArea));
    validateAddressPage();
  }

  /**
   * this method is using to setLatLong value for selected values in Map
   */
  void setLatLong(String latitude, String longitude, String stateName) {
    this.mLatitude = latitude;
    this.mLongitude = longitude;
    this.mStateName = stateName;
    if (isEditable()) {
      mAddressListItemData.setLatitude(mLatitude);
      mAddressListItemData.setLongitude(mLongitude);
      mAddressListItemData.setState(mStateName);
    }
  }

  /**
   * this method is using to set address value selected from google map
   *
   * @param addressLine1 addressLine1
   * @param area         area name
   * @param city         cityName
   * @param country      country name
   * @param postalCode   pin code
   */
  void setInitialAddressFieldValues(String addressLine1, String area, String city,
      String country,
      String postalCode) {
    this.mCity = city;
    this.mPostalCode = postalCode;
    this.mCountry = country;
    this.mArea = area;
    this.mAddressLine1 = addressLine1;
  }

  /**
   * checking whether it's Edit flow or not
   *
   * @return is Edit flow
   */
  private boolean isEditable() {
    return mUpdateType == EDIT_TYPE && mAddressListItemData != null;
  }

  /**
   * Set CountryCode and phone Number status
   *
   * @param countryCode   selected country code
   * @param isPhoNumValid is Phone number valid or not
   */
  void setPhoneNum(String countryCode, boolean isPhoNumValid) {
    mIsPhoneNumValid = isPhoNumValid;
    mCountryCode = countryCode;
    validateAddressPage();
  }

  /**
   * This method is using for validation for maintaining Button status
   */
  private void validateAddressPage() {
    if (TextUtils.isEmpty(mName) || TextUtils.isEmpty(mAddressLine1) || TextUtils.isEmpty(
        mPostalCode) || TextUtils.isEmpty(mCity) || TextUtils.isEmpty(mArea) || TextUtils.isEmpty(
        mCountry) || !mIsPhoneNumValid) {
      canActivateBtn.set(false);
      return;
    }
    canActivateBtn.set(true);
  }

  /**
   * if it's Edit Address flow set initial values of the page
   *
   * @param addressListItemData Address mData
   * @param updateType          type of the page flow
   */
  void setAddressDataForEdit(AddressListItemData addressListItemData, int updateType) {
    this.mAddressListItemData = addressListItemData;
    this.mUpdateType = updateType;
    mName = mAddressListItemData.getName();
    mAddressLine1 = mAddressListItemData.getAddLine1();
    mCity = mAddressListItemData.getCity();
    mIsPhoneNumValid = true;
    mArea = mAddressListItemData.getLocality();
    mPostalCode = mAddressListItemData.getPincode();
    mAddressType = mAddressListItemData.getTagged();
    mAddressTypeVal = mAddressListItemData.getTaggedAs();
    validateAddressPage();
  }

  /**
   * Deciding USeCase for Save Button Click
   */
  void saveBtnClickAction() {
    if (mUpdateType == EDIT_TYPE) {
      editAddress();
    } else {
      addAddress();
    }
  }

  /**
   * This method is using to send address to Server using post API
   */
  private void addAddress() {
    DisposableSingleObserver<AddAddressUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<AddAddressUseCase.ResponseValues>() {

          @Override
          public void onSuccess(AddAddressUseCase.ResponseValues responseValues) {
            mAddAddressUiAction.setValue(FINISH);
          }

          @Override
          public void onError(Throwable e) {
            isDataFound.set(e.getMessage());
          }
        };
    mHandler.execute(mAddAddressUseCase,
        new AddAddressUseCase.RequestValues(mName, mPhoneNum, mCountryCode, mArea, mAddressLine1,
            mAddressLine2, mLandMark, mCity, mCountry, "", mPostalCode, mLatitude, mLongitude,
            mAddressTypeVal, mStateName, mAddressType),
        disposableSingleObserver);

  }

  /**
   * This Method is Using to Set Address type
   *
   * @param addressVal address type value
   * @param type       address type
   */
  void setAddressType(String addressVal, int type) {
    mAddressType = type;
    mAddressTypeVal = addressVal;
    if (mAddressListItemData != null) {
      mAddressListItemData.setTagged(mAddressType);
      mAddressListItemData.setTaggedAs(mAddressTypeVal);
    }
  }

  /**
   * This method is using to edit selected address
   */
  private void editAddress() {
    DisposableSingleObserver<EditAddressUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<EditAddressUseCase.ResponseValues>() {

          @Override
          public void onSuccess(EditAddressUseCase.ResponseValues responseValues) {
            mAddAddressUiAction.setValue(FINISH);
          }

          @Override
          public void onError(Throwable e) {

          }
        };
    mHandler.execute(mEditAddressUseCase,
        new EditAddressUseCase.RequestValues(mAddressListItemData),
        disposableSingleObserver);

  }

  /**
   * This method is using to get the UI action LiveData object
   *
   * @return Ui action liveData
   */
  MutableLiveData<AddAddressUiAction> getUiActionLiveData() {
    return mAddAddressUiAction;
  }

  @Override
  public void backButtonClickListener(View view) {
    mAddAddressUiAction.setValue(FINISH);
  }
}