package com.customer.fivecanale.allbrands;

import static com.customer.fivecanale.util.EcomConstants.BRAND_HEIGHT;
import static com.customer.fivecanale.util.EcomConstants.BRAND_WIDTH;
import static com.customer.fivecanale.util.EcomConstants.EMPTY;
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
import com.customer.fivecanale.SelectItem;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ItemBrandBinding;
import java.util.ArrayList;

/**
 * adapter class for notifications
 */
public class AllBrandsAdapter extends RecyclerView.Adapter<AllBrandsAdapter.ViewHolder> {
  private Context mContext;
  private ArrayList<CategoryData> mCategoryData;
  private SelectItem mSelectItem;

  /**
   * constructor for this class
   *
   * @param categoryData arrayList category data
   */
  AllBrandsAdapter(ArrayList<CategoryData> categoryData, SelectItem selectItem) {
    this.mCategoryData = categoryData;
    this.mSelectItem = selectItem;
  }

  @NonNull
  @Override
  public AllBrandsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    mContext = parent.getContext();
    ItemBrandBinding itemBrandBinding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_brand, parent, false);
    return new ViewHolder(itemBrandBinding);
  }

  @Override
  public void onBindViewHolder(@NonNull AllBrandsAdapter.ViewHolder holder, int position) {
    CategoryData categoryData = mCategoryData.get(position);
    String imageUrl = categoryData.getBannerImage();
    EcomUtil.printLog("exe" + "BannerImage" + imageUrl);
    RequestOptions myOptions = new RequestOptions()
        .override(BRAND_WIDTH, BRAND_HEIGHT)
        .autoClone();
    if (!TextUtils.isEmpty(imageUrl)) {
      Glide.with(holder.mItemBrandBinding.ivBrandLogo.getContext())
          .load(imageUrl.replace(EMPTY, "%20"))
          .apply(myOptions)
          .into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable drawable,
                @Nullable Transition<? super Drawable> transition) {
              holder.mItemBrandBinding.ivBrandLogo.setImageDrawable(drawable);
            }
          });
    }
    holder.mItemBrandBinding.clItemBrand.setOnClickListener(
        view -> {
          mSelectItem.onSelectItem(position);
        });
  }

  @Override
  public int getItemCount() {
    return mCategoryData != null ? mCategoryData.size() : ZERO;
  }

  /**
   * view holder class
   */
  class ViewHolder extends RecyclerView.ViewHolder {
    ItemBrandBinding mItemBrandBinding;

    /**
     * view holder class constructor
     *
     * @param itemBrandBinding binding reference.
     */
    ViewHolder(@NonNull ItemBrandBinding itemBrandBinding) {
      super(itemBrandBinding.getRoot());
      mItemBrandBinding = itemBrandBinding;
    }
  }
}
