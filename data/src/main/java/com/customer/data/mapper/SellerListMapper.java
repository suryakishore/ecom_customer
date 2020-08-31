package com.customer.data.mapper;

import com.customer.data.utils.DataUtils;
import com.customer.domain.model.sellerlist.ExchangePolicyData;
import com.customer.domain.model.sellerlist.ProductSeoData;
import com.customer.domain.model.sellerlist.ReplacementPolicyData;
import com.customer.domain.model.sellerlist.ReturnPolicyData;
import com.customer.domain.model.sellerlist.SellerListData;
import com.customer.domain.model.sellerlist.SellerListDataItem;
import com.customer.domain.model.sellerlist.TermAndConditionData;
import com.customer.domain.model.sellerlist.WarrantyData;
import com.customer.remote.http.model.response.sellerlist.ProductSeoDetails;
import com.customer.remote.http.model.response.sellerlist.SellerDetails;
import com.customer.remote.http.model.response.sellerlist.SellerDetailsItem;
import java.util.ArrayList;

public class SellerListMapper {
  public SellerListData mapper(SellerDetails sellerDetails) {
    return new SellerListData(convertToSelleListData(sellerDetails.getData()),
        sellerDetails.getMessage());
  }

  private ArrayList<SellerListDataItem> convertToSelleListData(
      ArrayList<SellerDetailsItem> detailsItemArrayList) {
    ArrayList<SellerListDataItem> listDataItems = new ArrayList<>();
    if (!DataUtils.isEmptyArray(detailsItemArrayList)) {

      for (SellerDetailsItem detailsItem : detailsItemArrayList) {

        ExchangePolicyData exchangePolicyData = detailsItem.getExchangePolicy() != null ? new
            ExchangePolicyData(detailsItem.getExchangePolicy().getNoofdays(),
            detailsItem.getExchangePolicy().isExchange()) : null;
        SellerListDataItem item = new SellerListDataItem(
            CommonMapper.convertToImageData(detailsItem.getImages()),
            detailsItem.getProductSeo() != null ? convertToProductSeoData(
                detailsItem.getProductSeo()) : null, exchangePolicyData,
            CommonMapper.convertToSupplierDataList(detailsItem.getSupplier()),
            detailsItem.getReturnPolicy() != null ? new ReturnPolicyData(
                detailsItem.getReturnPolicy().isReturn(),
                detailsItem.getReturnPolicy().getNoofdays()) : null,
            detailsItem.getCurrencySymbol(),
            detailsItem.getWarranty() != null ? new WarrantyData(detailsItem.getWarranty().getEn(),
                detailsItem.getWarranty().getAr())
                : null, detailsItem.getCurrency(), detailsItem.getTotalStarRating(),
            detailsItem.getReplacementPolicy() != null ? new ReplacementPolicyData(
                detailsItem.getReplacementPolicy().getNoofdays(),
                detailsItem.getReplacementPolicy().isReplacement()) : null,
            detailsItem.getTermAndcondition() != null ? new TermAndConditionData(
                detailsItem.getTermAndcondition().getEn(),
                detailsItem.getTermAndcondition().getEs()) : null, detailsItem.getProductName()
        );
        listDataItems.add(item);
      }
    }
    return listDataItems;
  }

  private ProductSeoData convertToProductSeoData(ProductSeoDetails details) {
    return new ProductSeoData(details.getDescription(), details.getMetatags(), details.getTitle(),
        details.getSlug());
  }

}
