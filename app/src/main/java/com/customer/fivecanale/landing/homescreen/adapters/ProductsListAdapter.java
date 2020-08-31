package com.customer.fivecanale.landing.homescreen.adapters;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.customer.fivecanale.util.EcomConstants.ONE_MINUTE_MILLI_SECOND;
import static com.customer.fivecanale.util.EcomConstants.PARENT_PRODUCT_ID;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_ID;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.bumptech.glide.Glide;
import com.customer.domain.model.home.CategoryData;
import com.customer.fivecanale.pdp.ProductDetailsActivity;
import com.customer.fivecanale.util.DateAndTimeUtil;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ItemHomeProductsListBinding;
import java.util.ArrayList;

/*
 * Purpose – This class Holds Products List display in the HomePage.
 * @author 3Embed
 * Created on Oct 21, 2019
 * Modified on
 */
public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ViewHolder> {
  private ArrayList<CategoryData> mProductList;
  private Context mContext;
 ProductsListAdapter(ArrayList<CategoryData> productList) {
    this.mProductList = productList;
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
    String currency = mProductList.get(position).getCurrencySymbol();
    holder.mBinding.tvHomeItemProductName.setText(mProductList.get(position).getProductName());
    holder.mBinding.tvHomeItemProductCategories.setText(mProductList.get(position).getBrandName());
    holder.mBinding.tvHomeItemProductPrice.setText(
        String.format("%s %s", currency, mProductList.get(
            position).getFinalPriceList().getFinalPrice()));
    holder.mBinding.tvHomeItemProductOfferPrice.setText(
        String.format("%s%s", currency, mProductList.get(
            position).getFinalPriceList().getBasePrice()));
    holder.mBinding.tvOutOfStock.setVisibility(
        mProductList.get(position).isOutOfStock() ? VISIBLE : View.GONE);
    holder.mBinding.clShadow.setVisibility(
        mProductList.get(position).isOutOfStock() ? VISIBLE : View.GONE);
    holder.mBinding.tvHomeOffer.setVisibility(mProductList.get(position).getOffers() != null
        ? VISIBLE : View.INVISIBLE);
    holder.mBinding.tvHomeItemProductOfferPrice.setVisibility(mProductList.get(position).getOffers()
        != null
        ? VISIBLE : View.INVISIBLE);
    if (mProductList.get(position).getOffers() != null) {
      String disType = mProductList.get(position).getOffers().getDiscountType() == ZERO
          ? mContext.getString(R.string.flat) : mContext.getString(R.string.upto);
      holder.mBinding.tvHomeOffer.setText(String.format("%s %s %% %s", disType, mProductList.get(
          position).getOffers().getDiscountValue(), mContext.getString(R.string.off)));
      if (mProductList.get(
          position).getOffers().getDiscountValue().equals("" + ZERO)) {
        holder.mBinding.tvHomeOffer.setVisibility(View.INVISIBLE);
        holder.mBinding.tvHomeItemProductOfferPrice.setVisibility(View.INVISIBLE);
      }
    }
    holder.mBinding.clShadow.setVisibility(
        mProductList.get(position).isOutOfStock() ? VISIBLE : GONE);
    holder.mBinding.tvOutOfStock.setVisibility(
        mProductList.get(position).isOutOfStock() ? VISIBLE : GONE);
    holder.mBinding.rbHomeItemProductRatings.setVisibility(mProductList.get(
        position).getTotalStarRating() > ZERO ? VISIBLE : View.INVISIBLE);
    holder.mBinding.rbHomeItemProductRatings.setRating(mProductList.get(
        position).getTotalStarRating());
    String imgUrl = mProductList.get(position).getImages().get(ZERO).getSmall();
    if (!TextUtils.isEmpty(imgUrl)) {
      imgUrl = imgUrl.trim();
      Glide.with(mContext).load(imgUrl).into(holder.mBinding.ivHomeItemProducts);
    }
    if (holder.timer != null) {
      holder.timer.cancel();
    }
    if (mProductList.get(position).getOffers() != null && mProductList.get(
        position).getOffers().getEndDateTimeISO() > ZERO) {
      holder.timer = new CountDownTimer(mProductList.get(
          position).getOffers().getEndDateTimeISO(), ONE_MINUTE_MILLI_SECOND) {
        @Override
        public void onTick(long l) {
          holder.mBinding.tvDealTimer.setVisibility(VISIBLE);
          holder.mBinding.tvDealTimer.setText(
              DateAndTimeUtil.findNumberOfDays(mContext, mProductList.get(
                  position).getOffers().getEndDateTimeISO()));
        }

        @Override
        public void onFinish() {
          holder.mBinding.tvDealTimer.setVisibility(View.INVISIBLE);
        }
      }.start();
    }
    holder.mBinding.clProductMain.setOnClickListener(
        v -> {
          Intent intent =
              new Intent(holder.mBinding.clProductMain.getContext(),
                  ProductDetailsActivity.class);
          intent.putExtra(PARENT_PRODUCT_ID, mProductList.get(position).getParentProductId());
          intent.putExtra(PRODUCT_ID, mProductList.get(position).getChildProductId());
          holder.mBinding.clProductMain.getContext().startActivity(intent);
        });
  }

  @Override
  public int getItemCount() {
    return EcomUtil.isEmptyArray(mProductList) ? ZERO : mProductList.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    ItemHomeProductsListBinding mBinding;
    CountDownTimer timer;

    ViewHolder(@NonNull ItemHomeProductsListBinding binding) {
      super(binding.getRoot());
      this.mBinding = binding;
      binding.tvHomeItemProductOfferPrice.setPaintFlags(
          binding.tvHomeItemProductOfferPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }
  }
}