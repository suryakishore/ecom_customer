package com.customer.fivecanale.landing.homescreen.adapters;

import static com.customer.fivecanale.util.EcomConstants.EMPTY;
import static com.customer.fivecanale.util.EcomConstants.FOUR;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.customer.domain.model.home.CategoryData;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ItemHomeSubcategoriesListBinding;
import java.util.ArrayList;

/*
 * Purpose – This class Holds Sub category items in Grid UI format that will be display in the
 * HomePage.
 * @author 3Embed
 * Created on Nov 25, 2019
 * Modified on
 */
public class SubCategoriesAdapter extends RecyclerView.Adapter<SubCategoriesAdapter.ViewHolder> {

  private ArrayList<CategoryData> mCategoryData;
  private Context mContext;
  private String mCatName;
  private SubCatClickListener mClickListener;

  SubCategoriesAdapter(ArrayList<CategoryData> categoryProducts, String catName,
      SubCatClickListener clickListener) {
    this.mCategoryData = categoryProducts;
    this.mCatName = catName;
    this.mClickListener = clickListener;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    mContext = parent.getContext();

    ItemHomeSubcategoriesListBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_home_subcategories_list, parent, false);
    return new ViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.binding.tvCateName.setText(mCategoryData.get(position).getSubCategoryName());
    String imageUrl = mCategoryData.get(position).getImageUrl();

    if (!TextUtils.isEmpty(imageUrl)) {
      Glide.with(mContext)
          .load(imageUrl.replace(EMPTY, "%20"))
          .into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable drawable,
                @Nullable Transition<? super Drawable>
                    transition) {
              holder.binding.ivHomeItemSubCategories.setImageDrawable(drawable);
            }

          });
    }
    holder.binding.cvMain.setOnClickListener(v -> mClickListener.onSubCatClicked(mCatName,
        mCategoryData.get(position).getSubCategoryName())
    );
  }

  @Override
  public int getItemCount() {
    return !EcomUtil.isEmptyArray(mCategoryData) ? mCategoryData.size() > FOUR ? FOUR
        : mCategoryData.size() : ZERO;
  }

  /**
   * Sub Category Click Listener
   */
  public interface SubCatClickListener {
    /**
     * SubCategory Element click Listener
     *
     * @param catName    mCatName for Getting All the sub categories under this Category
     * @param subCatName subCatName for Getting All the Products under this Sub Category
     */
    void onSubCatClicked(String catName, String subCatName);
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    ItemHomeSubcategoriesListBinding binding;

    ViewHolder(@NonNull ItemHomeSubcategoriesListBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }
  }
}
