package com.customer.remote.http;

import static com.customer.remote.http.RemoteConstants.CONNECT_TIME_OUT;
import static com.customer.remote.http.RemoteConstants.READ_TIME_OUT;

import android.content.Context;
import com.customer.remote.BuildConfig;
import com.customer.remote.http.core.ItemTypeAdapterFactory;
import com.customer.remote.http.core.RestInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManagerImpl implements NetworkManager {
  private Context context;
  private RestInterceptor interceptor;
  private ApiHandlerImpl apiHandler;
  private PythonApiHandlerImpl secApiHandler;
  private PromoCodeApiHandlerImpl mPromoCodeApiHandler;
  private IpConfigApiHandler mIpConfigApiHandler;
  public NetworkManagerImpl(Context context) {
    this.context = context;
    interceptor = new RestInterceptor();
    SecApi secApi = secApi();
    RestApi restApi = restApi();
    PromoCodeApi promoCodeApi = promoCodeApi();
    IpConfigApi ipConfigApi = ipConfigApi();
    apiHandler = new ApiHandlerImpl(restApi);
    interceptor.apiHandler(apiHandler);
    secApiHandler = new PythonApiHandlerImpl(secApi);
    mPromoCodeApiHandler = new PromoCodeApiHandlerImpl(promoCodeApi);
    mIpConfigApiHandler = new IpConfigApiHandler(ipConfigApi);
  }

  private SecApi secApi() {
    return build(BuildConfig.PYTHON_URL, getOkHttpClient(),
        createGsonConverterFactory(ItemTypeAdapterFactory.newInstance())).create(SecApi.class);
  }

  private PromoCodeApi promoCodeApi() {
    return build(BuildConfig.PROMO_CODE_URL, getOkHttpClient(),
        createGsonConverterFactory(ItemTypeAdapterFactory.newInstance())).create(
        PromoCodeApi.class);
  }

  private RestApi restApi() {
    return build(BuildConfig.BASE_URL, getOkHttpClient(),
        createGsonConverterFactory(ItemTypeAdapterFactory.newInstance())).create(RestApi.class);
  }

  private IpConfigApi ipConfigApi() {
    return build(BuildConfig.IP_CONFIG_URL, getOkHttpClient(),
        createGsonConverterFactory(ItemTypeAdapterFactory.newInstance())).create(IpConfigApi.class);
  }

  private GsonConverterFactory createGsonConverterFactory(TypeAdapterFactory typeAdapterFactory) {
    Gson gson = new GsonBuilder().registerTypeAdapterFactory(typeAdapterFactory)
        .excludeFieldsWithoutExposeAnnotation().create();
    return GsonConverterFactory.create(gson);
  }

  private Retrofit build(
      String baseUrl, OkHttpClient okHttpClient, GsonConverterFactory gsonConverterFactory) {
    return new Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
  }

  private OkHttpClient getOkHttpClient() {
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    builder.sslSocketFactory(SSLCertificate.initSSL(context).getSocketFactory(),
        SSLCertificate.systemDefaultTrustManager());
    builder.callTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS);
    builder.connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS);
    builder.readTimeout(READ_TIME_OUT, TimeUnit.SECONDS);
    builder.addInterceptor(interceptor);
    if (BuildConfig.DEBUG) {
      HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
      logging.setLevel(HttpLoggingInterceptor.Level.BODY);
      builder.addInterceptor(logging);
    }
    return builder.build();
  }

  @Override
  public ApiHandler nodeApiHandler() {
    return apiHandler;
  }

  @Override
  public PythonApiHandler pythonApiHandler() {
    return secApiHandler;
  }

  @Override
  public PromoCodeApiHandler promocodeApiHandler() {
    return mPromoCodeApiHandler;
  }
}
