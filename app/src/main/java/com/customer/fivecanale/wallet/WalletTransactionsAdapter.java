package com.customer.fivecanale.wallet;

import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.SIX;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.customer.domain.model.wallet.WalletTransactionItemData;
import com.customer.fivecanale.util.DateAndTimeUtil;
import com.databinding.ItemTransactionBinding;
import java.util.ArrayList;

/**
 * adapter class for the  wallet transactions
 */
public class WalletTransactionsAdapter extends
    RecyclerView.Adapter<WalletTransactionsAdapter.WalletViewHolder> {
  private ArrayList<WalletTransactionItemData> mWalletTransactionItemData;
  private Context mContext;

  WalletTransactionsAdapter(
      ArrayList<WalletTransactionItemData> walletTransactionItemData) {
    mWalletTransactionItemData = walletTransactionItemData;
  }

  @NonNull
  @Override
  public WalletTransactionsAdapter.WalletViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    mContext = parent.getContext();
    ItemTransactionBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_transaction, parent, false);
    return new WalletViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull WalletTransactionsAdapter.WalletViewHolder holder,
      int position) {
    WalletTransactionItemData walletTransactionItemData = mWalletTransactionItemData.get(position);
    holder.mBinding.tvTransactionId.setText(
        String.format("%s .......%s", mContext.getResources().getString(R.string.transactionId),
            walletTransactionItemData.getTxnid().substring(
                walletTransactionItemData.getTxnid().length() - SIX)));
    holder.mBinding.tvTransactionDes.setText(walletTransactionItemData.getDescription());
    holder.mBinding.tvTransactionDate.setText(
        DateAndTimeUtil.getTransactionTime(walletTransactionItemData.getTxntimestamp()));
    holder.mBinding.tvTransactionAmt.setText(
        String.format("%s %s", walletTransactionItemData.getCurrency(),
            walletTransactionItemData.getAmount()));
    holder.mBinding.ivTransactionType.setImageDrawable(
        walletTransactionItemData.getTxntype() != null
            && Integer.parseInt(walletTransactionItemData.getTxntype()) == ONE
            ? mContext.getResources().getDrawable(R.drawable.ic_arraow_credit)
            : mContext.getResources().getDrawable(R.drawable.ic_arrow_debit));
  }

  @Override
  public int getItemCount() {
    return mWalletTransactionItemData != null ? mWalletTransactionItemData.size() : ZERO;
  }

  /**
   * view holder class for the wallet item
   */
  class WalletViewHolder extends RecyclerView.ViewHolder {
    private ItemTransactionBinding mBinding;

    WalletViewHolder(@NonNull ItemTransactionBinding binding) {
      super(binding.getRoot());
      this.mBinding = binding;
    }
  }
}
