package com.customer.fivecanale.preview;

import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;
import com.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.databinding.ItemPreviewImageBinding;
import java.util.ArrayList;

/**
 * pager adapter for reviews.
 */
public class PreviewPagerAdapter extends PagerAdapter {
  private ArrayList<String> mReviewImages;

  /**
   * constructor for this PreviewPagerAdapter class
   * @param reviewImages review images arrayList
   */
  PreviewPagerAdapter(ArrayList<String> reviewImages) {
    this.mReviewImages = reviewImages;
  }

  @NonNull
  @Override
  public Object instantiateItem(@NonNull ViewGroup container, int position) {
    ItemPreviewImageBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(container.getContext()),
        R.layout.item_preview_image, container, false);
    String imageUrl = mReviewImages.get(position);
    if (imageUrl != null && !"".equals(imageUrl)) {
      Glide.with(container.getContext())
          .load(imageUrl.trim())
          .into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable drawable,
                @Nullable Transition<? super Drawable>
                    transition) {
              binding.ivPreview.setImageDrawable(drawable);
            }
          });
    }
    container.addView(binding.getRoot(), ZERO);
    return binding.getRoot();
  }

  @Override
  public int getCount() {
    return mReviewImages != null ? mReviewImages.size() : ZERO;
  }

  @Override
  public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
    return view.equals(object);
  }

  @Override
  public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    container.removeView((View) object);
  }
}