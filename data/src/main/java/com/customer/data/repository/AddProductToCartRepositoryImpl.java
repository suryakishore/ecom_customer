package com.customer.data.repository;

import com.customer.data.DataSource;
import com.customer.data.mapper.SuccessMapper;
import com.customer.domain.interactor.AddProductToCartUseCase;
import com.customer.domain.model.common.ImageData;
import com.customer.domain.model.pdp.PdpOfferData;
import com.customer.domain.repository.AddProductToCartRepository;
import com.customer.remote.http.model.request.AddProductToCartRequest;
import com.customer.remote.http.model.response.CommonModel;
import com.customer.remote.http.model.response.common.ImagesDetails;
import com.customer.remote.http.model.response.pdp.OfferName;
import com.customer.remote.http.model.response.pdp.PdpOfferDetails;
import com.data.cache.DatabaseManager;
import com.data.cache.entities.UserCart;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class AddProductToCartRepositoryImpl extends BaseRepository implements
    AddProductToCartRepository {
  private DataSource mDataSource;
  private SuccessMapper mMapper = new SuccessMapper();
  private DatabaseManager mDatabaseManager;

  @Inject
  public AddProductToCartRepositoryImpl(DataSource dataSource) {
    super(dataSource);
    mDataSource = dataSource;
    mDatabaseManager = dataSource.db();
  }

  @Override
  public Single<AddProductToCartUseCase.ResponseValues> addProductToCart(int userType,
      String centralProductId, final String productId, String unitId, final String storeId,
      final int quantity,
      int cartType, String notes, String userIp, String userPostCode, double latitude,
      double longitude, String city, int storeTypeId, final String productName,
      String estimatedProductPrice, String extraNotes, PdpOfferData offerData) {
    AddProductToCartRequest request;
    if (offerData != null) {
      PdpOfferDetails offerDetails = new PdpOfferDetails(offerData.getApplicableOnStatus(),
          convertToImageDetails(offerData.getImages()), new OfferName(offerData.getOfferName()),
          offerData.getEndDateTimeISO(), offerData.getEndDateTime(),
          offerData.getGlobalClaimCount(), offerData.getStartDateTimeISO(),
          convertToImageDetails(offerData.getWebimages()), offerData.getStartDateTime(),
          offerData.getPerUserLimit(), offerData.getMinimumPurchaseQty(),
          offerData.getStatusString(), offerData.getOfferId(), offerData.getDiscountType(),
          offerData.getOfferFor(), offerData.getDiscountValue(), offerData.getApplicableOn(),
          offerData.getStatus());
      request = new AddProductToCartRequest(userType,
          centralProductId, productId, unitId, storeId, quantity,
          cartType, notes, userIp, getHeader().getStoreCatId(), userPostCode, latitude,
          longitude, city, storeTypeId, productName,
          estimatedProductPrice, extraNotes, offerDetails);
    } else {
      request = new AddProductToCartRequest(userType,
          centralProductId, productId, unitId, storeId, quantity,
          cartType, notes, userIp, getHeader().getStoreCatId(), userPostCode, latitude,
          longitude, city, storeTypeId, productName,
          estimatedProductPrice, extraNotes);
    }
    return mDataSource.api().nodeApiHandler().addProductToCart(getHeader(), request).flatMap(
        new Function<CommonModel, SingleSource<? extends AddProductToCartUseCase.ResponseValues>>() {
          @Override
          public SingleSource<? extends AddProductToCartUseCase.ResponseValues> apply(
              CommonModel model) throws Exception {
            Thread t = new Thread() {
              public void run() {
                mDatabaseManager.cart().insertItemToCart(
                    new UserCart(productId, storeId, productName, quantity));
              }
            };
            t.start();
            return Single.just(new AddProductToCartUseCase.ResponseValues(mMapper.mapper(model)));
          }
        });
  }

  private ImagesDetails convertToImageDetails(ImageData imageData) {
    if (imageData != null) {
      return new ImagesDetails(imageData.getImageText(), imageData.getLarge(),
          imageData.getExtraLarge(), imageData.getSmall(), imageData.getAltText(),
          imageData.getMedium());
    }
    return null;
  }
}
