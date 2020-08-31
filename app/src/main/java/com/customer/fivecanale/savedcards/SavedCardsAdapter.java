package com.customer.fivecanale.savedcards;

import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.MINUS_ONE;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.TWO;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.appscrip.stripe.UserAccounts;
import com.customer.fivecanale.SelectItem;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ItemSavedCardsBinding;
import java.util.ArrayList;

/**
 * adapter class for the saved cards
 */
public class SavedCardsAdapter extends RecyclerView.Adapter<SavedCardsAdapter.ViewHolder> {
  private static int unCheckedPos = -1;
  private static int checkedPos = -1;
  private ArrayList<SavedCardsData> mSavedCardsData;
  private Context mContext;
  private boolean mIsToSelectCard;
  private SelectItem mSelectCard;
  private boolean mIsEnabled = TRUE;

  public SavedCardsAdapter(SelectItem selectCard,
      ArrayList<SavedCardsData> savedCardsData, boolean isToSelectCard) {
    mSavedCardsData = savedCardsData;
    mIsToSelectCard = isToSelectCard;
    mSelectCard = selectCard;
  }

  public void enabled(boolean isEnabled) {
    mIsEnabled = isEnabled;
  }

  @NonNull
  @Override
  public SavedCardsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    ItemSavedCardsBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_saved_cards, parent, false);
    return new ViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull SavedCardsAdapter.ViewHolder holder, int position) {
    SavedCardsData savedCardsData = mSavedCardsData.get(position);
    holder.mBinding.clCardItem.setEnabled(mIsEnabled);
    holder.mBinding.ivCardBrand.setImageResource(
        UserAccounts.INSTANCE.getBrandLogo(savedCardsData.getBrand()));
    String month = savedCardsData.getExpMonth();
    if (month != null) {
      if (month.length() == ONE) {
        month = ZERO + month;
      }
    }
    holder.mBinding.tvExpDate.setText(
        String.format("%s/%s", month, savedCardsData.getExpYear().substring(
            savedCardsData.getExpYear().length() - TWO)));
    holder.mBinding.ivRightArrow.setVisibility(mIsToSelectCard ? View.INVISIBLE : View.VISIBLE);
    holder.mBinding.rbCard.setVisibility(mIsToSelectCard ? View.VISIBLE : View.GONE);
    holder.mBinding.tvCardNumber.setText(
        String.format("%s %s", mContext.getString(R.string.xxx), savedCardsData.getLast4()));
    EcomUtil.printLog(
        "exe" + "isChecked" + savedCardsData.isChecked() + "pos " + position + "four  "
            + savedCardsData.getLast4());
    if (mIsToSelectCard) {
      holder.mBinding.rbCard.setChecked(savedCardsData.isChecked() ? TRUE : FALSE);
    }
    holder.mBinding.clCardItem.setOnClickListener(view -> {
      if (mIsToSelectCard) {
        holder.mBinding.rbCard.setChecked(savedCardsData.isChecked() ? FALSE : TRUE);
        if (savedCardsData.isChecked()) {
          savedCardsData.setChecked(FALSE);
        } else {
          unCheckedPos = checkedPos;
          if (unCheckedPos != MINUS_ONE) {
            SavedCardsData unCheckedSavedCardsData = mSavedCardsData.get(unCheckedPos);
            unCheckedSavedCardsData.setChecked(FALSE);
            notifyItemChanged(unCheckedPos);
          }
          checkedPos = position;
          savedCardsData.setChecked(TRUE);
        }
        mSelectCard.onSelectItem(position);
      }
    });
    holder.mBinding.ivRightArrow.setOnClickListener(view -> mSelectCard.onSelectItem(position));
  }

  @Override
  public int getItemCount() {
    return mSavedCardsData != null ? mSavedCardsData.size() : ZERO;
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    ItemSavedCardsBinding mBinding;

    public ViewHolder(ItemSavedCardsBinding binding) {
      super(binding.getRoot());
      mBinding = binding;
    }
  }
}
