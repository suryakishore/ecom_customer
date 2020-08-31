package com.customer.fivecanale.cart;

import static com.customer.fivecanale.util.EcomConstants.CONFIRM_ORDER;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;

import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.R;
import com.customer.fivecanale.landing.cartscreen.EcomCartFragment;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ActivityEcomCartBinding;
import dagger.android.support.DaggerAppCompatActivity;
import javax.inject.Inject;

/*shows the cart screen*/
public class EcomCartActivity extends DaggerAppCompatActivity {
  @Inject
  EcomCartFragment mEcomCartFragment;
  private ActivityEcomCartBinding mBinding;
  private boolean isBack = TRUE;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeView();
    loadFragment();
  }

  private void initializeView() {
    mBinding = DataBindingUtil.setContentView(
        this,
        R.layout.activity_ecom_cart);
  }

  /*loads the cart fragment*/
  private void loadFragment() {
    EcomUtil.printLog("CONFIRM_ORDER" + getIntent().getBooleanExtra(CONFIRM_ORDER, FALSE));
    Bundle bundle = getIntent().getExtras();
    mEcomCartFragment.setArguments(bundle);
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.add(R.id.frameContainer, mEcomCartFragment);
    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    fragmentTransaction.commit();
  }

  /**
   * used to allow the user to go back
   *
   * @param isBack is back
   */
  public void allowBack(boolean isBack) {
    this.isBack = isBack;
  }

  @Override
  public void onBackPressed() {
    if (isBack) {
      super.onBackPressed();
    }
  }
}