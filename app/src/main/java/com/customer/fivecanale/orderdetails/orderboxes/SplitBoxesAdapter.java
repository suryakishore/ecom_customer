package com.customer.fivecanale.orderdetails.orderboxes;

import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.customer.domain.model.orderhistory.OrderHistProductData;
import com.customer.fivecanale.SelectItem;
import com.customer.fivecanale.util.EcomUtil;
import com.customer.fivecanale.util.OrderDetailClick;
import com.databinding.ItemHistoryBoxBinding;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * adapter class for notifications
 */
public class SplitBoxesAdapter extends RecyclerView.Adapter<SplitBoxesAdapter.ViewHolder> {
  private Context mContext;
  private LinkedHashMap<String, ArrayList<OrderHistProductData>> mHashMap;
  private OrderDetailClick mOrderDetailClick;
  private SelectItem mSelectItem;
  private String mStoreName;

  public SplitBoxesAdapter(String storeName,
      LinkedHashMap<String, ArrayList<OrderHistProductData>> hashMap,
      OrderDetailClick orderDetailClick, SelectItem selectItem) {
    mStoreName = storeName;
    mHashMap = hashMap;
    mOrderDetailClick = orderDetailClick;
    mSelectItem = selectItem;
  }

  @NonNull
  @Override
  public SplitBoxesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    mContext = parent.getContext();
    ItemHistoryBoxBinding itemHistoryBoxBinding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_history_box, parent, false);
    return new ViewHolder(itemHistoryBoxBinding);
  }

  @Override
  public void onBindViewHolder(@NonNull SplitBoxesAdapter.ViewHolder holder, int position) {
    holder.mItemHistoryBoxBinding.tvBoxCount.setText(
        String.format("%s %d", mContext.getResources().getString(R.string.box), (position + ONE)));
    EcomUtil.printLog("exe" + "pa Id" + mHashMap.keySet().toArray()[position]);
    if (mHashMap.keySet().toArray()[position] != null && !String.valueOf(
        mHashMap.keySet().toArray()[position]).isEmpty()) {
      holder.mItemHistoryBoxBinding.tvPackageId.setText(
          String.format("%s: %s", mContext.getResources().getString(R.string.packageId),
              mHashMap.keySet().toArray()[position]));
    } else {
      holder.mItemHistoryBoxBinding.tvPackageId.setVisibility(View.GONE);
    }
    if (mHashMap.values().toArray().length > ZERO) {
      ArrayList<OrderHistProductData> orderHistProductDataList =
          (ArrayList<OrderHistProductData>) mHashMap.values().toArray()[position];
      if (orderHistProductDataList != null && orderHistProductDataList.size() > ZERO) {
        OrderHistProductData orderHistProductData = orderHistProductDataList.get(ZERO);
        if (orderHistProductData.getStatus() != null) {
          holder.mItemHistoryBoxBinding.tvOrderStatus.setText(
              orderHistProductData.getStatus().getStatusText());
          if (orderHistProductData.getStatus().getUpdatedOnTimeStamp() != null) {
            holder.mItemHistoryBoxBinding.tvOrderTime.setText(
                EcomUtil.getDateForHistoryDet(
                    Long.parseLong(orderHistProductData.getStatus().getUpdatedOnTimeStamp())));
          }
        }
      }
    }
    holder.mItemHistoryBoxBinding.clBoxItem.setOnClickListener(
        view -> mSelectItem.onSelectItem(position));
    BoxItemsAdapter splitBoxItemsAdapter = new BoxItemsAdapter(mStoreName,
        (ArrayList<OrderHistProductData>) mHashMap.values().toArray()[position], mOrderDetailClick);
    holder.mItemHistoryBoxBinding.rvBoxItems.setAdapter(splitBoxItemsAdapter);
  }

  @Override
  public int getItemCount() {
    return mHashMap != null ? mHashMap.size() : ZERO;
  }

  /**
   * view holder class
   */
  class ViewHolder extends RecyclerView.ViewHolder {
    ItemHistoryBoxBinding mItemHistoryBoxBinding;

    /**
     * view holder class constructor
     *
     * @param itemHistoryBoxBinding binding reference.
     */
    ViewHolder(@NonNull ItemHistoryBoxBinding itemHistoryBoxBinding) {
      super(itemHistoryBoxBinding.getRoot());
      mItemHistoryBoxBinding = itemHistoryBoxBinding;
    }
  }
}
