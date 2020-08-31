package com.customer.fivecanale.landing.historyscreen;

import static com.customer.fivecanale.util.EcomConstants.CHAT;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.customer.domain.model.orderhistory.OrderHistStoreOrderData;
import com.databinding.ItemHistoryOrderStoresBinding;
import java.util.ArrayList;

/**
 * adapter class for history stores
 */
public class HistoryStoreAdapter extends
    RecyclerView.Adapter<HistoryStoreAdapter.HistoryStoreViewHolder> {
  private Context mContext;
  private ArrayList<OrderHistStoreOrderData> mOrderHistStoreOrderData;
  private HistoryItemClick mHistoryItemClick;
  private int mMasterPos;

  /**
   * history store adapter
   *
   * @param orderHistStoreOrderData arrayList orderHistStoreOrderData
   * @param historyItemClick        interface on click listener
   */
  HistoryStoreAdapter(
      ArrayList<OrderHistStoreOrderData> orderHistStoreOrderData, int masterPos,
      HistoryItemClick historyItemClick) {
    mOrderHistStoreOrderData = orderHistStoreOrderData;
    mMasterPos = masterPos;
    mHistoryItemClick = historyItemClick;
  }

  @NonNull
  @Override
  public HistoryStoreAdapter.HistoryStoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    mContext = parent.getContext();
    ItemHistoryOrderStoresBinding historyOrderStoresBinding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_history_order_stores, parent, false);
    return new HistoryStoreViewHolder(historyOrderStoresBinding);
  }

  @Override
  public void onBindViewHolder(@NonNull HistoryStoreAdapter.HistoryStoreViewHolder holder,
      int position) {
    OrderHistStoreOrderData orderHistStoreOrderData = mOrderHistStoreOrderData.get(position);
    if (orderHistStoreOrderData != null) {
      holder.mStoresBinding.tvStoreName.setText(orderHistStoreOrderData.getStoreName());
      holder.mStoresBinding.tvStoreId.setText(
          String.format("%s%s", mContext.getResources().getString(R.string.historyStoreOrderId),
              orderHistStoreOrderData.getStoreOrderId()));
      HistoryStoreProductsAdapter historyStoreAdapter = new HistoryStoreProductsAdapter(
          orderHistStoreOrderData,
          orderHistStoreOrderData.getProducts(), mMasterPos, position, mHistoryItemClick);
      holder.mStoresBinding.rvOrderStoreItems.setAdapter(historyStoreAdapter);
      holder.mStoresBinding.cvMessage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          mHistoryItemClick.onClick(CHAT, mMasterPos, position, position, null);
        }
      });
    }
  }

  @Override
  public int getItemCount() {
    return mOrderHistStoreOrderData != null ? mOrderHistStoreOrderData.size() : ZERO;
  }

  /**
   * view holder class
   */
  class HistoryStoreViewHolder extends RecyclerView.ViewHolder {
    ItemHistoryOrderStoresBinding mStoresBinding;

    /**
     * view holder class constructor
     *
     * @param historyOrderStoresBinding binding reference.
     */
    HistoryStoreViewHolder(
        @NonNull ItemHistoryOrderStoresBinding historyOrderStoresBinding) {
      super(historyOrderStoresBinding.getRoot());
      mStoresBinding = historyOrderStoresBinding;
    }
  }
}
