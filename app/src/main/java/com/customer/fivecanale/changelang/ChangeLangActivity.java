package com.customer.fivecanale.changelang;

import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.domain.model.changelan.ChangeLanDetails;
import com.customer.fivecanale.splash.SplashActivity;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ActivityChangeLangBinding;
import dagger.android.support.DaggerAppCompatActivity;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * used to change the language for the app
 */
public class ChangeLangActivity extends DaggerAppCompatActivity {
  @Inject
  public UserInfoHandler mUserInfoHandler;
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  private ActivityChangeLangBinding mActivityChangeLangBinding;
  private ChangeLanViewModel mChangeLanViewModel;
  private ImageView selectedIv;
  private String lang;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mActivityChangeLangBinding = DataBindingUtil.setContentView(this,
        R.layout.activity_change_lang);
    mChangeLanViewModel = ViewModelProviders.of(this, mViewModelFactory).get(
        ChangeLanViewModel.class);
    mActivityChangeLangBinding.setViewmodel(mChangeLanViewModel);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorWhite));
    }
    subscribeChangeLanguage();
    scrollViewListener();
    mActivityChangeLangBinding.ivToolbarBack.setOnClickListener(
        view -> finish());
    mChangeLanViewModel.callGetLanguagesApi();
  }

  /**
   * initialize for the change language data
   *
   * @param data data contains language data
   */
  private void initialize(ArrayList<ChangeLanDetails> data) {
    View viewInflator = null;
    LayoutInflater layoutInflater =
        (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    TextView langTv;
    for (int i = ZERO; i < data.size(); i++) {
      viewInflator = layoutInflater.inflate(R.layout.item_change_lang,
          mActivityChangeLangBinding.langLl, false);
      viewInflator.setId(i);
      langTv = viewInflator.findViewById(R.id.langTv);
      ImageView selectIv = viewInflator.findViewById(R.id.selectIv);
      LinearLayout mainContentLl = viewInflator.findViewById(R.id.mainContentLl);
      mainContentLl.setTag(data.get(i).getLanguageCode());
      langTv.setText(data.get(i).getLanguageName());
      if (mUserInfoHandler.getLanguage().equals(mainContentLl.getTag().toString())) {
        selectedIv = selectIv;
        selectedIv.setImageResource(R.drawable.ic_check_icon_on);
      }
      mainContentLl.setOnClickListener(
          view -> {
            EcomUtil.printLog("lang is " + mainContentLl.getTag());
            lang = mainContentLl.getTag().toString();
            if (selectedIv == null) {
              selectedIv = selectIv;
              selectedIv.setImageResource(R.drawable.ic_check_icon_on);
            } else {
              selectedIv.setImageResource(R.drawable.ic_check_off);
              DrawableCompat.setTint(
                  selectedIv.getDrawable(),
                  ContextCompat.getColor(ChangeLangActivity.this, R.color.app_color));
              selectedIv = selectIv;
              selectedIv.setImageResource(R.drawable.ic_check_icon_on);
            }
            mUserInfoHandler.setLanguage(lang);
            Intent intent = new Intent(ChangeLangActivity.this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
          });
      mActivityChangeLangBinding.langLl.addView(viewInflator);
    }
  }

  /**
   * used to hide and show the title for this screen while scrolling.
   */
  private void scrollViewListener() {
    mActivityChangeLangBinding.scrollView
        .getViewTreeObserver()
        .addOnScrollChangedListener(
            () -> {
              mActivityChangeLangBinding.tvToolbarTitle.setVisibility(
                  (mActivityChangeLangBinding.scrollView.getScrollY()
                      > mActivityChangeLangBinding.rlToolbarDummy.getHeight() + 30) ? View.VISIBLE
                      : View.INVISIBLE);
              mActivityChangeLangBinding.viewTopShadow.setVisibility(
                  (mActivityChangeLangBinding.scrollView.getScrollY()
                      > mActivityChangeLangBinding.rlToolbarDummy.getHeight() + 30) ? View.VISIBLE
                      : View.INVISIBLE);
            });
  }

  /**
   * subscribe to change language data
   */
  private void subscribeChangeLanguage() {
    mChangeLanViewModel.getLanguageData().observe(this,
        changeLangData -> initialize(changeLangData));
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
  }
}
