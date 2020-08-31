package com.customer.fivecanale.allcategory;

import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Context;
import android.os.Build.VERSION_CODES;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.customer.domain.model.common.CategoryDataModel;
import com.customer.domain.model.productcategory.SubCategoryData;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ItemAllCategoryBinding;
import java.util.ArrayList;

/*
 * Purpose – This class Holds Categories Item UI.
 * @author 3Embed
 * Created on Nov 15, 2019
 * Modified on
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

  private static int sLastSelected = 0;
  private Context mContext;
  private ArrayList<CategoryDataModel> mCategoryDataModels;
  private CategoryClickHandler mClickHandler;

  CategoryAdapter(ArrayList<CategoryDataModel> categoryDataModels,
      CategoryClickHandler clickHandler) {
    this.mCategoryDataModels = categoryDataModels;
    this.mClickHandler = clickHandler;
  }

  @NonNull
  @Override
  public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    mContext = parent.getContext();

    ItemAllCategoryBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_all_category, parent, false);
    return new CategoryViewHolder(binding);
  }

  @RequiresApi(api = VERSION_CODES.M)
  @Override
  public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

    holder.binding.tvCatName.setText(mCategoryDataModels.get(position).getCatName());
    Glide.with(mContext)
        .load(mCategoryDataModels.get(position).getImageUrl().replace(" ", "%20"))
        .apply(RequestOptions.circleCropTransform())
        .into(holder.binding.ivCatImg);

    holder.binding.selectedSideBar.setVisibility(
        position == sLastSelected ? View.VISIBLE : View.GONE);

    if (position != sLastSelected) {
      holder.binding.selectedSideBar.setVisibility(View.GONE);
    }

    holder.binding.clMain.setBackgroundColor(
        position == sLastSelected ? mContext.getColor(R.color.white)
            : mContext.getColor(R.color.desert_storm));

    holder.binding.clMain.setOnClickListener(v -> {
      sLastSelected = position;
      mClickHandler.onCatClickListener(mCategoryDataModels.get(position).getSubCategory(),
          mCategoryDataModels.get(position).getCatName());
      notifyDataSetChanged();
    });

  }

  @Override
  public int getItemCount() {
    return EcomUtil.isEmptyArray(mCategoryDataModels) ? ZERO : mCategoryDataModels.size();
  }

  static class CategoryViewHolder extends RecyclerView.ViewHolder {

    ItemAllCategoryBinding binding;

    CategoryViewHolder(ItemAllCategoryBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }
  }

  public interface CategoryClickHandler {
    void onCatClickListener(ArrayList<SubCategoryData> subCategoryData, String catName);
  }
}