package com.customer.domain.interactor.handler;

import com.customer.domain.repository.CartHandlerRepository;
import javax.inject.Inject;

public class CartHandlerImpl implements CartHandler {
  private CartHandlerRepository mRepository;

  @Inject
  public CartHandlerImpl(CartHandlerRepository cartHandlerRepository) {
    this.mRepository = cartHandlerRepository;
  }

  @Override
  public boolean isCartEmpty() {
    return mRepository.isCartEmpty();
  }

  @Override
  public boolean isProductExistInCart(String productId) {
    return mRepository.isProductExistInCart(productId);
  }

  @Override
  public int getQuantity(String productId) {
    return mRepository.getQuantity(productId);
  }

  @Override
  public int totalCartItems() {
    if (mRepository.getTotalCartItems() > 0) {
      return mRepository.getTotalCartItems();
    } else {
      return 0;
    }
  }
}
