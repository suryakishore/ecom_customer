package com.customer.fivecanale.wallet;

import static com.customer.fivecanale.util.EcomConstants.ALL_WALLET;
import static com.customer.fivecanale.util.EcomConstants.CREDIT_WALLET;
import static com.customer.fivecanale.util.EcomConstants.DEBIT_WALLET;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.WALLET_CODE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.R;
import com.customer.domain.model.wallet.WalletTransactionItemData;
import com.customer.fivecanale.dagger.ActivityScoped;
import com.customer.fivecanale.util.EcomUtil;
import com.customer.fivecanale.util.MyScrollListener;
import com.databinding.FragmentWalletTransactionBinding;
import dagger.android.support.DaggerFragment;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * fragment for wallet transactions
 */
@ActivityScoped
public class EcomWalletFragment extends DaggerFragment {
  private FragmentWalletTransactionBinding mWalletTransactionBinding;
  private WalletTransactionsAdapter mWalletTransactionsAdapter;
  private ArrayList<WalletTransactionItemData> mWalletTransactionItemData = new ArrayList<>();

  @Inject
  public EcomWalletFragment() {
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    mWalletTransactionBinding = DataBindingUtil.inflate(inflater,
        R.layout.fragment_wallet_transaction, container, false);
    return mWalletTransactionBinding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    initialize();
  }

  /**
   * does Initialization of basic fragment resources
   */
  private void initialize() {
    if (getArguments() != null) {
      mWalletTransactionItemData.clear();
      int request = getArguments().getInt(WALLET_CODE);
      if (request == ZERO) {
        mWalletTransactionItemData.addAll(getArguments().getParcelableArrayList(ALL_WALLET));
      } else if (request == ONE) {
        mWalletTransactionItemData.addAll(getArguments().getParcelableArrayList(DEBIT_WALLET));
      } else {
        mWalletTransactionItemData.addAll(getArguments().getParcelableArrayList(CREDIT_WALLET));
      }
      EcomUtil.printLog("wallet Fra " + "initialize" + "mWalletTransactionItemData"
          + mWalletTransactionItemData.size());
      mWalletTransactionBinding.vgNoTransactionsFound.setVisibility(
          mWalletTransactionItemData.size() > ZERO ? View.GONE : View.VISIBLE);
      LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
      mWalletTransactionBinding.rvWalletTransaction.setLayoutManager(linearLayoutManager);
      mWalletTransactionsAdapter = new WalletTransactionsAdapter(mWalletTransactionItemData);
      mWalletTransactionBinding.rvWalletTransaction.setAdapter(mWalletTransactionsAdapter);
      EcomWalletActivity ecomWalletActivity = (EcomWalletActivity) getActivity();
      mWalletTransactionBinding.rvWalletTransaction.addOnScrollListener(
          new MyScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
              EcomUtil.printLog(
                  "itemLoadingVisible" + mWalletTransactionItemData.size() + "count"
                      + ecomWalletActivity.mTotalCount);
              if (request == ZERO && (mWalletTransactionItemData.size()
                  < ecomWalletActivity.mTotalCount)) {
                ecomWalletActivity.callMoreItemsTransactions();
              }
            }

            @Override
            public boolean isLastPage() {
              return ecomWalletActivity.isLoading();
            }

            @Override
            public boolean isLoading() {
              return ecomWalletActivity.isLoading();
            }
          });
    }
  }
}
