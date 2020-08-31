package com.customer.fivecanale.recievers;

import static com.customer.fivecanale.util.EcomConstants.GET_IP_ADDRESS;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.customer.domain.interactor.handler.UserInfoHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class ConnectivityChangeReceiver extends BroadcastReceiver {
  private UserInfoHandler mInfoHandler;

  public ConnectivityChangeReceiver(UserInfoHandler userInfoHandler) {
    this.mInfoHandler = userInfoHandler;
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    Log.d("OnStateChangeListener", "Change");
    Thread thread = new Thread(this::getIpAddress);
    thread.start();
  }

  public void getIpAddress() {
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
        if (reader != null && (line = reader.readLine()) == null) {
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
    mInfoHandler.setIpAddress(fullString);
  }
}
