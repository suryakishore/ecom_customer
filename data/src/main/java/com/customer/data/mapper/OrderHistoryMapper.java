package com.customer.data.mapper;

import com.customer.data.utils.DataUtils;
import com.customer.domain.DomainUtil;
import com.customer.domain.model.orderhistory.OrderHistOrderData;
import com.customer.domain.model.orderhistory.OrderHistStoreOrderData;
import com.customer.domain.model.orderhistory.OrderHistoryData;
import com.customer.domain.model.orderhistory.OrderHistoryItemData;
import com.customer.domain.model.orderhistory.OrderHistorySellerLogoData;
import com.customer.domain.model.orderhistory.OrdersHistorySellerData;
import com.customer.remote.http.model.response.orderhistory.OrderHistOrderDetails;
import com.customer.remote.http.model.response.orderhistory.OrderHistStoreOrderDetails;
import com.customer.remote.http.model.response.orderhistory.OrderHistoryDetails;
import com.customer.remote.http.model.response.orderhistory.OrderHistoryItemDetails;
import com.customer.remote.http.model.response.orderhistory.OrderHistorySellerLogoDetails;
import com.customer.remote.http.model.response.orderhistory.OrdersHistorySellerDetails;
import java.util.ArrayList;

public class OrderHistoryMapper {
  public OrderHistoryData mapper(OrderHistoryDetails orderHistoryDetails) {
    return new OrderHistoryData(orderHistoryDetails.getCount(), orderHistoryDetails.getMessage(),
        convertToOrderHistItemData(orderHistoryDetails.getData()));
  }

  private ArrayList<OrderHistoryItemData> convertToOrderHistItemData(
      ArrayList<OrderHistoryItemDetails> historyItemDetails) {
    ArrayList<OrderHistoryItemData> orderHistoryItemData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(historyItemDetails)) {
      for (OrderHistoryItemDetails itemDetails : historyItemDetails) {
        OrderHistoryItemData itemData = new OrderHistoryItemData(itemDetails.getRequestedFor(),
            itemDetails.getOrderType(), itemDetails.getPaymentTypeText(), itemDetails.getOrderId(),
            CommonMapper.convertToTimeStampData(itemDetails.getTimestamps()),
            itemDetails.getCreatedTimeStamp(),
            itemDetails.getOrderTypeMsg(),
            CommonMapper.convertToAccountingData(itemDetails.getAccounting()),
            itemDetails.getPaymentType(), itemDetails.getRequestedForTimeStamp(),
            null,
            itemDetails.getCustomerId(),
            CommonMapper.convertCustomerData(itemDetails.getCustomerDetails()),
            itemDetails.getPayByWallet(), itemDetails.getStoreType(), itemDetails.getCoupon(),
            itemDetails.getCartId(), convertToStoreOrderData(itemDetails.getStoreOrders()),
            itemDetails.getStoreTypeMsg(), itemDetails.getCreatedDate(),
            convertToOrderData(itemDetails.getOrders()), itemDetails.get_id(),
            null,
            convertToSellerData(itemDetails.getSellers()),
            CommonMapper.convertToStatusData(itemDetails.getStatus())
        );
        orderHistoryItemData.add(itemData);
      }
    }
    return orderHistoryItemData;
  }

  private ArrayList<OrderHistOrderData> convertToOrderData(
      ArrayList<OrderHistOrderDetails> orderDetails) {
    ArrayList<OrderHistOrderData> histOrderData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(orderDetails)) {
      for (OrderHistOrderDetails details : orderDetails) {
        OrderHistOrderData orderData = new OrderHistOrderData(details.getStoreOrderId(),
            details.getProductOrderId());
        histOrderData.add(orderData);
      }
    }
    return histOrderData;
  }

  private ArrayList<OrderHistStoreOrderData> convertToStoreOrderData(
      ArrayList<OrderHistStoreOrderDetails> storeOrderDetails) {
    ArrayList<OrderHistStoreOrderData> histStoreOrderData = new ArrayList<>();
    if (!DomainUtil.isEmptyArray(storeOrderDetails)) {
      for (OrderHistStoreOrderDetails details : storeOrderDetails) {
        OrderHistStoreOrderData orderData = new OrderHistStoreOrderData(details.getRequestedFor(),
            details.getOrderType(), details.getPaymentTypeText(), details.getDriverTypeId(),
            CommonMapper.convertToTimeStampData(details.getTimestamps()),
            details.getCreatedTimeStamp(), details.getOrderTypeMsg(),
            CommonMapper.convertToAccountingData(details.getAccounting()), details.getPaymentType(),
            CommonMapper.convertToOrderHistProductData(details.getProducts()),
            details.getRequestedForTimeStamp(),
            details.getAutoDispatch(),
            CommonMapper.convertToPickUpAddressData(details.getDeliveryAddress()),
            details.getStorePhone(), details.getCustomerId(), details.getStoreName(),
            CommonMapper.convertCustomerData(details.getCustomerDetails()),
            details.getFreeDeliveryLimitPerStore(),
            details.getPayByWallet(), details.getStoreType(), details.getCartId(),
            details.getStoreId(), details.getDriverType(), details.getMasterOrderId(),
            details.getStoreTypeMsg(), details.getCreatedDate(),
            details.getStoreOrderId(), details.getAutoAcceptOrders(),
            CommonMapper.convertToPickUpAddressData(details.getPickupAddress()),
            CommonMapper.convertToStoreLogoData(details.getStoreLogo()), details.get_id(),
            null,
            CommonMapper.convertToPlanData(details.getStorePlan()),
            CommonMapper.convertToStatusData(details.getStatus())
        );
        histStoreOrderData.add(orderData);
      }
    }
    return histStoreOrderData;
  }

  private ArrayList<OrdersHistorySellerData> convertToSellerData(
      ArrayList<OrdersHistorySellerDetails> sellerDetails) {
    ArrayList<OrdersHistorySellerData> ordersHistorySellerData = new ArrayList<>();
    if (!DomainUtil.isEmptyArray(sellerDetails)) {
      for (OrdersHistorySellerDetails details : sellerDetails) {
        OrdersHistorySellerData sellerData = new OrdersHistorySellerData(
            details.getTargetAmtForFreeDelivery(),
            CommonMapper.convertToPlanData(details.getPlanData()),
            details.getFullFillMentCenterId(), details.getMinOrder(),
            details.getDriverType(), details.getFullfilledBy(), details.getContactPersonName(),
            details.getStoreTax(), details.getPhone(),
            CommonMapper.convertToPickUpAddressData(details.getPickupAddress()),
            details.getAutoDispatch(), details.getAutoAcceptOrders(),
            details.getIsInventoryCheck(), details.getName(),
            convertToLogoData(details.getLogo()), details.getContactPersonEmail()
        );
        ordersHistorySellerData.add(sellerData);
      }
    }
    return ordersHistorySellerData;
  }

  private OrderHistorySellerLogoData convertToLogoData(OrderHistorySellerLogoDetails logoDetails) {
    OrderHistorySellerLogoData sellerLogoData = null;
    if (logoDetails != null) {
      sellerLogoData = new OrderHistorySellerLogoData(logoDetails.getLogoImageThumb(),
          logoDetails.getLogoImageMobile());
    }
    return sellerLogoData;
  }
}
