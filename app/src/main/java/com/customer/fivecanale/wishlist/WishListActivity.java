package com.customer.fivecanale.wishlist;

import static com.customer.fivecanale.util.EcomConstants.BUFFERING_TIME;
import static com.customer.fivecanale.util.EcomConstants.CANCEL;
import static com.customer.fivecanale.util.EcomConstants.CLEAR_ALL;
import static com.customer.fivecanale.util.EcomConstants.DEFAULT_VAL;
import static com.customer.fivecanale.util.EcomConstants.DELETE_ITEM;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;
import static com.customer.remote.http.RemoteConstants.PARENT_PRODUCT_ID;
import static com.customer.remote.http.RemoteConstants.PRODUCT_ID;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.R;
import com.customer.domain.model.common.ProductsData;
import com.customer.fivecanale.landing.HomeActivity;
import com.customer.fivecanale.pdp.ProductDetailsActivity;
import com.customer.fivecanale.sort.SortBottomSheet;
import com.customer.fivecanale.uiutil.dialog.CustomDialogUtil;
import com.customer.fivecanale.util.EcomUtil;
import com.customer.fivecanale.util.LocationManagerUtil;
import com.customer.fivecanale.util.RxTextView;
import com.databinding.ActivityEcomWishlistBinding;
import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

/*
 * Purpose – This class Holds list of WishList products added by user.
 * @author 3Embed
 * Created on Dec 11, 2019
 * Modified on
 */
public class WishListActivity extends DaggerAppCompatActivity implements
    WishListProductsAdapter.OnClickListener, CustomDialogUtil.SimpleAlertDialogClickHandler {
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  @Inject
  SortBottomSheet mSortBottomSheet;
  private WishListViewModel mViewModel;
  private ArrayList<ProductsData> mProductsData = new ArrayList<>();
  private WishListProductsAdapter mAdapter;
  private ActivityEcomWishlistBinding mBinding;
  private String mProductId;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(
        this,
        R.layout.activity_ecom_wishlist);
    initialize();
    initializeSwipe();
  }

  /**
   * initialize swipe gesture
   */
  private void initializeSwipe() {
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ZERO,
            ItemTouchHelper.LEFT) {
      @Override
      public boolean onMove(@NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            @NonNull RecyclerView.ViewHolder target) {
        return false;
      }

      @Override
      public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();

        switch (direction) {
          case ItemTouchHelper.LEFT:
            deleteItem(mProductsData.get(position).getChildProductId());
            break;
        }
      }
    };

    ItemTouchHelper touchHelper = new ItemTouchHelper(simpleCallback);
    touchHelper.attachToRecyclerView(mBinding.rvWishListProducts);
  }

  /**
   * This Method is using to Initialization of all the basic resources needed by the activity
   */
  private void initialize() {
    mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(WishListViewModel.class);
    mBinding.setViewModel(mViewModel);
    mViewModel.subscribeToWishListChangeListener();
    mBinding.incWishListHeader.tvCenterCategoryName.setText(R.string.wishList);
    mViewModel.getWishLIstLiveData().observe(this, productsData -> {
      mProductsData.clear();
      mProductsData.addAll(productsData);
      mAdapter = new WishListProductsAdapter(productsData, this);
      mBinding.rvWishListProducts.setAdapter(mAdapter);
      mAdapter.notifyDataSetChanged();
      mBinding.swipeLayout.setRefreshing(FALSE);
    });
    mViewModel.getWishList();
    mViewModel.getUiActionListener().observe(this, wishListUiAction -> finish());
    mBinding.ivWishListFilter.setOnClickListener(view -> showSortBottomSheet());
    mViewModel.getSuggestionNotFound().observe(this, aBoolean -> {
      mBinding.incEmptyScreen.clEmptyScreenMain.setVisibility(
          aBoolean ? View.VISIBLE : View.GONE);
      mBinding.rvWishListProducts.setVisibility(aBoolean ? View.GONE : View.VISIBLE);
    });
    RxTextView.textChanges(mBinding.etWishListSearch)
        .debounce(BUFFERING_TIME, TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new io.reactivex.Observer<CharSequence>() {
          @Override
          public void onSubscribe(Disposable d) {
          }

          @Override
          public void onNext(CharSequence charSequence) {
            if (!TextUtils.isEmpty(charSequence)) {
              mViewModel.getWishListFromApi(DEFAULT_VAL, charSequence.toString().trim());
            } else {
              mViewModel.getWishList();
            }
          }

          @Override
          public void onError(Throwable e) {
          }

          @Override
          public void onComplete() {
          }
        });
    mBinding.swipeLayout.setOnRefreshListener(() -> mViewModel.getWishList());
  }

  @Override
  public void deleteItem(String productId) {
    mProductId = productId;
    CustomDialogUtil.basicAlertDialog(this, this, getString(R.string.alert),
        getString(R.string.deleteWishlistItemWarning), DELETE_ITEM);
  }

  /**
   * This method is using to handle click listener events
   *
   * @param view view is using to get id
   */
  public void onClickListener(View view) {
    switch (view.getId()) {
      case R.id.btnContinueShop:
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        break;
      case R.id.btnClearWishList:
        CustomDialogUtil.basicAlertDialog(this, this, getString(R.string.alert),
            getString(R.string.cleaAllWishList), CLEAR_ALL);
        break;
    }
  }

  /**
   * This method is using to show Sort mSortBottomSheet
   */
  private void showSortBottomSheet() {
    mSortBottomSheet.show(getSupportFragmentManager(), mSortBottomSheet.getTag());
  }

  @Override
  public void redirectToPdp(String parentProductId, String childProductId) {
    Intent intent =
        new Intent(this,
            ProductDetailsActivity.class);
    intent.putExtra(PARENT_PRODUCT_ID, parentProductId);
    intent.putExtra(PRODUCT_ID, childProductId);
    startActivity(intent);
  }

  @Override
  public void onOkClickListener(int type) {
    switch (type) {
      case CLEAR_ALL:
        mViewModel.clearAllWishListProducts();
        break;
      case CANCEL:
        mAdapter.updateList(mProductsData);
        break;
      case DELETE_ITEM:
        double[] latLng = LocationManagerUtil.getFuseLocation(this, null);
        mViewModel.deleteWishListProduct(EcomUtil.getIpAddress(this), mProductId,
            latLng[ZERO], latLng[ONE]);
        break;
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mViewModel.destroyListener();
  }
}