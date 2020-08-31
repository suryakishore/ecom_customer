package com.customer.fivecanale.review;

import static com.customer.fivecanale.util.EcomConstants.CAMERA_ITEM;
import static com.customer.fivecanale.util.EcomConstants.EIGHT;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.REVIEW_IMAGE_HEIGHT;
import static com.customer.fivecanale.util.EcomConstants.REVIEW_IMAGE_WIDTH;
import static com.customer.fivecanale.util.EcomConstants.TWO;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

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
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ItemCameraBinding;
import com.databinding.ItemReviewImagesBinding;
import java.io.File;
import java.util.ArrayList;

/**
 * adapter class for the review products
 */
public class ReviewProductsAdapter extends
    RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private static final int TYPE_CAMERA = TWO;
  private static final int TYPE_IMAGE = ONE;
  private ArrayList<String> mReviewImagesList;
  private Context mContext;
  private ReviewProductCameraClick mReviewProductCameraClick;

  /**
   * constructor for review products
   *
   * @param reviewImagesList         arrayList for review images
   * @param reviewProductCameraClick interface listener for the review product camera click
   */
  ReviewProductsAdapter(ArrayList<String> reviewImagesList,
      ReviewProductCameraClick reviewProductCameraClick) {
    this.mReviewImagesList = reviewImagesList;
    this.mReviewProductCameraClick = reviewProductCameraClick;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    switch (viewType) {
      case TYPE_IMAGE:
        ItemReviewImagesBinding binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()),
            R.layout.item_review_images, parent, false);
        return new ReviewProductViewHolder(binding);
      case TYPE_CAMERA:
        ItemCameraBinding itemCameraBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()),
            R.layout.item_camera, parent, false);
        return new CameraViewHolder(itemCameraBinding);
      default:
        return null;
    }
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof ReviewProductViewHolder) {
      ReviewProductViewHolder reviewProductViewHolder = (ReviewProductViewHolder) holder;
      String imageUrl = mReviewImagesList.get(position);
      EcomUtil.printLog("imageUrl" + imageUrl);
      RequestOptions requestOptions = new RequestOptions();
      requestOptions.override(REVIEW_IMAGE_WIDTH, REVIEW_IMAGE_HEIGHT).transforms(
          new CenterCrop(), new RoundedCorners(EIGHT));
      if (imageUrl.contains("https:") || imageUrl.contains("http:")) {
        Glide.with(mContext)
            .load(imageUrl.trim())
            .apply(requestOptions)
            .into(new SimpleTarget<Drawable>() {
              @Override
              public void onResourceReady(@NonNull Drawable drawable,
                  @Nullable Transition<? super Drawable>
                      transition) {
                reviewProductViewHolder.mBinding.ivReview.setImageDrawable(drawable);
              }
            });
      } else {
        if (!"".equals(imageUrl)) {
          File file = new File(imageUrl);
          Glide.with(mContext)
              .load(file)
              .apply(requestOptions)
              .into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable drawable,
                    @Nullable Transition<? super Drawable>
                        transition) {
                  reviewProductViewHolder.mBinding.ivReview.setImageDrawable(drawable);
                }
              });
        }
      }
    } else {
      CameraViewHolder cameraViewHolder = (CameraViewHolder) holder;
      cameraViewHolder.itemView.setOnClickListener(view -> {
        mReviewProductCameraClick.onCameraClick();
      });
    }
  }

  @Override
  public int getItemCount() {
    return mReviewImagesList != null ? mReviewImagesList.size() : ZERO;
  }

  @Override
  public int getItemViewType(int position) {
    if (mReviewImagesList.get(position) != null && mReviewImagesList.get(position).equals(
        CAMERA_ITEM)) {
      return TYPE_CAMERA;
    }
    return TYPE_IMAGE;
  }

  /**
   * view holder class for the review product
   */
  class ReviewProductViewHolder extends RecyclerView.ViewHolder {
    ItemReviewImagesBinding mBinding;

    ReviewProductViewHolder(@NonNull ItemReviewImagesBinding binding) {
      super(binding.getRoot());
      this.mBinding = binding;
    }
  }

  /**
   * view holder class for the camera
   */
  class CameraViewHolder extends RecyclerView.ViewHolder {
    ItemCameraBinding mBinding;

    CameraViewHolder(@NonNull ItemCameraBinding binding) {
      super(binding.getRoot());
      this.mBinding = binding;
    }
  }
}