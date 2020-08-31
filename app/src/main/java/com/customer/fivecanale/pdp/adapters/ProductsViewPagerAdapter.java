package com.customer.fivecanale.pdp.adapters;

import static com.customer.fivecanale.util.EcomConstants.MOBILE_IMAGES;
import static com.customer.fivecanale.util.EcomConstants.POSITION;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_IMAGE;
import static com.customer.fivecanale.util.EcomConstants.PROFILE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.customer.domain.model.common.ImageData;
import com.customer.fivecanale.ApplicationManager;
import com.customer.fivecanale.pdp.ProductDetailsActivity;
import com.customer.fivecanale.pdp.imagezoom.ZoomImageAct;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ItemPdpProductsViewPagerListBinding;
import java.util.ArrayList;

/**
 * adapter class for products
 */
public class ProductsViewPagerAdapter extends PagerAdapter {
  private ArrayList<ImageData> mImageDataArrayList;
  private Activity mContext;

  /**
   * constructor for this products view pager adapter
   *
   * @param context activity reference for the pdp activity.
   * @param images  images arrayList.
   */
  public ProductsViewPagerAdapter(Activity context, ArrayList<ImageData> images) {
    this.mContext = context;
    this.mImageDataArrayList = images;
  }

  @Override
  public int getCount() {
    return mImageDataArrayList != null ? mImageDataArrayList.size() : ZERO;
  }

  @Override
  public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
    return view == object;
  }

  @NonNull
  @Override
  public Object instantiateItem(@NonNull ViewGroup container, final int position) {
    ItemPdpProductsViewPagerListBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(container.getContext()),
        R.layout.item_pdp_products_view_pager_list, container, false);
    String imageUrl = mImageDataArrayList.get(position).getMedium();
    EcomUtil.printLog("exe" + imageUrl);
    if (imageUrl != null && !"".equals(imageUrl)) {
      Glide.with(ApplicationManager.getInstance())
          .load(imageUrl.trim())
          .into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable drawable,
                @Nullable Transition<? super Drawable>
                    transition) {
              binding.ivViewPager.setImageDrawable(drawable);
            }
          });
    }
    binding.ivViewPager.setOnClickListener(v -> {
      if (mContext instanceof ProductDetailsActivity) {
        Intent intent = new Intent(mContext, ZoomImageAct.class);
        intent.putExtra(PRODUCT_IMAGE, imageUrl);
        intent.putExtra(MOBILE_IMAGES, mImageDataArrayList);
        intent.putExtra(POSITION, position);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            mContext, binding.ivViewPager, PROFILE);
        mContext.startActivity(intent, options.toBundle());
        mContext.overridePendingTransition(R.anim.slide_up, R.anim.stay);
      }
    });
    ViewPager vp = (ViewPager) container;
    vp.addView(binding.getRoot(), ZERO);
    return binding.getRoot();
  }

  @Override
  public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    ViewPager vp = (ViewPager) container;
    View view = (View) object;
    vp.removeView(view);
  }
}