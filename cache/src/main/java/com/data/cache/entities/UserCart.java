package com.data.cache.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "cart", primaryKeys = {"productId"})
public class UserCart {
  @NonNull
  @ColumnInfo(name = "productId")
  public String productId;

  @ColumnInfo(name = "storeId")
  public String storeId;

  @ColumnInfo(name = "productName")
  public String productName;

  @ColumnInfo(name = "quantity")
  public int quantity;

  public UserCart(@NonNull String productId, String storeId, String productName, int quantity) {
    this.productId = productId;
    this.storeId = storeId;
    this.productName = productName;
    this.quantity = quantity;
  }

  @NonNull
  public String getProductId() {
    return productId;
  }

  public void setProductId(@NonNull String productId) {
    this.productId = productId;
  }

  public String getStoreId() {
    return storeId;
  }

  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
