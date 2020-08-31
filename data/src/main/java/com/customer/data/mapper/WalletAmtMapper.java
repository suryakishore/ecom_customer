package com.customer.data.mapper;

import com.customer.data.preference.PreferenceManager;
import com.customer.data.utils.DataUtils;
import com.customer.domain.model.wallet.WalletAmtDetails;
import com.customer.domain.model.wallet.WalletData;
import com.customer.domain.model.wallet.WalletEarningData;
import com.customer.remote.http.model.response.wallet.WalletDataDetails;
import com.customer.remote.http.model.response.wallet.WalletDetails;
import com.customer.remote.http.model.response.wallet.WalletEarningDetails;
import java.util.ArrayList;

public class WalletAmtMapper {
  private PreferenceManager preference;

  public WalletData mapper(WalletDataDetails walletListDetails, PreferenceManager preference) {
    this.preference = preference;
    return new WalletData(convertToWalletItemData(walletListDetails.getWalletEarningData()),
        convertToWalletData(walletListDetails.getWalletData()));
  }

  private ArrayList<WalletEarningData> convertToWalletItemData(
      ArrayList<WalletEarningDetails> trackingItemDetailsArrayList) {
    ArrayList<WalletEarningData> walletEarningData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(trackingItemDetailsArrayList)) {
      for (WalletEarningDetails itemDetails : trackingItemDetailsArrayList) {
        WalletEarningData itemData = new WalletEarningData(itemDetails.getBalance(),
            itemDetails.getCreateddate(), itemDetails.getWalletearningid(),
            itemDetails.getUsertype(), itemDetails.getStatustext(),
            itemDetails.getUserid(), itemDetails.getStatus());
        walletEarningData.add(itemData);
      }
    }
    return walletEarningData;
  }

  private ArrayList<WalletAmtDetails> convertToWalletData(
      ArrayList<WalletDetails> walletDetailsArrayList) {
    ArrayList<WalletAmtDetails> walletDetails = new ArrayList<>();
    if (!DataUtils.isEmptyArray(walletDetailsArrayList)) {
      for (WalletDetails itemDetails : walletDetailsArrayList) {
        if (itemDetails.getBalance() != null) {
          preference.setWalletAmt(
              String.format("%s %s", itemDetails.getCurrency(),
                  String.format("%.2f", Float.parseFloat(itemDetails.getBalance()))));
        }
        WalletAmtDetails itemData = new WalletAmtDetails(itemDetails.getWalletid(),
            itemDetails.getBalance(), itemDetails.getCreateddate(), itemDetails.getUsertype(),
            itemDetails.getStatustext(), itemDetails.getCurrency(), itemDetails.getUserid(),
            itemDetails.getStatus());
        walletDetails.add(itemData);
      }
    }
    return walletDetails;
  }
}
