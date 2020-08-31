package com.customer.data.mapper;

import com.customer.data.utils.DataUtils;
import com.customer.domain.model.getratable.RatableAttributesData;
import com.customer.domain.model.getratable.RatableData;
import com.customer.domain.model.getratable.RatableProductData;
import com.customer.domain.model.getratable.SellerData;
import com.customer.domain.model.getratable.UserReviewData;
import com.customer.remote.http.model.response.getratable.RatableDetails;
import com.customer.remote.http.model.response.getratable.RatableProducts;
import com.customer.remote.http.model.response.getratable.RatedAttributes;
import com.customer.remote.http.model.response.getratable.SellerDetails;
import com.customer.remote.http.model.response.getratable.UserReview;
import java.util.ArrayList;

public class GetRatableProductMapper {
  public RatableData mapper(RatableDetails details) {
    return new RatableData(convertToRatableProductData(details.getReviewData()),
        convertToSellerData(details.getSellerData()),
        details.getMessage());
  }

  private ArrayList<RatableProductData> convertToRatableProductData(
      ArrayList<RatableProducts> productsList) {
    ArrayList<RatableProductData> productData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(productsList)) {
      for (RatableProducts productsData : productsList) {
        RatableProductData data = new RatableProductData(productsData.getProductName(),
            productsData.getProductId(), productsData.getImage(),
            convertToUserReviewData(productsData.getUserReview()), productsData.getOrder());
        productData.add(data);
      }
    }
    return productData;
  }

  private ArrayList<RatableAttributesData> convertToRatableAttributesData(
      ArrayList<RatedAttributes> attributesList) {
    ArrayList<RatableAttributesData> attributesData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(attributesList)) {
      for (RatedAttributes attributes : attributesList) {
        attributesData.add(new RatableAttributesData(attributes.getAttributeName(),
            attributes.getAttributeId(), attributes.getAttributeRating()));
      }
    }
    return attributesData;
  }

  private UserReviewData convertToUserReviewData(
      UserReview userReview) {
    UserReviewData userReviewData = null;
    if (userReview != null) {
      userReviewData = new UserReviewData(userReview.getReviewDescription(),
          userReview.getRating(), userReview.getProductName(), userReview.getReviewTitle(),
          userReview.getImage(), convertToRatableAttributesData(userReview.getAttribute()));
    }
    return userReviewData;
  }

  private SellerData convertToSellerData(SellerDetails sellerDetails) {
    SellerData sellerData = null;
    if (sellerDetails != null) {
      sellerData = new SellerData(sellerDetails.getReviewDescription(),
          sellerDetails.getRating(), sellerDetails.getStoreName(),
          convertToRatableAttributesData(sellerDetails.getAttribute()),
          sellerDetails.getStoreId(), sellerDetails.getReviewTitle(),sellerDetails.getSellerReview());
    }
    return sellerData;
  }
}
