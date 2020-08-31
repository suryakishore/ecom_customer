package com.customer.data.mapper;

import com.customer.data.utils.DataUtils;
import com.customer.domain.model.wallet.WalletTransactionItemData;
import com.customer.domain.model.wallet.WalletTransactionsData;
import com.customer.remote.http.model.response.wallet.WalletTransactionItemDetails;
import com.customer.remote.http.model.response.wallet.WalletTransactionsListDetails;
import java.util.ArrayList;

public class WalletTransactionsMapper {
  public WalletTransactionsData mapper(
      WalletTransactionsListDetails walletTransactionsListDetails) {
    return new WalletTransactionsData(
        convertToWalletItemData(walletTransactionsListDetails.getData()),
        walletTransactionsListDetails.getMessage(), walletTransactionsListDetails.getTotalCount(),
        walletTransactionsListDetails.getPageState());
  }

  private ArrayList<WalletTransactionItemData> convertToWalletItemData(
      ArrayList<WalletTransactionItemDetails> trackingItemDetailsArrayList) {
    ArrayList<WalletTransactionItemData> walletTransactionItemData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(trackingItemDetailsArrayList)) {
      for (WalletTransactionItemDetails itemDetails : trackingItemDetailsArrayList) {
        WalletTransactionItemData itemData = new WalletTransactionItemData(
            itemDetails.getWalletid(), itemDetails.getAmount(), itemDetails.getNotes(),
            itemDetails.getTxntypetext(), itemDetails.getTxntimestamp(),
            itemDetails.getClosingbal(), itemDetails.getDescription(),
            itemDetails.getTrigger(), itemDetails.getOpeningbal(), itemDetails.getTxntype(),
            itemDetails.getCurrency(), itemDetails.getInitiatedby(), itemDetails.getTxnid());
        walletTransactionItemData.add(itemData);
      }
    }
    return walletTransactionItemData;
  }
}
