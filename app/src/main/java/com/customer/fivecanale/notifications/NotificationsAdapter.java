package com.customer.fivecanale.notifications;

import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.bumptech.glide.Glide;
import com.customer.domain.model.notification.NotificationListData;
import com.customer.fivecanale.SelectItem;
import com.customer.fivecanale.util.DateAndTimeUtil;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ItemNotificationsBinding;
import java.util.ArrayList;

/**
 * adapter class for notifications
 */
public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {
  private Context mContext;
  private ArrayList<NotificationListData> mNotificationList;
  private SelectItem mSelectItem;

  /**
   * constructor for this class
   *
   * @param notificationList arrayList notification data
   */
  NotificationsAdapter(ArrayList<NotificationListData> notificationList, SelectItem selectItem) {
    this.mNotificationList = notificationList;
    this.mSelectItem = selectItem;
  }

  @NonNull
  @Override
  public NotificationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    mContext = parent.getContext();
    ItemNotificationsBinding notificationsBinding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_notifications, parent, false);
    return new ViewHolder(notificationsBinding);
  }

  @Override
  public void onBindViewHolder(@NonNull NotificationsAdapter.ViewHolder holder, int position) {
    NotificationListData notificationListData = mNotificationList.get(position);
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
    }
  }

  @Override
  public int getItemCount() {
    return mNotificationList != null ? mNotificationList.size() : ZERO;
  }

  /**
   * view holder class
   */
  class ViewHolder extends RecyclerView.ViewHolder {
    ItemNotificationsBinding mNotificationsBinding;

    /**
     * view holder class constructor
     *
     * @param notificationsBinding binding reference.
     */
    ViewHolder(@NonNull ItemNotificationsBinding notificationsBinding) {
      super(notificationsBinding.getRoot());
      mNotificationsBinding = notificationsBinding;
    }
  }
}
