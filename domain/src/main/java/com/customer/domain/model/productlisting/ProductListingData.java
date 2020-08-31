package com.customer.domain.model.productlisting;

import android.text.TextUtils;
import com.customer.domain.model.common.ProductsData;
import java.util.ArrayList;

public class ProductListingData {
  private String penCount;

  private ArrayList<ProductsData> products;

  public ProductListingData(String penCount, ArrayList<ProductsData> products) {
    this.penCount = penCount;
    this.products = products;
  }

  public int getPenCount() {
    return !TextUtils.isEmpty(penCount) && TextUtils.isDigitsOnly(penCount) ? Integer.parseInt(
        penCount) : 0;

  }

  public void setPenCount(String penCount) {
    this.penCount = penCount;
  }

  public ArrayList<ProductsData> getProducts() {
    return products;
  }

  public void setProducts(ArrayList<ProductsData> products) {
    this.products = products;
  }
}
