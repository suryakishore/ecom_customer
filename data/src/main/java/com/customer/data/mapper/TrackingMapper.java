package com.customer.data.mapper;

import com.customer.data.utils.DataUtils;
import com.customer.domain.model.tracking.TrackingData;
import com.customer.domain.model.tracking.TrackingItemData;
import com.customer.remote.http.model.response.tracking.TrackingItemDetails;
import com.customer.remote.http.model.response.tracking.TrackingListOrderStatusData;
import java.util.ArrayList;

public class TrackingMapper {
  public TrackingData mapper(
      TrackingListOrderStatusData trackingListData) {
    TrackingData trackingData = null;
    if (trackingListData != null) {
      trackingData = new TrackingData(convertToTrackingItemData(trackingListData.getOrderStatus()));
    }
    return trackingData;
  }

  private ArrayList<TrackingItemData> convertToTrackingItemData(
      ArrayList<TrackingItemDetails> trackingItemDetailsArrayList) {
    ArrayList<TrackingItemData> trackingItemData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(trackingItemDetailsArrayList)) {
      for (TrackingItemDetails itemDetails : trackingItemDetailsArrayList) {
        TrackingItemData itemData = new TrackingItemData(itemDetails.getStatusText(),
            itemDetails.getTime(), itemDetails.getFormatedDate(), itemDetails.getStatus());
        trackingItemData.add(itemData);
      }
    }
    return trackingItemData;
  }
}
