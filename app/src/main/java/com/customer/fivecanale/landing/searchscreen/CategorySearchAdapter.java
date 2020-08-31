package com.customer.fivecanale.landing.searchscreen;

import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.bumptech.glide.Glide;
import com.customer.domain.model.common.CategoryDataModel;
import com.customer.domain.model.productcategory.SubCategoryData;
import com.databinding.ItemShopByCategoryBinding;
import java.util.ArrayList;

/*
 * Purpose – This class Holds Ui Category list in Search page.
 * @author 3Embed
 * Created on DEC 12, 2019
 * Modified on
 */
public class CategorySearchAdapter extends
    RecyclerView.Adapter<CategorySearchAdapter.CategoryViewHolder> {

  private ArrayList<CategoryDataModel> mCategoryDataModels;
  private Context mContext;
  private CategoryClickListener mListener;

  CategorySearchAdapter(ArrayList<CategoryDataModel> categoryDataModels,
      CategoryClickListener listener) {
    this.mCategoryDataModels = categoryDataModels;
    this.mListener = listener;
  }

  @NonNull
  @Override
  public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    mContext = parent.getContext();

    ItemShopByCategoryBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_shop_by_category, parent, false);
    return new CategoryViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

    holder.binding.tvCatName.setText(mCategoryDataModels.get(position).getCatName());
    Glide.with(mContext)
        .load(mCategoryDataModels.get(position).getImageUrl())
        .into(holder.binding.ivCatImage);
    holder.binding.clItem.setOnClickListener(
        v -> mListener.itemClickListener(mCategoryDataModels.get(position).getSubCategory(),
            mCategoryDataModels.get(position).getCatName()));

  }

  @Override
  public int getItemCount() {
    return mCategoryDataModels != null ? mCategoryDataModels.size() : ZERO;
  }

  public interface CategoryClickListener {
    void itemClickListener(ArrayList<SubCategoryData> subCategoryData, String categoryName);
  }

  class CategoryViewHolder extends RecyclerView.ViewHolder {

    ItemShopByCategoryBinding binding;

    CategoryViewHolder(ItemShopByCategoryBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }
  }
}