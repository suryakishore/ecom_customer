package com.customer.fivecanale.pdp.adapters;

import static com.customer.fivecanale.util.EcomConstants.FIVE;
import static com.customer.fivecanale.util.EcomConstants.SIX;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.databinding.ItemPdpHighlightsListBinding;
import java.util.ArrayList;

/**
 * adapter class for the highlights
 */
public class HighLightsAdapter extends
    RecyclerView.Adapter<HighLightsAdapter.HighLightsViewHolder> {
  private ArrayList<String> mHighLightsList;

  /**
   * highlights adapter class
   *
   * @param highLights arrayList for highlights
   */
  public HighLightsAdapter(ArrayList<String> highLights) {
    mHighLightsList = highLights;
  }

  @NonNull
  @Override
  public HighLightsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemPdpHighlightsListBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_pdp_highlights_list, parent, false);
    return new HighLightsViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull HighLightsViewHolder holder, int position) {
    String highLight = mHighLightsList.get(position);
    if (highLight != null) {
      holder.mBinding.tvHighLightsText.setText(highLight.trim());
    }
  }

  @Override
  public int getItemCount() {
    return mHighLightsList != null && mHighLightsList.size() > ZERO ? (
        (mHighLightsList.size() > FIVE)
            ? SIX : mHighLightsList.size()) : ZERO;
  }

  /**
   * view holder class highlights
   */
  class HighLightsViewHolder extends RecyclerView.ViewHolder {
    ItemPdpHighlightsListBinding mBinding;

    HighLightsViewHolder(@NonNull ItemPdpHighlightsListBinding binding) {
      super(binding.getRoot());
      this.mBinding = binding;
    }
  }
}