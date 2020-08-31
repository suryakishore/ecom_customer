package com.customer.fivecanale.landing;

import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.FIVE;
import static com.customer.fivecanale.util.EcomConstants.IS_TO_SHOW_MY_ORDERS;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.ORDER_ID;
import static com.customer.fivecanale.util.EcomConstants.THREE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.TWO;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.customer.domain.interactor.handler.CartHandler;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.fivecanale.ApplicationManager;
import com.customer.fivecanale.boarding.login.EcomLoginActivity;
import com.customer.fivecanale.landing.cartscreen.EcomCartFragment;
import com.customer.fivecanale.landing.historyscreen.HistoryScreenFragment;
import com.customer.fivecanale.landing.homescreen.HomeFragment;
import com.customer.fivecanale.landing.profile.EcomProfileFragment;
import com.customer.fivecanale.landing.searchscreen.SearchScreenFragment;
import com.customer.fivecanale.recievers.ConnectivityChangeReceiver;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import dagger.android.support.DaggerAppCompatActivity;
import javax.inject.Inject;

/*
 * Purpose – This class Holds all the Fragment which showing BottomNavigationView .
 * @author 3Embed
 * Created on Dec 10, 2019
 * Modified on
 */
public class HomeActivity extends DaggerAppCompatActivity implements
    OnNavigationItemSelectedListener {
  @Inject
  public UserInfoHandler mUserInfoHandler;
  @Inject
  HomeFragment homeScreenFragment;
  @Inject
  SearchScreenFragment searchScreenFragment;
  @Inject
  EcomCartFragment mEcomCartFragment;
  @Inject
  HistoryScreenFragment historyScreenFragment;
  @Inject
  EcomProfileFragment profileScreenFragment;
  View badgeViewCart;
  View childViewCart;
  View badgeViewHistory;
  View childViewHistory;
  BottomNavigationMenuView bottomNavigationMenuView;
  @Inject
  CartHandler mCartHandler;
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  private Fragment activeFragment;
  private FragmentManager fragManager;
  private ActivityHomeBinding mBinding;
  private TextView cartCountTv, historyCountTv;
  private HomeModel mHomeModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(
        this,
        R.layout.activity_home);
    initView();
    initializeViewModel();
    subScribeCartData();
    subScribeHistoryData();
    setCartIconNotificationBatch();
    ApplicationManager.getInstance().callUpdateVersionApi(this);
    ApplicationManager.getInstance().callGetAppConfigApi(this, ZERO);
    if (mUserInfoHandler.isUserLoggedIn()) {
      mHomeModel.callGetOrdersCountApi();
    }
  }

  /**
   * This method is using to initialize all the basic resources.
   */
  private void initView() {
    fragManager = getSupportFragmentManager();
    mBinding.bnvHome.setOnNavigationItemSelectedListener(this);
    getApplication().registerReceiver(new ConnectivityChangeReceiver(mUserInfoHandler),
        new IntentFilter(
            ConnectivityManager.CONNECTIVITY_ACTION));
    bottomNavigationMenuView = (BottomNavigationMenuView) mBinding.bnvHome.getChildAt(ZERO);
    childViewCart = bottomNavigationMenuView.getChildAt(TWO);
    BottomNavigationItemView itemView = (BottomNavigationItemView) childViewCart;
    badgeViewCart = LayoutInflater.from(this).inflate(R.layout.cart_bottomnavigation_tv,
        bottomNavigationMenuView, false);
    cartCountTv = badgeViewCart.findViewById(R.id.cartCountTv);
    itemView.addView(badgeViewCart);
    childViewHistory = bottomNavigationMenuView.getChildAt(THREE);
    BottomNavigationItemView itemViewHistory = (BottomNavigationItemView) childViewHistory;
    badgeViewHistory = LayoutInflater.from(this).inflate(R.layout.cart_bottomnavigation_tv,
        bottomNavigationMenuView, false);
    historyCountTv = badgeViewHistory.findViewById(R.id.cartCountTv);
    itemViewHistory.addView(badgeViewHistory);
    badgeViewHistory.setVisibility(View.GONE);
  }

  /**
   * <p>This method is used initialize the viewModel class for this activity.</p>
   */
  private void initializeViewModel() {
    mHomeModel = ViewModelProviders.of(this, mViewModelFactory).get(
        HomeModel.class);
    mBinding.setViewmodel(mHomeModel);
    mHomeModel.onCartUpdate();
  }

  /**
   * subscribe to live data
   */
  private void subScribeCartData() {
    mHomeModel.getCartCountData().observe(this, action -> {
      if (action == FIVE) {
        badgeViewCart.setVisibility(View.VISIBLE);
        cartCountTv.setText(String.valueOf(ONE));
      } else {
        if (action == ZERO) {
          badgeViewHistory.setVisibility(View.GONE);
        }
        setCartIconNotificationBatch();
      }
    });
  }

  /**
   * subscribe to history count data
   */
  private void subScribeHistoryData() {
    mHomeModel.getHistoryCountData().observe(this, count -> {
      historyCountTv.setText(String.valueOf(count));
      badgeViewHistory.setVisibility(count > ZERO ? View.VISIBLE : View.GONE);
    });
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      case R.id.homeHome:
        return preserveLastFragment(homeScreenFragment);
      case R.id.homeSearch:
        if (!searchScreenFragment.isAdded()) {
          fragManager
              .beginTransaction()
              .add(R.id.clHomeContainer, searchScreenFragment, searchScreenFragment.getTag())
              .hide(searchScreenFragment)
              .commit();
        }
        return preserveLastFragment(searchScreenFragment);
      case R.id.homeProfile:
        return preserveLastFragment(profileScreenFragment);
      case R.id.homeCart:
        if (!mEcomCartFragment.isAdded()) {
          fragManager
              .beginTransaction()
              .add(R.id.clHomeContainer, mEcomCartFragment, mEcomCartFragment.getTag())
              .hide(mEcomCartFragment)
              .commit();
        }
        return preserveLastFragment(mEcomCartFragment);
      case R.id.homeHistory:
        if (mUserInfoHandler.isUserLoggedIn()) {
          if (!historyScreenFragment.isAdded()) {
            fragManager
                .beginTransaction()
                .add(R.id.clHomeContainer, historyScreenFragment, historyScreenFragment.getTag())
                .hide(historyScreenFragment)
                .commit();
          }
          return preserveLastFragment(historyScreenFragment);
        } else {
          Intent intent = new Intent(this, EcomLoginActivity.class);
          startActivity(intent);
        }
    }
    return false;
  }

  /**
   * This method is using to maintain Fragment Status
   *
   * @param fragment fragment object to maintain status
   * @return return true true always to send it for OnNavigation super method
   */
  private boolean preserveLastFragment(Fragment fragment) {
    fragManager.beginTransaction().hide(activeFragment).show(fragment).commit();
    activeFragment = fragment;
    return true;
  }

  /**
   * This method is using to load all the Fragment to BottomNavigationView
   */
  public void loadFragment() {
    activeFragment = homeScreenFragment;
    if (!activeFragment.isAdded()) {
      fragManager
          .beginTransaction()
          .add(R.id.clHomeContainer, homeScreenFragment, homeScreenFragment.getTag())
          .commit();
      fragManager
          .beginTransaction()
          .add(R.id.clHomeContainer, profileScreenFragment, profileScreenFragment.getTag())
          .hide(profileScreenFragment)
          .commit();
      if (getIntent().getBooleanExtra(IS_TO_SHOW_MY_ORDERS, FALSE) || getIntent().getStringExtra(
          ORDER_ID) != null) {
        loadHistoryFragment();
      }
    }
  }

  /**
   * displays the history fragment.
   */
  private void loadHistoryFragment() {
    if (!historyScreenFragment.isAdded()) {
      fragManager
          .beginTransaction()
          .add(R.id.clHomeContainer, historyScreenFragment, historyScreenFragment.getTag())
          .commit();
      mBinding.bnvHome.getMenu().getItem(THREE).setChecked(TRUE);
    }
    preserveLastFragment(historyScreenFragment);
  }

  /*displays the home fragment*/
  public void showHomeFragment() {
    mBinding.bnvHome.setSelectedItemId(R.id.homeHome);
  }

  /*displays the home fragment*/
  public void showCartFragment() {
    mBinding.bnvHome.setSelectedItemId(R.id.homeCart);
  }

  /**
   * <H>Showing batch count for cart</H>
   * <P>This method is used</P>
   */
  public void setCartIconNotificationBatch() {
    new Thread(() -> {
      int count = mCartHandler.totalCartItems();
      EcomUtil.printLog("exe" + "setCartIconNotificationBatch   " + count);
      if (count > ZERO) {
        runOnUiThread(() -> {
          // Stuff that updates the UI
          badgeViewCart.setVisibility(View.VISIBLE);
          cartCountTv.setText(String.valueOf(count));
        });
      } else {
        runOnUiThread(() ->
            badgeViewCart.setVisibility(View.GONE)
        );
      }
    }).start();
  }

  @Override
  public void onBackPressed() {
    if (activeFragment != null && activeFragment == homeScreenFragment) {
      super.onBackPressed();
    } else {
      fragManager.beginTransaction().hide(activeFragment).commit();
      activeFragment = homeScreenFragment;
      showHomeFragment();
    }
  }

  /**
   * this will show progress when api hitted from application manager.
   *
   * @param showProgress boolean
   */
  public void showProgress(boolean showProgress) {
    mHomeModel.progressVisible.set(showProgress);
  }
}