package com.customer.data.mapper;

import com.customer.domain.model.wishlist.WishListData;
import com.customer.remote.http.model.response.wishlist.WishListDetails;

public class WishListMapper {

  public WishListData mapper(WishListDetails details) {

    return new WishListData(CommonMapper.convertToProductData(details.getData()),
        details.getPenCount(),
        details.getMessage());
  }
}
