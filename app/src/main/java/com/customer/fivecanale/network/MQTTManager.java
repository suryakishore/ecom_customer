package com.customer.fivecanale.network;

import static com.appscrip.stripe.Constants.DATA;
import static com.customer.fivecanale.util.EcomConstants.ACTION;
import static com.customer.fivecanale.util.EcomConstants.CANCELLED;
import static com.customer.fivecanale.util.EcomConstants.CONTENT;
import static com.customer.fivecanale.util.EcomConstants.FROM_ID;
import static com.customer.fivecanale.util.EcomConstants.ORDER_ID;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_ORDER_ID;
import static com.customer.fivecanale.util.EcomConstants.STORE_ID;
import static com.customer.fivecanale.util.EcomConstants.STORE_ORDER_ID;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.BuildConfig;
import com.R;
import com.customer.data.repository.TrackingObservable;
import com.customer.domain.model.getcart.TrackingData;
import com.customer.fivecanale.mqtt_chat.ChatDataObervable;
import com.customer.fivecanale.mqtt_chat.ChattingActivity;
import com.customer.fivecanale.util.EcomUtil;
import com.facebook.internal.Utility;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.inject.Inject;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

/**
 * <h1>MQTTManager</h1>
 * This class is used to handle the MQTT mData
 *
 * @author 3Embed
 * @since on 21-12-2017.
 */
public class MQTTManager {
  private static final String TAG = "MQTTManager";
  private IMqttActionListener mMQTTListener;
  private MqttAndroidClient mqttAndroidClient;
  private MqttConnectOptions mqttConnectOptions;
  private String mMqttTopic, mUserId="";
  private Context mContext;

  @Inject
  public MQTTManager(Context context) {
    mContext = context;
    /*this.sessionHelper = dDataSource;*/
    mMQTTListener = new IMqttActionListener() {
      @Override
      public void onSuccess(IMqttToken asyncActionToken) {
        EcomUtil.printLog(TAG + " topic: " + mMqttTopic + "mUserInfoHandler  " + mUserId);
        unSubscribeToTopic(mMqttTopic);
        unSubscribeToTopic(
            String.format("%s/%s", mContext.getResources().getString(R.string.message),
                mUserId));
        subscribeToTopic(mMqttTopic);
        subscribeToTopic(String.format("%s/%s", mContext.getResources().getString(R.string.message), mUserId));
        EcomUtil.printLog(TAG + "onSuccess: myqtt client ");
      }

      @Override
      public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        exception.printStackTrace();
        EcomUtil.printLog(TAG + "onFailure: myqtt client " + asyncActionToken.isComplete()
            + " " + exception.toString());
      }
    };
  }

  /**
   * <h2>subscribeToTopic</h2>
   * This method is used to subscribe to the mqtt topic
   */
  private void subscribeToTopic(String mqttTopic) {
    try {
      if (mqttAndroidClient != null) {
        mqttAndroidClient.subscribe(mqttTopic, 2);
      }
    } catch (MqttException e) {
      EcomUtil.printLog(TAG + " MqttException " + e);
      e.printStackTrace();
    } catch (NullPointerException e) {
      EcomUtil.printLog(TAG + " MqttException " + e);
      e.printStackTrace();
    }
  }

  /**
   * <h2>unSubscribeToTopic</h2>
   * This method is used to unSubscribe to topic already subscribed
   *
   * @param topic Topic name from which to  unSubscribe
   */
  @SuppressWarnings("TryWithIdenticalCatches")
  private void unSubscribeToTopic(String topic) {
    try {
      if (mqttAndroidClient != null) {
        mqttAndroidClient.unsubscribe(topic);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * <h2>isMQTTConnected</h2>
   * This method is used to check whether MQTT is connected
   *
   * @return boolean value whether MQTT is connected
   */
  public boolean isMQTTConnected() {
    return mqttAndroidClient != null && mqttAndroidClient.isConnected();
  }

  /**
   * <h2>createMQttConnection</h2>
   * This method is used to create the connection with MQTT
   *
   * @param clientId customer ID to connect MQTT
   */
  @SuppressWarnings("unchecked")
  public void createMQttConnection(String clientId, String mqttTopic, String userId) {
    this.mMqttTopic = mqttTopic;
    this.mUserId = userId;
    EcomUtil.printLog(TAG + " createMQtftConnection: " + clientId);
    String serverUri = String.format("ssl://%s:%s", BuildConfig.MQTT_HOST, BuildConfig.MQTT_PORT);
    EcomUtil.printLog(TAG + " serverUri: " + serverUri);
    mqttAndroidClient = new MqttAndroidClient(mContext, serverUri, clientId);
    mqttAndroidClient.setCallback(new MqttCallback() {
      @Override
      public void connectionLost(Throwable cause) {
        EcomUtil.printLog(TAG + " connectionLost: " + cause);
      }

      @Override
      public void messageArrived(String topic, final MqttMessage message) throws Exception {
        final JSONObject jsonObject = new JSONObject(new String(message.getPayload()));
        EcomUtil.printLog(TAG + " messageArrived: " + new String(message.getPayload()));
        if (jsonObject.has(DATA)) {
          if (ChattingActivity.isOpen) {
            ChatDataObervable.getInstance().emitData(jsonObject);
          } else {
            String bid = jsonObject.getJSONObject(DATA).getString(STORE_ORDER_ID);
            String content = jsonObject.getJSONObject(DATA).getString(CONTENT);
            String custID = jsonObject.getJSONObject(DATA).getString(FROM_ID);
            EcomUtil.printLog(
                "data" + "orderID:-" + bid + "driverId:-" + custID + "driverName:-");
            sendNotification(bid, content, custID);
          }
        } else {
          if (jsonObject.has(ACTION)) {
            int action = jsonObject.getInt(ACTION);
            switch (action) {
              case CANCELLED:
                TrackingObservable.getInstance().emit(
                    new TrackingData(jsonObject.getString(PRODUCT_ORDER_ID),
                        String.valueOf(jsonObject.getInt(ACTION))));
                break;
            }
          }
        }
        //{"action":12,"message":"Your session has expired, please login again to continue
        // accessing your account.","deviceId":"5da4d7d3d8d62a9e"}
      }

      @Override
      public void deliveryComplete(IMqttDeliveryToken token) {
        EcomUtil.printLog(TAG + " deliveryComplete: " + token);
      }
    });
    mqttConnectOptions = new MqttConnectOptions();
    SocketFactory.SocketFactoryOptions socketFactoryOptions =
        new SocketFactory.SocketFactoryOptions();
    try {
      socketFactoryOptions.withCaInputStream(mContext.getResources().openRawResource(R.raw.ecomm));
      mqttConnectOptions.setSocketFactory(new SocketFactory(socketFactoryOptions));
    } catch (IOException | NoSuchAlgorithmException | KeyStoreException | CertificateException | KeyManagementException | UnrecoverableKeyException e) {
      e.printStackTrace();
      EcomUtil.printLog(TAG + " socketFactoryOptions: " + e.toString());
    }
    mqttConnectOptions.setCleanSession(false);
    mqttConnectOptions.setAutomaticReconnect(true);
    mqttConnectOptions.setUserName(BuildConfig.MQTT_USERNAME);
    mqttConnectOptions.setPassword(BuildConfig.MQTT_PASSWORD.toCharArray());
    connectMQTTClient(mContext);
  }

  /**
   * <h2>connectMQTTClient</h2>
   * This method is used to connect to MQTT client
   */
  private void connectMQTTClient(Context context) {
    try {
      EcomUtil.printLog(TAG + " connectMQTTClient: ");
      mqttAndroidClient.connect(mqttConnectOptions, context, mMQTTListener);
    } catch (MqttException e) {
      EcomUtil.printLog(TAG + " MqttException: " + e);
      e.printStackTrace();
    }
  }

  /**
   * <h2>disconnect</h2>
   * This method is used To disconnect the MQtt client
   */
  public void disconnect() {
    try {
      if (mqttAndroidClient != null && mqttAndroidClient.isConnected()) {
        mqttAndroidClient.disconnect();
        EcomUtil.printLog(TAG + " disconnect: " + mqttAndroidClient.isConnected());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void publish(JSONObject jsonObject) {
    try {
      mqttAndroidClient.publish("driver_location_update", jsonObject.toString().getBytes(), 2,
          false);
    } catch (MqttException e) {
      e.printStackTrace();
    }
  }

  private void sendNotification(String bid, String message, String custID) {
    if (mUserId!=null&&!mUserId.isEmpty()) {
      Intent intent = new Intent(mContext, ChattingActivity.class);
      intent.putExtra(STORE_ORDER_ID, bid);
      intent.putExtra(ORDER_ID, bid);
      intent.putExtra(STORE_ID, custID);
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
      PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0,
          intent, PendingIntent.FLAG_ONE_SHOT);
      Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
      NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
      bigText.bigText(message);
      bigText.setBigContentTitle(mContext.getString(R.string.app_name));
      Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),
          R.drawable.launch);
      NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(
          Context.NOTIFICATION_SERVICE);
      Bitmap largeIcon = BitmapFactory.decodeResource(mContext.getResources(),
          R.drawable.launch);
      NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext,
          mContext.getString(R.string.app_name))
          .setContentTitle(mContext.getString(R.string.app_name))
          .setContentText(message)
          .setContentIntent(pendingIntent)
          .setSmallIcon(R.drawable.launch)
          .setLargeIcon(largeIcon)
          .setPriority(NotificationCompat.PRIORITY_MAX)
          .setAutoCancel(true)
          .setLargeIcon(bitmap)
          .setStyle(bigText);
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Creating an Audio Attribute
        CharSequence name = mContext.getString(R.string.app_name);
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build();
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(
            mContext.getString(R.string.app_name), name, importance);
        mChannel.setSound(defaultSoundUri, audioAttributes);
        assert notificationManager != null;
        notificationManager.createNotificationChannel(mChannel);
      }
      assert notificationManager != null;
      notificationManager.notify(0, notificationBuilder.build());
    }
  }
}
