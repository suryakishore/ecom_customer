package com.customer.fivecanale.uiutil;

import static com.customer.fivecanale.util.EcomConstants.EIGHT;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Context;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import com.R;
import com.customer.fivecanale.landing.homescreen.adapters.ViewPagerAdapter;
import java.util.Objects;

/*
 * Purpose – This class holds ViewPageScroll listenre
 * @author 3Embed
 * Created on Nov 25, 2019
 * Modified on
 */
public class ViewPagerScrollChangeListener {
  private static int dotsCount;
  private static AppCompatImageView[] dots;

  /**
   * this methods is using to listen scroll listen of ViewPager
   *
   * @param viewPagerAdapter adapter reference
   * @param llHomeDots       dots to show
   * @param vpHomeBanner     view pager reference
   * @param context          context to show
   */
  public static void onScrollChangeListener(
      ViewPagerAdapter viewPagerAdapter, LinearLayout llHomeDots, ViewPager vpHomeBanner,
      Context context) {
    viewPagerAdapter.notifyDataSetChanged();
    dotsCount = viewPagerAdapter.getCount();
    if (dotsCount != ZERO) {
      dots = new AppCompatImageView[dotsCount];
      for (int i = ZERO; i < dotsCount; i++) {
        dots[i] = new AppCompatImageView(Objects.requireNonNull(context));
        dots[i].setImageDrawable(
            ContextCompat.getDrawable(
                context.getApplicationContext(), R.drawable.non_active_dot));
        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(EIGHT, ZERO, EIGHT, ZERO);
        llHomeDots.addView(dots[i], params);
      }
      dots[ZERO].setImageDrawable(
          ContextCompat.getDrawable(
              Objects.requireNonNull(context).getApplicationContext(),
              R.drawable.active_dot));
      vpHomeBanner.addOnPageChangeListener(
          new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(
                int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
              for (int i = ZERO; i < dotsCount; i++) {
                dots[i].setImageDrawable(
                    ContextCompat.getDrawable(
                        Objects.requireNonNull(context).getApplicationContext(),
                        R.drawable.non_active_dot));
              }
              dots[position].setImageDrawable(
                  ContextCompat.getDrawable(
                      Objects.requireNonNull(context).getApplicationContext(),
                      R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
          });
    }
  }
}