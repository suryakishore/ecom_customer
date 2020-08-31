package com.customer.remote.http;

import com.customer.remote.http.model.request.ApplyPromoCodeRequest;
import com.customer.remote.http.model.response.promocode.ApplyPromoCodeListData;
import com.customer.remote.http.model.response.promocode.PromoCodeListDetails;
import io.reactivex.Single;
import java.util.HashMap;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface PromoCodeApi {
  @POST("v3/promotion/")
  Single<ApplyPromoCodeListData> applyPromoCodeApi(@Body ApplyPromoCodeRequest req);

  @GET("promocode/view")
  Single<PromoCodeListDetails> getPromoCodeApi(
      @QueryMap(encoded = true) HashMap<String, Object> query);
}
