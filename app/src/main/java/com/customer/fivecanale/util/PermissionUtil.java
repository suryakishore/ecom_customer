package com.customer.fivecanale.util;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.R;
import com.customer.domain.interactor.handler.PermissionHandler;

public class PermissionUtil {

  /**
   * This method is using to handle run time permissions
   *
   * @param activity          activity reference
   * @param permissionHandler handler to get permission status
   * @param permission        permission type
   * @param requestCode       request code to handle
   */
  @RequiresApi(api = Build.VERSION_CODES.M)
  public static void requestPermission(Activity activity, int requestCode,
      PermissionHandler permissionHandler, String permission) {

    if (ContextCompat.checkSelfPermission(activity, permission)
        != PackageManager.PERMISSION_GRANTED) {

      if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
          permission)) {
        showPrompt(activity, permissionHandler, permission, requestCode, false);
      } else {

        if (permissionHandler.isFirstTimeAsking(permission)) {
          permissionHandler.firstTimeAsking(permission, false);
          ActivityCompat.requestPermissions(activity, new String[]{permission},
              requestCode);
        } else {
          showPrompt(activity, permissionHandler, permission, requestCode, true);
        }
      }
    }
  }

  /**
   * This method is using to show prompt message
   *
   * @param activity            activity reference
   * @param permissionHandler   handler to get permission status
   * @param permission          permission type
   * @param requestCode         request code to handle
   * @param isDeniedPermanently is denied permanently
   */
  private static void showPrompt(Activity activity, PermissionHandler permissionHandler,
      String permission, int requestCode, boolean isDeniedPermanently) {
    AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
    alertDialog.setMessage(activity.getString(R.string.permissionPromptMsg));
    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, activity.getString(R.string.denyBtnText),
        (dialogInterface, i) -> alertDialog.dismiss());
    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,
        isDeniedPermanently ? activity.getString(R.string.settingsBtnTxt)
            : activity.getString(R.string.allowBtnTxt),
        (dialog, which) -> {
          if (isDeniedPermanently) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
            intent.setData(uri);
            activity.startActivity(intent);
          } else {
            permissionHandler.firstTimeAsking(permission, false);
            ActivityCompat.requestPermissions(activity, new String[]{permission},
                requestCode);
          }

        });
    alertDialog.show();
  }
}
