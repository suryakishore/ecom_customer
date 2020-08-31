package com.customer.remote.http;

import static com.customer.remote.http.RemoteConstants.CART_ID;
import static com.customer.remote.http.RemoteConstants.CITY_ID;
import static com.customer.remote.http.RemoteConstants.COUNTRY_ID;
import static com.customer.remote.http.RemoteConstants.ONE;
import static com.customer.remote.http.RemoteConstants.SERVICE_TYPE;
import static com.customer.remote.http.RemoteConstants.STORE_ID;

import com.customer.remote.http.model.request.ApplyPromoCodeRequest;
import com.customer.remote.http.model.request.Header;
import com.customer.remote.http.model.response.promocode.ApplyPromoCodeListData;
import com.customer.remote.http.model.response.promocode.PromoCodeListDetails;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.HashMap;

public class PromoCodeApiHandlerImpl implements PromoCodeApiHandler {
  private PromoCodeApi promoCodeApi;

  PromoCodeApiHandlerImpl(PromoCodeApi promoCodeApi) {
    this.promoCodeApi = promoCodeApi;
  }

  @Override
  public Single<PromoCodeListDetails> getPromoCodes(Header header, String countryId, String cityId,
      String cartId, String storeId) {
    HashMap<String, Object> map = new HashMap<>();
    map.put(SERVICE_TYPE, ONE);
    map.put(COUNTRY_ID, countryId);
    map.put(CITY_ID, cityId);
    map.put(CART_ID, cartId);
    map.put(STORE_ID, storeId);
    return promoCodeApi.getPromoCodeApi(map);
  }

  @Override
  public Single<ApplyPromoCodeListData> applyPromoCode(Header header,
      ApplyPromoCodeRequest applyPromoCodeRequest) {
    return promoCodeApi.applyPromoCodeApi(applyPromoCodeRequest);
  }
}