package com.customer.remote.http;

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
import com.customer.remote.http.model.response.chat.GetChatItemDetails;
import com.customer.remote.http.model.response.chat.GetChatListDetails;
import com.customer.remote.http.model.response.generatetoken.GenerateTokenItemDetails;
import com.customer.remote.http.model.response.getReasons.GetReasonsItemDetails;
import com.customer.remote.http.model.response.getaddress.AddressDetails;
import com.customer.remote.http.model.response.getcart.CartDetails;
import com.customer.remote.http.model.response.guestSignIn.GuestSignInDetailsData;
import com.customer.remote.http.model.response.help.HelpListDetails;
import com.customer.remote.http.model.response.location.IpAddressToLocationDetails;
import com.customer.remote.http.model.response.newHome.HomeListDetails;
import com.customer.remote.http.model.response.ordercount.OrderCountListDetails;
import com.customer.remote.http.model.response.orderdetails.MasterOrderDetails;
import com.customer.remote.http.model.response.orderdetails.OrderDetails;
import com.customer.remote.http.model.response.orderhistory.OrderHistoryDetails;
import com.customer.remote.http.model.response.profile.ProfileDetails;
import com.customer.remote.http.model.response.promocode.PromoCodeListDetails;
import com.customer.remote.http.model.response.signIn.SignInDetailsData;
import com.customer.remote.http.model.response.signUp.SignUpDetails;
import com.customer.remote.http.model.response.tracking.TrackingListDetails;
import com.customer.remote.http.model.response.tracking.TrackingListOrderStatusData;
import com.customer.remote.http.model.response.uploadImage.UploadImageDetails;
import com.customer.remote.http.model.response.uploadImage.UploadImageItemDetails;
import com.customer.remote.http.model.response.version.VersionListData;
import com.customer.remote.http.model.response.wallet.WalletDataDetails;
import com.customer.remote.http.model.response.wallet.WalletTransactionsListDetails;
import com.customer.remote.http.model.response.walletEstimate.EstimateItemDetails;
import com.customer.remote.http.model.response.walletEstimate.WalletEstimateListDetails;
import com.customer.remote.http.model.response.webview.WebPageData;
import com.customer.remote.http.model.response.webview.WebPageDetails;
import io.reactivex.Single;
import java.io.File;

public interface ApiHandler {
  Single<GuestSignInDetailsData> guestSignIn(Header header, GuestLogInReq request);

  Single<SignUpDetails> signUp(Header header, SignUpRequest signUpRequest);

  Single<SignInDetailsData> signIn(Header header, SignInRequest signInRequest);

  Single<SignInDetailsData> verifyOTP(Header header, VerifyOTPRequest verifyOTPRequest);

  Single<ForgotPasswordDetails> forgotPassword(Header header,
      ForgotPasswordRequest forgotPasswordRequest);

  Single<VerifyMobileOrMailDetails> verifyMobileOrMail(Header header,
      VerifyMobileOrMailRequest verifyMobileOrMailRequest);

  Single<CommonModel> resetPassword(Header header, String otpID,
      ResetPasswordRequest resetPasswordRequest);

  Single<ForgotPasswordDetails> sendOtp(Header header, SendOtpRequest sendOtpRequest);

  Single<CommonModel> updateProfile(Header header,
      UpdateProfileRequest updateProfileRequest);

  Single<CommonModel> addAddress(Header header, AddAddressRequest request);

  Single<AddressDetails> getAddress(Header header, String userId);

  Single<CommonModel> deleteAddress(Header header, String addressID);

  Single<CommonModel> editAddress(Header header, AddAddressRequest request);

  Single<CommonModel> logout(Header header, LogOutRequest request);

  Single<CommonModel> makeAddressDefault(Header header, MakeAddressDefaultRequest request);

  Single<ProfileDetails> getProfileDetails(Header header);

  Single<ForgotPasswordDetails> getOtpForUpdateProfile(Header header, GetProfileOtpRequest request);

  Single<CommonModel> verifyProfileOtp(Header header, ProfileOtpVerifyRequest request);

  Single<CartDetails> getCartProducts(Header header, String userId);

  Single<CommonModel> addProductToCart(Header header, AddProductToCartRequest request);

  Single<IpAddressToLocationDetails> ipAddressToLocation(Header header);

  Single<CommonModel> updateCart(Header header, UpdateCartRequest request);

  Single<CommonModel> placeOrder(Header header, PlaceOrderRequest request);

  Single<OrderHistoryDetails> getOrderHistory(Header header, GetOrderHistoryRequest request);

  Single<CommonModel> makeReorder(Header header, ReorderRequest request);

  Single<CommonModel> cancelOrder(Header header, CancelOrderRequest request);

  Single<MasterOrderDetails> getOrderDetails(Header header, String type, String orderId);

  Single<OrderDetails> getPackageDetails(Header header, String packId,
      String productOrderId);

  Single<GetReasonsItemDetails> getReasons(Header header);

  Single<TrackingListOrderStatusData> getDeliveryStatus(Header header,
      String productOrderId);

  Single<WalletDataDetails> getWalletAmt(Header header, String userId, String userType);

  Single<WalletDataDetails> getWalletAddMoney(Header header, AddMoneyRequest request);

  Single<UploadImageItemDetails> uploadImage(String folder, File file,
      String fileName);


  Single<WalletTransactionsListDetails> getWalletTransactions(Header header, String walletId,
      int fetchSize,String pageState);


  Single<EstimateItemDetails> walletTransactions(Header header,String fromCurrency,
      String toCurrency, float amount);

  Single<ChangeLanListDetails> getLanguages();
  Single<HelpListDetails> getHelp(Header header);
  Single<SignInDetailsData> getAppConfig(Header header,int backGroundFlag);
  Single<VersionListData> version(Header header, VersionRequest versionRequest);
  Single<OrderCountListDetails> orderCount(Header header);
  Single<GenerateTokenItemDetails> generateToken(Header header, GenerateTokenRequest generateTokenRequest);
  Single<WebPageData> getWebPageData(Header header);
  Single<GetChatListDetails> getChatHistory(Header header,String bookingId ,String pageNo  );
  Single<GetChatListDetails> postMessge(Header header, PostMessage postMessage  );

}
