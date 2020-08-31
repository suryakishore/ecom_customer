package com.customer.fivecanale.referfriend;

import static com.customer.fivecanale.util.EcomConstants.EMPTY;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.BuildConfig;
import com.R;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.databinding.ActivityReferFriendBinding;
import dagger.android.support.DaggerAppCompatActivity;

import java.util.Locale;

import javax.inject.Inject;

/*
 * Purpose – This class Holds UI for Refer Friends.
 * @author 3Embed
 * Created on Dec 20, 2019
 * Modified on
 */
public class ReferFriendActivity extends DaggerAppCompatActivity {
  @Inject
  public UserInfoHandler mUserInfoHandler;
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  private ActivityReferFriendBinding mBinding;
  private ReferFriendViewModel mViewModel;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(
        this,
        R.layout.activity_refer_friend);
    mBinding.incRefFriendHeader.tvCenterCategoryName.setText(R.string.referFriends);
    initialize();
  }

  /**
   * This method is using to initialize basic resources
   */
  private void initialize() {
    mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ReferFriendViewModel.class);
    mBinding.setViewModel(mViewModel);
    mViewModel.getUiActionLiveData().observe(this, referFriendUiAction -> finish());
    mBinding.tvReferFriendCode.setText(mUserInfoHandler.getUserReferral());
  }

  /**
   * This method is using to handle click events
   *
   * @param view view that fired the click event
   */
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.tvReferFriendCode:
      case R.id.tvCopyToClip:
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(EMPTY,
            mBinding.tvReferFriendCode.getText().toString());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, R.string.clipboardSuccess, Toast.LENGTH_SHORT).show();
        break;
      case R.id.btnShareReferralCode:
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String message = String.format(Locale.ENGLISH, "%s. %s %s\n%s %s %s %s", mBinding.tvReferFriendDesc.getText(),
                getResources().getString(R.string.useCode), mBinding.tvReferFriendCode.getText(),
                getResources().getString(R.string.visit), BuildConfig.REFER_LINK,
                getResources().getString(R.string.toInstall), getResources().getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(intent, getString(R.string.app_name)));
    }
  }
}