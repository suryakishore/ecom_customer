package com.customer.fivecanale.landing.homescreen.adapters;

import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.bumptech.glide.Glide;
import com.customer.domain.model.home.HomeOfferData;
import com.databinding.ItemMediumBannerSliderBinding;
import java.util.List;

/*
 * Purpose – This class Holds Offer Banner Images under the Category items in Grid UI format that
 *  will be display in the
 * HomePage.
 * @author 3Embed
 * Created on Nov 29, 2019
 * Modified on
 */
public class MediumSliderAdapter extends
    RecyclerView.Adapter<MediumSliderAdapter.SlidingBannerViewHolder> {

  private List<HomeOfferData> mBanners;
  private CategoryOfferClickListener mClickListener;

  MediumSliderAdapter(List<HomeOfferData> banners, CategoryOfferClickListener clickListener) {
    this.mBanners = banners;
    this.mClickListener = clickListener;
  }

  @NonNull
  @Override
  public SlidingBannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    ItemMediumBannerSliderBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_medium_banner_slider, parent, false);
    return new SlidingBannerViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull SlidingBannerViewHolder holder, int position) {

    if (!TextUtils.isEmpty(mBanners.get(position).getMobimage())) {
      Glide.with(holder.mBinding.ivHomeSliding.getContext())
          .load(mBanners.get(position).getMobimage())
          .into(holder.mBinding.ivHomeSliding);
    }
    holder.mBinding.cvOfferBanner.setOnClickListener(
        view -> mClickListener.onCategoryOfferBannerClickListener(
            mBanners.get(position).getOfferId(),
            mBanners.get(position).getOfferName()));

  }

  @Override
  public int getItemCount() {
    return null != mBanners ? mBanners.size() : ZERO;
  }

  class SlidingBannerViewHolder extends RecyclerView.ViewHolder {

    ItemMediumBannerSliderBinding mBinding;

    SlidingBannerViewHolder(@NonNull ItemMediumBannerSliderBinding binding) {
      super(binding.getRoot());
      this.mBinding = binding;
    }
  }

  public interface CategoryOfferClickListener {
    void onCategoryOfferBannerClickListener(String offerId, String offerName);
  }
}
