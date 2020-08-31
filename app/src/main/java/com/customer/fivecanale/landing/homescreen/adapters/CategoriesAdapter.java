package com.customer.fivecanale.landing.homescreen.adapters;

import static com.customer.fivecanale.util.EcomConstants.FIVE;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.VIEW_ALL;
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
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.customer.domain.model.home.CategoryData;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ItemHomeCategoriesListBinding;
import java.util.ArrayList;

/*
 * Purpose – This class Holds Categories UI elements display in the HomePage.
 * @author 3Embed
 * Created on Nov 15, 2019
 * Modified on
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
  private ArrayList<CategoryData> mList;
  private Context mContext;
  private CategoryOnClickListener mCategoryOnClickListener;

  CategoriesAdapter(ArrayList<CategoryData> arrayList, CategoryOnClickListener clickListener) {
    mList = arrayList;
    this.mCategoryOnClickListener = clickListener;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    ItemHomeCategoriesListBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_home_categories_list, parent, false);
    binding.getRoot().getRootView().getLayoutParams().width = EcomUtil.getScreenWidth(
        parent.getContext()) / FIVE;
    return new ViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    if (position == ZERO) {
      holder.mBinding.ivHomeItemCategories.setImageResource(R.drawable.home_all_categories);
    }
    holder.mBinding.tvHomeItemCategoriesName.setText(mList.get(position).getCatName());

      String imageUrl = mList.get(position).getImageUrl();
      if (!TextUtils.isEmpty(imageUrl)) {
        Glide.with(mContext)
                .load(imageUrl.replace(" ", "%20"))
                .apply(new RequestOptions().circleCrop())
                .into(new SimpleTarget<Drawable>() {
                  @Override
                  public void onResourceReady(@NonNull Drawable drawable,
                                              @Nullable Transition<? super Drawable>
                                                      transition) {
                    holder.mBinding.ivHomeItemCategories.setImageDrawable(drawable);
                  }

                });
    }
    holder.mBinding.clCatMain.setOnClickListener(v -> {
      if (position == ZERO) {
        mCategoryOnClickListener.categoryOnClick();
      } else {
        mCategoryOnClickListener.catViewMoreOnClickListener(mList.get(position).getId(),
            mList.get(position).getCatName());
      }
    });
  }

  @Override
  public int getItemCount() {
    return EcomUtil.isEmptyArray(mList) ? ZERO : mList.size();
  }

  /**
   * Category item onClick listener
   */
  public interface CategoryOnClickListener {

    /**
     * Category onClick Listener Callback method which is implemented in HomeFragment
     */
    void categoryOnClick();

    /**
     * This method is using to redirect to categoryList page
     *
     * @param catId   catId to get Categories under this category
     * @param catName canName to get Categories under this category
     */
    void catViewMoreOnClickListener(String catId, String catName);
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    ItemHomeCategoriesListBinding mBinding;

    ViewHolder(@NonNull ItemHomeCategoriesListBinding binding) {
      super(binding.getRoot());
      this.mBinding = binding;
    }
  }
}
