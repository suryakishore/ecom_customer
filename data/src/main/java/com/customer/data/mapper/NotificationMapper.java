package com.customer.data.mapper;

import com.customer.data.utils.DataUtils;
import com.customer.domain.model.notification.NotificationListData;
import com.customer.domain.model.notification.NotificationListDetails;
import com.customer.remote.http.model.response.notification.NotificationData;
import com.customer.remote.http.model.response.notification.NotificationDetails;
import java.util.ArrayList;

public class NotificationMapper {
  public NotificationListDetails mapper(NotificationDetails notificationDetails) {
    return new NotificationListDetails(convertToNotificationItemData(notificationDetails.getData()),
        notificationDetails.getTotal_count(), notificationDetails.getMessage());
  }

  private ArrayList<NotificationListData> convertToNotificationItemData(
      ArrayList<NotificationData> notificationDataArrayList) {
    ArrayList<NotificationListData> notificationListDataArrayList = new ArrayList<>();
    if (!DataUtils.isEmptyArray(notificationDataArrayList)) {
      for (NotificationData itemDetails : notificationDataArrayList) {
        NotificationListData itemData = new NotificationListData(itemDetails.getDate(),
            itemDetails.getNotificationTypeMsg(), itemDetails.getUserTypeMsg(),
            itemDetails.getAppName(), itemDetails.getTopic(), itemDetails.getUserType(),
            itemDetails.getNotificationType(), itemDetails.getBody(), itemDetails.getImage(),
            itemDetails.getUserName(),
            itemDetails.getTitle(), itemDetails.getMasterOrderId(), itemDetails.getProductOrderId(),
            itemDetails.getPackageId());
        notificationListDataArrayList.add(itemData);
      }
    }
    return notificationListDataArrayList;
  }
}
