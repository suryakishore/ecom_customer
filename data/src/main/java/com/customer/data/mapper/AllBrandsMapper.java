package com.customer.data.mapper;

import com.customer.data.utils.DataUtils;
import com.customer.domain.model.brands.AllBrandsListData;
import com.customer.domain.model.home.CategoryData;
import com.customer.remote.http.model.response.brands.AllBrandsListDetails;
import com.customer.remote.http.model.response.newHome.CategoryDetails;
import java.util.ArrayList;

public class AllBrandsMapper {
  public AllBrandsListData mapper(AllBrandsListDetails categoryDetails) {
    return new AllBrandsListData(categoryDetails.getPenCount(), categoryDetails.getMessage(),
        convertToCategoryItemData(categoryDetails.getData())
    );
  }

  private ArrayList<CategoryData> convertToCategoryItemData(
      ArrayList<CategoryDetails> trackingItemDetailsArrayList) {
    ArrayList<CategoryData> categoryData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(trackingItemDetailsArrayList)) {
      for (CategoryDetails details : trackingItemDetailsArrayList) {
        CategoryData data = new CategoryData(details.getSubCategoryName(), details.getPenCount(),
            details.getProductCount(), details.getId(), details.getImageUrl(),
            details.getLogo(), details.getName(), details.getBannerImage(),
            details.getOfferdescription(), details.getCurrencySymbol(), details.getCurrency(),
            details.getOfferId(), details.getOffername(), details.getCatName(),
            details.getBannerImageUrl(),
            CommonMapper.convertToImageData(details.getImages()),
            details.getDiscountType(), details.getParentProductId(),
            details.getAvailableQuantity(), details.getChildProductId(),
            details.getDiscountPrice(), null,
            null,
            details.getUnitId(),
            details.isOutOfStock(), null, details.getProductName(),
            details.getBrandName(), details.getImageMobile(), details.getSubCatName(),
            details.getType(), details.getTotalStarRating(), details.getOfferName());
        categoryData.add(data);
      }
    }
    return categoryData;
  }
}
