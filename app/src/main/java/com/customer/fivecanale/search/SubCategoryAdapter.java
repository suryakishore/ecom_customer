package com.customer.fivecanale.search;

import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.customer.domain.model.productcategory.SubCategoryData;
import com.customer.fivecanale.search.SubSubCategoryAdapter.SubSubCatItemClickListener;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ItemSubCategoryBinding;
import java.util.ArrayList;

/*
 * Purpose – This class Holds the UI elements display in the SubCategory page.
 * @author 3Embed
 * Created on Nov 10, 2019
 * Modified on
 */
public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.SubCatViewHolder> {

  private ArrayList<SubCategoryData> mSubCategoryData;
  private Context mContext;
  private SubCatClickListener mSubCatClickListener;

  SubCategoryAdapter(ArrayList<SubCategoryData> subCategoryData,
      SubCatClickListener clickListener) {
    this.mSubCategoryData = subCategoryData;
    this.mSubCatClickListener = clickListener;
  }

  @NonNull
  @Override
  public SubCatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    ItemSubCategoryBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_sub_category, parent, false);
    return new SubCatViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull SubCatViewHolder holder, int position) {
    holder.binding.tvSubCatName.setText(mSubCategoryData.get(position).getSubCategoryName());

    if (!EcomUtil.isEmptyArray(mSubCategoryData.get(position).getSubSubCategory())) {
      holder.binding.ivExpandable.setVisibility(View.VISIBLE);
      holder.binding.ivRightArrow.setVisibility(View.INVISIBLE);
      SubSubCategoryAdapter adapter = new SubSubCategoryAdapter(
          mSubCategoryData.get(position).getSubSubCategory(), mSubCatClickListener,
          mSubCategoryData.get(position).getSubCategoryName());
      holder.binding.rvSubSubCatList.setAdapter(adapter);
      adapter.notifyDataSetChanged();
      handleItemViewExpand(holder, position);
    } else {
      holder.binding.ivExpandable.setVisibility(View.INVISIBLE);
      holder.binding.ivRightArrow.setVisibility(View.VISIBLE);
      handleRedirect(holder, position);
    }
  }

  private void handleRedirect(SubCatViewHolder holder, int position) {
    holder.binding.clMainLayout.setOnClickListener(
        v -> mSubCatClickListener.catOnClick(mSubCategoryData.get(position).getSubCategoryName()));
  }

  @Override
  public int getItemCount() {
    return EcomUtil.isEmptyArray(mSubCategoryData) ? ZERO : mSubCategoryData.size();
  }

  private void handleItemViewExpand(SubCatViewHolder holder, int position) {

    holder.binding.clMainLayout.setOnClickListener(v -> {
      if (mSubCategoryData.get(position).isClicked()) {
        holder.binding.rvSubSubCatList.setVisibility(View.GONE);
        holder.binding.ivExpandable.setImageDrawable(
            ContextCompat.getDrawable(mContext, R.drawable.ic_add));
        mSubCategoryData.get(position).setClicked(false);
      } else {
        holder.binding.rvSubSubCatList.setVisibility(View.VISIBLE);
        holder.binding.ivExpandable.setImageDrawable(
            ContextCompat.getDrawable(mContext, R.drawable.ic_minus));
        mSubCategoryData.get(position).setClicked(true);
      }
    });
  }

  public interface SubCatClickListener extends SubSubCatItemClickListener {

    /**
     * THis method is using to handle categoryOnclick listeners
     *
     * @param subCatName subCatName to get SubCategory products
     */
    void catOnClick(String subCatName);
  }

  static class SubCatViewHolder extends RecyclerView.ViewHolder {

    ItemSubCategoryBinding binding;

    SubCatViewHolder(ItemSubCategoryBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }
  }
}
