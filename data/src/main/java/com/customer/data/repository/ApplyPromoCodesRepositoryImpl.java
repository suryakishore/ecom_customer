package com.customer.data.repository;

import static com.customer.remote.http.RemoteConstants.ECOM_PARTNER;
import static com.customer.remote.http.RemoteConstants.REGION_APPLIED;

import com.customer.data.DataSource;
import com.customer.data.mapper.ApplyPromoCodeMapper;
import com.customer.data.preference.PreferenceManager;
import com.customer.data.utils.DataUtils;
import com.customer.domain.interactor.ApplyPromoCodesUseCase;
import com.customer.domain.model.promocode.ProductPromoInput;
import com.customer.domain.repository.ApplyPromoCodeRepository;
import com.customer.remote.http.model.request.ApplyPromoCodeRequest;
import com.customer.remote.http.model.request.ApplyPromoProductRequest;
import com.customer.remote.http.model.response.getcart.QuantityDetails;
import com.customer.remote.http.model.response.promocode.ApplyPromoCodeListData;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import javax.inject.Inject;

public class ApplyPromoCodesRepositoryImpl extends BaseRepository implements
    ApplyPromoCodeRepository {
  private DataSource mDataSource;
  private PreferenceManager preference;
  private ApplyPromoCodeMapper mMapper = new ApplyPromoCodeMapper();

  @Inject
  public ApplyPromoCodesRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    this.mDataSource = dataSource;
    preference = dataSource.preference();
  }

  @Override
  public Single<ApplyPromoCodesUseCase.ResponseValues> applyPromoCode(
      int paymentMethodType, String promoCode, String promoId, String currencySymbol,
      float totalPurchaseValue,
      String cartId,
      float deliveryFee, String currencyName,
      ArrayList<ProductPromoInput> productPromoInputArrayList) {
    return mDataSource.api().promocodeApiHandler().applyPromoCode(getHeader(),
        new ApplyPromoCodeRequest(preference.getUserDetails().getMail(), paymentMethodType,
            "$", promoCode, promoId, totalPurchaseValue, cartId, deliveryFee,
            "USD",
            preference.getUserId(), ECOM_PARTNER, REGION_APPLIED, 0, "", 0, 1,
            getProductsList(productPromoInputArrayList)
        )
    ).flatMap(
        new Function<ApplyPromoCodeListData, SingleSource<?
            extends ApplyPromoCodesUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends ApplyPromoCodesUseCase.ResponseValues> apply(
              ApplyPromoCodeListData details) {
            return Single.just(
                new ApplyPromoCodesUseCase.ResponseValues(mMapper.mapper(details)));
          }
        });
  }

  private ArrayList<ApplyPromoProductRequest> getProductsList(
      ArrayList<ProductPromoInput> productPromoInputArrayList) {
    ArrayList<ApplyPromoProductRequest> applyPromoProductRequests = new ArrayList<>();
    if (!DataUtils.isEmptyArray(productPromoInputArrayList)) {
      for (ProductPromoInput details : productPromoInputArrayList) {
        ApplyPromoProductRequest promoProductRequest = new ApplyPromoProductRequest();
        promoProductRequest.setBrand_name(
            details.getBrand_name() != null && !details.getBrand_name().isEmpty()
                ? details.getBrand_name() : "");
        promoProductRequest.setProduct_id(
            details.getProduct_id() != null && !details.getProduct_id().isEmpty()
                ? details.getProduct_id() : "");
        promoProductRequest.setName(
            details.getName() != null && !details.getName().isEmpty()
                ? details.getName() : "");
        promoProductRequest.setPrice(details.getPrice());
        promoProductRequest.setDelivery_fee(details.getDelivery_fee());
        promoProductRequest.setCityId(details.getCityId());
        promoProductRequest.setUnitPrice(details.getUnitPrice());
        promoProductRequest.setTaxAmount(details.getTaxAmount());
        promoProductRequest.setCentralproduct_id(details.getCentralproduct_id());
        promoProductRequest.setTax(details.getTax());
        promoProductRequest.setQuantity(new QuantityDetails(details.getQuantityData().getUnit(),
            details.getQuantityData().getValue()));
        promoProductRequest.setCategory_id(
            details.getCategory_id() != null && !details.getCategory_id().isEmpty()
                ? details.getCategory_id() : null);
        promoProductRequest.setStore_id(
            details.getStore_id() != null && !details.getStore_id().isEmpty()
                ? details.getStore_id() : null);
        applyPromoProductRequests.add(promoProductRequest);
      }
    }
    return applyPromoProductRequests;
  }
}
