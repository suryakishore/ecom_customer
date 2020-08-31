package com.customer.fivecanale.category;

import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.bumptech.glide.Glide;
import com.customer.domain.model.productcategory.SubCategoryData;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ItemGridSubCategoryBinding;
import java.util.ArrayList;

/*
 * Purpose – This class Holds Categories Item UI.
 * @author 3Embed
 * Created on Nov 15, 2019
 * Modified on
 */
public class CategoryViewMoreAdapter extends
    RecyclerView.Adapter<CategoryViewMoreAdapter.CatViewMoreViewHolder> {
  private Context mContext;
  private ArrayList<SubCategoryData> mSubCategoryData;
  private SubCatClickListener mClickListener;
  private String mCatName;
  private String mCatId;

  CategoryViewMoreAdapter(ArrayList<SubCategoryData> subCategoryData, String catName,
      SubCatClickListener clickListener, String catId) {
    this.mSubCategoryData = subCategoryData;
    this.mCatName = catName;
    this.mClickListener = clickListener;
    this.mCatId = catId;
  }

  @NonNull
  @Override
  public CatViewMoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    ItemGridSubCategoryBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_grid_sub_category, parent, false);
    return new CatViewMoreViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull CatViewMoreViewHolder holder, int position) {
    EcomUtil.printLog("exe" + "mSubCategoryData" + mSubCategoryData.size());
    String name = TextUtils.isEmpty(mSubCategoryData.get(position).getSubCategoryName())
        ? mSubCategoryData.get(position).getSubSubCatName()
        : mSubCategoryData.get(position).getSubCategoryName();
    holder.binding.tvCateName.setText(name);
    String imgUrl = mSubCategoryData.get(position).getImageUrl().trim();
    if (!TextUtils.isEmpty(imgUrl)) {
      Glide.with(mContext).load(imgUrl).into(holder.binding.ivHomeItemSubCategories);
    }
    holder.binding.clMain.setOnClickListener(
        v -> {
          /*ProductListing page redirection if it's in sub sub category page*/
          EcomUtil.printLog("exe" + "mSubCategoryData" + mSubCategoryData.size());
          if (mSubCategoryData.get(position).getSubCatCount() > ZERO) {
            mClickListener.redirectToSubSubCategory(mSubCategoryData.get(position).getId(),
                "",
                mSubCategoryData.get(position).getSubCategoryName());
          } else {
            mClickListener.onSubCatClicked(mCatName,
                mSubCategoryData.get(position).getSubCategoryName());
          }
        });
  }

  @Override
  public int getItemCount() {
    return EcomUtil.isEmptyArray(mSubCategoryData) ? ZERO : mSubCategoryData.size();
  }

  interface SubCatClickListener {
    /**
     * This method is using to handle onCategoryClickListener
     *
     * @param catName    categoryName to get Products
     * @param subCatName sub categoryName to get Products
     */
    void onSubCatClicked(String catName, String subCatName);

    /**
     * Redirect to same activity if it has more than one sub sub categories
     */
    void redirectToSubSubCategory(String catId, String subCatId, String subCatName);

    /**
     * handler sub sub category click
     *
     * @param subSubCatName sunSubCategoryName
     */
    void onSubSubCategoryClicked(String subSubCatName);
  }

  static class CatViewMoreViewHolder extends RecyclerView.ViewHolder {
    ItemGridSubCategoryBinding binding;

    CatViewMoreViewHolder(ItemGridSubCategoryBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }
  }
}
