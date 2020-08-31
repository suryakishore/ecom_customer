package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.CommonData;
import com.customer.domain.repository.PlaceOrderRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class PlaceOrderUseCase extends
    UseCase<PlaceOrderUseCase.RequestValues, PlaceOrderUseCase.ResponseValues> {
  private PlaceOrderRepository mRepository;

  @Inject
  public PlaceOrderUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread, PlaceOrderRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.placeOrder(requestValues.cartId, requestValues.addressId,
        requestValues.billingAddressId,
        requestValues.paymentType, requestValues.cardId, requestValues.payByWallet,
        requestValues.coupon, requestValues.couponId, requestValues.discount,
        requestValues.latitude,
        requestValues.longitude, requestValues.ipAddress, requestValues.extraNote,
        requestValues.storeType, requestValues.orderType);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String cartId;
    private String addressId;
    private String billingAddressId;
    private int paymentType;
    private String cardId;
    private boolean payByWallet;
    private String coupon;
    private String couponId;
    private double discount;
    private double latitude;
    private double longitude;
    private String ipAddress;
    private String extraNote;
    private int storeType;
    private int orderType;

    public RequestValues(String cartId, String addressId, String billingAddressId, int paymentType,
        String cardId,
        boolean payByWallet, String coupon, String couponId, double discount, double latitude,
        double longitude,
        String ipAddress, String extraNote, int storeType, int orderType) {
      this.cartId = cartId;
      this.addressId = addressId;
      this.billingAddressId = billingAddressId;
      this.paymentType = paymentType;
      this.cardId = cardId;
      this.payByWallet = payByWallet;
      this.coupon = coupon;
      this.couponId = couponId;
      this.discount = discount;
      this.latitude = latitude;
      this.longitude = longitude;
      this.ipAddress = ipAddress;
      this.extraNote = extraNote;
      this.storeType = storeType;
      this.orderType = orderType;
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private CommonData mData;

    public ResponseValues(CommonData data) {
      this.mData = data;
    }

    public CommonData getData() {
      return mData;
    }

    public void setData(CommonData data) {
      this.mData = data;
    }
  }
}
