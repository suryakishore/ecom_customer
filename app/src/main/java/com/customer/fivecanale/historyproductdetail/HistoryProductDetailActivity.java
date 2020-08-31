package com.customer.fivecanale.historyproductdetail;

import static com.customer.fivecanale.util.EcomConstants.BOX_COUNT;
import static com.customer.fivecanale.util.EcomConstants.CANCEL_ORDER_REQUEST;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.IS_SPIT_PRODUCT;
import static com.customer.fivecanale.util.EcomConstants.IS_TO_FINISH;
import static com.customer.fivecanale.util.EcomConstants.OFFER_PRICE;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.OPEN_CART;
import static com.customer.fivecanale.util.EcomConstants.ORDER_ID;
import static com.customer.fivecanale.util.EcomConstants.PACKAGE_ID;
import static com.customer.fivecanale.util.EcomConstants.PRICE;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_COLOUR;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_ID;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_IMAGE;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_NAME;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_PRICE;
import static com.customer.fivecanale.util.EcomConstants.RATING;
import static com.customer.fivecanale.util.EcomConstants.REASON;
import static com.customer.fivecanale.util.EcomConstants.REVIEW_REQUEST_CODE;
import static com.customer.fivecanale.util.EcomConstants.SEVEN;
import static com.customer.fivecanale.util.EcomConstants.THREE;
import static com.customer.fivecanale.util.EcomConstants.TITLE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.TWO;
import static com.customer.fivecanale.util.EcomConstants.URL;
import static com.customer.fivecanale.util.EcomConstants.ZERO;
import static com.customer.remote.http.RemoteConstants.ORDER_STATUS;
import static com.customer.remote.http.RemoteConstants.PACK_ID;
import static com.customer.remote.http.RemoteConstants.PARENT_PRODUCT_ID;
import static com.customer.remote.http.RemoteConstants.PRODUCT_ORDER_ID;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.bumptech.glide.Glide;
import com.customer.domain.model.getcart.CartTaxData;
import com.customer.domain.model.orderdetails.MasterOrderData;
import com.customer.domain.model.orderdetails.OrderData;
import com.customer.domain.model.orderhistory.OrderHistProductData;
import com.customer.domain.model.tracking.TrackingItemData;
import com.customer.fivecanale.ApplicationManager;
import com.customer.fivecanale.cancelorder.CancelOrderOrProductActivity;
import com.customer.fivecanale.pdp.ProductDetailsActivity;
import com.customer.fivecanale.review.ReviewProductActivity;
import com.customer.fivecanale.tracking.EcomTrackingActivity;
import com.customer.fivecanale.util.EcomUtil;
import com.customer.fivecanale.util.OrderDetailClick;
import com.customer.fivecanale.webview.WebViewAct;
import com.databinding.ActivityHistoryProductDetailBinding;
import dagger.android.support.DaggerAppCompatActivity;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * This activity represents the product detail for history items.
 */
public class HistoryProductDetailActivity extends DaggerAppCompatActivity implements
    OrderDetailClick {
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  private HistoryProductDetailAdapter mProductDetailAdapter;
  private ActivityHistoryProductDetailBinding mProductDetailBinding;
  private HistoryProductDetailViewModel mHistoryProductDetailViewModel;
  private OrderHistProductData mOrderProductData;
  private MasterOrderData mMasterOrderData;
  private ArrayList<TrackingItemData> mTrackingItemData = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeView();
    initializeViewModel();
    subscribeOnClick();
    subscribePackageDetails();
  }

  /**
   * Initialising the View using Data Binding.
   */
  private void initializeView() {
    mProductDetailBinding = DataBindingUtil.setContentView(this,
        R.layout.activity_history_product_detail);
  }

  /**
   * <p>This method is used initialize the viewModel class for this activity.</p>
   */
  private void initializeViewModel() {
    mHistoryProductDetailViewModel = ViewModelProviders.of(this, mViewModelFactory).get(
        HistoryProductDetailViewModel.class);
    mProductDetailBinding.setViewmodel(mHistoryProductDetailViewModel);
    Bundle extras = getIntent().getExtras();
    if (extras != null) {
      boolean splitOrder = extras.getBoolean(IS_SPIT_PRODUCT, FALSE);
      mProductDetailBinding.clBox.setVisibility(splitOrder ? View.VISIBLE : View.GONE);
      mProductDetailBinding.tvBoxCount.setText(extras.getString(BOX_COUNT));
      if (extras.getString(PACK_ID) != null&&!extras.getString(PACK_ID).isEmpty()) {
        mProductDetailBinding.tvPackageId.setText(
            String.format("%s: %s", getResources().getString(R.string.packageId),
                extras.getString(PACK_ID)));
      } else {
        mProductDetailBinding.tvPackageId.setVisibility(View.GONE);
      }
      String productId = extras.getString(PRODUCT_ID);
      EcomUtil.printLog(
          "exe" + "productId" + productId + "packId" + extras.getString(PACK_ID) + "orderId"
              + extras.getString(PRODUCT_ORDER_ID) + "isSplitProduct" + extras.getBoolean(
              IS_SPIT_PRODUCT, FALSE));
      mHistoryProductDetailViewModel.productOrderId = extras.getString(PRODUCT_ORDER_ID);
      mProductDetailBinding.incHeader.tvCenterCategoryName.setText(
          String.format("%s%s", getResources().getString(R.string.allId),
              extras.getString(PRODUCT_ORDER_ID)));
      if (extras.getString(PRODUCT_ORDER_ID) != null && extras.getString(PACK_ID) != null
          && extras.getString(PACK_ID).length() > ZERO) {
        mProductDetailBinding.tvShippingWith.setText(
            String.format("%s (%s)", getResources().getString(R.string.historyTrackShipment),
                extras.getString(PACK_ID)));
        mHistoryProductDetailViewModel.getPackageDetails(extras.getString(PRODUCT_ORDER_ID),
            extras.getString(PACK_ID));
      } else {
        mHistoryProductDetailViewModel.trackingId.set(
            ApplicationManager.getInstance().getString(R.string.historyTrackShipment));
        mHistoryProductDetailViewModel.callGetHistoryApi(extras.getString(PRODUCT_ORDER_ID));
      }
    }
  }

  /**
   * subscribe for the back button on click
   */
  private void subscribeOnClick() {
    mHistoryProductDetailViewModel.onClick().observe(this,
        historyProductDetailUiAction -> {
          switch (historyProductDetailUiAction) {
            case BACK:
              finish();
              break;
            case TRACKING:
              Intent trackingIntent = new Intent(this, EcomTrackingActivity.class);
              Bundle bundle = new Bundle();
              bundle.putString(PACKAGE_ID, mOrderProductData.getPackageId());
              bundle.putString(PRODUCT_ORDER_ID, mOrderProductData.getProductOrderId());
              bundle.putString(BOX_COUNT, getIntent().getStringExtra(BOX_COUNT));
              bundle.putBoolean(IS_SPIT_PRODUCT,
                  getIntent().getBooleanExtra(IS_SPIT_PRODUCT, FALSE));
              bundle.putString(REASON, mOrderProductData.getReason());
              bundle.putParcelableArrayList(ORDER_STATUS, mTrackingItemData);
              trackingIntent.putExtras(bundle);
              startActivity(trackingIntent);
              break;
            case RATE_NOW:
              Intent intent = new Intent(this, ReviewProductActivity.class);
              intent.putExtra(PRODUCT_IMAGE, mOrderProductData.getImages().getMedium());
              intent.putExtra(PRODUCT_COLOUR, "");
              intent.putExtra(PRODUCT_NAME, mOrderProductData.getName());
              intent.putExtra(PRICE, String.format("%s%s", mOrderProductData.getCurrencySymbol(),
                  mOrderProductData.getSingleUnitPrice().getFinalUnitPrice()));
              intent.putExtra(PARENT_PRODUCT_ID, mOrderProductData.getCentralProductId());
              intent.putExtra(PRODUCT_ID, mOrderProductData.getProductId());
              startActivityForResult(intent, REVIEW_REQUEST_CODE);
              break;
            case CANCEL_ORDER:
              Intent cancelOrderIntent = new Intent(this, CancelOrderOrProductActivity.class);
              cancelOrderIntent.putExtra(ORDER_ID, mOrderProductData.getProductOrderId());
              cancelOrderIntent.putExtra(PRODUCT_NAME, mOrderProductData.getName());
              cancelOrderIntent.putExtra(PRODUCT_IMAGE, mOrderProductData.getImages().getMedium());
              cancelOrderIntent.putExtra(PRODUCT_PRICE,
                  mOrderProductData.getAccounting().getFinalTotal());
              cancelOrderIntent.putExtra(OFFER_PRICE,
                  String.format("%s%s", mOrderProductData.getCurrencySymbol(),
                      mOrderProductData.getAccounting().getUnitPrice()));
              startActivityForResult(cancelOrderIntent, CANCEL_ORDER_REQUEST);
              break;
            case REORDER:
              Intent reorderData = new Intent();
              reorderData.putExtra(OPEN_CART, TRUE);
              setResult(Activity.RESULT_OK, reorderData);
              finish();
              break;
            case DOWNLOAD_INVOICE:
              EcomUtil.printLog("exe" + "invoiceLink" + mOrderProductData.getInvoiceLink());
              String invoiceLink = "";
              Intent downLoadInvoice = new Intent(this, WebViewAct.class);
              if (mOrderProductData.getInvoiceLink().contains("https")) {
                invoiceLink = mOrderProductData.getInvoiceLink().replace("https", "http");
              }
              downLoadInvoice.putExtra(URL, invoiceLink.trim());
              downLoadInvoice.putExtra(TITLE, getResources().getString(R.string.invoice));
              startActivity(downLoadInvoice);
              break;
          }
        });
  }

  /**
   * subscribe for the back button on click
   */
  private void subscribePackageDetails() {
    mHistoryProductDetailViewModel.getData().observe(this, booleanObjectPair -> {
      if (booleanObjectPair.first) {
        OrderData orderData = (OrderData) booleanObjectPair.second;
        if (orderData != null) {
          if (orderData.getProducts() != null && orderData.getProducts().size() > ZERO) {
            EcomUtil.printLog(
                "exe" + "status" + Integer.parseInt(orderData.getStatus().getStatus()));
            mProductDetailBinding.tvOrderStatus.setText(orderData.getProducts().get(
                ZERO).getStatus().getStatusText());
            if (Integer.parseInt(orderData.getProducts().get(ZERO).getStatus().getStatus())
                == TWO) {
              mProductDetailBinding.tvOrderStatus.setTextColor(
                  getResources().getColor(R.color.hippie_green));
            } else if (Integer.parseInt(orderData.getProducts().get(ZERO).getStatus().getStatus())
                == THREE) {
              mProductDetailBinding.tvOrderStatus.setTextColor(
                  getResources().getColor(R.color.colorRed));
            } else {
              mProductDetailBinding.tvOrderStatus.setTextColor(
                  getResources().getColor(R.color.colorOrangePeel));
            }
            if (orderData.getProducts().get(ZERO).getStatus().getUpdatedOnTimeStamp() != null) {
              mProductDetailBinding.tvOrderTime.setText(
                  EcomUtil.getDateForHistoryDet(
                      Long.parseLong(orderData.getProducts().get(
                          ZERO).getStatus().getUpdatedOnTimeStamp())));
            }
          }
          mOrderProductData = orderData.getProducts().get(ZERO);
          setData(orderData.getStoreName(), orderData.getAlongWith(), FALSE);
          showRatingData();
        }
      } else {
        mMasterOrderData = (MasterOrderData) booleanObjectPair.second;
        if (mMasterOrderData.getProducts() != null
            && mMasterOrderData.getProducts().size() > ZERO) {
          mProductDetailBinding.tvOrderStatus.setText(mMasterOrderData.getProducts().get(
              ZERO).getStatus().getStatusText());
          if (Integer.parseInt(mMasterOrderData.getProducts().get(ZERO).getStatus().getStatus())
              == TWO) {
            mProductDetailBinding.tvOrderStatus.setTextColor(
                getResources().getColor(R.color.hippie_green));
          } else if (Integer.parseInt(mMasterOrderData.getProducts().get(
              ZERO).getStatus().getStatus()) == THREE) {
            mProductDetailBinding.tvOrderStatus.setTextColor(
                getResources().getColor(R.color.colorRed));
          } else {
            mProductDetailBinding.tvOrderStatus.setTextColor(
                getResources().getColor(R.color.colorOrangePeel));
          }
          if (mMasterOrderData.getProducts().get(ZERO).getStatus().getUpdatedOnTimeStamp()
              != null) {
            mProductDetailBinding.tvOrderTime.setText(
                EcomUtil.getDateForHistoryDet(
                    Long.parseLong(mMasterOrderData.getProducts().get(
                        ZERO).getStatus().getUpdatedOnTimeStamp())));
          }
          mTrackingItemData.clear();
          mTrackingItemData.addAll(mMasterOrderData.getOrderStatus());
          mOrderProductData = mMasterOrderData.getProducts().get(ZERO);
          setData(mMasterOrderData.getStoreName(), null, TRUE);
          showRatingData();
        }
      }
    });
  }

  /**
   * this method is used to set the product image
   *
   * @param url image url
   */
  private void setProductImage(String url) {
    String imageUrl = url;
    if (!TextUtils.isEmpty(imageUrl)) {
      Glide.with(this)
          .load(imageUrl.replace(" ", "%20"))
          .into(mProductDetailBinding.historyProductDetail.ivProductImg);
    }
  }

  /**
   * used to set the product data.
   *
   * @param storeName            store name
   * @param orderHistProductData arrayList of order historyProduct data.
   * @param materOrder           master order or normal order.
   */
  private void setData(String storeName, ArrayList<OrderHistProductData> orderHistProductData,
      boolean materOrder) {
    mProductDetailBinding.historyProductDetail.tvProductName.setText(mOrderProductData.getName());
    setProductImage(mOrderProductData.getImages().getMedium());
    StringBuilder attributeName = new StringBuilder();
    for (int i = ZERO; i < mOrderProductData.getAttributes().size(); i++) {
      if (mOrderProductData.getAttributes().get(i).getValue() != null
          && !mOrderProductData.getAttributes().get(
          i).getValue().isEmpty()) {
        attributeName.append(mOrderProductData.getAttributes().get(i).getAttrname()).append(
            ":").append(mOrderProductData.getAttributes().get(i).getValue()).append(" ");
      }
      if (i == ONE) {
        break;
      }
    }
    if (attributeName.length() > ZERO) {
      mProductDetailBinding.historyProductDetail.tvHistorySizeAndColor.setText(
          attributeName.toString());
    } else {
      mProductDetailBinding.historyProductDetail.tvHistorySizeAndColor.setVisibility(View.GONE);
    }
    mProductDetailBinding.historyProductDetail.tvProductCasePrice.setText(
        String.format("%s %s *%s%s", mOrderProductData.getQuantity().getValue(),
            mOrderProductData.getQuantity().getUnit(),
            mOrderProductData.getCurrencySymbol(),
            mOrderProductData.getSingleUnitPrice().getFinalUnitPrice()));
    mProductDetailBinding.historyProductDetail.tvSellerName.setText(storeName);
    if (!materOrder && orderHistProductData.size() > ZERO) {
      mProductDetailAdapter = new HistoryProductDetailAdapter(orderHistProductData,
          storeName, this::orderItemClick);
      mProductDetailBinding.rvOrders.setAdapter(mProductDetailAdapter);
    } else {
      mProductDetailBinding.tvShippingAlongWith.setVisibility(View.GONE);
    }
    mProductDetailBinding.historyProductDetail.clProductItem.setOnClickListener(
        view -> orderItemClick(mOrderProductData.getProductId(),
            mOrderProductData.getCentralProductId()));
    if (mOrderProductData.getOfferDetails() != null) {
      if (mOrderProductData.getOfferDetails().getOfferId() != null
          && !mOrderProductData.getOfferDetails().getOfferId().isEmpty()) {
        mProductDetailBinding.historyProductDetail.tvProductBasePrice.setText(
            String.format("%s %s", mOrderProductData.getCurrencySymbol(),
                mOrderProductData.getSingleUnitPrice().getUnitPrice()));
        EcomUtil.strikeThroughText(mProductDetailBinding.historyProductDetail.tvProductBasePrice);
      } else {
        mProductDetailBinding.historyProductDetail.tvProductBasePrice.setVisibility(View.GONE);
      }
    }
    setAccountDetails();
  }

  /**
   * This method is used to show the rating data
   */
  private void showRatingData() {
    EcomUtil.printLog("exe" + "mOrderProductData" + mOrderProductData.getStatus().getStatus());
    if (mOrderProductData.getStatus().getStatus() != null && Integer.parseInt(
        mOrderProductData.getStatus().getStatus()) == SEVEN) {
      if (mOrderProductData.getRattingData().getRating() > ZERO) {
        mProductDetailBinding.tvProductReview.setVisibility(View.VISIBLE);
        mProductDetailBinding.rbProductRatings.setRating(
            mOrderProductData.getRattingData().getRating());
        mProductDetailBinding.tvProductReview.setText(
            getResources().getString(R.string.historyEditReview));
      }
    }
  }

  /**
   * set the tax data
   *
   * @param taxData cart data object from server.
   */
  private void setTaxData(ArrayList<CartTaxData> taxData) {
    LinearLayout linearLayout = mProductDetailBinding.accoutingDetails.cartAddTaxLl;
    linearLayout.removeAllViews();
    LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(
        Context.LAYOUT_INFLATER_SERVICE);
    View viewInflater;
    if (taxData != null && taxData.size() > ZERO) {
      for (int i = ZERO; i < taxData.size(); i++) {
        viewInflater = layoutInflater.inflate(R.layout.tax_row, null);
        TextView taxTxtTv = viewInflater.findViewById(R.id.taxTxtTv);
        TextView taxValueTv = viewInflater.findViewById(R.id.taxValueTv);
        taxTxtTv.setText(taxData.get(i).getTaxName());
        taxValueTv.setText(
            String.format("%s%s", mOrderProductData.getCurrencySymbol(),
                taxData.get(i).getTotalValue()));
        linearLayout.addView(viewInflater);
      }
    }
  }

  /**
   * set the accounting details for the product.
   */
  private void setAccountDetails() {
    if (mOrderProductData.getAccounting() != null) {
      mProductDetailBinding.accoutingDetails.tvCartSubTotalAmt.setText(
          String.format("%s%s", mOrderProductData.getAccounting().getCurrencySymbol(),
              mOrderProductData.getAccounting().getFinalUnitPrice()));
      if (mOrderProductData.getAccounting().getDeliveryFee() != null) {
        mProductDetailBinding.accoutingDetails.tvCartShippingAmt.setText(
            (mOrderProductData.getAccounting().getDeliveryFee().equals(String.valueOf(ZERO)))
                ? getResources().getString(R.string.free) :
                String.format("%s%s", mOrderProductData.getAccounting().getCurrencySymbol(),
                    mOrderProductData.getAccounting().getDeliveryFee()));
      }
      if (mOrderProductData.getAccounting().getFinalTotal() != null
          && !mOrderProductData.getAccounting().getFinalTotal().isEmpty()) {
        mProductDetailBinding.accoutingDetails.tvCartOrderTotalAmt.setText(
            String.format("%s%s", mOrderProductData.getAccounting().getCurrencySymbol(),
                String.format("%.2f",
                    Float.parseFloat(mOrderProductData.getAccounting().getFinalTotal()))));
      }
      if (mOrderProductData.getAccounting().getTax().size() > ZERO) {
        setTaxData(mOrderProductData.getAccounting().getTax());
      } else {
        mProductDetailBinding.accoutingDetails.tvTaxes.setVisibility(View.GONE);
      }
      if (mOrderProductData.getAccounting().getFinalTotal() != null
          && !mOrderProductData.getAccounting().getFinalTotal().isEmpty()) {
        mProductDetailBinding.accoutingDetails.tvCartTotalSavingsAmt.setText(
            String.format("%s%s", mOrderProductData.getAccounting().getCurrencySymbol(),
                String.format("%.2f",
                    Float.parseFloat(mOrderProductData.getAccounting().getFinalTotal()))));
      }
      mProductDetailBinding.accoutingDetails.tvCartTotalSavingsAmt.setText(
          String.format("%s%s", mOrderProductData.getAccounting().getCurrencySymbol(),
              mOrderProductData.getAccounting().getOfferDiscount()));
      mProductDetailBinding.accoutingDetails.tvCashOnDelivery.setText(
          getResources().getString(R.string.confirmOrderCashOnDel));
    }
  }

  @Override
  public void orderItemClick(String productId, String parentProductId) {
    Intent intent = new Intent(this, ProductDetailsActivity.class);
    intent.putExtra(PARENT_PRODUCT_ID, parentProductId);
    intent.putExtra(PRODUCT_ID, productId);
    startActivity(intent);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      if (requestCode == CANCEL_ORDER_REQUEST) {
        if (data != null) {
          boolean isFinish = data.getBooleanExtra(IS_TO_FINISH, FALSE);
          if (isFinish) {
            finish();
          }
        }
      } else if (requestCode == REVIEW_REQUEST_CODE) {
        if (data != null) {
          double rating = data.getDoubleExtra(RATING, ZERO);
          if (rating > ZERO) {
            mProductDetailBinding.rbProductRatings.setRating((float) rating);
          }
        }
      }
    }
  }
}
