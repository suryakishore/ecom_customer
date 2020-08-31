package com.customer.fivecanale.uiutil;

import android.app.Activity;
import com.R;

/*
 * Purpose – This class holds different types of Animations that are generic
 * @author 3Embed
 * Created on Nov 25, 2019
 * Modified on
 */
public class AnimUtil {

  /**
   * Activity transition animation between one activity to another activity
   *
   * @param activity activity reference to be holding this
   */
  public static void activityTransitionAnimation(Activity activity) {
    activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
  }
}