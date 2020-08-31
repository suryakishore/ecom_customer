package com.customer.fivecanale.search;

import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.customer.domain.model.productcategory.SubSubCategoryData;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ItemSubSubCategoryBinding;
import java.util.ArrayList;

/*
 * Purpose – This class Holds the UI elements display in the Sub sub Category page.
 * @author 3Embed
 * Created on Nov 10, 2019
 * Modified on
 */
public class SubSubCategoryAdapter extends
    RecyclerView.Adapter<SubSubCategoryAdapter.SubSubCatViewHolder> {
  private ArrayList<SubSubCategoryData> mSubSubCategoryData;
  private SubSubCatItemClickListener mSubSubCatItemClickListener;
  private String mSubCategoryName;

  public SubSubCategoryAdapter(ArrayList<SubSubCategoryData> subSubCategoryData,
      SubSubCatItemClickListener clickListener, String subCategoryName) {
    this.mSubSubCategoryData = subSubCategoryData;
    this.mSubSubCatItemClickListener = clickListener;
    this.mSubCategoryName = subCategoryName;
  }

  @NonNull
  @Override
  public SubSubCatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemSubSubCategoryBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_sub_sub_category, parent, false);
    return new SubSubCatViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull SubSubCatViewHolder holder, int position) {
    EcomUtil.printLog(
        "exe" + "getSubSubCatName" + mSubSubCategoryData.get(position).getSubSubCategoryName());
    holder.binding.tvSubSubCatName.setText(
        mSubSubCategoryData.get(position).getSubSubCategoryName());
    holder.binding.clMain.setOnClickListener(
        v -> mSubSubCatItemClickListener.subSubCatClickListener(
            mSubSubCategoryData.get(position).getSubSubCategoryName(),
            mSubCategoryName));
  }

  @Override
  public int getItemCount() {
    return EcomUtil.isEmptyArray(mSubSubCategoryData) ? ZERO : mSubSubCategoryData.size();
  }

  public interface SubSubCatItemClickListener {
    void subSubCatClickListener(String subSubCatName, String subCatName);
  }

  static class SubSubCatViewHolder extends RecyclerView.ViewHolder {
    ItemSubSubCategoryBinding binding;

    SubSubCatViewHolder(ItemSubSubCategoryBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }
  }
}