package com.customer.remote.http;

import static com.customer.remote.http.RemoteConstants.ADDRESS_ID;
import static com.customer.remote.http.RemoteConstants.ADMIN;
import static com.customer.remote.http.RemoteConstants.ADMIN_TYPE;
import static com.customer.remote.http.RemoteConstants.AMAZON_SERVER;
import static com.customer.remote.http.RemoteConstants.AMOUNT;
import static com.customer.remote.http.RemoteConstants.BACK_GROUND_FLAG;
import static com.customer.remote.http.RemoteConstants.FETCH_SIZE;
import static com.customer.remote.http.RemoteConstants.FILE;
import static com.customer.remote.http.RemoteConstants.FOR;
import static com.customer.remote.http.RemoteConstants.FOR_WHICH_APP;
import static com.customer.remote.http.RemoteConstants.FROM_CURRENCY;
import static com.customer.remote.http.RemoteConstants.ID;
import static com.customer.remote.http.RemoteConstants.IP_ADDRESS;
import static com.customer.remote.http.RemoteConstants.ORDER_ID;
import static com.customer.remote.http.RemoteConstants.PACK_ID;
import static com.customer.remote.http.RemoteConstants.PAGE_STATE;
import static com.customer.remote.http.RemoteConstants.PRODUCT_ORDER_ID;
import static com.customer.remote.http.RemoteConstants.REASON_TYPE;
import static com.customer.remote.http.RemoteConstants.STORE_CATEGORY_ID;
import static com.customer.remote.http.RemoteConstants.TO_CURRENCY;
import static com.customer.remote.http.RemoteConstants.TYPE;
import static com.customer.remote.http.RemoteConstants.USER_ID;
import static com.customer.remote.http.RemoteConstants.USER_TYPE;
import static com.customer.remote.http.RemoteConstants.WALLET_ID;

import android.util.ArrayMap;
import com.customer.remote.http.model.request.AddAddressRequest;
import com.customer.remote.http.model.request.AddMoneyRequest;
import com.customer.remote.http.model.request.AddProductToCartRequest;
import com.customer.remote.http.model.request.CancelOrderRequest;
import com.customer.remote.http.model.request.ForgotPasswordRequest;
import com.customer.remote.http.model.request.GenerateTokenRequest;
import com.customer.remote.http.model.request.GetOrderHistoryRequest;
import com.customer.remote.http.model.request.GetProfileOtpRequest;
import com.customer.remote.http.model.request.GuestLogInReq;
import com.customer.remote.http.model.request.Header;
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
import io.reactivex.Single;
import java.io.File;
import java.util.HashMap;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ApiHandlerImpl implements ApiHandler {
  private RestApi restApi;

  ApiHandlerImpl(RestApi restApi) {
    this.restApi = restApi;
  }

  @Override
  public Single<GuestSignInDetailsData> guestSignIn(Header header, GuestLogInReq guestLogInReq) {
    return restApi.guestSignIn(
        header.getLanguage(),
        header.getPlatform(),
        header.getCurrencySymbol(),
        header.getCurrencyCode(),
        guestLogInReq);
  }

  @Override
  public Single<SignUpDetails> signUp(Header header, SignUpRequest signUpRequest) {
    return restApi.signUp(
        header.getToken(),
        header.getLanguage(),
        header.getPlatform(),
        header.getCurrencySymbol(),
        header.getCurrencyCode(),
        signUpRequest);
  }

  @Override
  public Single<SignInDetailsData> signIn(Header header, SignInRequest signInRequest) {
    return restApi.signIn(
        header.getToken(),
        header.getLanguage(),
        header.getPlatform(),
        header.getCurrencySymbol(),
        header.getCurrencyCode(),
        signInRequest);
  }

  @Override
  public Single<SignInDetailsData> verifyOTP(Header header, VerifyOTPRequest verifyOTPRequest) {
    return restApi.verifyOTP(
        header.getLanguage(),
        header.getPlatform(),
        header.getCurrencySymbol(),
        header.getCurrencyCode(),
        verifyOTPRequest);
  }

  @Override
  public Single<ForgotPasswordDetails> forgotPassword(
      Header header, ForgotPasswordRequest forgotPasswordRequest) {
    return restApi.forgotPassword(
        header.getLanguage(),
        header.getPlatform(),
        header.getCurrencySymbol(),
        header.getCurrencyCode(),
        forgotPasswordRequest);
  }

  @Override
  public Single<VerifyMobileOrMailDetails> verifyMobileOrMail(
      Header header, VerifyMobileOrMailRequest verifyMobileOrMailRequest) {
    return restApi.validateMailOrPhone(
        header.getLanguage(),
        header.getPlatform(),
        header.getCurrencySymbol(),
        header.getCurrencyCode(),
        verifyMobileOrMailRequest);
  }

  @Override
  public Single<CommonModel> resetPassword(
      Header header, String otpId, ResetPasswordRequest resetPasswordRequest) {
    return restApi.resetPassword(
        otpId,
        header.getLanguage(),
        header.getPlatform(),
        header.getCurrencySymbol(),
        header.getCurrencyCode(),
        resetPasswordRequest);
  }

  @Override
  public Single<ForgotPasswordDetails> sendOtp(Header header, SendOtpRequest sendOtpRequest) {
    return restApi.sendOTP(
        header.getLanguage(),
        header.getPlatform(),
        header.getCurrencySymbol(),
        header.getCurrencyCode(),
        sendOtpRequest);
  }

  @Override
  public Single<CommonModel> updateProfile(Header header,
      UpdateProfileRequest updateProfileRequest) {
    return restApi.updateProfile(
        header.getToken(),
        header.getLanguage(),
        header.getPlatform(),
        header.getCurrencySymbol(),
        header.getCurrencyCode(),
        updateProfileRequest);
  }

  @Override
  public Single<CommonModel> addAddress(Header header, AddAddressRequest request) {
    return restApi.addAddress(header.getToken(),
        header.getLanguage(),
        header.getPlatform(),
        header.getCurrencySymbol(),
        header.getCurrencyCode(), request);
  }

  @Override
  public Single<AddressDetails> getAddress(Header header, String userId) {
    ArrayMap<String, String> userIdMapper = null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
      userIdMapper = new ArrayMap<>();
      userIdMapper.put(USER_ID, userId);
    }
    return restApi.getAddress(header.getToken(),
        header.getLanguage(),
        header.getPlatform(),
        header.getCurrencySymbol(),
        header.getCurrencyCode(), userIdMapper);
  }

  @Override
  public Single<CommonModel> deleteAddress(Header header, String addressID) {
    ArrayMap<String, String> userIdMapper = null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
      userIdMapper = new ArrayMap<>();
      userIdMapper.put(ADDRESS_ID, addressID);
    }
    return restApi.deleteAddress(header.getToken(),
        header.getLanguage(),
        header.getPlatform(),
        header.getCurrencySymbol(),
        header.getCurrencyCode(), userIdMapper);
  }

  @Override
  public Single<CommonModel> editAddress(Header header, AddAddressRequest request) {
    return restApi.editAddress(header.getToken(),
        header.getLanguage(),
        header.getPlatform(),
        header.getCurrencySymbol(),
        header.getCurrencyCode(), request);
  }

  @Override
  public Single<CommonModel> logout(Header header, LogOutRequest request) {
    return restApi.logOut(header.getToken(),
        header.getLanguage(),
        header.getPlatform(),
        header.getCurrencySymbol(),
        header.getCurrencyCode(), request);
  }

  @Override
  public Single<CommonModel> makeAddressDefault(Header header, MakeAddressDefaultRequest request) {
    return restApi.makeDefaultAddress(header.getToken(),
        header.getLanguage(),
        header.getPlatform(),
        header.getCurrencySymbol(),
        header.getCurrencyCode(), request);
  }

  public Single<ProfileDetails> getProfileDetails(Header header) {
    return restApi.getProfileDetails(header.getToken(),
        header.getLanguage(),
        header.getPlatform(),
        header.getCurrencySymbol(),
        header.getCurrencyCode());
  }

  public Single<ForgotPasswordDetails> getOtpForUpdateProfile(Header header,
      GetProfileOtpRequest request) {
    return restApi.getOtpForUpdateProfile(header.getToken(),
        header.getLanguage(), header.getPlatform(), header.getCurrencySymbol(),
        header.getCurrencyCode(), request);
  }

  public Single<CommonModel> verifyProfileOtp(Header header, ProfileOtpVerifyRequest request) {
    return restApi.verifyProfileOtp(header.getToken(), header.getLanguage(),
        header.getPlatform(), header.getCurrencySymbol(), header.getCurrencyCode(), request);
  }

  public Single<CartDetails> getCartProducts(Header header, String userId) {
    HashMap<String, Object> map = new HashMap<>();
    map.put(STORE_CATEGORY_ID, header.getStoreCatId());
    return restApi.getCartProducts(header.getToken(), header.getLanguage(),
        header.getPlatform(), header.getCurrencySymbol(), header.getCurrencyCode(), map);
  }

  public Single<CommonModel> addProductToCart(Header header, AddProductToCartRequest request) {
    return restApi.addProductToCart(header.getToken(), header.getLanguage(),
        header.getPlatform(), header.getCurrencySymbol(), header.getCurrencyCode(), request);
  }

  @Override
  public Single<IpAddressToLocationDetails> ipAddressToLocation(Header header) {
    ArrayMap<String, String> ipAddressMapper = null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
      ipAddressMapper = new ArrayMap<>();
      ipAddressMapper.put(IP_ADDRESS, header.getIpAddress());
    }
    return restApi.ipAddressToLocation(header.getLanguage(), header.getPlatform(),
        header.getCurrencySymbol(), header.getCurrencyCode(), ipAddressMapper);
  }

  @Override
  public Single<CommonModel> updateCart(Header header, UpdateCartRequest request) {
    return restApi.updateCart(header.getToken(), header.getLanguage(),
        header.getPlatform(), header.getCurrencySymbol(), header.getCurrencyCode(), request);
  }

  @Override
  public Single<CommonModel> placeOrder(Header header, PlaceOrderRequest request) {
    return restApi.placeOrder(header.getToken(), header.getLanguage(),
        header.getPlatform(), header.getCurrencySymbol(), header.getCurrencyCode(), request);
  }

  @Override
  public Single<OrderHistoryDetails> getOrderHistory(Header header,
      GetOrderHistoryRequest request) {
    return restApi.getOrderHistory(header.getToken(), header.getLanguage(),
        header.getPlatform(), header.getCurrencySymbol(), header.getCurrencyCode(),
        request.getQuery());
  }

  @Override
  public Single<CommonModel> makeReorder(Header header, ReorderRequest request) {
    return restApi.makeReorder(header.getToken(), header.getLanguage(),
        header.getPlatform(), header.getCurrencySymbol(), header.getCurrencyCode(), request);
  }

  @Override
  public Single<CommonModel> cancelOrder(Header header, CancelOrderRequest request) {
    return restApi.cancelOrder(header.getToken(), header.getLanguage(),
        header.getPlatform(), header.getCurrencySymbol(), header.getCurrencyCode(), request);
  }

  @Override
  public Single<MasterOrderDetails> getOrderDetails(Header header, String type, String orderId) {
    HashMap<String, Object> map = new HashMap<>();
    map.put(TYPE, type);
    map.put(ORDER_ID, orderId);
    return restApi.getOrderDetails(header.getToken(), header.getLanguage(),
        header.getPlatform(), header.getCurrencySymbol(), header.getCurrencyCode(), map);
  }

  @Override
  public Single<OrderDetails> getPackageDetails(Header header, String packId,
      String productOrderId) {
    HashMap<String, Object> map = new HashMap<>();
    map.put(PRODUCT_ORDER_ID, productOrderId);
    map.put(PACK_ID, packId);
    return restApi.getPackageDetails(header.getToken(), header.getLanguage(),
        header.getPlatform(), header.getCurrencySymbol(), header.getCurrencyCode(), map);
  }

  @Override
  public Single<GetReasonsItemDetails> getReasons(Header header) {
    HashMap<String, Object> map = new HashMap<>();
    map.put(TYPE, Integer.parseInt(REASON_TYPE));
    map.put(ID, REASON_TYPE);
    map.put(ADMIN, ADMIN_TYPE);
    return restApi.getReasons(header.getToken(), header.getLanguage(),
        header.getPlatform(), header.getCurrencySymbol(), header.getCurrencyCode(), map);
  }

  @Override
  public Single<TrackingListOrderStatusData> getDeliveryStatus(Header header,
      String productOrderId) {
    HashMap<String, Object> map = new HashMap<>();
    map.put(ORDER_ID, productOrderId);
    return restApi.getTrackingDetails(header.getToken(), header.getLanguage(),
        header.getPlatform(), header.getCurrencySymbol(), header.getCurrencyCode(), map);
  }

  @Override
  public Single<WalletDataDetails> getWalletAmt(Header header, String userId, String userType) {
    HashMap<String, Object> map = new HashMap<>();
    map.put(USER_ID, userId);
    map.put(USER_TYPE, userType);
    return restApi.getWalletDetailsApi(header.getToken(), header.getLanguage(), map);
  }

  @Override
  public Single<WalletDataDetails> getWalletAddMoney(Header header, AddMoneyRequest request) {
    return restApi.getWalletAddMoneyApi(header.getToken(), header.getLanguage(),
        header.getPlatform(), header.getCurrencySymbol(), header.getCurrencyCode(), request);
  }

  @Override
  public Single<UploadImageItemDetails> uploadImage(String folder, File file, String fileName) {
    RequestBody requestFile =
        RequestBody.create(
            MediaType.parse("image/jpg"),
            file
        );
    MultipartBody.Part body =
        MultipartBody.Part.createFormData(FILE, file.getName(), requestFile);
    return restApi.uploadImageApi(AMAZON_SERVER, folder, body, fileName);
  }

  @Override
  public Single<WalletTransactionsListDetails> getWalletTransactions(Header header, String walletId,
      int fetchSize, String pageState) {
    HashMap<String, Object> map = new HashMap<>();
    map.put(WALLET_ID, walletId);
    map.put(FETCH_SIZE, fetchSize);
    if (pageState != null && !pageState.isEmpty()) {
      map.put(PAGE_STATE, pageState);
    }
    return restApi.getWalletTransactionsApi(header.getToken(), header.getLanguage(), map);
  }

  @Override
  public Single<EstimateItemDetails> walletTransactions(Header header, String fromCurrency,
      String toCurrency, float amount) {
    HashMap<String, Object> map = new HashMap<>();
    map.put(FROM_CURRENCY, fromCurrency);
    map.put(TO_CURRENCY, toCurrency);
    map.put(AMOUNT, amount);
    return restApi.getWalletEstimateApi(header.getToken(), header.getLanguage(), map);
  }

  @Override
  public Single<ChangeLanListDetails> getLanguages() {
    return restApi.getLanguages();
  }

  @Override
  public Single<HelpListDetails> getHelp(Header header) {
    return restApi.getHelp(header.getLanguage(),
        header.getPlatform(), header.getCurrencySymbol(), header.getCurrencyCode());
  }

  @Override
  public Single<SignInDetailsData> getAppConfig(Header header, int backGroundFlag) {
    HashMap<String, Object> map = new HashMap<>();
    map.put(BACK_GROUND_FLAG, backGroundFlag);
    return restApi.getAppConfig(header.getToken(), header.getLanguage(),
        header.getPlatform(), header.getCurrencySymbol(), header.getCurrencyCode(), map);
  }

  @Override
  public Single<VersionListData> version(Header header, VersionRequest versionRequest) {
    return restApi.version(header.getToken(), header.getLanguage(),
        header.getPlatform(), header.getCurrencySymbol(), header.getCurrencyCode(), versionRequest);
  }

  @Override
  public Single<OrderCountListDetails> orderCount(Header header) {
    return restApi.getOrderCount(header.getToken(), header.getLanguage(),
        header.getPlatform(), header.getCurrencySymbol(), header.getCurrencyCode());
  }

  @Override
  public Single<GenerateTokenItemDetails> generateToken(Header header,
      GenerateTokenRequest generateTokenRequest) {
    return restApi.generateToken(header.getLanguage(),
        header.getPlatform(), header.getCurrencySymbol(), header.getCurrencyCode(),
        generateTokenRequest);
  }

  @Override
  public Single<WebPageData> getWebPageData(Header header) {
    HashMap<String, Object> map = new HashMap<>();
    map.put(FOR, FOR_WHICH_APP);
    return restApi.webPageData(header.getToken(), header.getLanguage(),
        header.getPlatform(), header.getCurrencySymbol(), header.getCurrencyCode(),
        map);
  }

  @Override
  public Single<GetChatListDetails> getChatHistory(Header header, String bookingId, String pageNo) {
    return restApi.chatHistory(header.getLanguage(),
        header.getPlatform(), header.getCurrencySymbol(), header.getCurrencyCode(), 2,
        bookingId, pageNo);
  }

  @Override
  public Single<GetChatListDetails> postMessge(Header header, PostMessage postMessage) {
    return restApi.postMessage(header.getToken(), header.getLanguage(),
        header.getPlatform(), header.getCurrencySymbol(), header.getCurrencyCode(), postMessage);
  }
}