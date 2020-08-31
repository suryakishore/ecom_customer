package com.customer.data.mapper;

import com.customer.data.utils.DataUtils;
import com.customer.domain.model.homesubcategory.HomeCatData;
import com.customer.domain.model.homesubcategory.HomeSubCategoryData;
import com.customer.domain.model.homesubcategory.ProductOfferData;
import com.customer.domain.model.homesubcategory.SubCategoryProductData;
import com.customer.remote.http.model.response.homeSubCategory.CategoryList;
import com.customer.remote.http.model.response.homeSubCategory.HomeCatDetails;
import com.customer.remote.http.model.response.homeSubCategory.SubCategoryProductDetails;
import java.util.ArrayList;

public class HomeSubCategoryMapper {

  public HomeCatData mapper(HomeCatDetails details) {

    return new HomeCatData(convertToSubCatData(details.getData()), details.getMessage());
  }

  private ArrayList<HomeSubCategoryData> convertToSubCatData(
      ArrayList<CategoryList> subCategoryDetails) {
    ArrayList<HomeSubCategoryData> dataList = new ArrayList<>();
    if (!DataUtils.isEmptyArray(subCategoryDetails)) {
      for (CategoryList details : subCategoryDetails) {
        HomeSubCategoryData data = new HomeSubCategoryData(
            convertToProductData(details.getSubCategory()),
            details.getSubCategoryName(), details.getCatName());
        dataList.add(data);
      }
    }
    return dataList;
  }

  private ArrayList<SubCategoryProductData> convertToProductData(
      ArrayList<SubCategoryProductDetails> productDetailsList) {
    ArrayList<SubCategoryProductData> dataList = new ArrayList<>();

    if (!DataUtils.isEmptyArray(productDetailsList)) {
      for (SubCategoryProductDetails details : productDetailsList) {

        ProductOfferData offer = null;
        if (details.getOffers() != null) {
          offer = new ProductOfferData(details.getOffers().getApplicableOnStatus(),
              CommonMapper.convertToImagesData(details.getOffers().getImages()),
              details.getOffers().getOfferName() != null
                  ? details.getOffers().getOfferName().getEn() : "",
              details.getOffers().getEndDateTimeISO(), details.getOffers().getEndDateTime(),
              details.getOffers().getGlobalClaimCount(), details.getOffers().getStartDateTimeISO(),
              details.getOffers().getStartDateTime(), details.getOffers().getMinimumPurchaseQty(),
              details.getOffers().getPerUserLimit(),
              details.getOffers().getStatusString(), details.getOffers().getOfferId(),
              details.getOffers().getDiscountType(), details.getOffers().getOfferFor(),
              details.getOffers().getDiscountValue(),
              details.getOffers().getApplicableOn(), details.getOffers().getStatus()
          );
        }
        SubCategoryProductData data = new SubCategoryProductData(offer,
            details.getAvailableQuantity(),
            details.getBrandName(),
            CommonMapper.convertToImageData(details.getImages()),
            details.getParentProductId(), details.getChildProductId(),
            details.getCurrencySymbol(), details.getTotalStarRating(),
            details.getProductName(), CommonMapper.convertSupplierData(details.getSupplier()),
            details.getOutOfStock(), details.getUnitId(),
            details.getCurrency(), details.getDiscountType(),
            CommonMapper.convertToFinalPriceData(details.getFinalPriceList())
        );
        dataList.add(data);
      }
    }

    return dataList;
  }
}
