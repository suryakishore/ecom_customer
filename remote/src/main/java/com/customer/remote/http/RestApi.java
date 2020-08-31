package com.customer.remote.http;

import android.util.ArrayMap;
import com.customer.remote.http.model.request.AddAddressRequest;
import com.customer.remote.http.model.request.AddMoneyRequest;
import com.customer.remote.http.model.request.AddProductToCartRequest;
import com.customer.remote.http.model.request.CancelOrderRequest;
import com.customer.remote.http.model.request.ForgotPasswordRequest;
import com.customer.remote.http.model.request.GenerateTokenRequest;
import com.customer.remote.http.model.request.GetProfileOtpRequest;
import com.customer.remote.http.model.request.GuestLogInReq;
import com.customer.remote.http.model.request.LogOutRequest;
import com.customer.remote.http.model.request.MakeAddressDefaultRequest;
import com.customer.remote.http.model.request.PlaceOrderRequest;
import com.customer.remote.http.model.request.PostMessage;
import com.customer.remote.http.model.request.ProfileOtpVerifyRequest;
import com.customer.remote.http.model.request.ReorderRequest;
import com.customer.remote.http.model.request.ResetPasswordRequest;
import com.customer.remote.http.model.request.SendOtpRequest;
import com.customer.remote.http.model.request.SignInRequest;
import com.customer.remote.http.model.request.UpdateCartRequest;
import com.customer.remote.http.model.request.UpdateProfileRequest;
import com.customer.remote.http.model.request.VerifyMobileOrMailRequest;
import com.customer.remote.http.model.request.VerifyOTPRequest;
import com.customer.remote.http.model.request.VersionRequest;
import com.customer.remote.http.model.request.signUp.SignUpRequest;
import com.customer.remote.http.model.response.CommonModel;
import com.customer.remote.http.model.response.ForgotPasswordDetails;
import com.customer.remote.http.model.response.VerifyMobileOrMailDetails;
import com.customer.remote.http.model.response.changelanguage.ChangeLanListDetails;
import com.customer.remote.http.model.response.chat.GetChatListDetails;
import com.customer.remote.http.model.response.generatetoken.GenerateTokenItemDetails;
import com.customer.remote.http.model.response.getReasons.GetReasonsItemDetails;
import com.customer.remote.http.model.response.getaddress.AddressDetails;
import com.customer.remote.http.model.response.getcart.CartDetails;
import com.customer.remote.http.model.response.guestSignIn.GuestSignInDetailsData;
import com.customer.remote.http.model.response.help.HelpListDetails;
import com.customer.remote.http.model.response.location.IpAddressToLocationDetails;
import com.customer.remote.http.model.response.ordercount.OrderCountListDetails;
import com.customer.remote.http.model.response.orderdetails.MasterOrderDetails;
import com.customer.remote.http.model.response.orderdetails.OrderDetails;
import com.customer.remote.http.model.response.orderhistory.OrderHistoryDetails;
import com.customer.remote.http.model.response.profile.ProfileDetails;
import com.customer.remote.http.model.response.signIn.SignInDetailsData;
import com.customer.remote.http.model.response.signUp.SignUpDetails;
import com.customer.remote.http.model.response.tracking.TrackingListOrderStatusData;
import com.customer.remote.http.model.response.uploadImage.UploadImageItemDetails;
import com.customer.remote.http.model.response.version.VersionListData;
import com.customer.remote.http.model.response.wallet.WalletDataDetails;
import com.customer.remote.http.model.response.wallet.WalletTransactionsListDetails;
import com.customer.remote.http.model.response.walletEstimate.EstimateItemDetails;
import com.customer.remote.http.model.response.webview.WebPageData;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.HashMap;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RestApi {
  @POST("guest/signIn")
  Single<GuestSignInDetailsData> guestSignIn(
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @Body GuestLogInReq req);

  @POST("signUp/New")
  Single<SignUpDetails> signUp(
      @Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @Body SignUpRequest req);

  @POST("signIn")
  Single<SignInDetailsData> signIn(
      @Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @Body SignInRequest req);

  @POST("emailPhoneValidate")
  Single<VerifyMobileOrMailDetails> validateMailOrPhone(
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @Body VerifyMobileOrMailRequest req);

  @POST("customer/verifyOtp")
  Single<SignInDetailsData> verifyOTP(
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @Body VerifyOTPRequest req);

  @POST("forgotPassword")
  Single<ForgotPasswordDetails> forgotPassword(
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @Body ForgotPasswordRequest req);

  @POST("resetPassword")
  Single<CommonModel> resetPassword(
      @Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @Body ResetPasswordRequest req);

  @POST("customer/sendOtp")
  Single<ForgotPasswordDetails> sendOTP(
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @Body SendOtpRequest req);

  @PATCH("customer/profile")
  Single<CommonModel> updateProfile(
      @Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @Body UpdateProfileRequest request);

  @POST("address")
  Single<CommonModel> addAddress(@Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @Body AddAddressRequest request);

  @GET("address")
  Single<AddressDetails> getAddress(@Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @QueryMap(encoded = true) ArrayMap<String, String> userId);

  @DELETE("address")
  Single<CommonModel> deleteAddress(@Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @QueryMap(encoded = true) ArrayMap<String, String> userId);

  @PATCH("address")
  Single<CommonModel> editAddress(@Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @Body AddAddressRequest request);

  @POST("customer/logout")
  Single<CommonModel> logOut(@Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @Body LogOutRequest request);

  @PATCH("defaultAddress")
  Single<CommonModel> makeDefaultAddress(@Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @Body MakeAddressDefaultRequest request);

  @GET("customer/profile")
  Single<ProfileDetails> getProfileDetails(@Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode);

  @POST("customer/emPatchSend")
  Single<ForgotPasswordDetails> getOtpForUpdateProfile(
      @Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @Body GetProfileOtpRequest request);

  @POST("customer/emPatchVerify")
  Single<CommonModel> verifyProfileOtp(@Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @Body ProfileOtpVerifyRequest request);

  @GET("cart")
  Single<CartDetails> getCartProducts(@Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @QueryMap(encoded = true) HashMap<String, Object> query);

  @POST("cart")
  Single<CommonModel> addProductToCart(@Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @Body AddProductToCartRequest request);

  @GET("ipLocation")
  Single<IpAddressToLocationDetails> ipAddressToLocation(@Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @QueryMap(encoded = true) ArrayMap<String, String> ipAddress);

  @POST("cart")
  Single<CommonModel> updateCart(@Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @Body UpdateCartRequest request);

  @POST("order")
  Single<CommonModel> placeOrder(@Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @Body PlaceOrderRequest request);

  @GET("orders")
  Single<OrderHistoryDetails> getOrderHistory(@Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @QueryMap(encoded = true) HashMap<String, Object> query);

  @POST("reOrder")
  Single<CommonModel> makeReorder(@Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @Body ReorderRequest request);

  /*
    @DELETE("order")
  */
  @HTTP(method = "DELETE", path = "order", hasBody = true)
  Single<CommonModel> cancelOrder(@Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @Body CancelOrderRequest request);

  @GET("orders/details")
  Single<MasterOrderDetails> getOrderDetails(@Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @QueryMap(encoded = true) HashMap<String, Object> query);

  @GET("order/package/details")
  Single<OrderDetails> getPackageDetails(@Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @QueryMap(encoded = true) HashMap<String, Object> query);

  @GET("reason")
  Single<GetReasonsItemDetails> getReasons(
      @Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @QueryMap(encoded = true) HashMap<String, Object> query);

  @GET("order/status")
  Single<TrackingListOrderStatusData> getTrackingDetails(
      @Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @QueryMap(encoded = true) HashMap<String, Object> query);

  @GET("Wallet")
  Single<WalletDataDetails> getWalletDetailsApi(@Header("authorization") String authorization,
      @Header("lan") String language,
      @QueryMap(encoded = true) HashMap<String, Object> query);

  @GET("Wallettransaction")
  Single<WalletTransactionsListDetails> getWalletTransactionsApi(
      @Header("authorization") String authorization,
      @Header("lan") String language,
      @QueryMap(encoded = true) HashMap<String, Object> query);

  @POST("wallet/recharge")
  Single<WalletDataDetails> getWalletAddMoneyApi(@Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @Body AddMoneyRequest addMoneyRequest);

  @GET("walletTransaction/estimate")
  Single<EstimateItemDetails> getWalletEstimateApi(
      @Header("authorization") String authorization,
      @Header("lan") String language,
      @QueryMap(encoded = true) HashMap<String, Object> query);

  @POST("imageUpload")
  @Multipart
  Single<UploadImageItemDetails> uploadImageApi(
      @Part("uploadTo") int uploadTo,
      @Part("folder") String folder,
      @Part MultipartBody.Part file,
      @Part("fileName") String fileName);

  @GET("/v1/utility/languages")
  Single<ChangeLanListDetails> getLanguages();

  @GET("customer/support")
  Single<HelpListDetails> getHelp(
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode);

  @GET("customer/config")
  Single<SignInDetailsData> getAppConfig(
      @Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @QueryMap(encoded = true) HashMap<String, Object> query);

  @POST("version")
  Single<VersionListData> version(@Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @Body VersionRequest request);

  @GET("orders/count")
  Single<OrderCountListDetails> getOrderCount(
      @Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode);

  @POST("generateToken")
  Single<GenerateTokenItemDetails> generateToken(
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @Body GenerateTokenRequest generateTokenRequest);

  @GET("webpagedata")
  Single<WebPageData> webPageData(
      @Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @QueryMap(encoded = true) HashMap<String, Object> query);

  @POST("message")
  Single<GetChatListDetails> postMessage(
      @Header("authorization") String authorization,
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencySymbol,
      @Header("currencycode") String currencyCode,
      @Body PostMessage postMessage);

  @GET("chatHistory")
  Single<GetChatListDetails> chatHistory(
      @Header("language") String language,
      @Header("platform") int platform,
      @Header("currencysymbol") String currencysymbol,
      @Header("currencycode") String currencycode,
      @Query("type") int type,
      @Query("storeOrderId") String bookingId,
      @Query("pageNo") String pageNo);
}
