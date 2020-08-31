package com.customer.data.mapper;

import static com.customer.data.utils.DataConstants.FIVE_STAR;
import static com.customer.data.utils.DataConstants.FOUR_STAR;
import static com.customer.data.utils.DataConstants.ONE_STAR;
import static com.customer.data.utils.DataConstants.THREE_STAR;
import static com.customer.data.utils.DataConstants.TWO_STAR;

import com.customer.data.utils.DataUtils;
import com.customer.domain.model.common.ImageData;
import com.customer.domain.model.pdp.AttributeRatingData;
import com.customer.domain.model.pdp.AttributesData;
import com.customer.domain.model.pdp.ColorsData;
import com.customer.domain.model.pdp.InnerAttributesData;
import com.customer.domain.model.pdp.LinkToUnitData;
import com.customer.domain.model.pdp.PdpOfferData;
import com.customer.domain.model.pdp.ProductData;
import com.customer.domain.model.pdp.ProductDataModel;
import com.customer.domain.model.pdp.ProductModel;
import com.customer.domain.model.pdp.Rating;
import com.customer.domain.model.pdp.ReviewData;
import com.customer.domain.model.pdp.SizeChartData;
import com.customer.domain.model.pdp.SizeData;
import com.customer.domain.model.pdp.UserReviewData;
import com.customer.domain.model.pdp.VariantsData;
import com.customer.remote.http.model.response.common.BasicSizeDetails;
import com.customer.remote.http.model.response.common.ImagesDetails;
import com.customer.remote.http.model.response.common.SizeChart;
import com.customer.remote.http.model.response.pdp.AttributeRating;
import com.customer.remote.http.model.response.pdp.AttributesDetails;
import com.customer.remote.http.model.response.pdp.ColorsDetails;
import com.customer.remote.http.model.response.pdp.InnerAttributesDetails;
import com.customer.remote.http.model.response.pdp.LinkToUnit;
import com.customer.remote.http.model.response.pdp.PdpOfferDetails;
import com.customer.remote.http.model.response.pdp.ProductDetails;
import com.customer.remote.http.model.response.pdp.ProductModelDetails;
import com.customer.remote.http.model.response.pdp.ReviewDetails;
import com.customer.remote.http.model.response.pdp.SizeDetails;
import com.customer.remote.http.model.response.pdp.UserReviewDetails;
import com.customer.remote.http.model.response.pdp.VariantDetails;
import java.util.ArrayList;

public class PdpMapper {
  public ProductDataModel map(ProductDetails productDetails) {
    ReviewDetails reviewDetails = productDetails.getReview();
    ReviewData reviewData = new ReviewData(
        reviewDetails.getFourStartRating(), reviewDetails.getPenCount(),
        reviewDetails.getTwoStarRating(),
        reviewDetails.getTotalNoOfRatings(), reviewDetails.getThreeStarRating(),
        reviewDetails.getTotalStarRating(), reviewDetails.getFiveStarRating(),
        reviewDetails.getOneStarRating(),
        reviewDetails.getTotalNoOfReviews(),
        convertToAttributeRatingData(reviewDetails.getAttributeRating()),
        convertToReviewData(reviewDetails.getUserReviews()), reviewDetails.getImages(),
        getReviewList(reviewDetails)
    );
    ProductData productData = null;
    if (productDetails.getProductData() != null) {
      productData = new ProductData(
          convertToProductModel(productDetails.getProductData().getData()),
          productDetails.getProductData().getMessage());
    }
    return new ProductDataModel(reviewData,
        productData);
  }

  private ArrayList<Rating> getReviewList(ReviewDetails reviewDetails) {
    ArrayList<Rating> reviews = new ArrayList<>();
    reviews.add(
        new Rating(FIVE_STAR, reviewDetails.getFiveStarRating(),
            reviewDetails.getTotalNoOfRatings()));
    reviews.add(
        new Rating(FOUR_STAR, reviewDetails.getFourStartRating(),
            reviewDetails.getTotalNoOfRatings()));
    reviews.add(
        new Rating(THREE_STAR, reviewDetails.getThreeStarRating(),
            reviewDetails.getTotalNoOfRatings()));
    reviews.add(
        new Rating(TWO_STAR, reviewDetails.getTwoStarRating(),
            reviewDetails.getTotalNoOfRatings()));
    reviews.add(
        new Rating(ONE_STAR, reviewDetails.getOneStarRating(),
            reviewDetails.getTotalNoOfRatings()));
    return reviews;
  }

  private ArrayList<UserReviewData> convertToReviewData(
      ArrayList<UserReviewDetails> reviewDetails) {
    ArrayList<UserReviewData> userReviewList = new ArrayList<>();
    if (!DataUtils.isEmptyArray(reviewDetails)) {
      for (UserReviewDetails userReviewDetails : reviewDetails) {
        UserReviewData userReviewData = new UserReviewData(userReviewDetails.getImages(),
            userReviewDetails.getRating(),
            userReviewDetails.getName(),
            userReviewDetails.getReviewDesc(), userReviewDetails.getReviewId(),
            userReviewDetails.getTimestamp(),
            userReviewDetails.getReviewTitle(), userReviewDetails.getLikes(),
            userReviewDetails.getDisLikes(), userReviewDetails.isUserLikes(),
            userReviewDetails.isUserdisLikes());
        userReviewList.add(userReviewData);
      }
    }
    return userReviewList;
  }

  private ArrayList<AttributeRatingData> convertToAttributeRatingData(
      ArrayList<AttributeRating> attributesData) {
    ArrayList<AttributeRatingData> userAttributesList = new ArrayList<>();
    if (!DataUtils.isEmptyArray(attributesData)) {
      for (AttributeRating userReviewDetails : attributesData) {
        AttributeRatingData userReviewData = new AttributeRatingData(
            userReviewDetails.getAttributeId(), userReviewDetails.getRating(),
            userReviewDetails.getAttributeName(),
            userReviewDetails.getTotalStarRating());
        userAttributesList.add(userReviewData);
      }
    }
    return userAttributesList;
  }

  private ArrayList<ProductModel> convertToProductModel(
      ArrayList<ProductModelDetails> productDataList) {
    ArrayList<ProductModel> productModels = new ArrayList<>();
    if (!DataUtils.isEmptyArray(productDataList)) {
      for (ProductModelDetails details : productDataList) {
        ProductModel model = new ProductModel(convertToVariantData(details.getVariants()),
            convertToOfferListData(details.getOffers()), convertToOfferData(details.getAllOffers()),
            details.getBrandName(), CommonMapper.convertToImageData(details.getImages()),
            details.getParentProductId(),
            details.getChildProductId(), details.getSubCatName(), details.getCurrencySymbol(),
            details.getProductName(), convertToColorData(details.getColors()),
            details.getSubSubCatName(),
            details.getDetailDesc(), details.getHighlight(),
            convertToMiniSizeData(details.getSizes()), details.getIsPrimary(),
            details.getCatName(), CommonMapper.convertSupplierData(details.getSupplier()),
            details.getUnitId(),
            details.getCurrency(), details.getShortDesc(),
            convertToAttributeData(details.getAttributes()),
            details.getIsFavourite(),
            CommonMapper.convertToFinalPriceData(details.getFinalPriceList()),
            convertToLinkToUnitData(details.getLinkToUnit()),
            details.getMouUnit(), details.getDiscountType(), details.isOutOfStock(),
            details.getMou(),
            details.getAvailableQuantity(), convertToSizeChartData(details.getSizeChart()),
            details.getSellerCount(), details.getShareLink());
        productModels.add(model);
      }
    }
    return productModels;
  }

  private ArrayList<VariantsData> convertToVariantData(ArrayList<VariantDetails> detailsList) {
    ArrayList<VariantsData> dataList = new ArrayList<>();
    if (!DataUtils.isEmptyArray(detailsList)) {
      for (VariantDetails details : detailsList) {
        VariantsData data = new VariantsData(details.getImage(), details.getChildProductId(),
            details.getIsPrimary(), details.getName(), details.getUnitId(), details.getRgb(),
            convertToMiniSizeData(details.getSizeData()));
        dataList.add(data);
      }
    }
    return dataList;
  }

  private ArrayList<SizeData> convertToMiniSizeData(ArrayList<BasicSizeDetails> sizeList) {
    ArrayList<SizeData> sizeData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(sizeList)) {
      for (BasicSizeDetails details : sizeList) {
        sizeData.add(new SizeData(details.getRgb(), details.getImage(),
            details.getSize(), details.isPrimary(), details.getChildProductId(),
            details.getName(), details.getUnitId()));
      }
    }
    return sizeData;
  }

  private ArrayList<LinkToUnitData> convertToLinkToUnitData(ArrayList<LinkToUnit> linkToUnitsList) {
    ArrayList<LinkToUnitData> unitData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(linkToUnitsList)) {
      for (LinkToUnit unit : linkToUnitsList) {
        LinkToUnitData toUnitData = new LinkToUnitData(unit.getImage(), unit.getChildProductId(),
            unit.getIsPrimary(), unit.getName(), unit.getUnitId(),
            convertToSizeData(unit.getSizeData()));
        unitData.add(toUnitData);
      }
    }
    return unitData;
  }

  private ArrayList<SizeChartData> convertToSizeChartData(ArrayList<SizeChart> sizeCharts) {
    ArrayList<SizeChartData> sizeChartData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(sizeCharts)) {
      for (SizeChart sizeChart : sizeCharts) {
        SizeChartData data = new SizeChartData(sizeChart.getSize(), sizeChart.getName());
        sizeChartData.add(data);
      }
    }
    return sizeChartData;
  }

  private ArrayList<SizeData> convertToSizeData(ArrayList<SizeDetails> sizeDetails) {
    ArrayList<SizeData> sizeData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(sizeDetails)) {
      for (SizeDetails details : sizeDetails) {
        SizeData sizeDat = new SizeData(details.getColourId(),
            details.getImage(), details.getSize(), details.getIsPrimary(),
            details.getChildProductId(), details.getName(), details.getUnitId());
        sizeData.add(sizeDat);
      }
    }
    return sizeData;
  }

  private ArrayList<AttributesData> convertToAttributeData(
      ArrayList<AttributesDetails> attributeList) {
    ArrayList<AttributesData> attributesData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(attributeList)) {
      for (AttributesDetails details : attributeList) {
        AttributesData data = new AttributesData(details.getName(),
            convertToInnerAttribute(details.getInnerAttributes()));
        attributesData.add(data);
      }
    }
    return attributesData;
  }

  private ArrayList<InnerAttributesData> convertToInnerAttribute(
      ArrayList<InnerAttributesDetails> innerAttributesList) {
    ArrayList<InnerAttributesData> dataArrayList = new ArrayList<>();
    if (!DataUtils.isEmptyArray(innerAttributesList)) {
      for (InnerAttributesDetails details : innerAttributesList) {
        InnerAttributesData data = new InnerAttributesData(details.getName(),
            details.getValue());
        dataArrayList.add(data);
      }
    }
    return dataArrayList;
  }

  private ArrayList<ColorsData> convertToColorData(ArrayList<ColorsDetails> colorsDetList) {
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

  private ArrayList<ImageData> convertToImageData(ArrayList<ImagesDetails> imageDetList) {
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

  private PdpOfferData convertToOfferListData(PdpOfferDetails details) {
    ImageData webImage = null;
    if (details.getWebimages() != null) {
      webImage = new ImageData(details.getWebimages().getImage(),
          details.getWebimages().getThumbnail(), details.getWebimages().getMobile());
    }
    ImageData image = null;
    if (details.getImages() != null) {
      image = new ImageData(details.getImages().getImage(), details.getImages().getThumbnail(),
          details.getImages().getMobile());
    }
    return new PdpOfferData(details.getApplicableOnStatus(), image,
        details.getOfferName() != null ? details.getOfferName().getEn() : "",
        details.getEndDateTimeISO(),
        details.getEndDateTime(),
        details.getGlobalClaimCount(), details.getStartDateTimeISO(), webImage,
        details.getStartDateTime(), details.getPerUserLimit(), details.getMinimumPurchaseQty(),
        details.getStatusString(), details.getOfferId(), details.getDiscountType(),
        details.getOfferFor(),
        details.getDiscountValue(), details.getApplicableOn(), details.getStatus(),details.getTermscond()
    );
  }

  private ArrayList<PdpOfferData> convertToOfferData(ArrayList<PdpOfferDetails> detailsList) {
    ArrayList<PdpOfferData> dataList = new ArrayList<>();
    if (!DataUtils.isEmptyArray(detailsList)) {
      for (PdpOfferDetails details : detailsList) {
        ImageData webImage = null;
        if (details.getWebimages() != null) {
          webImage = new ImageData(details.getWebimages().getImage(),
              details.getWebimages().getThumbnail(), details.getWebimages().getMobile());
        }
        ImageData image = null;
        if (details.getImages() != null) {
          image = new ImageData(details.getImages().getImage(), details.getImages().getThumbnail(),
              details.getImages().getMobile());
        }
        PdpOfferData pdpOfferData = new PdpOfferData(details.getApplicableOnStatus(), image,
            details.getOfferName() != null ? details.getOfferName().getEn() : "",
            details.getEndDateTimeISO(),
            details.getEndDateTime(),
            details.getGlobalClaimCount(), details.getStartDateTimeISO(), webImage,
            details.getStartDateTime(), details.getPerUserLimit(), details.getMinimumPurchaseQty(),
            details.getStatusString(), details.getOfferId(), details.getDiscountType(),
            details.getOfferFor(),
            details.getDiscountValue(), details.getApplicableOn(), details.getStatus(),details.getTermscond());
        dataList.add(pdpOfferData);
      }
    }
    return dataList;
  }
}
