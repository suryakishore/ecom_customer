package com.customer.domain.interactor.handler;

public interface CartHandler {

  boolean isCartEmpty();
  boolean isProductExistInCart(String productId);
  int getQuantity(String productId);
  int totalCartItems();
}
