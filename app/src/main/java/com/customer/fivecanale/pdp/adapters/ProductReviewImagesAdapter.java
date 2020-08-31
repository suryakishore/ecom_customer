package com.customer.fivecanale.pdp.adapters;

import static com.customer.fivecanale.util.EcomConstants.ONE_FIFTY_FIVE;
import static com.customer.fivecanale.util.EcomConstants.TWO;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.databinding.ItemReviewImagesBinding;
import java.util.ArrayList;

/**
 * adapter class for the review images
 */
public class ProductReviewImagesAdapter extends
    RecyclerView.Adapter<ProductReviewImagesAdapter.ReviewImagesViewHolder> {
  private ArrayList<String> mReviewImages;
  private Context mContext;

  /**
   * constructor for review images.
   *
   * @param reviewImages review images arrayList.
   */
  public ProductReviewImagesAdapter(ArrayList<String> reviewImages) {
    this.mReviewImages = reviewImages;
  }

  @NonNull
  @Override
  public ProductReviewImagesAdapter.ReviewImagesViewHolder onCreateViewHolder(
      @NonNull ViewGroup parent, int viewType) {
    this.mContext = parent.getContext();
    ItemReviewImagesBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_review_images, parent, false);
    return new ReviewImagesViewHolder(binding);
  }

  @SuppressLint("CheckResult")
  @Override
  public void onBindViewHolder(@NonNull ProductReviewImagesAdapter.ReviewImagesViewHolder holder,
      int position) {
    String imageUrl = mReviewImages.get(position);
    if (imageUrl != null && !"".equals(imageUrl)) {
      RequestOptions requestOptions = new RequestOptions();
      requestOptions.override(ONE_FIFTY_FIVE, ONE_FIFTY_FIVE).transforms(new CenterCrop(),
          new RoundedCorners(TWO));
      Glide.with(mContext)
          .load(imageUrl.trim())
          .apply(requestOptions)
          .into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable drawable,
                @Nullable Transition<? super Drawable>
                    transition) {
              holder.mBinding.ivReview.setImageDrawable(drawable);
            }
          });
    }
  }

  @Override
  public int getItemCount() {
    return mReviewImages != null ? mReviewImages.size() : ZERO;
  }

  /**
   * view holder class for the review images.
   */
  class ReviewImagesViewHolder extends RecyclerView.ViewHolder {
    private ItemReviewImagesBinding mBinding;

    ReviewImagesViewHolder(@NonNull ItemReviewImagesBinding binding) {
      super(binding.getRoot());
      this.mBinding = binding;
    }
  }
}