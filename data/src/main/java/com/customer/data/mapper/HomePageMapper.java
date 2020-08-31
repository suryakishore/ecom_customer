package com.customer.data.mapper;

import static com.customer.data.utils.DataConstants.LOWER_BOUND;
import static com.customer.data.utils.DataConstants.SUB_PRODUCT_LIST_HORIZONTAL;

import com.customer.data.utils.DataUtils;
import com.customer.domain.model.home.CategoryData;
import com.customer.domain.model.home.CategoryOfferData;
import com.customer.domain.model.home.HomeListData;
import com.customer.domain.model.home.ListData;
import com.customer.remote.http.model.response.homeSubCategory.CategoryList;
import com.customer.remote.http.model.response.homeSubCategory.HomeCatDetails;
import com.customer.remote.http.model.response.homeSubCategory.ProductOfferDetails;
import com.customer.remote.http.model.response.homeSubCategory.SubCategoryProductDetails;
import com.customer.remote.http.model.response.newHome.HomeListDetails;
import com.customer.remote.http.model.response.newHome.ListDetails;
import java.util.ArrayList;

public class HomePageMapper {
  public HomeListData mapper(HomeListDetails homeListDetails) {
    return new HomeListData(convertToListData(homeListDetails.getList()),
        homeListDetails.getTotalCatCount());
  }

  public HomeListData secondApiMapper(HomeCatDetails homeCatDetails) {

    return new HomeListData(convertToSecApiList(homeCatDetails.getCategoryData()));
  }

  private ArrayList<ListData> convertToSecApiList(ArrayList<CategoryList> categoryData) {

    ArrayList<ListData> listData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(categoryData)) {

      for (CategoryList categoryList : categoryData) {
        ListData data = new ListData(categoryList.getCatName(), categoryList.getSubCategoryName(),
            convertToSecApiCategoryData(categoryList.getSubCategory()),
            SUB_PRODUCT_LIST_HORIZONTAL);
        listData.add(data);
      }

    }
    return listData;
  }

  private ArrayList<CategoryData> convertToSecApiCategoryData(
      ArrayList<SubCategoryProductDetails> productDetails) {

    ArrayList<CategoryData> categoryDataList = new ArrayList<>();

    if (!DataUtils.isEmptyArray(productDetails)) {

      for (SubCategoryProductDetails details : productDetails) {
        CategoryOfferData offer = null;
        if (details.getOffers() != null) {
          ProductOfferDetails offerDetails = details.getOffers();
          offer = new CategoryOfferData(offerDetails.getStartDateTimeISO(),
              offerDetails.getApplicableOnStatus(),
              CommonMapper.convertToImage(offerDetails.getImages()),
              offerDetails.getOfferName() != null ? offerDetails.getOfferName().getEn() : "",
              offerDetails.getDiscountValue() + "",
              offerDetails.getEndDateTimeISO(), offerDetails.getDiscountType(),
              offerDetails.getEndDateTime(),
              offerDetails.getGlobalClaimCount(),
              offerDetails.getStartDateTime(), offerDetails.getMinimumPurchaseQty(),
              offerDetails.getPerUserLimit(),
              offerDetails.getStatusString(), offerDetails.getOfferId(), offerDetails.getOfferFor(),
              offerDetails.getApplicableOn(), offerDetails.getStatus());
        }
        CategoryData categoryData = new CategoryData(offer,
            details.getAvailableQuantity(),
            details.getBrandName(),
            CommonMapper.convertToImageData(details.getImages()),
            details.getParentProductId(), details.getChildProductId(),
            details.getCurrencySymbol(), details.getTotalStarRating(),
            details.getProductName(), CommonMapper.convertSupplierData(details.getSupplier()),
            details.getOutOfStock(), details.getUnitId(),
            details.getCurrency(), details.getDiscountType(),
            CommonMapper.convertToFinalPriceData(details.getFinalPriceList()));
        categoryDataList.add(categoryData);
      }
    }
    return categoryDataList;
  }

  private ArrayList<ListData> convertToListData(ArrayList<ListDetails> list) {

    ArrayList<ListData> listData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(list)) {
      for (ListDetails details : list) {

        ListData data = CommonMapper.convertToListData(details);

        if (data.getCategoryData() != null && data.getCategoryData().size() > LOWER_BOUND
            || data.getOffers() != null && data.getOffers().size() > LOWER_BOUND) {
          listData.add(data);
        }
      }
    }
    return listData;
  }
}
