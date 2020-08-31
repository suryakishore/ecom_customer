package com.customer.fivecanale.pdp.adapters;

import static com.customer.fivecanale.util.EcomConstants.APP_COLOUR;
import static com.customer.fivecanale.util.EcomConstants.BLACK_COLOUR;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.customer.domain.model.pdp.PdpOfferData;
import com.customer.fivecanale.pdp.OfferItemOnClick;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ItemOfferBinding;
import java.util.ArrayList;

/**
 * adapter class for rating distribution
 */
public class AllOffersAdapter extends
    RecyclerView.Adapter<AllOffersAdapter.AllOffersViewHolder> {
  private ArrayList<PdpOfferData> mAllOffersArrayList;
  private OfferItemOnClick mItemOnClick;
  private Context mContext;

  /**
   * all offers adapter
   *
   * @param allOffersArrayList rating arrayList
   */
  public AllOffersAdapter(ArrayList<PdpOfferData> allOffersArrayList,
      OfferItemOnClick itemOnClick) {
    this.mAllOffersArrayList = allOffersArrayList;
    this.mItemOnClick = itemOnClick;
  }

  @NonNull
  @Override
  public AllOffersViewHolder onCreateViewHolder(
      @NonNull ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    ItemOfferBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_offer, parent, false);
    return new AllOffersViewHolder(binding);
  }

  @SuppressLint("DefaultLocale")
  @Override
  public void onBindViewHolder(@NonNull AllOffersViewHolder holder, int position) {
    PdpOfferData pdpOfferData = mAllOffersArrayList.get(position);
    if (pdpOfferData != null) {
      String offerName = EcomUtil.getColoredSpanned(pdpOfferData.getOfferName(), BLACK_COLOUR);
      String tAndC = EcomUtil.getColoredSpanned(mContext.getResources().getString(R.string.tAndC),
          APP_COLOUR);
      holder.mBinding.tvPdpBulletPoint.setText(Html.fromHtml(
          String.format("%s %s", offerName, tAndC)));
      holder.mBinding.clItemBrand.setOnClickListener(
          v -> mItemOnClick.onOfferItemClick(pdpOfferData.getTermscond()));
    }
  }

  @Override
  public int getItemCount() {
    return mAllOffersArrayList != null ? mAllOffersArrayList.size() : ZERO;
  }

  class AllOffersViewHolder extends RecyclerView.ViewHolder {
    ItemOfferBinding mBinding;

    AllOffersViewHolder(@NonNull ItemOfferBinding binding) {
      super(binding.getRoot());
      this.mBinding = binding;
    }
  }
}