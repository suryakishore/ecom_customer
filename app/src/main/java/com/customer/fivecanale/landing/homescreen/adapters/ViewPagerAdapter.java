package com.customer.fivecanale.landing.homescreen.adapters;

import static com.customer.fivecanale.util.EcomConstants.EMPTY;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.customer.domain.model.home.CategoryData;
import java.util.ArrayList;

/*
 * Purpose – This class Holds Offer Banner UI elements display in the HomePage.
 * @author 3Embed
 * Created on Nov 19, 2019
 * Modified on
 */
public class ViewPagerAdapter extends PagerAdapter {

  private ArrayList<CategoryData> mBannerImageDataList;
  private ViewPagerItemClickListener mClickListener;

  ViewPagerAdapter(ArrayList<CategoryData> bannerImageDataArrayList,
      ViewPagerItemClickListener clickListener) {
    mBannerImageDataList = bannerImageDataArrayList;
    this.mClickListener = clickListener;
  }

  @Override
  public int getCount() {
    return mBannerImageDataList != null ? mBannerImageDataList.size() : ZERO;
  }

  @Override
  public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
    return view == object;
  }

  @NonNull
  @Override
  public Object instantiateItem(@NonNull ViewGroup container, final int position) {

    LayoutInflater layoutInflater = (LayoutInflater) container.getContext().getSystemService(
        Context.LAYOUT_INFLATER_SERVICE);
    View view = layoutInflater.inflate(R.layout.item_home_view_pager_list, container, false);
    ImageView imageView = view.findViewById(R.id.imageView);
    String imageUrl = mBannerImageDataList.get(position).getImageMobile();
    if (!TextUtils.isEmpty(imageUrl)) {
      Glide.with(container.getContext())
          .load(imageUrl.replace(EMPTY, "%20"))
          .into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable drawable,
                @Nullable Transition<? super Drawable>
                    transition) {
              imageView.setImageDrawable(drawable);
            }

          });
    }
    ViewPager vp = (ViewPager) container;
    vp.addView(view, ZERO);
    imageView.setOnClickListener(
        view1 -> mClickListener.onBannerClickListener(mBannerImageDataList.get(position)));
    return view;
  }

  @Override
  public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    ViewPager vp = (ViewPager) container;
    View view = (View) object;
    vp.removeView(view);
  }

  public interface ViewPagerItemClickListener {
    void onBannerClickListener(CategoryData categoryData);
  }
}