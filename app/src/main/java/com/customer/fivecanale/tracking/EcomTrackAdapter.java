package com.customer.fivecanale.tracking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.R;
import com.customer.domain.model.tracking.TrackingItemData;
import com.customer.fivecanale.util.DateAndTimeUtil;
import com.databinding.ItemTrackingBinding;

import java.util.ArrayList;

import static com.customer.fivecanale.util.EcomConstants.CANCELLED_ORDER_STATUS;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

/**
 * adapter class for the tracking activity.
 */
public class EcomTrackAdapter extends RecyclerView.Adapter<EcomTrackAdapter.TrackViewHolder> {
  private ArrayList<TrackingItemData> mTrackingItemData;
  private Context mContext;
  private String mReason;

  EcomTrackAdapter(
          ArrayList<TrackingItemData> trackingItemData) {
    mTrackingItemData = trackingItemData;
  }

  public void setReason(String reason) {
    mReason = reason;
  }

  @NonNull
  @Override
  public EcomTrackAdapter.TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                             int viewType) {
    mContext = parent.getContext();
    ItemTrackingBinding binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()),
            R.layout.item_tracking, parent, false);
    return new TrackViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull EcomTrackAdapter.TrackViewHolder holder, int position) {
    TrackingItemData trackingItemData = mTrackingItemData.get(position);
    holder.mBinding.tvTrackingStatus.setText(trackingItemData.getStatusText());
    if (trackingItemData.getTime() != null && !trackingItemData.getTime().isEmpty()) {
      holder.mBinding.tvOrderStatusDate.setText(
              DateAndTimeUtil.getTrackingTransactionTime(trackingItemData.getTime()));
      if (trackingItemData.getStatus() != null) {
        if (Integer.parseInt(trackingItemData.getStatus()) == CANCELLED_ORDER_STATUS) {
          holder.mBinding.tvOrderStatusReason.setVisibility(View.VISIBLE);
          holder.mBinding.tvOrderStatusReason.setText(mReason);
        }
      }
    }
    holder.mBinding.viewTrackIndicatorHalf1.setBackgroundColor(trackingItemData.isViewStatus1()
            ? mContext.getResources().getColor(R.color.colorightWhite)
            : mContext.getResources().getColor(R.color.hippie_green));
    holder.mBinding.viewTrackIndicatorHalf2.setBackgroundColor(trackingItemData.isViewStatus2()
            ? mContext.getResources().getColor(R.color.colorightWhite)
            : mContext.getResources().getColor(R.color.hippie_green));
    holder.mBinding.ivCircle.setImageDrawable(
            trackingItemData.getFormatedDate().isEmpty() ? mContext.getResources().getDrawable(
                    R.drawable.alto_circle) : mContext.getResources().getDrawable(R.drawable.green_circle));
    if (mTrackingItemData.size() - ONE == position) {
      holder.mBinding.viewTrackIndicatorHalf1.setVisibility(View.GONE);
      holder.mBinding.viewTrackIndicatorHalf2.setVisibility(View.GONE);
    }
  }

  @Override
  public int getItemCount() {
    return mTrackingItemData != null ? mTrackingItemData.size() : ZERO;
  }

  class TrackViewHolder extends RecyclerView.ViewHolder {
    ItemTrackingBinding mBinding;

    TrackViewHolder(@NonNull ItemTrackingBinding binding) {
      super(binding.getRoot());
      this.mBinding = binding;
    }
  }
}
