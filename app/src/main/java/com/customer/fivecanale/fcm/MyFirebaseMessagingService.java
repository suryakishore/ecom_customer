package com.customer.fivecanale.fcm;

import static com.customer.fivecanale.util.EcomConstants.ACTION;
import static com.customer.fivecanale.util.EcomConstants.CART_UPDATED;
import static com.customer.fivecanale.util.EcomConstants.NOTIFICATION_COUNT;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.ORDER_ID;
import static com.customer.fivecanale.util.EcomConstants.TWO;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.R;
import com.customer.data.repository.observable.CartDataObservable;
import com.customer.data.repository.observable.NotificationCountObservable;
import com.customer.domain.model.getcart.CartData;
import com.customer.fivecanale.landing.HomeActivity;
import com.customer.fivecanale.splash.SplashActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Map;
import java.util.Objects;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
  private static final String TAG = "MyFirebaseMsgService";

  /**
   * Called when message is received.
   *
   * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
   */
  // [START receive_message]
  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
    // [START_EXCLUDE]
    // There are two types of messages mData messages and notification messages. Data messages are
    // handled
    // here in onMessageReceived whether the app is in the foreground or splash_background. Data
    // messages
    // are the type
    // traditionally used with GCM. Notification messages are only received here in
    // onMessageReceived when the app
    // is in the foreground. When the app is in the splash_background an automatically generated
    // notification is displayed.
    // When the user taps on the notification they are returned to the app. Messages containing both
    // notification
    // and mData payloads are treated as notification messages. The Firebase console always sends
    // notification
    // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
    // [END_EXCLUDE]
    // TODO(developer): Handle FCM messages here.
    // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
    Log.d(TAG, "From: " + remoteMessage.getFrom());
    // Check if message contains a mData payload.
    if (remoteMessage.getData() != null) {
      //   {{orderid=58a6b0b93e529a3655cb30b2, status=3}
      Log.d(TAG, "Message mData  " + remoteMessage.getData());
      Map map = remoteMessage.getData();
      Log.d(TAG, "Message mData not: " + map.toString());
      // {action=12, msg=Your profile has been banned by our operations team. Please reach out to
      // our support team for more information., mData={}, title=Ufly, pushType=1}
      try {
        if (map.get("body") != null && map.get("orderId") != null && map.get("action") != null) {
          sendNotification(
              Objects.requireNonNull(map.get("body")).toString(),
              Objects.requireNonNull(map.get("orderId")).toString(),
              Integer.parseInt(map.get("action").toString()));
        } else {
          if (map.get("action") != null) {
            sendNotification(Integer.parseInt(map.get("action").toString()),
                Objects.requireNonNull(map.get("body")).toString());
          } else {
            sendNotification(Objects.requireNonNull(map.get("body")).toString());
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      Intent intent = new Intent("order_update");
      intent.putExtra("mData", remoteMessage.getData().toString());
      sendBroadcast(intent);
    }
    // Check if message contains a notification payload.
    if (remoteMessage.getNotification() != null) {
      Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
    }
    // Also if you intend on generating your own notifications as a result of a received FCM
    // message, here is where that should be initiated. See sendNotification method below.
  }
  // [END receive_message]

  /**
   * Create and show a simple notification containing the received FCM message.
   *
   * @param messageBody FCM message body received.
   */
  private void sendNotification(String messageBody, String bid, int action) {
    NOTIFICATION_COUNT += ONE;
    NotificationCountObservable.getInstance().emit(NOTIFICATION_COUNT);
    Intent intent = null;
    switch (action) {
      case ONE:
        intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(ORDER_ID, bid);
        intent.putExtra(ACTION, action);
        break;
      case TWO:
        break;
      default:
        intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(ORDER_ID, bid);
    }
    PendingIntent pendingIntent =
        PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);
    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.launch);
    NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
    bigText.bigText(messageBody);
    bigText.setBigContentTitle(getString(R.string.app_name));
    NotificationManager notificationManager =
        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    if (notificationManager != null) {
      notificationManager.cancel(0);
    }
    NotificationCompat.Builder notificationBuilder =
        new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.notification_icon_blue)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(messageBody)
            .setChannelId("1")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setSound(defaultSoundUri)
            .setAutoCancel(true)
            .setLargeIcon(bitmap)
            .setStyle(bigText);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      int importance = NotificationManager.IMPORTANCE_HIGH;
      NotificationChannel channel =
          new NotificationChannel("1", getString(R.string.app_name), importance);
      assert notificationManager != null;
      notificationManager.createNotificationChannel(channel);
    }
    assert notificationManager != null;
    notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
  }

  private void sendNotification(String messageBody) {
    NOTIFICATION_COUNT += ONE;
    NotificationCountObservable.getInstance().emit(NOTIFICATION_COUNT);
    Intent intent = new Intent(this, SplashActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    PendingIntent pendingIntent =
        PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);
    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.launch);
    NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
    bigText.bigText(messageBody);
    bigText.setBigContentTitle(getString(R.string.app_name));
    NotificationManager notificationManager =
        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    if (notificationManager != null) {
      notificationManager.cancel(0);
    }
    NotificationCompat.Builder notificationBuilder =
        new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.notification_icon_blue)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(messageBody)
            .setChannelId("1")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setSound(defaultSoundUri)
            .setAutoCancel(true)
            .setLargeIcon(bitmap)
            .setStyle(bigText);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      int importance = NotificationManager.IMPORTANCE_HIGH;
      NotificationChannel channel =
          new NotificationChannel("1", getString(R.string.app_name), importance);
      assert notificationManager != null;
      notificationManager.createNotificationChannel(channel);
    }
    assert notificationManager != null;
    notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
  }

  /**
   * This method basically used to refresh the cart data from push notification data with ation is
   * 2.
   *
   * @param action      this represents which type of action in our case is 2 represents cart
   *                    updated by dispatcher.
   * @param messageBody this represents body for the push notification.
   */
  private void sendNotification(int action, String messageBody) {
    NOTIFICATION_COUNT += ONE;
    NotificationCountObservable.getInstance().emit(NOTIFICATION_COUNT);
    if (action == CART_UPDATED) {
      CartDataObservable.getInstance().postData(new CartData(1, "", 0));
    }
    Intent intent = new Intent(this, SplashActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    PendingIntent pendingIntent =
        PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);
    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.launch);
    NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
    bigText.bigText(messageBody);
    bigText.setBigContentTitle(getString(R.string.app_name));
    NotificationManager notificationManager =
        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    if (notificationManager != null) {
      notificationManager.cancel(0);
    }
    NotificationCompat.Builder notificationBuilder =
        new NotificationCompat.Builder(this, "1")
            .setSmallIcon(R.drawable.notification_icon_blue)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(messageBody)
            .setChannelId("1")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setSound(defaultSoundUri)
            .setAutoCancel(true)
            .setLargeIcon(bitmap)
            .setStyle(bigText);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      int importance = NotificationManager.IMPORTANCE_HIGH;
      NotificationChannel channel =
          new NotificationChannel("1", getString(R.string.app_name), importance);
      assert notificationManager != null;
      notificationManager.createNotificationChannel(channel);
    }
    assert notificationManager != null;
    notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
  }
}
