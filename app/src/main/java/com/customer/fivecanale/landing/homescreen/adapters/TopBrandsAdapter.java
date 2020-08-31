package com.customer.fivecanale.landing.homescreen.adapters;

import static com.customer.fivecanale.util.EcomConstants.BRAND_HEIGHT;
import static com.customer.fivecanale.util.EcomConstants.BRAND_WIDTH;
import static com.customer.fivecanale.util.EcomConstants.EMPTY;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
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
import com.databinding.ItemHomeTopBrandsListBinding;
import java.util.ArrayList;

/*
 * Purpose – This class Holds Top Brands UI elements display in the HomePage.
 * @author 3Embed
 * Created on Nov 21, 2019
 * Modified on
 */
public class TopBrandsAdapter extends RecyclerView.Adapter<TopBrandsAdapter.ViewHolder> {
  private ArrayList<CategoryData> mBrandsList;
  private BrandItemClickListener mClickListener;

  TopBrandsAdapter(ArrayList<CategoryData> brandsArrayList, BrandItemClickListener clickListener) {
    mBrandsList = brandsArrayList;
    this.mClickListener = clickListener;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemHomeTopBrandsListBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_home_top_brands_list, parent, false);
    return new ViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    String imageUrl = mBrandsList.get(position).getBannerImage();
    RequestOptions myOptions = new RequestOptions()
        .override(BRAND_WIDTH, BRAND_HEIGHT)
        .autoClone();
    if (!TextUtils.isEmpty(imageUrl)) {
      Glide.with(holder.mBinding.ivHomeItemBrands.getContext())
          .load(imageUrl.replace(EMPTY, "%20"))
          .apply(myOptions)
          .into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable drawable,
                @Nullable Transition<? super Drawable> transition) {
              holder.mBinding.ivHomeItemBrands.setImageDrawable(drawable);
            }
          });
    }
    holder.mBinding.clMain.setOnClickListener(
        view -> mClickListener.onBrandItemClickListener(mBrandsList.get(position).getName()));
  }

  @Override
  public int getItemCount() {
    return mBrandsList != null ? mBrandsList.size() : ZERO;
  }

  public interface BrandItemClickListener {
    void onBrandItemClickListener(String brandName);
  }

  /**
   * Top brand Images UI Holder for HomePage
   */
  class ViewHolder extends RecyclerView.ViewHolder {
    ItemHomeTopBrandsListBinding mBinding;

    ViewHolder(@NonNull ItemHomeTopBrandsListBinding binding) {
      super(binding.getRoot());
      this.mBinding = binding;
    }
  }
}