package com.customer.data.mapper;

import com.customer.domain.model.productlisting.ProductListingData;
import com.customer.remote.http.model.response.productListing.ProductListingDetails;

public class FilteredProductsListMapper {

  public ProductListingData mapper(ProductListingDetails details) {
    return new ProductListingData(details.getPenCount(),
        CommonMapper.convertToProductData(details.getProducts()));
  }

}
