package com.customer.data.mapper;

import static com.customer.data.utils.DataConstants.CATEGORY_TYPE;

import android.util.Log;
import com.customer.data.utils.DataUtils;
import com.customer.domain.model.common.FinalPriceListData;
import com.customer.domain.model.common.ImageData;
import com.customer.domain.model.common.OffersListData;
import com.customer.domain.model.common.ProductsData;
import com.customer.domain.model.common.SellerData;
import com.customer.domain.model.getaddress.AddressListItemData;
import com.customer.domain.model.getcart.CartPickupAddressData;
import com.customer.domain.model.getcart.CartTaxData;
import com.customer.domain.model.getcart.QuantityData;
import com.customer.domain.model.home.CategoryData;
import com.customer.domain.model.home.CategoryOfferData;
import com.customer.domain.model.home.HomeOfferData;
import com.customer.domain.model.home.ListData;
import com.customer.domain.model.orderhistory.OrderHistAttributeData;
import com.customer.domain.model.orderhistory.OrderHistOfferData;
import com.customer.domain.model.orderhistory.OrderHistPackagingData;
import com.customer.domain.model.orderhistory.OrderHistProductData;
import com.customer.domain.model.orderhistory.OrderHistRatingData;
import com.customer.domain.model.orderhistory.OrderHistShippingData;
import com.customer.domain.model.orderhistory.OrderHistoryAccountingData;
import com.customer.domain.model.orderhistory.OrderHistoryCustomerData;
import com.customer.domain.model.orderhistory.OrderHistoryPlanData;
import com.customer.domain.model.orderhistory.OrderStatusData;
import com.customer.domain.model.orderhistory.OrderTimeStampData;
import com.customer.domain.model.orderhistory.PayByData;
import com.customer.domain.model.orderhistory.SingleUnitPriceData;
import com.customer.domain.model.orderhistory.StoreLogoData;
import com.customer.domain.model.pdp.ColorsData;
import com.customer.domain.model.pdp.ReviewParameterData;
import com.customer.domain.model.pdp.SizeData;
import com.customer.domain.model.pdp.SupplierData;
import com.customer.remote.http.model.response.common.BasicSizeDetails;
import com.customer.remote.http.model.response.common.FinalPriceListDetails;
import com.customer.remote.http.model.response.common.ImagesDetails;
import com.customer.remote.http.model.response.common.OffersDataDetails;
import com.customer.remote.http.model.response.common.ProductsDetails;
import com.customer.remote.http.model.response.getaddress.AddressListItemDetails;
import com.customer.remote.http.model.response.getcart.CartPickupAddressDetails;
import com.customer.remote.http.model.response.getcart.CartTaxDetails;
import com.customer.remote.http.model.response.getcart.QuantityDetails;
import com.customer.remote.http.model.response.newHome.CatOfferDetails;
import com.customer.remote.http.model.response.newHome.CategoryDetails;
import com.customer.remote.http.model.response.newHome.HomeOfferDetails;
import com.customer.remote.http.model.response.newHome.ListDetails;
import com.customer.remote.http.model.response.orderhistory.OrderHistAttributeDetails;
import com.customer.remote.http.model.response.orderhistory.OrderHistOfferDetails;
import com.customer.remote.http.model.response.orderhistory.OrderHistPackagingDetails;
import com.customer.remote.http.model.response.orderhistory.OrderHistProductDetails;
import com.customer.remote.http.model.response.orderhistory.OrderHistRatingDetails;
import com.customer.remote.http.model.response.orderhistory.OrderHistShippingDetails;
import com.customer.remote.http.model.response.orderhistory.OrderHistoryAccounting;
import com.customer.remote.http.model.response.orderhistory.OrderHistoryCustomerDetails;
import com.customer.remote.http.model.response.orderhistory.OrderHistoryPlanDetails;
import com.customer.remote.http.model.response.orderhistory.OrderStatusDetails;
import com.customer.remote.http.model.response.orderhistory.OrderTimeStampDetails;
import com.customer.remote.http.model.response.orderhistory.PayByDetails;
import com.customer.remote.http.model.response.orderhistory.SingleUnitPriceDetails;
import com.customer.remote.http.model.response.orderhistory.StoreLogoDetails;
import com.customer.remote.http.model.response.pdp.ColorsDetails;
import com.customer.remote.http.model.response.pdp.ReviewParametersDetails;
import com.customer.remote.http.model.response.pdp.Supplier;
import com.customer.remote.http.model.response.pdp.SupplierDetails;
import java.util.ArrayList;

class CommonMapper {
  static ArrayList<ImageData> convertToImageDataList(ArrayList<ImagesDetails> imageDetList) {
    ArrayList<ImageData> imagesData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(imageDetList)) {
      for (ImagesDetails det : imageDetList) {
        ImageData data = new ImageData(det.getImageText(), det.getImage(), det.getThumbnail(),
            det.getMobile(), det.getDescription(), det.getTitle(), det.getKeyword());
        imagesData.add(data);
      }
    }
    return imagesData;
  }

  static ImageData convertToImagesData(ImagesDetails det) {
    if (det != null) {
      return new ImageData(det.getImageText(), det.getImage(), det.getThumbnail(),
          det.getMobile(), det.getDescription(), det.getTitle(), det.getKeyword());
    } else {
      return new ImageData();
    }
  }

  static SupplierData convertSupplierData(Supplier supplier) {
    SupplierData supplierData = null;
    if (supplier != null) {
      supplierData = new SupplierData(supplier.getProductId(),
          supplier.getRetailerQty(), supplier.getRetailerPrice(), supplier.getCurrencySymbol(),
          supplier.getDistributorPrice(), supplier.getCurrency(),
          supplier.getId(), supplier.getDistributorQty(), supplier.getSupplierName(),
          supplier.getRating(), supplier.getTotalRating(), getSellerData(supplier.getSupplier()),
          getFinalPriceListData(supplier.getFinalPriceList()),
          getReviewParamDataList(supplier.getReviewParameter()), supplier.getSellerSince());
    }
    return supplierData;
  }

  static FinalPriceListData convertToFinalPriceData(FinalPriceListDetails details) {
    FinalPriceListData priceListData = null;
    if (details != null) {
      priceListData = new FinalPriceListData(details.getDiscountPrice(),
          details.getFinalPrice(), details.getBasePrice(),
          details.getDiscountPercentage(), details.getMou());
    }
    return priceListData;
  }

  static ArrayList<ProductsData> convertToProductData(ArrayList<ProductsDetails> detailsList) {
    ArrayList<ProductsData> dataList = new ArrayList<>();
    if (!DataUtils.isEmptyArray(detailsList)) {
      for (ProductsDetails details : detailsList) {
        OffersListData offersData = null;
        if (details.getOffers() != null) {
          OffersDataDetails offersDetails = details.getOffers();
          ImageData imageData = null;
          if (offersDetails.getImagesDetails() != null) {
            imageData = new ImageData(offersDetails.getImagesDetails().getImageText(),
                offersDetails.getImagesDetails().getImage(),
                offersDetails.getImagesDetails().getThumbnail(),
                offersDetails.getImagesDetails().getMobile(),
                offersDetails.getImagesDetails().getDescription(),
                offersDetails.getImagesDetails().getTitle(),
                offersDetails.getImagesDetails().getKeyword());
          }
          offersData = new OffersListData(imageData, details.getOffers().getOffername(),
              details.getOffers().getOfferId(), details.getOffers().getDiscountValue());
        }
        Supplier supplier = details.getSuppliers();
        SupplierData supplierData = null;
        if (supplier != null) {
          supplierData = new SupplierData(supplier.getProductId(), supplier.getRetailerQty(),
              supplier.getRetailerPrice(), supplier.getCurrencySymbol(),
              supplier.getDistributorPrice(),
              supplier.getCurrency(), supplier.getId(), supplier.getDistributorQty(),
              supplier.getSupplierName(), supplier.getRating(), supplier.getTotalRating(),
              getSellerData(supplier.getSupplier()),
              getFinalPriceListData(supplier.getFinalPriceList()),
              getReviewParamDataList(supplier.getReviewParameter()), supplier.getSellerSince());
        }
        FinalPriceListData priceListData = null;
        if (details.getFinalPriceList() != null) {
          priceListData = new FinalPriceListData(
              details.getFinalPriceList().getDiscountPrice(),
              details.getFinalPriceList().getFinalPrice(),
              details.getFinalPriceList().getBasePrice(),
              details.getFinalPriceList().getDiscountPercentage(),
              details.getFinalPriceList().getMou());
        }
        ProductsData data = new ProductsData(offersData, details.getAvailableQuantity(),
            details.getBrandName(), convertToImageData(details.getImages()),
            details.getParentProductId(),
            supplierData, details.getStores(), details.getChildProductId(), details.getStoreCount(),
            details.getDiscountPrice(), details.getCurrencySymbol(), details.getTotalStarRating(),
            convertToColorData(details.getColors()), details.getProductName(), details.getMou(),
            convertToMiniSizeData(details.getSizes()), details.getMinimumPurchaseUnit(),
            details.getOutOfStock(),
            details.getUnitId(), details.getDiscountType(), details.getCurrency(),
            details.getMouUnit(),
            priceListData, details.getAvgRating());
        dataList.add(data);
      }
    }
    return dataList;
  }

  private static ArrayList<ColorsData> convertToColorData(ArrayList<ColorsDetails> colorsDetList) {
    ArrayList<ColorsData> colorsData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(colorsDetList)) {
      for (ColorsDetails details : colorsDetList) {
        ColorsData data = new ColorsData(details.getImage(), details.getIsPrimary(),
            details.getChildProductId(), details.getName(), details.getRgb());
        colorsData.add(data);
      }
    }
    return colorsData;
  }

  private static FinalPriceListData getFinalPriceListData(
      FinalPriceListDetails finalPriceListDetails) {
    FinalPriceListData finalPriceListData = null;
    if (finalPriceListDetails != null) {
      finalPriceListData = new FinalPriceListData(finalPriceListDetails.getDiscountPrice(),
          finalPriceListDetails.getFinalPrice(), finalPriceListDetails.getBasePrice(),
          finalPriceListDetails.getDiscountPrice(), finalPriceListDetails.getMou());
    }
    return finalPriceListData;
  }

  private static ArrayList<ReviewParameterData> getReviewParamDataList(
      ArrayList<ReviewParametersDetails> reviewParametersDetails) {
    ArrayList<ReviewParameterData> reviewParameterData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(reviewParametersDetails)) {
      for (ReviewParametersDetails details : reviewParametersDetails) {
        ReviewParameterData data = new ReviewParameterData(details.getAttributeId(),
            details.getName(),
            details.getRating(), details.getTotalStarRating());
        reviewParameterData.add(data);
      }
    }
    return reviewParameterData;
  }

  private static SellerData getSellerData(SupplierDetails supplierDetails) {
    SellerData sellerData = null;
    if (supplierDetails != null) {
      sellerData = new SellerData(supplierDetails.getRetailerPrice(),
          supplierDetails.getSupplierName(), supplierDetails.getRating(),
          supplierDetails.getCurrency(), supplierDetails.getProductId());
    }
    return sellerData;
  }

  private static ArrayList<SizeData> convertToMiniSizeData(ArrayList<BasicSizeDetails> sizeList) {
    ArrayList<SizeData> sizeData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(sizeList)) {
      for (BasicSizeDetails details : sizeList) {
        sizeData.add(
            new SizeData(details.getSize(), details.isPrimary(), details.getChildProductId()));
      }
    }
    return sizeData;
  }

  static ArrayList<ImageData> convertToImageData(ArrayList<ImagesDetails> imageDetList) {
    ArrayList<ImageData> imagesData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(imageDetList)) {
      for (ImagesDetails det : imageDetList) {
        imagesData.add(convertToImage(det));
      }
    }
    return imagesData;
  }

  public static ImageData convertToImage(ImagesDetails details) {
    ImageData data = null;
    if (details != null) {
      data = new ImageData(details.getLarge(), details.getExtraLarge(), details.getSmall(),
          details.getAltText(), details.getMedium());
    }
    return data;
  }

  public static ListData convertToListData(ListDetails details) {
    ListData data = new ListData(convertToHomeOfferData(details.getOffers()),
        details.getPenCount(), details.getBannerImageUrl(),
        details.getCatName(), details.getImageUrl(),
        convertToCategoryData(details.getCategoryData(), details.getType()),
        details.getId(), details.getType()
    );
    return data;
  }

  private static ArrayList<HomeOfferData> convertToHomeOfferData(
      ArrayList<HomeOfferDetails> offerDetailsList) {
    ArrayList<HomeOfferData> offerData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(offerDetailsList)) {
      for (HomeOfferDetails offerDetails : offerDetailsList) {
        HomeOfferData data = new HomeOfferData(offerDetails.getDiscountValue(),
            offerDetails.getStartDateTimeISO(), offerDetails.getOfferName(),
            offerDetails.getMobimage(), offerDetails.getOfferId());
        offerData.add(data);
      }
    }
    return offerData;
  }

  private static ArrayList<CategoryData> convertToCategoryData(
      ArrayList<CategoryDetails> categoryDetailsList, int type) {
    ArrayList<CategoryData> categoryData = new ArrayList<>();
    if (type == CATEGORY_TYPE) {
      CategoryData data = new CategoryData("All Categories");
      categoryData.add(data);
    }
    if (!DataUtils.isEmptyArray(categoryDetailsList)) {
      for (CategoryDetails details : categoryDetailsList) {
        FinalPriceListData finalPriceListData = CommonMapper.convertToFinalPriceData(
            details.getFinalPriceList());
        SupplierData supplierData = null;
        if (details.getSupplier() != null) {
          Supplier supplier = details.getSupplier();
          supplierData = new SupplierData(supplier.getProductId(),
              supplier.getRetailerQty(), supplier.getRetailerPrice(),
              supplier.getCurrencySymbol(),
              supplier.getDistributorPrice(), supplier.getCurrency(),
              details.getSupplier().getId(), details.getSupplier().getDistributorQty(),
              details.getSupplier().getSellerSince());
        }
        CategoryData data = new CategoryData(details.getSubCategoryName(), details.getPenCount(),
            details.getProductCount(), details.getId(), details.getImageUrl(),
            details.getLogo(), details.getName(), details.getBannerImage(),
            details.getOfferdescription(), details.getCurrencySymbol(), details.getCurrency(),
            details.getOfferId(), details.getOffername(), details.getCatName(),
            details.getBannerImageUrl(),
            CommonMapper.convertToImageData(details.getImages()),
            details.getDiscountType(), details.getParentProductId(),
            details.getAvailableQuantity(), details.getChildProductId(),
            details.getDiscountPrice(), convertToCatOfferData(details.getOffers()),
            supplierData,
            details.getUnitId(),
            details.isOutOfStock(), finalPriceListData, details.getProductName(),
            details.getBrandName(), details.getImageMobile(), details.getSubCatName(),
            details.getType(), details.getTotalStarRating(), details.getOfferName());
        categoryData.add(data);
      }
    }
    return categoryData;
  }

  private static CategoryOfferData convertToCatOfferData(CatOfferDetails catOfferDetails) {
    CategoryOfferData offerData = null;
    if (catOfferDetails != null) {
      ImageData data = null;
      if (catOfferDetails.getImages() != null) {
        data = new ImageData(catOfferDetails.getImages().getImageText(),
            catOfferDetails.getImages().getImage(),
            catOfferDetails.getImages().getThumbnail(),
            catOfferDetails.getImages().getMobile(),
            catOfferDetails.getImages().getDescription(),
            catOfferDetails.getImages().getTitle(),
            catOfferDetails.getImages().getKeyword());
      }
      offerData = new CategoryOfferData(catOfferDetails.getStartDateTimeISO(),
          catOfferDetails.getApplicableOnStatus(), data,
          catOfferDetails.getOfferName() != null ? catOfferDetails.getOfferName().getEn() : "",
          catOfferDetails.getMobimage(),
          catOfferDetails.getDiscountValue(), catOfferDetails.getEndDateTimeISO(),
          catOfferDetails.getDiscountType());
    }
    return offerData;
  }

  public static ArrayList<SupplierData> convertToSupplierDataList(ArrayList<Supplier> suppliers) {
    ArrayList<SupplierData> supplierData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(suppliers)) {
      for (Supplier supplier : suppliers) {
        supplierData.add(convertSupplierData(supplier));
      }
    }
    return supplierData;
  }

  public static AddressListItemData convertToAddressData(AddressListItemDetails details) {
    if (details != null) {
      AddressListItemData itemData = new AddressListItemData(details.getMobileNumberCode(),
          details.getCountry(), details.getPincode(), details.getCity(),
          details.getMobileNumber(),
          details.getFlatNumber(), details.getLatitude(), details.getAlternatePhoneCode(),
          details.getAlternatePhone(), details.getLocality(),
          details.getPlaceId(), details.getCreatedTimeStamp(),
          details.getUserId(), details.getCreatedIsoDate(), details.getName(),
          details.getAddLine1(), details.get_id(), details.getAddLine2(),
          details.getState(), details.getUserType(), details.getLandmark(), details.getTaggedAs(),
          details.getLongitude(), details.isDefaultAd(), details.getTagged(),details.getCityId(),details.getCountryId());
      return itemData;
    } else {
      return null;
    }
  }

  public static CartPickupAddressData convertToPickUpAddressData(
      CartPickupAddressDetails addressDetails) {
    CartPickupAddressData cartPickupAddressData = null;
    if (addressDetails != null) {
      cartPickupAddressData = new CartPickupAddressData(addressDetails.getCountry(),
          addressDetails.getAddress(), addressDetails.getLocality(),
          addressDetails.getCityId(),
          addressDetails.getSublocality_level_2(), addressDetails.getSublocality_level_1(),
          addressDetails.getRoute(), addressDetails.getAdministrative_area_level_2(),
          addressDetails.getCityName(), addressDetails.getAdministrative_area_level_1(),
          addressDetails.getPostal_code());
    }
    return cartPickupAddressData;
  }

  static ArrayList<CartTaxData> convertCartTaxDataList(
      ArrayList<CartTaxDetails> cartTaxDetails) {
    ArrayList<CartTaxData> cartTaxData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(cartTaxDetails)) {
      for (CartTaxDetails details : cartTaxDetails) {
        Log.d("exe", "details" + details.getTaxName());
        cartTaxData.add(convertToTaxData(details));
      }
    }
    return cartTaxData;
  }

  private static CartTaxData convertToTaxData(CartTaxDetails cartTaxDetails) {
    CartTaxData cartTaxData = null;
    if (cartTaxDetails != null) {
      cartTaxData = new CartTaxData(cartTaxDetails.getTotalValue(),
          cartTaxDetails.getTaxValue(), cartTaxDetails.getTaxId(), cartTaxDetails.getTaxName());
    }
    return cartTaxData;
  }

  static OrderTimeStampData convertToTimeStampData(OrderTimeStampDetails details) {
    OrderTimeStampData timeStampData = null;
    if (details != null) {
      timeStampData = new OrderTimeStampData(details.getInDispatch(),
          details.getCreated(), details.getReadyForPickup(), details.getAccepted(),
          details.getCancelled(), details.getCompleted(), details.getPacked());
    }
    return timeStampData;
  }

  static OrderHistoryAccountingData convertToAccountingData(OrderHistoryAccounting accounting) {
    OrderHistoryAccountingData accountingData = null;
    if (accounting != null) {
      accountingData = new OrderHistoryAccountingData(accounting.getUnitPrice(),
          accounting.getTaxableAmount(), accounting.getFinalUnitPrice(),
          accounting.getAppEarningWithTax(), convertToPaidByData(accounting.getPayBy()),
          accounting.getCurrencySymbol(), accounting.getAddOnsAmount(),
          CommonMapper.convertCartTaxDataList(accounting.getTax()),
          accounting.getSubTotal(), accounting.getStoreEarning(),
          accounting.getOfferDiscount(), accounting.getDeliveryFee(), accounting.getTaxAmount(),
          accounting.getPromoDiscount(), accounting.getCurrencyCode(), accounting.getFinalTotal(),
          accounting.getAppEarning());
    }
    return accountingData;
  }

  private static PayByData convertToPaidByData(PayByDetails payByDetails) {
    PayByData payByData = null;
    if (payByDetails != null) {
      payByData = new PayByData(payByDetails.getWallet(), payByDetails.getCash(),
          payByDetails.getCard());
    }
    return payByData;
  }

  static ArrayList<OrderHistProductData> convertToOrderHistProductData(
      ArrayList<OrderHistProductDetails> productDetails) {
    ArrayList<OrderHistProductData> productData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(productDetails)) {
      for (OrderHistProductDetails details : productDetails) {
        OrderHistProductData histProductData = new OrderHistProductData(
            details.getUpcNumber(), details.getColor(),
            CommonMapper.convertToTimeStampData(details.getTimestamps()),
            convertToUnitPriceData(details.getSingleUnitPrice()),
            convertToOfferData(details.getOfferDetails()),
            details.getProductOrderId(),
            CommonMapper.convertToAccountingData(details.getAccounting()),
            details.getAisle(), details.getPackageType(), details.getCentralProductId(),
            details.getAllowOrderOutOfStock(),
            convertToOrderShippingData(details.getShippingDetails()),
            details.getUnitId(), details.getProductDeliveryFee(), null,
            CommonMapper.convertToImage(details.getImages()), details.getBrandName(),
            convertToQuantityData(details.getQuantity()), details.getProductId(),
            details.getAddOns(), details.getPackageId(), details.getCurrencySymbol(),
            convertToPackageData(details.getPackaging()), details.getShelf(),
            details.getIsCentral(), details.getDirections(), details.getName(),
            convertToRatingData(details.getRattingData()),
            convertToAttributeDataList(details.getAttributes()),
            details.getCurrencyCode(), convertToStatusData(details.getStatus()),
            details.getInvoiceLink(), details.getReason(), details.getSplitProduct());
        productData.add(histProductData);
      }
    }
    return productData;
  }

  static OrderStatusData convertToStatusData(OrderStatusDetails statusDetails) {
    OrderStatusData orderStatusData = null;
    if (statusDetails != null) {
      orderStatusData = new OrderStatusData(statusDetails.getStatusText(),
          statusDetails.getUpdatedOnTimeStamp(), statusDetails.getUpdatedOn(),
          statusDetails.getStatus());
    }
    return orderStatusData;
  }

  private static ArrayList<OrderHistAttributeData> convertToAttributeDataList(
      ArrayList<OrderHistAttributeDetails> attributeDetails) {
    ArrayList<OrderHistAttributeData> orderHistAttributeData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(attributeDetails)) {
      for (OrderHistAttributeDetails details : attributeDetails) {
        OrderHistAttributeData attributeData = new OrderHistAttributeData(
            details.getRateable(), details.getSearchable(),
            details.getAttriubteType(), details.getLinkedtounit(),
            details.getAttributeDataType(), details.getDropdownSelectType(),
            details.getAttributeId(),
            details.getValue(), details.getMeasurementUnit(), details.getAttrname());
        orderHistAttributeData.add(attributeData);
      }
    }
    return orderHistAttributeData;
  }

  private static SingleUnitPriceData convertToUnitPriceData(SingleUnitPriceDetails details) {
    SingleUnitPriceData singleUnitPriceData = null;
    if (details != null) {
      singleUnitPriceData = new SingleUnitPriceData(details.getUnitPrice(),
          details.getOfferDiscount(), details.getTaxableAmount(), details.getFinalUnitPrice(),
          details.getAddOnsAmount(), CommonMapper.convertCartTaxDataList(details.getTax()),
          details.getSubTotal(),
          details.getTaxAmount());
    }
    return singleUnitPriceData;
  }

  private static OrderHistOfferData convertToOfferData(OrderHistOfferDetails offerDetails) {
    OrderHistOfferData orderHistOfferData = null;
    if (offerDetails != null) {
      orderHistOfferData = new OrderHistOfferData(offerDetails.getOfferTitle(),
          offerDetails.getOfferId(), offerDetails.getOfferType(),
          offerDetails.getOfferValue());
    }
    return orderHistOfferData;
  }

  private static OrderHistShippingData convertToOrderShippingData(
      OrderHistShippingDetails shippingDetails) {
    OrderHistShippingData shippingData = null;
    if (shippingDetails != null) {
      shippingData = new OrderHistShippingData(shippingDetails.getName(),
          shippingDetails.getId(), shippingDetails.getTrackingId());
    }
    return shippingData;
  }

  private static QuantityData convertToQuantityData(QuantityDetails quantityDetails) {
    Log.d("exe", "quantityDetails" + quantityDetails.getUnit());
    QuantityData quantityData = null;
    if (quantityDetails != null) {
      quantityData = new QuantityData(quantityDetails.getUnit(), quantityDetails.getValue());
    }
    return quantityData;
  }

  private static OrderHistPackagingData convertToPackageData(
      OrderHistPackagingDetails packagingDetails) {
    OrderHistPackagingData orderHistPackagingData = null;
    if (packagingDetails != null) {
      orderHistPackagingData = new OrderHistPackagingData(packagingDetails.getUnitType(),
          packagingDetails.getPackingType());
    }
    return orderHistPackagingData;
  }

  private static OrderHistRatingData convertToRatingData(OrderHistRatingDetails ratingDetails) {
    OrderHistRatingData orderHistRatingData = null;
    if (ratingDetails != null) {
      orderHistRatingData = new OrderHistRatingData(ratingDetails.getReviewDescription(),
          ratingDetails.isRated(), ratingDetails.getRating(), ratingDetails.getReviewTitle());
    }
    return orderHistRatingData;
  }

  static OrderHistoryCustomerData convertCustomerData(
      OrderHistoryCustomerDetails details) {
    OrderHistoryCustomerData customerData = null;
    if (details != null) {
      customerData = new OrderHistoryCustomerData(details.getFirstName(),
          details.getLastName(), details.getCountryCode(),
          details.getUserTypeText(), details.getMobile(), details.getId(), details.getUserType(),
          details.getMqttTopic(), details.getFcmTopic(), details.getEmail());
    }
    return customerData;
  }

  static StoreLogoData convertToStoreLogoData(StoreLogoDetails logoDetails) {
    StoreLogoData storeLogoData = null;
    if (logoDetails != null) {
      storeLogoData = new StoreLogoData(logoDetails.getLogoImageThumb(),
          logoDetails.getLogoImageMobile());
    }
    return storeLogoData;
  }

  static OrderHistoryPlanData convertToPlanData(OrderHistoryPlanDetails planDetails) {
    OrderHistoryPlanData historyPlanData = null;
    if (planDetails != null) {
      historyPlanData = new OrderHistoryPlanData(planDetails.getAppCommissionTypeText(),
          planDetails.getAppCommission(), planDetails.getName(),
          planDetails.getAppCommissionType());
    }
    return historyPlanData;
  }
}
