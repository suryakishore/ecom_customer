package com.customer.fivecanale.promocode;

import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.customer.domain.model.promocode.PromoCodeData;
import com.customer.fivecanale.SelectItem;
import com.customer.fivecanale.util.DateAndTimeUtil;
import com.databinding.ItemPromoCodeBinding;
import java.util.ArrayList;

/**
 * adapter class for the saved cards
 */
public class PromoCodesAdapter extends RecyclerView.Adapter<PromoCodesAdapter.ViewHolder> {
  private ArrayList<PromoCodeData> mPromoCodeData;
  private Context mContext;
  private SelectItem mSelectItem;

  public PromoCodesAdapter(SelectItem selectCard,
      ArrayList<PromoCodeData> promoCodeData) {
    mPromoCodeData = promoCodeData;
    mSelectItem = selectCard;
  }

  @NonNull
  @Override
  public PromoCodesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    ItemPromoCodeBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_promo_code, parent, false);
    return new ViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull PromoCodesAdapter.ViewHolder holder, int position) {
    PromoCodeData promoCodeData = mPromoCodeData.get(position);
    if (promoCodeData != null) {
      holder.mBinding.tvPromoCode.setText(promoCodeData.getCode());
      if (promoCodeData.getEndTime() != null) {
        holder.mBinding.tvValidDate.setText(
            String.format("%s %s", mContext.getResources().getString(R.string.validTill),
                DateAndTimeUtil.getPromoCodeValidTime(promoCodeData.getEndTime())));
      }
      StringBuilder stringBuilder = new StringBuilder();
      for (int i = ZERO; i < promoCodeData.getDescription().size(); i++) {
        holder.mBinding.tvOfferDes.setText(
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ? Html.fromHtml(
                stringBuilder.append(promoCodeData.getDescription().get(i).getContain()).toString(),
                Html.FROM_HTML_MODE_COMPACT) : Html.fromHtml(
                stringBuilder.append(
                    promoCodeData.getDescription().get(i).getContain()).toString()));
      }
      holder.mBinding.clPromoCodeItem.setOnClickListener(v -> mSelectItem.onSelectItem(position));
    }
  }

  @Override
  public int getItemCount() {
    return mPromoCodeData != null ? mPromoCodeData.size() : ZERO;
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    ItemPromoCodeBinding mBinding;

    public ViewHolder(ItemPromoCodeBinding binding) {
      super(binding.getRoot());
      mBinding = binding;
    }
  }
}
