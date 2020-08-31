package com.customer.fivecanale.util;

import static android.content.Context.WIFI_SERVICE;
import static com.customer.fivecanale.util.EcomConstants.FB_PROFILE_PIC_FIRST;
import static com.customer.fivecanale.util.EcomConstants.FB_PROFILE_PIC_LAST;
import static com.customer.fivecanale.util.EcomConstants.FOUR;
import static com.customer.fivecanale.util.EcomConstants.GET_IP_ADDRESS;
import static com.customer.fivecanale.util.EcomConstants.NINE;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.SEVEN;
import static com.customer.fivecanale.util.EcomConstants.SIXTY;
import static com.customer.fivecanale.util.EcomConstants.TEN;
import static com.customer.fivecanale.util.EcomConstants.THOUSAND;
import static com.customer.fivecanale.util.EcomConstants.THREE;
import static com.customer.fivecanale.util.EcomConstants.TWO;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.text.format.Formatter;
import android.util.Log;
import android.util.Patterns;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.R;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*holds all reusable methods */
public class EcomUtil {
  public static boolean isEmpty(Object object) {
    return object == null;
  }

  public static boolean isStringValidEmpty(String object) {
    Log.d("exe", "object" + object);
    if (object == null) {
      return true;
    } else {
      return object.length() <= 0;
    }
  }

  public static boolean isEmptyArray(List object) {
    return object == null || object.size() == ZERO;
  }

  /**
   * <h>Get Window width</h>
   *
   * @param context Context reference
   * @return retuns window size
   */
  public static int getScreenWidth(Context context) {
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = wm.getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    return size.x;
  }

  /*matches the email format*/
  public static boolean isEmail(@Nullable CharSequence str) {
    return Patterns.EMAIL_ADDRESS.matcher(str).matches();
  }

  /**
   * return if a name is valid or not
   *
   * @param str CharSequence
   * @return boolean
   */
  public static boolean isName(@Nullable CharSequence str) {
    final String regex = "^[\\p{L} .'-]+$";
    Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    if (str != null) {
      Matcher matcher = pattern.matcher(str);
      return matcher.find();
    }
    return false;
  }

  /**
   * returns the timer in minutes and seconds format for each millisecond.
   */
  public static StringBuilder getTimerValue(long millisUntilFinished) {
    StringBuilder stringBuilder = new StringBuilder();
    long secondsUntilFinished = millisUntilFinished / THOUSAND;
    long seconds = secondsUntilFinished % SIXTY;
    long mins = secondsUntilFinished / SIXTY;
    String finalSec = (seconds > NINE) ? "" + seconds : "" + ZERO + seconds;
    String finalMin = (mins > NINE) ? "" + mins : "" + ZERO + mins;
    stringBuilder.delete(ZERO, stringBuilder.length());
    stringBuilder.append(finalMin).append(":").append(finalSec);
    return stringBuilder;
  }

  public static void setViewPagerDots(Context context, ImageView[] dots, int dotsCount,
      LinearLayout llPdpProductImagePosition) {
    for (int i = 0; i < dotsCount; i++) {
      dots[i] = new ImageView(context);
      dots[i].setImageDrawable(context.getResources().getDrawable(R.drawable.nonselecteditem_dot));
      LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
          LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
      params.setMargins(FOUR, ZERO, FOUR, ZERO);
      llPdpProductImagePosition.addView(dots[i], params);
    }
    dots[0].setImageDrawable(context.getResources().getDrawable(R.drawable.selecteditem_dot));
  }

  public static void setViewPagerSelectedDot(Context context, ImageView[] dots, int dotsCount,
      int position) {
    for (int i = 0; i < dotsCount; i++) {
      dots[i].setImageDrawable(context.getResources().getDrawable(R.drawable.nonselecteditem_dot));
    }
    dots[position].setImageDrawable(
        context.getResources().getDrawable(R.drawable.selecteditem_dot));
  }

  /*generates the deep link for sharing*/
  public static void generateDeepLink(Intent intent, Activity activity) {
    FirebaseDynamicLinks.getInstance()
        .getDynamicLink(intent)
        .addOnSuccessListener(activity, pendingDynamicLinkData -> {
          Uri deepLink = null;
          if (pendingDynamicLinkData != null) {
            deepLink = pendingDynamicLinkData.getLink();
          }
          if (deepLink != null) {
            Uri data = Uri.parse(deepLink.toString());
            String pathSegments = data.getPath();
            if (pathSegments != null && !"".equals(pathSegments)) {
              int beginIndex = pathSegments.indexOf(":");
              int endIndex = pathSegments.indexOf("/00");
              String prefix = pathSegments.substring(beginIndex + 1, endIndex);
              //  mProductId = prefix;
              EcomUtil.printLog("getDynamicLink" + "prefix" + prefix);
            }
          } else {
            EcomUtil.printLog("getDynamicLink" + "null");
          }
        })
        .addOnFailureListener(activity, e -> EcomUtil.printLog("getDynamicLink" + "Error"));
  }

  public static boolean isValidPhone(String phone) {
    return phone.trim().length() >= SEVEN;
  }

  /**
   * used to get the ip address of the network which we have connected.
   *
   * @return ip address
   */
  public static String getIpAddress() {
    String fullString = "";
    URL url = null;
    try {
      url = new URL(GET_IP_ADDRESS);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new InputStreamReader(url.openStream()));
    } catch (IOException e) {
      e.printStackTrace();
    }
    String line = null;
    while (true) {
      try {
        if (reader != null && !((line = reader.readLine()) != null)) {
          break;
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      fullString += line;
    }
    try {
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return fullString;
  }

  public static String getIpAddress(Context context) {
    String ip = "";
    try {
      WifiManager wm = (WifiManager) context.getSystemService(WIFI_SERVICE);
      ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ip;
  }

  /**
   * returns the facebook url
   *
   * @param id facebook id
   * @return return the fb profile pic url.
   */
  public static String getFbUserProfilePic(String id) {
    return MessageFormat.format("{0}{1}{2}", FB_PROFILE_PIC_FIRST, id, FB_PROFILE_PIC_LAST);
  }

  /**
   * return date format
   *
   * @param time time stamp
   * @return return time string formate
   */
  public static String getDate(long time) {
    Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    cal.setTimeInMillis(time * 1000L);
    int day = cal.get(Calendar.DATE);
    switch (day % TEN) {
      case ONE:
        return DateFormat.format("dd'st' MMM yyyy hh:mm a", cal).toString();
      case TWO:
        return DateFormat.format("dd'nd' MMM yyyy hh:mm a", cal).toString();
      case THREE:
        return DateFormat.format("dd'rd' MMM yyyy hh:mm a", cal).toString();
      default:
        return DateFormat.format("dd'th' MMM yyyy hh:mm a", cal).toString();
    }
  }

  /**
   * return date format
   *
   * @param time time stamp
   * @return return time string formate
   */
  public static String getDateForHistoryDet(long time) {
    Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    cal.setTimeInMillis(time * 1000L);
    return DateFormat.format("dd MMMM yyyy hh:mm", cal).toString();
  }

  public static void downloadFile(Context context, String url) {
    Uri uri = Uri.parse(url);
    DownloadManager downloadManager = (DownloadManager) context.getSystemService(
        Context.DOWNLOAD_SERVICE);
    DownloadManager.Request request = new DownloadManager.Request(uri);
    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
        | DownloadManager.Request.NETWORK_MOBILE);
    request.setTitle("Data Download");
    request.setDescription("Android Data download using DownloadManager.");
    request.setAllowedOverRoaming(false);
    request.setVisibleInDownloadsUi(true);
    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/pdfName" + ".pdf");
    request.setMimeType("application/pdf");
    downloadManager.enqueue(request);
  }

  public static boolean isNetworkAvailable(Context context) {
    ConnectivityManager connectivity = null;
    try {
      connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      if (connectivity != null) {
        NetworkInfo[] info = connectivity.getAllNetworkInfo();
        if (info != null) {
          for (int i = 0; i < info.length; i++) {
            if (info[i].getState() == NetworkInfo.State.CONNECTED) {
              return true;
            }
          }
        }
      }
      return false;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (connectivity != null) {
        connectivity = null;
      }
    }
    return false;
  }

  public static void printLog(String msg) {
    Log.i("Delivx", "msg  " + msg);
  }

  public static void copyStream(InputStream input, OutputStream output) throws IOException {
    byte[] buffer = new byte[1024];
    int bytesRead;
    while ((bytesRead = input.read(buffer)) != -1) {
      output.write(buffer, 0, bytesRead);
    }
  }

  public static void hideSoftKeyboard(View view) {
    try {
      InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(
          Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    } catch (Exception e) {
    }
  }

  public static void showSoftKeyboard(View view) {
    try {
      InputMethodManager inputMethodManager =
          (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
      inputMethodManager.toggleSoftInputFromWindow(view.getApplicationWindowToken(),
          InputMethodManager.SHOW_FORCED, 0);
    } catch (Exception e) {
    }
  }

  public static void showKeyboard(Activity activity, View view) {
    InputMethodManager imm = (InputMethodManager) activity.getSystemService(
        Context.INPUT_METHOD_SERVICE);
    imm.showSoftInput(view, 0);
  }

  public static void hideKeyboard(Activity mcontext) {
    try {
      InputMethodManager inputManager = (InputMethodManager) mcontext.getSystemService(
          Context.INPUT_METHOD_SERVICE);
      inputManager.hideSoftInputFromWindow(mcontext.getCurrentFocus().getWindowToken(),
          InputMethodManager.RESULT_UNCHANGED_SHOWN);
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
  }

  public static String getDeviceId(Context context) {
    // use the ANDROID_ID constant, generated at the first device boot
    String deviceId = Settings.Secure.getString(context.getContentResolver(),
        Settings.Secure.ANDROID_ID);
    // in case known problems are occured
    if ("9774d56d682e549c".equals(deviceId) || deviceId == null) {
      // getProductList a unique deviceID like IMEI for GSM or ESN for CDMA phones
      // don't forget:
      //
      deviceId = Build.SERIAL;
      // if nothing else works, generate a random number
      if (deviceId == null) {
        Random tmpRand = new Random();
        deviceId = String.valueOf(tmpRand.nextLong());
      }
    }
    return deviceId;
  }

  public static void strikeThroughText(TextView price) {
    price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
  }

  public static String getColoredSpanned(String text, String color) {
    String input = "<font color=" + color + ">" + text + "</font>";
    return input;
  }
}
