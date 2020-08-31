package com.customer.fivecanale.helpcenter;

import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.customer.domain.model.notification.NotificationListData;
import com.customer.fivecanale.SelectItem;
import com.databinding.ItemHelpIndexBinding;
import java.util.ArrayList;

/**
 * adapter class for notifications
 */
public class HelpIndexAdapter extends RecyclerView.Adapter<HelpIndexAdapter.ViewHolder> {
  private Context mContext;
  private ArrayList<NotificationListData> mHelpIndexList;
  private SelectItem mSelectItem;

  /**
   * constructor for this class
   *
   * @param helpIndexList arrayList notification data
   */
  HelpIndexAdapter(ArrayList<NotificationListData> helpIndexList, SelectItem selectItem) {
    this.mHelpIndexList = helpIndexList;
    this.mSelectItem = selectItem;
  }

  @NonNull
  @Override
  public HelpIndexAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    mContext = parent.getContext();
    ItemHelpIndexBinding itemHelpIndexBinding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_help_index, parent, false);
    return new ViewHolder(itemHelpIndexBinding);
  }

  @Override
  public void onBindViewHolder(@NonNull HelpIndexAdapter.ViewHolder holder, int position) {
   /* NotificationListData notificationListData = mNotificationList.get(position);
    if (notificationListData != null) {
      String imageUrl = notificationListData.getImage();
      EcomUtil.printLog("exe" + "imageUrl" + imageUrl);
      if (!TextUtils.isEmpty(imageUrl)) {
        Glide.with(mContext)
            .load(imageUrl.replace(" ", "%20"))
            .into(holder.mNotificationsBinding.ivProductImg);
      } else if (!TextUtils.isEmpty(notificationListData.getPackageId())) {
        holder.mNotificationsBinding.ivProductImg.setImageDrawable(
            mContext.getResources().getDrawable(R.drawable.ic_box_icon));
      } else {
        holder.mNotificationsBinding.ivProductImg.setVisibility(View.GONE);
      }
      holder.mNotificationsBinding.tvProductStatus.setText(notificationListData.getTitle());
      holder.mNotificationsBinding.tvStatusDesc.setText(notificationListData.getBody());
      holder.mNotificationsBinding.tvStatusTime.setText(
          DateAndTimeUtil.timeAgo(notificationListData.getDate()));
      holder.mNotificationsBinding.clItemNotifications.setOnClickListener(
          view -> mSelectItem.onSelectItem(position));
    }*/
  }

  @Override
  public int getItemCount() {
    return mHelpIndexList != null ? mHelpIndexList.size() : ZERO;
  }

  /**
   * view holder class
   */
  class ViewHolder extends RecyclerView.ViewHolder {
    ItemHelpIndexBinding mItemHelpIndexBinding;

    /**
     * view holder class constructor
     *
     * @param itemHelpIndexBinding binding reference.
     */
    ViewHolder(@NonNull ItemHelpIndexBinding itemHelpIndexBinding) {
      super(itemHelpIndexBinding.getRoot());
      mItemHelpIndexBinding = itemHelpIndexBinding;
    }
  }
}
