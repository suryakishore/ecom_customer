package com.customer.domain.interactor;

import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.promocode.ApplyPromoCodeData;
import com.customer.domain.model.promocode.ProductPromoInput;
import com.customer.domain.repository.ApplyPromoCodeRepository;
import io.reactivex.Single;
import java.util.ArrayList;
import javax.inject.Inject;

public class ApplyPromoCodesUseCase extends
    UseCase<ApplyPromoCodesUseCase.RequestValues, ApplyPromoCodesUseCase.ResponseValues> {
  private ApplyPromoCodeRepository mRepository;

  @Inject
  public ApplyPromoCodesUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread,
      ApplyPromoCodeRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = repository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    return mRepository.applyPromoCode(requestValues.paymentMethodType, requestValues.mPromoCode,requestValues.mPromoId,
        requestValues.currencySymbol, requestValues.totalPurchaseValue, requestValues.cartId,
        requestValues.deliveryFee, requestValues.currencyName,requestValues.products);
  }

  public static class RequestValues implements UseCase.RequestValues {
    private int paymentMethodType;
    private String mPromoCode;
    private String mPromoId;
    private String currencySymbol;
    private float totalPurchaseValue;
    private String cartId;
    private float deliveryFee;
    private String currencyName;
    private ArrayList<ProductPromoInput> products;
    public RequestValues(int paymentMethodType, String promoCode,String promoId, String cuurencySymbol,
        float totalPurchaseValue, String cartId, float deliveryFee, String currencyName,ArrayList<ProductPromoInput> products) {
      this.paymentMethodType = paymentMethodType;
      this.mPromoCode = promoCode;
      this.mPromoId=promoId;
      this.currencySymbol = cuurencySymbol;
      this.totalPurchaseValue = totalPurchaseValue;
      this.cartId = cartId;
      this.deliveryFee = deliveryFee;
      this.currencyName = currencyName;
      this.products=products;
    }
  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private ApplyPromoCodeData mData;

    public ResponseValues(ApplyPromoCodeData data) {
      this.mData = data;
    }

    public ApplyPromoCodeData getData() {
      return mData;
    }

    public void setData(ApplyPromoCodeData data) {
      this.mData = data;
    }
  }
}
