package com.customer.fivecanale.pdp.adapters;

import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.customer.domain.model.pdp.VariantsData;
import com.customer.fivecanale.pdp.PdpVariantsClickListener;
import com.databinding.ItemPdpVariantsBinding;
import java.util.ArrayList;

/**
 * adapter class for the pdp variants.
 */
public class PdpVariantsAdapter extends
    RecyclerView.Adapter<PdpVariantsAdapter.PdpVariantsViewHolder> {
  private ArrayList<VariantsData> mVariantsData;
  private PdpVariantsClickListener mPdpVariantsClickListener;

  /**
   * constructor for this adapter class
   *
   * @param variantsData             arraylist contains variants mData
   * @param pdpVariantsClickListener interface call back listener for the variants click
   */
  public PdpVariantsAdapter(ArrayList<VariantsData> variantsData,
      PdpVariantsClickListener pdpVariantsClickListener) {
    mVariantsData = variantsData;
    this.mPdpVariantsClickListener = pdpVariantsClickListener;
  }

  @NonNull
  @Override
  public PdpVariantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    ItemPdpVariantsBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_pdp_variants, parent, false);
    return new PdpVariantsViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull PdpVariantsViewHolder holder,
      int position) {
    VariantsData variantsData = mVariantsData.get(position);
    if (variantsData != null) {
      holder.mBinding.tvPdpVariantName.setText(variantsData.getName());
      if (variantsData.getSizeData() != null && variantsData.getSizeData().size() > ZERO) {
        for (int i = ZERO; i < variantsData.getSizeData().size(); i++) {
          if (variantsData.getSizeData().get(i).getIsPrimary()) {
            holder.mBinding.tvPdpPriVariantName.setText(
                variantsData.getSizeData().get(i).getName());
            break;
          }
        }
      }
      holder.mBinding.clVariants.setOnClickListener(view -> {
        if (mVariantsData != null) {
          mPdpVariantsClickListener.onVariantClick(mVariantsData,variantsData.getUnitId());
        }
      });
    }

    if (position == mVariantsData.size() - ONE) {
      holder.mBinding.viewVariants.setVisibility(View.GONE);
    }
  }

  @Override
  public int getItemCount() {
    return mVariantsData != null ? mVariantsData.size() : ZERO;
  }

  /**
   * view holder class for the variants
   */
  class PdpVariantsViewHolder extends RecyclerView.ViewHolder {
    ItemPdpVariantsBinding mBinding;

    PdpVariantsViewHolder(@NonNull ItemPdpVariantsBinding binding) {
      super(binding.getRoot());
      this.mBinding = binding;
    }
  }
}