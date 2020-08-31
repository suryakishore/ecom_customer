package com.customer.domain.repository;

public interface CartHandlerRepository {
  boolean isCartEmpty();

  boolean isProductExistInCart(String productId);

  int getQuantity(String productId);
  int getTotalCartItems();
}
