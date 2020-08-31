package com.customer.data.mapper;

import com.customer.data.utils.DataUtils;
import com.customer.domain.model.orderdetails.DeliveryStatusData;
import com.customer.domain.model.orderdetails.MasterOrderData;
import com.customer.domain.model.orderdetails.OrderData;
import com.customer.domain.model.orderdetails.PackageBoxData;
import com.customer.domain.model.orderdetails.PartnerData;
import com.customer.domain.model.tracking.TrackingItemData;
import com.customer.remote.http.model.response.orderdetails.DeliveryStatusDetails;
import com.customer.remote.http.model.response.orderdetails.MasterOrderDetails;
import com.customer.remote.http.model.response.orderdetails.OrderDetails;
import com.customer.remote.http.model.response.orderdetails.PackageBoxDetails;
import com.customer.remote.http.model.response.orderdetails.PartnerDetails;
import com.customer.remote.http.model.response.tracking.TrackingItemDetails;
import java.util.ArrayList;

public class OrderDetailsMapper {
  private ArrayList<OrderData> convertToOrderData(ArrayList<OrderDetails> orderDetailsList) {
    ArrayList<OrderData> orderDataList = new ArrayList<>();
    if (!DataUtils.isEmptyArray(orderDetailsList)) {
      for (OrderDetails orderDetails : orderDetailsList) {
        orderDataList.add(convertToOrderData(orderDetails));
      }
    }
    return orderDataList;
  }

  public OrderData convertToOrderData(OrderDetails orderDetails) {
    OrderData orderData = new OrderData(orderDetails.getRequestedFor(),
        orderDetails.getOrderType(),
        orderDetails.getPaymentTypeText(), orderDetails.getDriverTypeId(),
        CommonMapper.convertToTimeStampData(orderDetails.getTimestamps()),
        orderDetails.getCreatedTimeStamp(), orderDetails.getOrderTypeMsg(),
        CommonMapper.convertToAccountingData(orderDetails.getAccounting()),
        orderDetails.getPaymentType(),
        CommonMapper.convertToOrderHistProductData(orderDetails.getProducts()),
        CommonMapper.convertToOrderHistProductData(orderDetails.getAlongWith()),
        orderDetails.getRequestedForTimeStamp(),
        convertToPartnerData(orderDetails.getPartnerDetails()), orderDetails.getAutoDispatch(),
        CommonMapper.convertToAddressData(orderDetails.getDeliveryAddress()),
        orderDetails.getCustomerId(), orderDetails.getStoreName(),
        CommonMapper.convertCustomerData(orderDetails.getCustomerDetails()),
        orderDetails.getFreeDeliveryLimitPerStore(), orderDetails.getPayByWallet(),
        orderDetails.getStoreType(), orderDetails.getCartId(), orderDetails.getStoreId(),
        orderDetails.getDriverType(), orderDetails.getMasterOrderId(),
        orderDetails.getPackingDetails(), orderDetails.getStoreTypeMsg(),
        orderDetails.getCreatedDate(), orderDetails.getStoreOrderId(),
        orderDetails.getAutoAcceptOrders(),
        CommonMapper.convertToPickUpAddressData(orderDetails.getPickupAddress()),
        CommonMapper.convertToStoreLogoData(orderDetails.getStoreLogo()),
        orderDetails.getStoreEmail(), orderDetails.get_id(),
        CommonMapper.convertToAddressData(orderDetails.getBillingAddress()),
        CommonMapper.convertToPlanData(orderDetails.getStorePlan()),
        CommonMapper.convertToStatusData(orderDetails.getStatus()),
        convertToDelStatus(orderDetails.getDeliveryStatus()),
        convertToPackageData(orderDetails.getPackageBox())
    );
    return orderData;
  }

  public MasterOrderData mapper(MasterOrderDetails details) {
    return new MasterOrderData(details.getRequestedFor(), details.getOrderType(),
        details.getPaymentTypeText(), details.getOrderId(), details.getCreatedTimeStamp(),
        details.getOrderTypeMsg(), CommonMapper.convertToAccountingData(details.getAccounting()),
        details.getPaymentType(), details.getRequestedForTimeStamp(),
        CommonMapper.convertToAddressData(details.getDeliveryAddress()),
        details.getCustomerId(), details.getPayByWallet(),
        details.getStoreType(), details.getCoupon(), details.getCartId(),
        convertToOrderData(details.getStoreOrders()), null,
        details.getStoreTypeMsg(), details.getCreatedDate(), details.get_id(),
        CommonMapper.convertToAddressData(details.getBillingAddress()),
        CommonMapper.convertToStatusData(details.getStatus()),
        CommonMapper.convertToOrderHistProductData(details.getProducts()), details.getStoreName(),
        convertToOrderDelStatus(details.getOrderStatus()));
  }

  private ArrayList<DeliveryStatusData> convertToDelStatus(
      ArrayList<DeliveryStatusDetails> statusDetails) {
    ArrayList<DeliveryStatusData> deliveryStatusData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(statusDetails)) {
      for (DeliveryStatusDetails details : statusDetails) {
        DeliveryStatusData data = new DeliveryStatusData(details.getStatusText(),
            details.getTime(), details.getFormatedDate(), details.getStatus());
        deliveryStatusData.add(data);
      }
    }
    return deliveryStatusData;
  }

  private ArrayList<TrackingItemData> convertToOrderDelStatus(
      ArrayList<TrackingItemDetails> statusDetails) {
    ArrayList<TrackingItemData> deliveryStatusData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(statusDetails)) {
      for (TrackingItemDetails details : statusDetails) {
        TrackingItemData data = new TrackingItemData(details.getStatusText(),
            details.getTime(), details.getFormatedDate(), details.getStatus());
        deliveryStatusData.add(data);
      }
    }
    return deliveryStatusData;
  }

  private PackageBoxData convertToPackageData(PackageBoxDetails details) {
    PackageBoxData packageBoxData = null;
    if (details != null) {
      packageBoxData = new PackageBoxData(details.getImage(),
          details.getVolumeCapacity(), details.getVoulumeCapacityUnit(),
          details.getWidthCapacityUnitName(), details.getWeight(),
          details.getHeightCapacityUnitName(), details.getVoulumeCapacityUnitName(),
          details.getWeightCapacityUnitName(), details.getWidthCapacityUnit(),
          details.getHeightCapacity(), details.getWeightCapacityUnit(),
          details.getLengthCapacityUnitName(), details.getName().getEn(),
          details.getHeightCapacityUnit(), details.getLengthCapacityUnit(),
          details.getWidthCapacity(), details.getLengthCapacity(),
          details.getBoxId());
    }
    return packageBoxData;
  }

  private PartnerData convertToPartnerData(PartnerDetails details) {
    PartnerData partnerData = null;
    if (details != null) {
      partnerData = new PartnerData(details.getId(), details.getName(), details.getTrackingId());
    }
    return partnerData;
  }
}
