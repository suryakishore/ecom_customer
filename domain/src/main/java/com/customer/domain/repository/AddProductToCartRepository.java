package com.customer.domain.repository;

import com.customer.domain.interactor.AddProductToCartUseCase;
import com.customer.domain.model.pdp.PdpOfferData;
import io.reactivex.Single;

public interface AddProductToCartRepository {
  Single<AddProductToCartUseCase.ResponseValues> addProductToCart(int userType,
      String centralProductId, String productId, String unitId, String storeId, int quantity,
      int cartType, String notes, String userIp, String userPostCode, double latitude,
      double longitude, String city, int storeTypeId, String productName,
      String estimatedProductPrice, String extraNotes, PdpOfferData offerData);
}

