package com.customer.fivecanale.landing.historyscreen;

import static com.customer.fivecanale.util.EcomConstants.HISTROY_ORDER_DETAIL;
import static com.customer.fivecanale.util.EcomConstants.MINUS_ONE;
import static com.customer.fivecanale.util.EcomConstants.ORDER_ID;
import static com.customer.fivecanale.util.EcomConstants.ORDER_TIME;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.customer.domain.model.orderhistory.OrderHistoryItemData;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ItemHistoryOrderBinding;
import java.util.ArrayList;

/**
 * adapter class for history
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
  private Context mContext;
  private ArrayList<OrderHistoryItemData> mOrderHistoryItemData;
  private HistoryItemClick mHistoryItemClick;

  /**
   * constructor for this class
   *
   * @param orderHistoryItemData arrayList orderHistoryItemData
   * @param historyItemClick     interface on click listener
   */
  HistoryAdapter(ArrayList<OrderHistoryItemData> orderHistoryItemData,
      HistoryItemClick historyItemClick) {
    this.mOrderHistoryItemData = orderHistoryItemData;
    this.mHistoryItemClick = historyItemClick;
  }

  @NonNull
  @Override
  public HistoryAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    mContext = parent.getContext();
    ItemHistoryOrderBinding historyOrderBinding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_history_order, parent, false);
    return new HistoryViewHolder(historyOrderBinding);
  }

  @Override
  public void onBindViewHolder(@NonNull HistoryAdapter.HistoryViewHolder holder, int position) {
    OrderHistoryItemData orderHistoryItemData = mOrderHistoryItemData.get(position);
    holder.mHistoryOrderBinding.tvOrderNo.setText(orderHistoryItemData.getOrderId());
    holder.mHistoryOrderBinding.tvOrderTime.setText(
        EcomUtil.getDate(
            Long.parseLong(orderHistoryItemData.getCreatedTimeStamp())));
    HistoryStoreAdapter historyStoreAdapter = new HistoryStoreAdapter(
        orderHistoryItemData.getStoreOrders(), position, mHistoryItemClick);
    holder.mHistoryOrderBinding.rvOrderStores.setAdapter(historyStoreAdapter);
    holder.mHistoryOrderBinding.clOrderItem.setOnClickListener(view -> {
      Bundle bundle = new Bundle();
      bundle.putString(ORDER_ID, orderHistoryItemData.getOrderId());
      bundle.putString(ORDER_TIME, holder.mHistoryOrderBinding.tvOrderTime.getText().toString());
      mHistoryItemClick.onClick(HISTROY_ORDER_DETAIL, position, MINUS_ONE, MINUS_ONE, bundle);
    });
  }

  @Override
  public int getItemCount() {
    return mOrderHistoryItemData != null ? mOrderHistoryItemData.size() : ZERO;
  }

  /**
   * view holder class
   */
  class HistoryViewHolder extends RecyclerView.ViewHolder {
    ItemHistoryOrderBinding mHistoryOrderBinding;

    /**
     * view holder class constructor
     *
     * @param historyOrderBinding binding reference.
     */
    HistoryViewHolder(@NonNull ItemHistoryOrderBinding historyOrderBinding) {
      super(historyOrderBinding.getRoot());
      mHistoryOrderBinding = historyOrderBinding;
    }
  }
}
