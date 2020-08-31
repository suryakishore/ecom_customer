package com.customer.fivecanale.landing.profile;

import static com.customer.fivecanale.util.EcomConstants.COMING_FROM;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.IS_TO_SELECT_CARD;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.PROFILE_SCREEN;
import static com.customer.fivecanale.util.EcomConstants.WALLET_AMT;
import static com.customer.fivecanale.util.EcomConstants.WALLET_RC;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.fivecanale.addresslist.SavedAddressListActivity;
import com.customer.fivecanale.boarding.login.EcomLoginActivity;
import com.customer.fivecanale.changelang.ChangeLangActivity;
import com.customer.fivecanale.help.HelpActivity;
import com.customer.fivecanale.landing.profile.profiledetails.ProfileDetailsActivity;
import com.customer.fivecanale.referfriend.ReferFriendActivity;
import com.customer.fivecanale.savedcards.SavedCardsActivity;
import com.customer.fivecanale.uiutil.AnimUtil;
import com.customer.fivecanale.uiutil.dialog.CustomDialogUtil;
import com.customer.fivecanale.util.EcomUtil;
import com.customer.fivecanale.util.LocationManagerUtil;
import com.customer.fivecanale.wallet.EcomWalletActivity;
import com.customer.fivecanale.wishlist.WishListActivity;
import com.databinding.FragmentEcomProfileBinding;
import dagger.android.support.DaggerFragment;
import java.util.Objects;
import javax.inject.Inject;

/**
 * EcomProfileFragment
 * <p>
 * this class is used to show the user profile mData
 */
public class EcomProfileFragment extends DaggerFragment implements
    CustomDialogUtil.DialogCallBackListener {
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  @Inject
  UserInfoHandler mUserInfoHandler;
  private FragmentEcomProfileBinding mBinding;
  private ProfileViewModel mProfileViewModel;

  @Inject
  public EcomProfileFragment() {
  }

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    mBinding =
        DataBindingUtil.inflate(
            LayoutInflater.from(getContext()), R.layout.fragment_ecom_profile, null, false);
    View view = mBinding.getRoot();
    initialize();
    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    mProfileViewModel.getUserProfileInfo();
  }

  /**
   * initialize
   * <p>
   * This Method is to do initial set up
   */
  private void initialize() {
    mProfileViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ProfileViewModel.class);
    mProfileViewModel.onProfileUpdate();
    mProfileViewModel.subscribeToWalletAmtChangeListener();
    onClickListeners();
    mProfileViewModel
        .getUserHandleLiveData()
        .observe(
            Objects.requireNonNull(getActivity()),
            userData -> {
              EcomUtil.printLog("exe" + "userData.getProfilePic()" + userData.getProfilePic());
              if (!TextUtils.isEmpty(userData.getProfilePic())) {
                mBinding.pbProfilePick.setVisibility(View.VISIBLE);
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.drawable.ecom_profile_holder);
                requestOptions.error(R.drawable.ecom_profile_holder);
                Glide.with(this)
                    .load(userData.getProfilePic().replace(" ", "%20"))
                    .apply(RequestOptions.circleCropTransform().apply(requestOptions))
                    .listener(new RequestListener<Drawable>() {
                      @Override
                      public boolean onLoadFailed(@Nullable GlideException e, Object model,
                          Target<Drawable> target, boolean isFirstResource) {
                        mBinding.pbProfilePick.setVisibility(View.GONE);
                        return false;
                      }

                      @Override
                      public boolean onResourceReady(Drawable resource, Object model,
                          Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        mBinding.pbProfilePick.setVisibility(View.GONE);
                        return false;
                      }
                    })
                    .into(mBinding.ivProfileImage);
              }
              mBinding.tvProfileLogin.setVisibility(
                  mUserInfoHandler.isUserLoggedIn() ? View.GONE : View.VISIBLE);
              mBinding.tvProfileUserName.setVisibility(
                  mUserInfoHandler.isUserLoggedIn() ? View.VISIBLE : View.GONE);
              mBinding.tvProfileMobile.setVisibility(
                  mUserInfoHandler.isUserLoggedIn() ? View.VISIBLE : View.GONE);
              mBinding.tvProfileLogOut.setVisibility(
                  mUserInfoHandler.isUserLoggedIn() ? View.VISIBLE : View.GONE);
              if (mUserInfoHandler.isUserLoggedIn()) {
                mBinding.tvProfileUserName.setText(userData.getName());
                StringBuilder builder = new StringBuilder();
                builder.append(userData.getCountryCode());
                builder.append(userData.getPhoneNumber());
                mBinding.tvProfileMobile.setText(builder);
              }
            });
    mBinding.setViewmodel(mProfileViewModel);
    EcomUtil.printLog("exe" + "mUserInfoHandler.walletAmt()" + mUserInfoHandler.walletAmt());
    mProfileViewModel.walletAmt.set(mUserInfoHandler.walletAmt());
    try {
      String version = String.format("%s%s", getString(R.string.versionV), Objects.requireNonNull(
          getActivity()).getPackageManager().getPackageInfo(
          getActivity().getPackageName(), ZERO).versionName);
      mBinding.tvProfileVersionCode.setText(version);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * This Method is using to subscribe to clickListener events
   */
  private void onClickListeners() {
    mBinding.tvProfileLogOut.setOnClickListener(
        view -> CustomDialogUtil.showDialog(ZERO, getActivity(), "", EcomProfileFragment.this));
    mBinding.clProfile.setOnClickListener(this::isLoggedIn);
    mBinding.tvProfileWishList.setOnClickListener(this::isLoggedIn);
    mBinding.tvProfileAddress.setOnClickListener(this::isLoggedIn);
    mBinding.tvProfileWallet.setOnClickListener(this::isLoggedIn);
    mBinding.tvProfileCard.setOnClickListener(this::isLoggedIn);
    mBinding.tvProfileReferFriend.setOnClickListener(this::isLoggedIn);
    mBinding.tvProfileHelpCenter.setOnClickListener(this::isLoggedIn);
    mBinding.tvProfileLanguage.setOnClickListener(this::clickLister);
    mBinding.tvProfileFaq.setOnClickListener(this::clickLister);
  }

  /**
   * This method is using to check whether Logged in or not
   *
   * @param view view to redirect
   */
  private void isLoggedIn(View view) {
    if (mUserInfoHandler.isUserLoggedIn()) {
      clickLister(view);
    } else {
      startActivity(new Intent(getActivity(), EcomLoginActivity.class));
      AnimUtil.activityTransitionAnimation(Objects.requireNonNull(getActivity()));
    }
  }

  /**
   * This method is using to redirect view specific flow
   *
   * @param view view to get id
   */
  private void clickLister(View view) {
    switch (view.getId()) {
      case R.id.clProfile:
        startActivity(new Intent(getActivity(), ProfileDetailsActivity.class));
        AnimUtil.activityTransitionAnimation(Objects.requireNonNull(getActivity()));
        break;
      case R.id.tvProfileWishList:
        startActivity(new Intent(getActivity(), WishListActivity.class));
        break;
      case R.id.tvProfileAddress:
        Intent intents = new Intent(getActivity(), SavedAddressListActivity.class);
        intents.putExtra(COMING_FROM, PROFILE_SCREEN);
        startActivity(intents);
        break;
      case R.id.tvProfileWallet:
        Intent intentWallet = new Intent(getActivity(), EcomWalletActivity.class);
        startActivityForResult(intentWallet, WALLET_RC);
        break;
      case R.id.tvProfileCard:
        Intent intent = new Intent(getActivity(), SavedCardsActivity.class);
        intent.putExtra(IS_TO_SELECT_CARD, FALSE);
        startActivity(intent);
        break;
      case R.id.tvProfileReferFriend:
        startActivity(new Intent(getActivity(), ReferFriendActivity.class));
        break;
      case R.id.tvProfileLanguage:
        startActivity(new Intent(getActivity(), ChangeLangActivity.class));
        break;
      case R.id.tvProfileHelpCenter:
      case R.id.tvProfileFaq:
        startActivity(new Intent(getActivity(), HelpActivity.class));
        break;
    }
    AnimUtil.activityTransitionAnimation(Objects.requireNonNull(getActivity()));
  }

  @Override
  public void onLogOutClickListener() {
    mBinding.ivProfileImage.setImageDrawable(
        getResources().getDrawable(R.drawable.ecom_profile_holder));
    double[] latLong = LocationManagerUtil.getFuseLocation(getActivity(), null);
    EcomUtil.printLog("latLong[ZERO]" + latLong[ZERO] + "latLong[TWO]" + latLong[ONE]);
    mProfileViewModel.logout(mUserInfoHandler.getIpAddress(), latLong[ZERO], latLong[ONE]);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == WALLET_RC) {
      if (resultCode == Activity.RESULT_OK) {
        if (data != null) {
          mBinding.tvWalletAmt.setText(data.getStringExtra(WALLET_AMT));
        }
      }
    }
  }
}