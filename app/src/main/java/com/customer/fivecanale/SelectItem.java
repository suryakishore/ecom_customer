package com.customer.fivecanale;

/**
 * call back listener for recycler view items
 */
public interface SelectItem {
  /**
   * return the position where you clicked.
   * @param position integer view holder position.
   */
  void onSelectItem(int position);
}
