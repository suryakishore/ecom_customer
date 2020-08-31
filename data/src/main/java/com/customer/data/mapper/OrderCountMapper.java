package com.customer.data.mapper;

import com.customer.data.utils.DataUtils;
import com.customer.domain.model.ordercount.OrderCountItemListData;
import com.customer.domain.model.ordercount.OrderCountListData;
import com.customer.remote.http.model.response.ordercount.OrderCountItemDetails;
import com.customer.remote.http.model.response.ordercount.OrderCountListDetails;
import java.util.ArrayList;

public class OrderCountMapper {
  public OrderCountListData mapper(OrderCountListDetails orderCountListDetails) {
    return new OrderCountListData(convertToOrderCountItemData(orderCountListDetails.getData()),
        orderCountListDetails.getMessage());
  }

  private ArrayList<OrderCountItemListData> convertToOrderCountItemData(
      ArrayList<OrderCountItemDetails> orderCountItemDetails) {
    ArrayList<OrderCountItemListData> trackingItemData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(orderCountItemDetails)) {
      for (OrderCountItemDetails itemDetails : orderCountItemDetails) {
        OrderCountItemListData itemData = new OrderCountItemListData(itemDetails.getStatusText(),
            itemDetails.getCount(), itemDetails.getStatus());
        trackingItemData.add(itemData);
      }
    }
    return trackingItemData;
  }
}
