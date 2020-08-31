package com.customer.remote.http.core;

import android.util.Log;
import com.customer.remote.http.ApiHandler;
import java.io.IOException;
import java.net.HttpURLConnection;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class RestInterceptor implements Interceptor {
  private String host;
  private ApiHandler apiHandler;

  public void apiHandler(ApiHandler apiHandler) {
    this.apiHandler = apiHandler;
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
    synchronized (this) {
      Request request = chain.request();
      String host = this.host;
      if (host != null) {
        HttpUrl newUrl = request.url().newBuilder().host(host).build();
        request = request.newBuilder().url(newUrl).build();
      }
      Request.Builder builder = request.newBuilder();
      Response response = chain.proceed(builder.build());
      String respString = "";
      Log.d("exe", "code" + response.code() + "url" + request.url());
      if (response.code() >= 300) {
        respString = response.body().string();
        try {
          JSONObject jsonObject = new JSONObject(respString);
          if (jsonObject.has("message")) {
            respString = jsonObject.getString("message");
          } else if (jsonObject.has("data")) {
            respString = jsonObject.getJSONObject("data").getString("message");
          }
        } catch (JSONException e) {
          e.printStackTrace();
        }
        if (response.code() == 406) {
          GenerateTokenObservable.getInstance().emit(true);
         /* Call<AppVersion> appVersionCall = apiInterface.getVersion(
              data); // a sample call using Retrofit.
          APIHelper.enqueueWithRetry(appVersionCall, new Callback<AppVersion>() {
            @Override
            public void onResponse(Call<AppVersion> call, Response<AppVersion> response) {
              Log.d("callbackResponse", "Yay it worked! response is: " + response.toString());
            }
            @Override
            public void onFailure(Call<AppVersion> call, Throwable t) {
              Log.d("callbackResponse", "oh now, an error :(");
            }
          });*/
        } else if (response.code() == 401) {
          LogoutObservable.getInstance().emit(true);
        }
      }
      switch (response.code()) {
        case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
        case HttpURLConnection.HTTP_CLIENT_TIMEOUT:
        case HttpURLConnection.HTTP_BAD_GATEWAY:
        case HttpURLConnection.HTTP_INTERNAL_ERROR:
        case HttpURLConnection.HTTP_UNAUTHORIZED:
        case HttpURLConnection.HTTP_PRECON_FAILED:
        case HttpURLConnection.HTTP_NOT_FOUND:
        case HttpURLConnection.HTTP_BAD_REQUEST:
        case HttpURLConnection.HTTP_BAD_METHOD:
        case HttpURLConnection.HTTP_GONE:
        case HttpURLConnection.HTTP_LENGTH_REQUIRED:
        case HttpURLConnection.HTTP_ENTITY_TOO_LARGE:
          throw new IOException(respString);
      }
      return response;
    }
  }
}
