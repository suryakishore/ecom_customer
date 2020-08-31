package com.customer.fivecanale.pdp.imagezoom;

import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import com.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.customer.domain.model.common.ImageData;
import com.customer.fivecanale.util.customimageview.PhotoView;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;

/*
 * Purpose – This class Holds UI all the images .
 * @author 3Embed
 * Created on Feb 27, 2020
 * Modified on
 */
public class ZoomSlider extends PagerAdapter {
  private ArrayList<ImageData> mImageData;
  private LayoutInflater mInflater;
  private Activity mContext;

  ZoomSlider(Activity context, ArrayList<ImageData> images) {
    this.mContext = context;
    this.mImageData = images;
    mInflater = LayoutInflater.from(context);
  }

  @Override
  public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    container.removeView((View) object);
  }

  @Override
  public int getCount() {
    return mImageData.size();
  }

  @NonNull
  @Override
  public Object instantiateItem(@NonNull ViewGroup view, int position) {
    View imageLayout = mInflater.inflate(R.layout.single_image_row, view, false);
    assert imageLayout != null;
    final PhotoView imageView = imageLayout.findViewById(R.id.imageIv);
    String imageUrl = mImageData.get(position).getExtraLarge();
    if (!TextUtils.isEmpty(imageUrl)) {
      Glide.with(mContext)
          .load(imageUrl.trim())
          .into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable drawable,
                @Nullable Transition<? super Drawable>
                    transition) {
              imageView.setImageDrawable(drawable);
            }
          });
    }
    view.addView(imageLayout, ZERO);
    return imageLayout;
  }

  @Override
  public boolean isViewFromObject(View view, @NotNull Object object) {
    return view.equals(object);
  }
}
