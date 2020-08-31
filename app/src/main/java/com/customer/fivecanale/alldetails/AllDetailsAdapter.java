package com.customer.fivecanale.alldetails;

import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.customer.domain.model.pdp.AttributesData;
import com.customer.fivecanale.pdp.adapters.SpecificationsAdapter;
import com.databinding.ItemSpecificationsBinding;
import java.util.ArrayList;

/**
 * adapter class for the all details
 */
public class AllDetailsAdapter extends
    RecyclerView.Adapter<AllDetailsAdapter.AllDetailsViewHolder> {
  private ArrayList<AttributesData> mAttributesData;

  /**
   * constructor class for the AllDetailsAdapter class.
   *
   * @param attributesData attributes mData arrayList
   */
  AllDetailsAdapter(
      ArrayList<AttributesData> attributesData) {
    mAttributesData = attributesData;
  }

  @NonNull
  @Override
  public AllDetailsAdapter.AllDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    ItemSpecificationsBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_specifications, parent, false);
    return new AllDetailsViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull AllDetailsAdapter.AllDetailsViewHolder holder,
      int position) {
    AttributesData attributesData = mAttributesData.get(position);
    if (attributesData != null) {
      holder.mBinding.rvSpecifications.setVisibility((attributesData.getInnerAttributes() != null
          && attributesData.getInnerAttributes().size() > ZERO) ? View.VISIBLE : View.GONE);
      holder.mBinding.viewSpec.setVisibility((attributesData.getInnerAttributes() != null
          && attributesData.getInnerAttributes().size() > ZERO && position != (
          mAttributesData.size() - ONE)) ? View.VISIBLE : View.GONE);
      if (attributesData.getInnerAttributes() != null
          && attributesData.getInnerAttributes().size() > ZERO) {
        SpecificationsAdapter specificationsAdapter = new SpecificationsAdapter(
            attributesData.getInnerAttributes(), FALSE);
        holder.mBinding.rvSpecifications.setAdapter(specificationsAdapter);
        if (attributesData.getName() != null && !attributesData.getName().isEmpty()) {
          holder.mBinding.tvSpecificationName.setText(attributesData.getName());
        }
        holder.mBinding.tvSpecificationName.setVisibility(
            (attributesData.getName() != null && !attributesData.getName().isEmpty()) ? View.VISIBLE
                : View.GONE);
      } else {
        holder.mBinding.tvSpecificationName.setVisibility(View.GONE);
      }
    }
  }

  @Override
  public int getItemCount() {
    return mAttributesData != null ? mAttributesData.size() : ZERO;
  }

  /**
   * all details view holder class.
   */
  class AllDetailsViewHolder extends RecyclerView.ViewHolder {
    private ItemSpecificationsBinding mBinding;

    AllDetailsViewHolder(@NonNull ItemSpecificationsBinding binding) {
      super(binding.getRoot());
      this.mBinding = binding;
    }
  }
}