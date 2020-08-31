package com.customer.fivecanale.pdp.adapters;

import static com.customer.fivecanale.util.EcomConstants.EIGHT;
import static com.customer.fivecanale.util.EcomConstants.FIVE;
import static com.customer.fivecanale.util.EcomConstants.FOUR;
import static com.customer.fivecanale.util.EcomConstants.REVIEW_IMAGE_HEIGHT;
import static com.customer.fivecanale.util.EcomConstants.REVIEW_IMAGE_WIDTH;

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
import com.customer.fivecanale.allreviews.ReviewImgClick;
import com.databinding.ItemReviewImagesBinding;
import java.util.ArrayList;
import java.util.Objects;

/**
 * adapter class for the review images.
 */
public class ReviewImagesAdapter extends
    RecyclerView.Adapter<ReviewImagesAdapter.ReviewImagesViewHolder> {
  private ArrayList<String> mReviewImages;
  private ReviewImgClick mReviewImgClick;
  private Context mContext;
  private int mReviewPos;

  /**
   * constructor for the review images.
   *
   * @param reviewImages review images arrayList.
   */
  ReviewImagesAdapter(ArrayList<String> reviewImages, ReviewImgClick reviewImgClick,
      int reviewPos) {
    this.mReviewImages = reviewImages;
    this.mReviewImgClick = reviewImgClick;
    this.mReviewPos = reviewPos;
  }

  @NonNull
  @Override
  public ReviewImagesAdapter.ReviewImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    this.mContext = parent.getContext();
    ItemReviewImagesBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_review_images, parent, false);
    return new ReviewImagesViewHolder(binding);
  }

  @SuppressLint("CheckResult")
  @Override
  public void onBindViewHolder(@NonNull ReviewImagesAdapter.ReviewImagesViewHolder holder,
      int position) {
    String imageUrl = mReviewImages.get(position);
    if (imageUrl != null && !"".equals(imageUrl)) {
      RequestOptions requestOptions = new RequestOptions();
      requestOptions.override(REVIEW_IMAGE_WIDTH, REVIEW_IMAGE_HEIGHT).transforms(new CenterCrop(),
          new RoundedCorners(EIGHT));
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
    holder.mBinding.ivReview.setOnClickListener(
        view -> mReviewImgClick.onImgClick(position, mReviewPos));
  }

  @Override
  public int getItemCount() {
    return (mReviewImages != null && mReviewImages.size() > FOUR) ? FIVE : Objects.requireNonNull(
        mReviewImages).size();
  }

  /**
   * view holder class for the review images.
   */
  class ReviewImagesViewHolder extends RecyclerView.ViewHolder {
    ItemReviewImagesBinding mBinding;

    ReviewImagesViewHolder(@NonNull ItemReviewImagesBinding binding) {
      super(binding.getRoot());
      this.mBinding = binding;
    }
  }
}