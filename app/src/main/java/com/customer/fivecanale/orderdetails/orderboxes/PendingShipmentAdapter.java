package com.customer.fivecanale.orderdetails.orderboxes;

import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.customer.domain.model.orderhistory.OrderHistProductData;
import com.customer.fivecanale.SelectItem;
import com.databinding.ItemHistoryInsideBoxBinding;
import java.util.ArrayList;

/**
 * adapter class for notifications
 */
public class PendingShipmentAdapter extends
    RecyclerView.Adapter<PendingShipmentAdapter.ViewHolder> {
  private Context mContext;
  private ArrayList<OrderHistProductData> mOrderHistProductData;
  private SelectItem mSelectItem;
  private String mStoreName;

  /**
   * constructor for this class
   *
   * @param notificationList arrayList notification data
   */
  public PendingShipmentAdapter(String storeName,
      ArrayList<OrderHistProductData> notificationList) {
    this.mOrderHistProductData = notificationList;
    this.mStoreName = storeName;
  }

  @NonNull
  @Override
  public PendingShipmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    mContext = parent.getContext();
    ItemHistoryInsideBoxBinding itemHistoryInsideBoxBinding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_history_inside_box, parent, false);
    return new ViewHolder(itemHistoryInsideBoxBinding);
  }

  @Override
  public void onBindViewHolder(@NonNull PendingShipmentAdapter.ViewHolder holder, int position) {
  }

  @Override
  public int getItemCount() {
    return mOrderHistProductData != null ? mOrderHistProductData.size() : ZERO;
  }

  /**
   * view holder class
   */
  class ViewHolder extends RecyclerView.ViewHolder {
    ItemHistoryInsideBoxBinding mItemHistoryInsideBoxBinding;

    /**
     * view holder class constructor
     *
     * @param itemHistoryInsideBoxBinding binding reference.
     */
    ViewHolder(@NonNull ItemHistoryInsideBoxBinding itemHistoryInsideBoxBinding) {
      super(itemHistoryInsideBoxBinding.getRoot());
      mItemHistoryInsideBoxBinding = itemHistoryInsideBoxBinding;
    }
  }
}
