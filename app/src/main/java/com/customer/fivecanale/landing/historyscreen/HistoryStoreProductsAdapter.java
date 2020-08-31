package com.customer.fivecanale.landing.historyscreen;

import static com.customer.fivecanale.util.EcomConstants.HISTROY_ORDER_DETAIL;
import static com.customer.fivecanale.util.EcomConstants.HISTROY_PRODUCT_DETAIL;
import static com.customer.fivecanale.util.EcomConstants.IS_SPIT_PRODUCT;
import static com.customer.fivecanale.util.EcomConstants.ORDER_COMPLETED;
import static com.customer.fivecanale.util.EcomConstants.ORDER_ID;
import static com.customer.fivecanale.util.EcomConstants.PARENT_PRODUCT_ID;
import static com.customer.fivecanale.util.EcomConstants.PRICE;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_COLOUR;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_ID;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_IMAGE;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_NAME;
import static com.customer.fivecanale.util.EcomConstants.REVIEW_TYPE;
import static com.customer.fivecanale.util.EcomConstants.STORE_NAME;
import static com.customer.fivecanale.util.EcomConstants.THREE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.TWO;
import static com.customer.fivecanale.util.EcomConstants.ZERO;
import static com.customer.remote.http.RemoteConstants.PACK_ID;
import static com.customer.remote.http.RemoteConstants.PRODUCT_ORDER_ID;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.bumptech.glide.Glide;
import com.customer.domain.model.orderhistory.OrderHistProductData;
import com.customer.domain.model.orderhistory.OrderHistStoreOrderData;
import com.databinding.ItemHistoryOrderItemBinding;
import java.util.ArrayList;

/**
 * adapter class for history store products
 */
public class HistoryStoreProductsAdapter extends
    RecyclerView.Adapter<HistoryStoreProductsAdapter.HistoryStoreProductsViewHolder> {
  private Context mContext;
  private ArrayList<OrderHistProductData> mOrderHistProductData;
  private OrderHistStoreOrderData mOrderHistStoreOrderData;
  private HistoryItemClick mHistoryItemClick;
  private int mMasterPos, mStorePos;

  /**
   * constructor for history store products adapter
   *
   * @param orderHistProductData arrayList orderHistProductData
   * @param historyItemClick     interface on click listener
   */
  HistoryStoreProductsAdapter(
      OrderHistStoreOrderData orderHistStoreOrderData,
      ArrayList<OrderHistProductData> orderHistProductData, int masterPos, int storePos,
      HistoryItemClick historyItemClick) {
    mOrderHistProductData = orderHistProductData;
    mOrderHistStoreOrderData = orderHistStoreOrderData;
    mMasterPos = masterPos;
    mStorePos = storePos;
    mHistoryItemClick = historyItemClick;
  }

  @NonNull
  @Override
  public HistoryStoreProductsAdapter.HistoryStoreProductsViewHolder onCreateViewHolder(
      @NonNull ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    ItemHistoryOrderItemBinding historyOrderItemBinding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_history_order_item, parent, false);
    return new HistoryStoreProductsViewHolder(historyOrderItemBinding);
  }

  @Override
  public void onBindViewHolder(
      @NonNull HistoryStoreProductsAdapter.HistoryStoreProductsViewHolder holder, int position) {
    OrderHistProductData data = mOrderHistProductData.get(position);
    String imageUrl = data.getImages().getMedium();
    if (!TextUtils.isEmpty(imageUrl)) {
      Glide.with(mContext)
          .load(imageUrl.replace(" ", "%20"))
          .into(holder.mItemHistoryOrderItemBinding.ivProductImg);
    }
    holder.mItemHistoryOrderItemBinding.tvProductName.setText(data.getName());
    if (data.getStatus().getStatus() != null && Integer.parseInt(data.getStatus().getStatus())
        == ORDER_COMPLETED) {
      holder.mItemHistoryOrderItemBinding.vgHistoryProducts.setVisibility(View.VISIBLE);
      if (data.getRattingData().getRating() > ZERO) {
        holder.mItemHistoryOrderItemBinding.tvProductReview.setVisibility(View.VISIBLE);
        holder.mItemHistoryOrderItemBinding.rbProductRatings.setRating(
            data.getRattingData().getRating());
        holder.mItemHistoryOrderItemBinding.tvProductReview.setText(
            mContext.getResources().getString(R.string.historyEditReview));
      }
    }
    holder.mItemHistoryOrderItemBinding.tvProductStatus.setText(data.getStatus().getStatusText());
    if (data.getStatus().getStatus() != null && Integer.parseInt(data.getStatus().getStatus())
        == TWO) {
      holder.mItemHistoryOrderItemBinding.tvProductStatus.setTextColor(
          mContext.getResources().getColor(R.color.hippie_green));
    } else if (Integer.parseInt(data.getStatus().getStatus()) == THREE) {
      holder.mItemHistoryOrderItemBinding.tvProductStatus.setTextColor(
          mContext.getResources().getColor(R.color.colorRed));
    } else {
      holder.mItemHistoryOrderItemBinding.tvProductStatus.setTextColor(
          mContext.getResources().getColor(R.color.colorOrangePeel));
    }
    holder.mItemHistoryOrderItemBinding.clProductItem.setOnClickListener(
        view -> {
          if (data.isSplitProduct()) {
            Bundle bundle = new Bundle();
            bundle.putString(ORDER_ID, data.getProductOrderId());
            bundle.putBoolean(IS_SPIT_PRODUCT, TRUE);
            bundle.putString(STORE_NAME, mOrderHistStoreOrderData.getStoreName());
            mHistoryItemClick.onClick(HISTROY_ORDER_DETAIL, mMasterPos, mStorePos, position,
                bundle);
          } else {
            Bundle bundle = new Bundle();
            bundle.putString(PRODUCT_ID, data.getProductId());
            bundle.putString(PRODUCT_ORDER_ID, data.getProductOrderId());
            bundle.putString(PACK_ID, data.getPackageId());
            mHistoryItemClick.onClick(HISTROY_PRODUCT_DETAIL, mMasterPos, mStorePos, position,
                bundle);
          }
        });
    holder.mItemHistoryOrderItemBinding.tvProductReview.setOnClickListener(
        view -> {
          Bundle bundle = new Bundle();
          bundle.putString(PRODUCT_IMAGE, imageUrl);
          bundle.putString(PRODUCT_COLOUR, data.getColor());
          bundle.putString(PRODUCT_NAME, data.getName());
          bundle.putString(PRICE, String.format("%s%s", data.getCurrencySymbol(),
              data.getSingleUnitPrice().getFinalUnitPrice()));
          bundle.putString(PARENT_PRODUCT_ID, data.getCentralProductId());
          bundle.putString(PRODUCT_ID, data.getProductId());
          mHistoryItemClick.onClick(REVIEW_TYPE, mMasterPos, mStorePos, position, bundle);
        });
  }

  @Override
  public int getItemCount() {
    return mOrderHistProductData != null ? mOrderHistProductData.size() : ZERO;
  }

  /**
   * view holder class
   */
  class HistoryStoreProductsViewHolder extends RecyclerView.ViewHolder {
    ItemHistoryOrderItemBinding mItemHistoryOrderItemBinding;

    /**
     * view holder class constructor
     *
     * @param historyOrderItemBinding binding reference.
     */
    HistoryStoreProductsViewHolder(
        @NonNull ItemHistoryOrderItemBinding historyOrderItemBinding) {
      super(historyOrderItemBinding.getRoot());
      mItemHistoryOrderItemBinding = historyOrderItemBinding;
    }
  }
}
