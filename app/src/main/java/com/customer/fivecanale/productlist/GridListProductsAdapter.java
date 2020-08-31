package com.customer.fivecanale.productlist;

import static android.view.View.VISIBLE;
import static com.customer.fivecanale.util.EcomConstants.EMPTY;
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
import com.customer.domain.model.common.ProductsData;
import com.customer.fivecanale.pdp.ProductDetailsActivity;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ItemGridProductBinding;
import java.util.ArrayList;

/*
 * Purpose – This class Holds All the ProductListing page Items.
 * @author 3Embed
 * Created on Nov 05, 2019
 * Modified on
 */
public class GridListProductsAdapter extends
    RecyclerView.Adapter<GridListProductsAdapter.GridViewHolder> {

  private ArrayList<ProductsData> mProductsData;
  private Context mContext;

  public GridListProductsAdapter(ArrayList<ProductsData> data) {
    this.mProductsData = data;
  }

  @NonNull
  @Override
  public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    mContext = parent.getContext();

    ItemGridProductBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_grid_product, parent, false);
    return new GridViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {

    holder.mBinding.tvFinalPrice.setText(
        String.format("%s %s", mProductsData.get(position).getCurrencySymbol(), mProductsData.get(
            position).getFinalPriceList().getFinalPrice()));
    holder.mBinding.tvProductName.setText(mProductsData.get(position).getProductName());
    holder.mBinding.tvBrandName.setText(mProductsData.get(position).getBrandName());
    String imageUrl = mProductsData.get(position).getImages().get(ZERO).getSmall();

    holder.mBinding.rbHomeItemProductRatings.setVisibility(mProductsData.get(
        position).getTotalStarRating() > ZERO ? VISIBLE : View.INVISIBLE);
    holder.mBinding.rbHomeItemProductRatings.setRating(mProductsData.get(
        position).getTotalStarRating());
    holder.mBinding.clShadow.setVisibility(
        mProductsData.get(position).getOutOfStock() ? VISIBLE : View.GONE);
    holder.mBinding.tvOutOfStock.setVisibility(
        mProductsData.get(position).getOutOfStock() ? VISIBLE : View.GONE);
    if (!TextUtils.isEmpty(imageUrl)) {
      Glide.with(mContext)
          .load(imageUrl.replace(EMPTY, "%20"))
          .into(holder.mBinding.ivProductImage);
    }

    if (Integer.parseInt(mProductsData.get(position).getFinalPriceList().getDiscountPercentage())
        != ZERO) {
      holder.mBinding.tvActualPrice.setText(
          String.format("%s %s", mProductsData.get(position).getCurrencySymbol(), mProductsData.get(
              position).getFinalPriceList().getBasePrice()));
      holder.mBinding.tvActualPrice.setVisibility(View.VISIBLE);
      holder.mBinding.tvOnOffer.setVisibility(View.VISIBLE);
    } else {
      if (mProductsData.get(position).getFinalPriceList().getDiscountPrice() != null
          && !mProductsData.get(
          position).getFinalPriceList().getDiscountPrice().isEmpty() && !mProductsData.get(
          position).getFinalPriceList().getDiscountPrice().equals("0")) {
        holder.mBinding.tvActualPrice.setText(
            String.format("%s %s", mProductsData.get(position).getCurrencySymbol(),
                mProductsData.get(
                    position).getFinalPriceList().getBasePrice()));
        holder.mBinding.tvActualPrice.setVisibility(View.VISIBLE);
        holder.mBinding.tvOnOffer.setVisibility(View.VISIBLE);
      } else {
        holder.mBinding.tvActualPrice.setVisibility(View.GONE);
        holder.mBinding.tvOnOffer.setVisibility(View.GONE);
      }
    }
    holder.mBinding.clGridListItem.setOnClickListener(
        v -> {
          Intent intent =
              new Intent(holder.mBinding.clGridListItem.getContext(),
                  ProductDetailsActivity.class);
          intent.putExtra(PARENT_PRODUCT_ID, mProductsData.get(position).getParentProductId());
          intent.putExtra(PRODUCT_ID, mProductsData.get(position).getChildProductId());
          holder.mBinding.clGridListItem.getContext().startActivity(intent);
        });
  }

  @Override
  public int getItemCount() {
    return EcomUtil.isEmptyArray(mProductsData) ? ZERO : mProductsData.size();
  }

  static class GridViewHolder extends RecyclerView.ViewHolder {
    ItemGridProductBinding mBinding;

    GridViewHolder(ItemGridProductBinding binding) {
      super(binding.getRoot());
      this.mBinding = binding;
      binding.tvActualPrice.setPaintFlags(
          binding.tvActualPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }
  }
}