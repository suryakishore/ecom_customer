package com.customer.fivecanale.addresslist;

import static com.customer.fivecanale.util.EcomConstants.ACTION;
import static com.customer.fivecanale.util.EcomConstants.ADDRESS;
import static com.customer.fivecanale.util.EcomConstants.ADDRESS_LIST_DATA;
import static com.customer.fivecanale.util.EcomConstants.ADDRESS_SCREEN;
import static com.customer.fivecanale.util.EcomConstants.CART_AMT;
import static com.customer.fivecanale.util.EcomConstants.CART_SCREEN;
import static com.customer.fivecanale.util.EcomConstants.COMING_FROM;
import static com.customer.fivecanale.util.EcomConstants.EDIT_TYPE;
import static com.customer.fivecanale.util.EcomConstants.EMPTY_STRING;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.IS_DEFAULT;
import static com.customer.fivecanale.util.EcomConstants.IS_FROM_CART;
import static com.customer.fivecanale.util.EcomConstants.NONE;
import static com.customer.fivecanale.util.EcomConstants.PROFILE_SCREEN;
import static com.customer.fivecanale.util.EcomConstants.TRUE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.customer.domain.interactor.handler.PermissionHandler;
import com.customer.domain.model.getaddress.AddressListItemData;
import com.customer.fivecanale.manageaddress.AddAddressActivity;
import com.customer.fivecanale.payment.PaymentMethodActivity;
import com.customer.fivecanale.uiutil.dialog.CustomDialogUtil;
import com.databinding.ActivitySavedAddressListBinding;
import dagger.android.support.DaggerAppCompatActivity;
import java.util.ArrayList;
import javax.inject.Inject;

/*
 * Purpose – This class Holds list of Address added by user.
 * @author 3Embed
 * Created on Dec 10, 2019
 * Modified on
 */
public class SavedAddressListActivity extends DaggerAppCompatActivity implements
    AddressListAdapter.OnAddressItemClickListener, CustomDialogUtil.SimpleAlertDialogClickHandler {
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  @Inject
  PermissionHandler mPermissionHandler;
  private ActivitySavedAddressListBinding mBinding;
  private SavedAddressListViewModel mViewModel;
  private String mAddressId;
  private String mComingFrom = EMPTY_STRING;
  private String mTotalAmount = EMPTY_STRING;
  private AddressListItemData mAddressData;
  private ArrayList<AddressListItemData> mAddressListItemData = new ArrayList<>();
  private AddressListAdapter mAdapter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(
        this,
        R.layout.activity_saved_address_list);
    initialization();
  }

  /**
   * This method is using for resource initialization
   */
  private void initialization() {
    mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(
        SavedAddressListViewModel.class);
    mBinding.setViewModel(mViewModel);
    mViewModel.getAddressList();
    boolean isFromCart = getIntent().getBooleanExtra(IS_FROM_CART, FALSE);
    if(getIntent() != null && getIntent().getStringExtra(COMING_FROM) != null) {
      mComingFrom = getIntent().getStringExtra(COMING_FROM);
      if (mComingFrom != null && mComingFrom.equals(PROFILE_SCREEN)) {
        mBinding.btnContinue.setVisibility(View.GONE);
      }
    }
    if(getIntent() != null && getIntent().getStringExtra(CART_AMT) != null) {
      mTotalAmount = getIntent().getStringExtra(CART_AMT);
    }
    mBinding.incHeader.tvCenterCategoryName.setText(isFromCart ? getString(R.string.confirmAddressTitle)
        : getString(R.string.manageAddressTitle));
    mViewModel.getUiActionListener().observe(this, savedAddressUiAction -> finish());
    mViewModel.getAddressListLiveData().observe(this,
        addressListItemData -> {
          mAddressListItemData.clear();
          mAddressListItemData.addAll(addressListItemData);
          mAdapter = new AddressListAdapter(mAddressListItemData, this, isFromCart, mViewModel.lastSelectedPosition);
          mBinding.rvAddressList.setAdapter(mAdapter);
          mAdapter.notifyDataSetChanged();
          if(mViewModel.lastSelectedPosition != NONE && mViewModel.lastSelectedPosition < mAddressListItemData.size()) {
            mBinding.rvAddressList.scrollToPosition(mViewModel.lastSelectedPosition);
          }
        });
    mViewModel.getAddressListData().observe(this, indexValue -> {
      if (indexValue != null) {
        if (isFromCart) {
          selectAddress(mAddressListItemData.get(indexValue));
        }
      }
    });
    mBinding.btnAddAddress.setOnClickListener(
        view -> {
          launchAddAddressActivity();
        });

    mBinding.tvAddAddress.setOnClickListener(
            view -> {
              launchAddAddressActivity();
            });

    mBinding.btnContinue.setOnClickListener(v -> {
      switch (mComingFrom) {

        case CART_SCREEN:
          Intent intents = new Intent(SavedAddressListActivity.this, PaymentMethodActivity.class);
          intents.putExtra(CART_AMT, mTotalAmount);
          intents.putExtra(IS_FROM_CART, TRUE);
          intents.putExtra(COMING_FROM, ADDRESS_SCREEN);
          startActivity(intents);
          break;

        default:
          Intent data = new Intent();
          data.putExtra(ADDRESS_LIST_DATA, mAddressData);
          setResult(RESULT_OK, data);
          finish();
          break;
      }
    });
  }

  /**
   * launch add address activity
   */
  private void launchAddAddressActivity() {
    startActivity(new Intent(SavedAddressListActivity.this, AddAddressActivity.class));
    mBinding.btnAddAddress.setEnabled(false);
  }

  /**
   * set up screen as per delivery
   */
  private void setUpScreen() {
      mBinding.tvAddAddress.setVisibility(View.VISIBLE);
      mBinding.tvSavedAddresses.setVisibility(View.VISIBLE);
      mBinding.btnAddAddress.setVisibility(View.GONE);
  }

  @Override
  protected void onRestart() {
    super.onRestart();
    mBinding.btnAddAddress.setEnabled(true);
    mBinding.tvAddAddress.setEnabled(true);
    mViewModel.getAddressList();
  }

  @Override
  public void onDeleteClickListener(String addressId) {
    this.mAddressId = addressId;
    CustomDialogUtil.basicAlertDialog(this, this,
        getString(R.string.alert), getString(R.string.deleteAddressWarning), NONE);
  }

  @Override
  public void onEditClickListener(AddressListItemData addressListItemData) {
    Intent intent = new Intent(this, AddAddressActivity.class);
    intent.putExtra(ACTION, EDIT_TYPE);
    intent.putExtra(ADDRESS, addressListItemData);
    intent.putExtra(IS_DEFAULT, mViewModel.getIsEmptyValue());
    startActivity(intent);
  }

  @Override
  public void makeAddressDefault(String addressId, String prevDefAddressId, int lastSelectedPosition) {
    mViewModel.makeAddressAsDefault(addressId, prevDefAddressId, lastSelectedPosition);
  }

  @Override
  public void selectAddress(AddressListItemData addressListItemData) {
    mBinding.btnContinue.setEnabled(TRUE);
    this.mAddressData = addressListItemData;
  }

  @Override
  public void onOkClickListener(int type) {
    mViewModel.deleteAddress(mAddressId);
  }
}
