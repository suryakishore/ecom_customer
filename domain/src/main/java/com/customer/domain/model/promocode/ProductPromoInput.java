package com.customer.domain.model.promocode;

import com.customer.domain.model.getcart.QuantityData;
import java.util.ArrayList;

public class ProductPromoInput {
  private String product_id;
  private String centralproduct_id;
  private String name;
  private String brand_name;
  private float price;
  private float unitPrice;
  private float taxAmount;
  private float delivery_fee;
  private ArrayList<String> category_id;
  private ArrayList<String> tax;
  private String cityId;
  private String store_id;
  private QuantityData quantityData;

  public String getProduct_id() {
    return product_id;
  }

  public void setProduct_id(String product_id) {
    this.product_id = product_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBrand_name() {
    return brand_name;
  }

  public void setBrand_name(String brand_name) {
    this.brand_name = brand_name;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public float getDelivery_fee() {
    return delivery_fee;
  }

  public void setDelivery_fee(float delivery_fee) {
    this.delivery_fee = delivery_fee;
  }

  public String getCentralproduct_id() {
    return centralproduct_id;
  }

  public void setCentralproduct_id(String centralproduct_id) {
    this.centralproduct_id = centralproduct_id;
  }

  public float getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(float unitPrice) {
    this.unitPrice = unitPrice;
  }

  public float getTaxAmount() {
    return taxAmount;
  }

  public void setTaxAmount(float taxAmount) {
    this.taxAmount = taxAmount;
  }

  public ArrayList<String> getCategory_id() {
    return category_id;
  }

  public void setCategory_id(ArrayList<String> category_id) {
    this.category_id = category_id;
  }

  public ArrayList<String> getTax() {
    return tax;
  }

  public void setTax(ArrayList<String> tax) {
    this.tax = tax;
  }

  public String getCityId() {
    return cityId;
  }

  public void setCityId(String cityId) {
    this.cityId = cityId;
  }

  public String getStore_id() {
    return store_id;
  }

  public void setStore_id(String store_id) {
    this.store_id = store_id;
  }

  public QuantityData getQuantityData() {
    return quantityData;
  }

  public void setQuantityData(QuantityData quantityData) {
    this.quantityData = quantityData;
  }
}
