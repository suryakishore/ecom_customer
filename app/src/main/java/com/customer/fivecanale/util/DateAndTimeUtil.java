package com.customer.fivecanale.util;

import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.THOUSAND;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.annotation.SuppressLint;
import android.content.Context;
import com.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/*
 * Purpose – This class holds different types of Date and time conversion logic
 * @author 3Embed
 * Created on Nov 25, 2019
 * Modified on
 */
public class DateAndTimeUtil {
  @SuppressLint("DefaultLocale")
  public static String findNumberOfDays(Context context, long endTime) {
    String diffTime;
    long msDiff = Math.max(endTime, Calendar.getInstance().getTimeInMillis()) -
            Math.min(endTime, Calendar.getInstance().getTimeInMillis());
    long daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);
    long hoursDiff = TimeUnit.MILLISECONDS.toHours(msDiff);
    long minutesDiff = TimeUnit.MILLISECONDS.toMinutes(msDiff);
    long secondDiff = TimeUnit.MILLISECONDS.toSeconds(msDiff);
    long minutes = minutesDiff % 60;
    long seconds = secondDiff % 60;
    if (daysDiff > 1) {
      long hrs = hoursDiff % 24;
      diffTime = String.format(
          String.format("%s %%d:%%d:%%d", context.getString(R.string.dealEnds)), daysDiff, hrs,
          minutes);
    } else {
      diffTime = String.format(
          String.format("%s %%d:%%d:%%d", context.getString(R.string.dealEnds)), hoursDiff,
          minutes, seconds);
    }
    return diffTime;
  }

  /**
   * get the time stamp based on month
   *
   * @param index month index
   * @return timestamp
   */
  public static String getCurrentTimestamp(int index) {
    Date d = new Date();
    Calendar c = new GregorianCalendar();
    c.setTime(d);
    switch (index) {
      case ZERO:
        c.add(Calendar.MONTH, -1);
        break;
      case ONE:
        c.add(Calendar.MONTH, -6);
        break;
    }
    Date oneMonthAgo = c.getTime();
    long oneMonthAgoMillis = oneMonthAgo.getTime() / THOUSAND;
    return String.valueOf(oneMonthAgoMillis);
  }

  /**
   * get the timestamp based on year
   *
   * @param year year
   * @return timestamp
   */
  public static String getTimeStampBasedOnYear(int year) {
    String strDate = "01-01-" + year;
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    Date date = null;
    try {
      date = (Date) formatter.parse(strDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return String.valueOf(date.getTime() / THOUSAND);
  }

  /**
   * required time stamp in output date format input is gmt time stamp
   */
  public static String getTransactionTime(String txnTimestamp) {
    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
    SimpleDateFormat output = new SimpleDateFormat("dd/MMMM/yyyy HH:mm");
    Date d = null;
    try {
      d = input.parse(txnTimestamp);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    String formatted = output.format(d);
    return formatted;
  }

  /**
   * required time stamp in output date format input is gmt time stamp
   */
  public static String getTrackingTransactionTime(String txnTimestamp) {
    Long time = Long.parseLong(txnTimestamp);
    SimpleDateFormat output = new SimpleDateFormat("dd MMMM yyyy  HH:mm");
    Date date = null;
    date = new Date(time * 1000L);
    String formatted = output.format(date);
    return formatted;
  }



  /**
   * required time stamp in output date format input is gmt time stamp
   */
  public static String getPromoCodeValidTime(String txnTimestamp) {
    Long time = Long.parseLong(txnTimestamp);
    SimpleDateFormat output = new SimpleDateFormat("MMM yyyy");
    Date date = null;
    date = new Date(time * 1000L);
    String formatted = output.format(date);
    return formatted;
  }

  public static String timeAgo(String timeFormat) {
    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    long timeAgo = 0;
    try {
      Date date = (Date) input.parse(timeFormat);
      timeAgo = date.getTime() / 1000;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    long curTime = (System.currentTimeMillis()) / 1000;
    long timeElapsed = curTime - timeAgo;
    long seconds = timeElapsed;
    int minutes = Math.round(timeElapsed / 60);
    int hours = Math.round(timeElapsed / 3600);
    int days = Math.round(timeElapsed / 86400);
    int weeks = Math.round(timeElapsed / 604800);
    int months = Math.round(timeElapsed / 2600640);
    int years = Math.round(timeElapsed / 31207680);
    if (seconds <= 60) {
      return "just now";
    } else if (minutes <= 60) {
      if (minutes == 1) {
        return "one minute ago";
      } else {
        return minutes + " minutes ago";
      }
    } else if (hours <= 24) {
      if (hours == 1) {
        return "an hour ago";
      } else {
        return hours + " hrs ago";
      }
    } else if (days <= 7) {
      if (days == 1) {
        return "yesterday";
      } else {
        return days + " days ago";
      }
    } else if (weeks <= 4.3) {
      if (weeks == 1) {
        return "a week ago";
      } else {
        return weeks + " weeks ago";
      }
    } else if (months <= 12) {
      if (months == 1) {
        return "a month ago";
      } else {
        return months + " months ago";
      }
    } else {
      if (years == 1) {
        return "one year ago";
      } else {
        return years + " years ago";
      }
    }
  }
}