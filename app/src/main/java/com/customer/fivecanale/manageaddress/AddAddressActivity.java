package com.customer.fivecanale.manageaddress;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.customer.fivecanale.util.EcomConstants.ACTION;
import static com.customer.fivecanale.util.EcomConstants.ADDRESS;
import static com.customer.fivecanale.util.EcomConstants.ADDRESS_HOME_TYPE;
import static com.customer.fivecanale.util.EcomConstants.ADDRESS_OTHER_TYPE;
import static com.customer.fivecanale.util.EcomConstants.ADDRESS_REQUEST;
import static com.customer.fivecanale.util.EcomConstants.ADDRESS_WORK_TYPE;
import static com.customer.fivecanale.util.EcomConstants.EDIT_TYPE;
import static com.customer.fivecanale.util.EcomConstants.LAT;
import static com.customer.fivecanale.util.EcomConstants.LONG;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.REQUEST_FOR_LOCATION;
import static com.customer.fivecanale.util.EcomConstants.ZERO;
import static com.customer.fivecanale.util.EcomConstants.ZOOM_RANGE;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.customer.domain.model.getaddress.AddressListItemData;
import com.customer.fivecanale.util.EcomUtil;
import com.customer.fivecanale.util.LocationManagerUtil;
import com.customer.fivecanale.util.WorkaroundMapFragment;
import com.databinding.ActivitySaveAddressBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import dagger.android.support.DaggerAppCompatActivity;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import javax.inject.Inject;

/*
 * Purpose – This class Holds Ui for Add and edit Address .
 * @author 3Embed
 * Created on Dec 10, 2019
 * Modified on
 */
public class AddAddressActivity extends DaggerAppCompatActivity implements OnMapReadyCallback,
    GoogleMap.OnCameraIdleListener {
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  private ActivitySaveAddressBinding mBinding;
  private GoogleMap mGoogleMap;
  private AddAddressViewModel mViewModel;
  private boolean mIsValidPhoneNum;
  private double mLat, mLong;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(
        this,
        R.layout.activity_save_address);
    initialize();
    LocationManagerUtil.setMapHeight(this);
  }

  /**
   * this method is using to do all the resource initialization
   */
  private void initialize() {
    mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(AddAddressViewModel.class);
    mViewModel.setAddressType(getString(R.string.addressHomeType), ADDRESS_HOME_TYPE);
    mBinding.setViewModel(mViewModel);
    mBinding.rbAddressHome.setChecked(true);
    mBinding.rgAddressType.setOnCheckedChangeListener((radioGroup, i) -> {
      mBinding.tilSaveAddressOther.setVisibility(View.GONE);
      switch (i) {
        case R.id.rbAddressHome:
          mViewModel.setAddressType(getString(R.string.addressHomeType), ADDRESS_HOME_TYPE);
          break;
        case R.id.rbAddressWork:
          mViewModel.setAddressType(getString(R.string.addressWorkType), ADDRESS_WORK_TYPE);
          break;
        case R.id.rbAddressOther:
          mBinding.tilSaveAddressOther.setVisibility(View.VISIBLE);
          mBinding.tilSaveAddressOther.requestFocus();
          mViewModel.setAddressType(mBinding.etSaveAddressOther.getText().toString(),
              ADDRESS_OTHER_TYPE);
          break;
      }
    });
    if (getIntent() != null && getIntent().getIntExtra(ACTION, ZERO) == EDIT_TYPE) {
      AddressListItemData addressListItemData = getIntent().getParcelableExtra(ADDRESS);
      switch (addressListItemData.getTagged()) {
        case ADDRESS_HOME_TYPE:
          mBinding.rbAddressHome.setChecked(true);
          break;
        case ADDRESS_WORK_TYPE:
          mBinding.rbAddressWork.setChecked(true);
          break;
        case ADDRESS_OTHER_TYPE:
          mBinding.rbAddressOther.setChecked(true);
          mBinding.etSaveAddressOther.setVisibility(View.VISIBLE);
          mBinding.etSaveAddressOther.setText(addressListItemData.getTaggedAs());
          break;
      }
      mViewModel.setAddressDataForEdit(addressListItemData, getIntent().getIntExtra(ACTION, ZERO));
      mBinding.incHeader.tvTitle.setText(getString(R.string.editAddress));
      setAddressForEdit(getIntent().getParcelableExtra(ADDRESS));
    } else {
      getFuseLocation();
      mBinding.incHeader.tvTitle.setText(getString(R.string.addNewAddress));
    }
    perMissionForLocation();
    mViewModel.setInitialAddressFieldValues(
        Objects.requireNonNull(mBinding.etSaveAddressLine1.getText()).toString(),
        Objects.requireNonNull(mBinding.etSaveAddressArea.getText()).toString(),
        Objects.requireNonNull(mBinding.etSaveAddressCity.getText()).toString(),
        Objects.requireNonNull(mBinding.etSaveAddressCountry.getText()).toString(),
        Objects.requireNonNull(mBinding.etSaveAddressPostalCode.getText()).toString());
    SupportMapFragment mapFragment =
        (SupportMapFragment) (getSupportFragmentManager().findFragmentById(R.id.map));
    assert mapFragment != null;
    mapFragment.getMapAsync(this);
    ((WorkaroundMapFragment) mapFragment).setListener(
        () -> mBinding.scrollView.requestDisallowInterceptTouchEvent(true));
    mBinding.btnAddAddressSave.setOnClickListener(view -> mViewModel.saveBtnClickAction());
    mBinding.ccpGetNumber.registerCarrierNumberEditText(mBinding.etSaveAddressMob);
    mBinding.ccpGetNumber.setPhoneNumberValidityChangeListener(isValidNumber -> {
      mIsValidPhoneNum = isValidNumber;
      mViewModel.setPhoneNum(mBinding.ccpGetNumber.getSelectedCountryCode(), isValidNumber);
      mBinding.etSaveAddressMob.setFilters(
          mIsValidPhoneNum && !Objects.requireNonNull(
              mBinding.etSaveAddressMob.getText()).toString().isEmpty()
              ?
              new InputFilter[]{new InputFilter.LengthFilter(
                  mBinding.etSaveAddressMob.getText().toString().length())}
              : new InputFilter[]{});
    });
    mViewModel.getUiActionLiveData().observe(this, addAddressUiAction -> {
      setResult(RESULT_OK);
      finish();
    });
  }

  /**
   * This method is using to get current location of the device
   */
  private void getFuseLocation() {
    FusedLocationProviderClient fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(this);
    fusedLocationClient.getLastLocation()
        .addOnSuccessListener(this, location -> {
          if (location != null) {
            mLat = location.getLatitude();
            mLong = location.getLongitude();
            if (mGoogleMap != null) {
              setLocationInMap(mLat, mLong);
            }
          }
        });
  }

  /**
   * This method is using to set the Ui Values in Edit Address flow
   *
   * @param addressData mData to set UI
   */
  private void setAddressForEdit(AddressListItemData addressData) {
    mBinding.etSaveAddressMob.setText(addressData.getMobileNumber());
    mBinding.etSaveAddressName.setText(addressData.getName());
    mBinding.etSaveAddressLine1.setText(addressData.getAddLine1());
    mBinding.etSaveAddressLine2.setText(addressData.getAddLine2());
    mBinding.etSaveAddressLandMark.setText(addressData.getLandmark());
    mBinding.etSaveAddressCity.setText(addressData.getCity());
    mBinding.etSaveAddressCountry.setText(addressData.getCountry());
    mBinding.etSaveAddressArea.setText(addressData.getLocality());
    mBinding.etSaveAddressPostalCode.setText(addressData.getPincode());
    if (addressData.getLatitude() != null && !addressData.getLatitude().isEmpty()) {
      mLat = Double.parseDouble(addressData.getLatitude());
    }
    if (addressData.getLongitude() != null && !addressData.getLongitude().isEmpty()) {
      mLong = Double.parseDouble(addressData.getLongitude());
    }
    if (TextUtils.isDigitsOnly(addressData.getMobileNumberCode())) {
      mBinding.ccpGetNumber.setCountryForPhoneCode(
          Integer.parseInt(addressData.getMobileNumberCode()));
    }
  }

  @Override
  public void onCameraIdle() {
    LatLng center = mGoogleMap.getCameraPosition().target;
    EcomUtil.printLog("Map LatLong Lat-" + center.latitude + "Long-" + center.longitude);
    if (mBinding.incHeader.tvTitle.getText().toString().equals(
        getResources().getString(R.string.addNewAddress))) {
      getAddressAndSetFromLatLng(center.latitude, center.longitude);
    }
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    this.mGoogleMap = googleMap;
    this.mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
    this.mGoogleMap.setOnCameraIdleListener(this);
    setLocationInMap(mLat, mLong);
  }

  /**
   * This method is using to set lat long in map
   *
   * @param lat latitude to set
   * @param lng longitude to set
   */
  private void setLocationInMap(double lat, double lng) {
    LatLng latLng = new LatLng(lat, lng);
    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM_RANGE));
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      return;
    }
    mGoogleMap.setMyLocationEnabled(true);
  }

  /**
   * This method is using to get the Address using Latitude and Longitude of the selected location
   * from the map
   */
  private void getAddressAndSetFromLatLng(double latitude, double longitude) {
    Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
    try {
      List<Address> addresses = geocoder.getFromLocation(latitude, longitude, ONE);
      if (addresses.size() > ZERO) {
        Address fetchedAddress = addresses.get(ZERO);
        EcomUtil.printLog("AddressList-" + addresses.toString());
        mBinding.etSaveAddressLine1.setText(fetchedAddress.getThoroughfare());
        mBinding.etSaveAddressCity.setText(fetchedAddress.getLocality());
        mBinding.etSaveAddressCountry.setText(fetchedAddress.getCountryName());
        mBinding.etSaveAddressPostalCode.setText(fetchedAddress.getPostalCode());
        mViewModel.setLatLong(String.valueOf(fetchedAddress.getLatitude()),
            String.valueOf(fetchedAddress.getLongitude()), fetchedAddress.getAdminArea());
      }
    } catch (IOException e) {
      e.printStackTrace();
      EcomUtil.printLog("AddressList- Could not get address..!" + e.getMessage());
    }
  }

  /**
   * To check for run time permission for location if post Marshmallow
   * else directly call location API
   */
  private void perMissionForLocation() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !(checkSelfPermission(
        ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
      requestPermissions(new String[]{ACCESS_FINE_LOCATION}, REQUEST_FOR_LOCATION);
    } else {
      if (mGoogleMap != null) {
        mGoogleMap.setMyLocationEnabled(true);
      }
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (grantResults[ZERO] == PackageManager.PERMISSION_GRANTED) {
      getFuseLocation();
    } else {
      /*Intent intent = new Intent(this, AddressActivity.class);
      intent.putExtra(COMING_FROM, MAP_PAGE);
      startActivityForResult(intent, ADDRESS_REQUEST);*/
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == ADDRESS_REQUEST && data != null) {
      double lat = data.getDoubleExtra(LAT, ZERO);
      double lng = data.getDoubleExtra(LONG, ZERO);
      setLocationInMap(lat, lng);
    }
  }
}