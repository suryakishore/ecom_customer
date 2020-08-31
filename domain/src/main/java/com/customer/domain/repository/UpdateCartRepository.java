package com.customer.domain.repository;

import com.customer.domain.interactor.UpdateCartUseCase;
import com.customer.domain.model.pdp.PdpOfferData;

import io.reactivex.Single;

public interface UpdateCartRepository {

  Single<UpdateCartUseCase.ResponseValues> updateCart(int userType, String centralProductId,
                                                      String productId, String unitId, String storeId, int storeTypeId, String productName,
                                                      String estimatedProductPrice, String extraNotes, int newQuantity, int oldQuantity,
                                                      int action, int cartType, String userIp, double latitude, double longitude);

  Single<UpdateCartUseCase.ResponseValues> updateCart(int userType, String centralProductId,
                                                      String productId, String unitId, String storeId, int storeTypeId, String productName,
                                                      String estimatedProductPrice, String extraNotes, int newQuantity, int oldQuantity,
                                                      int action, int cartType, String userIp, double latitude, double longitude, PdpOfferData offer);


}
