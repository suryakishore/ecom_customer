package com.customer.fivecanale.landing.homescreen.adapters;

import static android.view.View.VISIBLE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;
import static com.customer.remote.http.RemoteConstants.PARENT_PRODUCT_ID;
import static com.customer.remote.http.RemoteConstants.PRODUCT_ID;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.bumptech.glide.Glide;
import com.customer.domain.model.homesubcategory.SubCategoryProductData;
import com.customer.fivecanale.pdp.ProductDetailsActivity;
import com.databinding.ItemHomeProductsListBinding;
import java.util.ArrayList;

/*
 * Purpose – This class Holds Sub Categories Product Data UI.
 * @author 3Embed
 * Created on Nov 15, 2019
 * Modified on
 */
public class SubCatProductAdapter extends RecyclerView.Adapter<SubCatProductAdapter.ViewHolder> {
  private ArrayList<SubCategoryProductData> mProductData;
  private Context mContext;

  SubCatProductAdapter(ArrayList<SubCategoryProductData> productList) {
    this.mProductData = productList;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    ItemHomeProductsListBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_home_products_list, parent, false);
    return new ViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    String currency = mProductData.get(position).getCurrencySymbol();
    holder.mBinding.tvHomeItemProductName.setText(mProductData.get(position).getProductName());
    holder.mBinding.tvHomeItemProductCategories.setText(mProductData.get(position).getBrandName());
    holder.mBinding.rbHomeItemProductRatings.setVisibility(mProductData.get(
        position).getTotalStarRating() > ZERO ? VISIBLE : View.INVISIBLE);
    holder.mBinding.rbHomeItemProductRatings.setRating(mProductData.get(
        position).getTotalStarRating());
    holder.mBinding.tvHomeItemProductPrice.setText(
        String.format("%s %s", currency, mProductData.get(
            position).getFinalPriceList().getFinalPrice()));
    holder.mBinding.tvHomeItemProductOfferPrice.setText(
        String.format("%s %s", currency, mProductData.get(
            position).getFinalPriceList().getBasePrice()));
    holder.mBinding.tvHomeOffer.setVisibility(mProductData.get(position).getOffers() != null
        && !TextUtils.isEmpty(mProductData.get(
        position).getOffers().getOfferName()) ? View.VISIBLE : View.INVISIBLE);
    if (mProductData.get(position).getOffers() != null && !TextUtils.isEmpty(mProductData.get(
        position).getOffers().getOfferName())) {
      String disType = mProductData.get(position).getOffers().getDiscountType() == ZERO
          ? mContext.getString(
          R.string.flat) : mContext.getString(R.string.upto);
      holder.mBinding.tvHomeOffer.setText(String.format("%s %s %%", disType, mProductData.get(
          position).getOffers().getDiscountValue()));
    }
    String imgUrl = mProductData.get(position).getImages().get(ZERO).getSmall();
    if (!TextUtils.isEmpty(imgUrl)) {
      imgUrl = imgUrl.trim();
      Glide.with(mContext).load(imgUrl).into(holder.mBinding.ivHomeItemProducts);
    }
    holder.mBinding.clProductMain.setOnClickListener(
        v -> {
          Intent intent =
              new Intent(holder.mBinding.ivHomeItemProducts.getContext(),
                  ProductDetailsActivity.class);
          intent.putExtra(PARENT_PRODUCT_ID, mProductData.get(position).getParentProductId());
          intent.putExtra(PRODUCT_ID, mProductData.get(position).getChildProductId());
          holder.mBinding.ivHomeItemProducts.getContext().startActivity(intent);
        });
  }

  @Override
  public int getItemCount() {
    return mProductData != null ? mProductData.size() : ZERO;
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    ItemHomeProductsListBinding mBinding;

    ViewHolder(ItemHomeProductsListBinding binding) {
      super(binding.getRoot());
      this.mBinding = binding;
      binding.tvHomeItemProductOfferPrice.setPaintFlags(
          binding.tvHomeItemProductOfferPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }
  }
}