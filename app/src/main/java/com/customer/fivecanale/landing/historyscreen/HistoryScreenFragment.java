package com.customer.fivecanale.landing.historyscreen;

import static com.customer.fivecanale.util.EcomConstants.BUFFERING_TIME;
import static com.customer.fivecanale.util.EcomConstants.CHAT;
import static com.customer.fivecanale.util.EcomConstants.CUSTOMER_NAME;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.FILTER_REQ_CODE;
import static com.customer.fivecanale.util.EcomConstants.HISTORY_PRODUCT_DETAIL;
import static com.customer.fivecanale.util.EcomConstants.HISTROY_ORDER_DETAIL;
import static com.customer.fivecanale.util.EcomConstants.HISTROY_PRODUCT_DETAIL;
import static com.customer.fivecanale.util.EcomConstants.LIMIT;
import static com.customer.fivecanale.util.EcomConstants.MINUS_ONE;
import static com.customer.fivecanale.util.EcomConstants.OPEN_CART;
import static com.customer.fivecanale.util.EcomConstants.ORDER_ID;
import static com.customer.fivecanale.util.EcomConstants.ORDER_NAME;
import static com.customer.fivecanale.util.EcomConstants.ORDER_TIME;
import static com.customer.fivecanale.util.EcomConstants.PARENT_PRODUCT_ID;
import static com.customer.fivecanale.util.EcomConstants.POSITION;
import static com.customer.fivecanale.util.EcomConstants.PRICE;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_COLOUR;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_ID;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_IMAGE;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_NAME;
import static com.customer.fivecanale.util.EcomConstants.RATING;
import static com.customer.fivecanale.util.EcomConstants.REVIEW_TYPE;
import static com.customer.fivecanale.util.EcomConstants.STATUS;
import static com.customer.fivecanale.util.EcomConstants.STORE_ID;
import static com.customer.fivecanale.util.EcomConstants.STORE_ORDER_ID;
import static com.customer.fivecanale.util.EcomConstants.THOUSAND;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.R;
import com.customer.domain.model.orderhistory.OrderHistoryItemData;
import com.customer.fivecanale.dagger.ActivityScoped;
import com.customer.fivecanale.historyfilter.HistoryOrderFilterActivity;
import com.customer.fivecanale.historyproductdetail.HistoryProductDetailActivity;
import com.customer.fivecanale.landing.HomeActivity;
import com.customer.fivecanale.mqtt_chat.ChattingActivity;
import com.customer.fivecanale.orderdetails.HistoryOrderDetailActivity;
import com.customer.fivecanale.review.ReviewProductActivity;
import com.customer.fivecanale.util.EcomUtil;
import com.customer.fivecanale.util.MyScrollListener;
import com.customer.fivecanale.util.RxTextView;
import com.databinding.FragmentHistoryScreenBinding;
import dagger.android.support.DaggerFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
@ActivityScoped
public class HistoryScreenFragment extends DaggerFragment implements HistoryItemClick {
  private static final int REVIEW_REQUEST_CODE = 10;
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  private HistoryViewModel mHistoryViewModel;
  private FragmentHistoryScreenBinding mBinding;
  private ArrayList<OrderHistoryItemData> mOrderHistoryItemData = new ArrayList<>();
  private HistoryAdapter mHistoryAdapter;
  private int mPenCount, status;
  private String searchList = "";
  private String mOrderTime = "", mName = "";
  private int mPosition = MINUS_ONE;
  private int mMasterPos, mStorePos, mProductPos;

  @Inject
  public HistoryScreenFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    mBinding = DataBindingUtil.inflate(inflater,
        R.layout.fragment_history_screen, container,
        false);
    initializeView();
    subscribeOrderHistoryData();
    subscribeOrderHistoryCount();
    subscribeFilterData();
    subscribeToCrossClick();
    return mBinding.getRoot();
  }

  /**
   * this method is used to initialize the viewmodel.
   */
  private void initializeView() {
    mHistoryViewModel = ViewModelProviders.of(this, mViewModelFactory).get(HistoryViewModel.class);
    mBinding.setViewModel(mHistoryViewModel);//used to connect mBinding to viewModel
    mHistoryViewModel.onHistoryUpdate();
    mHistoryAdapter = new HistoryAdapter(mOrderHistoryItemData, this);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    mBinding.rvHistory.setLayoutManager(linearLayoutManager);
    mBinding.rvHistory.setAdapter(mHistoryAdapter);
    mHistoryViewModel.callGetHistoryApi(ZERO, LIMIT, status, "", "");
    mBinding.rvHistory.addOnScrollListener(new MyScrollListener(linearLayoutManager) {
      @Override
      protected void loadMoreItems() {
        EcomUtil.printLog("exe" + "size" + mOrderHistoryItemData.size() + "mPenCount" + mPenCount);
        mHistoryViewModel.itemLoadingVisible.set(mOrderHistoryItemData.size() < mPenCount);
        if (mOrderHistoryItemData.size() < mPenCount) {
          EcomUtil.printLog("exe" + "sizeWhileCalling" + mOrderHistoryItemData.size() + "LIMIT " + (
              mOrderHistoryItemData.size() + LIMIT) + "mPenCount" + mPenCount);
          mHistoryViewModel.callGetHistoryApi(
              mOrderHistoryItemData.size(),
              mOrderHistoryItemData.size() + LIMIT, status, searchList, mOrderTime);
        }
      }

      @Override
      public boolean isLastPage() {
        return mHistoryViewModel.isLoading;
      }

      @Override
      public boolean isLoading() {
        return mHistoryViewModel.isLoading;
      }
    });
    mBinding.refreshSr.setOnRefreshListener(() -> {
      if (mBinding.refreshSr.isRefreshing()) {
        mBinding.refreshSr.setRefreshing(FALSE);
      }
      mHistoryViewModel.callGetHistoryApi(ZERO, LIMIT, status, searchList, "");
    });
    RxTextView.textChanges(mBinding.etHomeSearch)
        .debounce(BUFFERING_TIME, TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new io.reactivex.Observer<CharSequence>() {
          @Override
          public void onSubscribe(Disposable d) {
          }

          @Override
          public void onNext(CharSequence charSequence) {
            searchList = charSequence.toString().toLowerCase();
            mBinding.ivCross.setVisibility(searchList.length() == ZERO ? View.GONE : View.VISIBLE);
            if (searchList.length() == ZERO) {
              mOrderHistoryItemData.clear();
              mOrderHistoryItemData.addAll(mHistoryViewModel.mOrderHistoryItemData);
              mHistoryAdapter.notifyDataSetChanged();
            } else {
              mHistoryViewModel.callGetHistoryApi(ZERO, LIMIT, status, searchList, "");
            }
          }

          @Override
          public void onError(Throwable e) {
          }

          @Override
          public void onComplete() {
          }
        });
  }

  /**
   * subscribe for history data
   */
  private void subscribeOrderHistoryData() {
    mHistoryViewModel.getOrderHistoryLiveData().observe(getActivity(),
        orderHistoryItemData -> {
          if (orderHistoryItemData != null) {
            mOrderHistoryItemData.clear();
            mOrderHistoryItemData.addAll(orderHistoryItemData);
            mBinding.vgNoHistoryFound.setVisibility(
                mOrderHistoryItemData.size() > ZERO ? View.GONE : View.VISIBLE);
            mHistoryAdapter.notifyDataSetChanged();
          }
        });
  }

  /**
   * subscribe for pen count
   */
  private void subscribeOrderHistoryCount() {
    mHistoryViewModel.getOrderHistoryCount().observe(getActivity(), count -> {
      mPenCount = count;
    });
  }

  /**
   * subscribe for pen count
   */
  private void subscribeFilterData() {
    mHistoryViewModel.applyFilter().observe(getActivity(), aBoolean -> {
      Intent intent = new Intent(getActivity(), HistoryOrderFilterActivity.class);
      intent.putExtra(ORDER_NAME, mName);
      intent.putExtra(POSITION, mPosition);
      intent.putExtra(STATUS, status);
      startActivityForResult(intent, FILTER_REQ_CODE);
    });
  }

  /**
   * subscribe for cross click
   */
  private void subscribeToCrossClick() {
    mHistoryViewModel.searchData().observe(getActivity(), s -> {
      mBinding.etHomeSearch.setText(s);
      mOrderHistoryItemData.clear();
      mOrderHistoryItemData.addAll(mHistoryViewModel.mOrderHistoryItemData);
      mBinding.vgNoHistoryFound.setVisibility(
          mOrderHistoryItemData.size() > ZERO ? View.GONE : View.VISIBLE);
      mHistoryAdapter.notifyDataSetChanged();
    });
  }

  @Override
  public void onClick(int action, int masterPos, int storePos, int productPos, Bundle bundle) {
    mMasterPos = masterPos;
    mStorePos = storePos;
    mProductPos = productPos;
    switch (action) {
      case REVIEW_TYPE:
        Intent intent = new Intent(getActivity(), ReviewProductActivity.class);
        intent.putExtra(PRODUCT_IMAGE, bundle.getString(PRODUCT_IMAGE));
        intent.putExtra(PRODUCT_COLOUR, bundle.getString(PRODUCT_COLOUR));
        intent.putExtra(PRODUCT_NAME, bundle.getString(PRODUCT_NAME));
        intent.putExtra(PRICE, bundle.getString(PRICE));
        intent.putExtra(PARENT_PRODUCT_ID, bundle.getString(PARENT_PRODUCT_ID));
        intent.putExtra(PRODUCT_ID, bundle.getString(PRODUCT_ID));
        startActivityForResult(intent, REVIEW_REQUEST_CODE);
        break;
      case HISTROY_PRODUCT_DETAIL:
        Intent historyDetailIntent = new Intent(getActivity(), HistoryProductDetailActivity.class);
        historyDetailIntent.putExtras(bundle);
        startActivityForResult(historyDetailIntent, HISTORY_PRODUCT_DETAIL);
        break;
      case HISTROY_ORDER_DETAIL:
        Intent historyOrderDetailIntent = new Intent(getActivity(),
            HistoryOrderDetailActivity.class);
        bundle.putString(ORDER_TIME, EcomUtil.getDate(
            Long.parseLong(mOrderHistoryItemData.get(mMasterPos).getCreatedTimeStamp())));
        historyOrderDetailIntent.putExtras(bundle);
        startActivity(historyOrderDetailIntent);
        break;
      case CHAT:
        Intent chatIntent = new Intent(getActivity(),
            ChattingActivity.class);
        chatIntent.putExtra(STORE_ID, mOrderHistoryItemData.get(mMasterPos).getStoreOrders().get(
            mStorePos).getStoreId());
        chatIntent.putExtra(STORE_ORDER_ID, mOrderHistoryItemData.get(
            mMasterPos).getStoreOrders().get(
            mStorePos).getStoreOrderId());
        chatIntent.putExtra(CUSTOMER_NAME, mOrderHistoryItemData.get(
            mMasterPos).getStoreOrders().get(
            mStorePos).getStoreName());
        chatIntent.putExtra(ORDER_ID, mOrderHistoryItemData.get(mMasterPos).getStoreOrders().get(
            mStorePos).getStoreOrderId());
        startActivity(chatIntent);
        break;
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == Activity.RESULT_OK) {
      switch (requestCode) {
        case HISTORY_PRODUCT_DETAIL:
          if (data != null) {
            boolean openCart = data.getBooleanExtra(OPEN_CART, FALSE);
            if (openCart) {
              if (getActivity() instanceof HomeActivity) {
                ((HomeActivity) getActivity()).showCartFragment();
              }
            }
          }
          break;
        case REVIEW_REQUEST_CODE:
          if (data != null) {
            double rating = data.getDoubleExtra(RATING, ZERO);
            EcomUtil.printLog("exe" + "rating" + rating);
            if (rating > ZERO) {
              mOrderHistoryItemData.get(mMasterPos).getStoreOrders().get(
                  mStorePos).getProducts().get(
                  mProductPos).getRattingData().setRating((float) rating);
              mHistoryAdapter.notifyItemChanged(mMasterPos);
            }
          }
          break;
        case FILTER_REQ_CODE:
          if (data != null) {
            mOrderTime = (String) data.getStringExtra(ORDER_TIME);
            mName = (String) data.getStringExtra(ORDER_NAME);
            status = data.getIntExtra(STATUS, ZERO);
            mPosition = data.getIntExtra(POSITION, MINUS_ONE);
            if (status == ZERO && mOrderTime.length() > ZERO
                || status != ZERO && mOrderTime.length() == ZERO || mOrderTime.length() > ZERO) {
              Long tsLong = System.currentTimeMillis() / THOUSAND;
              String ts = tsLong.toString();
              mHistoryViewModel.callGetHistoryApi(ZERO, LIMIT, status, searchList,
                  mOrderTime + "-" + ts);
            } else {
              mOrderHistoryItemData.clear();
              mOrderHistoryItemData.addAll(mHistoryViewModel.mOrderHistoryItemData);
              mBinding.vgNoHistoryFound.setVisibility(
                  mOrderHistoryItemData.size() > ZERO ? View.GONE : View.VISIBLE);
              mHistoryAdapter.notifyDataSetChanged();
            }
          }
          break;
      }
    }
  }
}
