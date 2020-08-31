package com.customer.remote.http;

import android.os.Build;
import java.util.Calendar;
import java.util.Date;

public class DeviceInfo {

  public static String DEVICE_MODEL = android.os.Build.MODEL;
  public static String DEVICE_MAKE = Build.MANUFACTURER;
  public static int DEVICE_OS_VERSION = Build.VERSION.SDK_INT;
  public static Date DEVICE_TIME = Calendar.getInstance().getTime();
}
