package com.customer.fivecanale.dagger;

import com.customer.fivecanale.addmoney.EcomAddMoneyActivity;
import com.customer.fivecanale.addresslist.SavedAddressListActivity;
import com.customer.fivecanale.allbrands.AllBrandsActivity;
import com.customer.fivecanale.allcategory.AllCategoryActivity;
import com.customer.fivecanale.alldetails.AllDetailsActivity;
import com.customer.fivecanale.allreviews.AllReviewsActivity;
import com.customer.fivecanale.boarding.forgotpassword.EcomForgotPasswordActivity;
import com.customer.fivecanale.boarding.login.EcomLoginActivity;
import com.customer.fivecanale.boarding.register.EcomSignUpActivity;
import com.customer.fivecanale.cancelorder.CancelOrderOrProductActivity;
import com.customer.fivecanale.cart.EcomCartActivity;
import com.customer.fivecanale.cart.EcomCartDaggerModule;
import com.customer.fivecanale.category.CategoryViewMoreActivity;
import com.customer.fivecanale.changelang.ChangeLangActivity;
import com.customer.fivecanale.filter.ProductFilterActivity;
import com.customer.fivecanale.help.HelpActivity;
import com.customer.fivecanale.help.helpsubcategory.HelpSubCatActivity;
import com.customer.fivecanale.historyfilter.HistoryOrderFilterActivity;
import com.customer.fivecanale.historyproductdetail.HistoryProductDetailActivity;
import com.customer.fivecanale.injection.module.AddAddressViewModelModule;
import com.customer.fivecanale.injection.module.AllBrandsViewModelModule;
import com.customer.fivecanale.injection.module.AllCategoryViewModelModule;
import com.customer.fivecanale.injection.module.AllDetailsModelModule;
import com.customer.fivecanale.injection.module.AttributesBottomSheetDaggerModule;
import com.customer.fivecanale.injection.module.AttributesBottomSheetViewModule;
import com.customer.fivecanale.injection.module.BarCodePreviewViewModelModule;
import com.customer.fivecanale.injection.module.CancelOrderViewModelModule;
import com.customer.fivecanale.injection.module.CatViewMoreViewModelModule;
import com.customer.fivecanale.injection.module.CategoryModelModule;
import com.customer.fivecanale.injection.module.ChangeLanViewModelModule;
import com.customer.fivecanale.injection.module.ChatModelModule;
import com.customer.fivecanale.injection.module.EcomAllReviewsModelModule;
import com.customer.fivecanale.injection.module.EcomAllReviewsViewModule;
import com.customer.fivecanale.injection.module.EcomCartViewModel;
import com.customer.fivecanale.injection.module.EcomForgotPasswordModelModule;
import com.customer.fivecanale.injection.module.EcomForgotPasswordViewModule;
import com.customer.fivecanale.injection.module.EcomLoginModelModule;
import com.customer.fivecanale.injection.module.EcomLoginViewModule;
import com.customer.fivecanale.injection.module.EcomProductDetaillsModelModule;
import com.customer.fivecanale.injection.module.EcomSignUpModelModule;
import com.customer.fivecanale.injection.module.EcomSignUpViewModule;
import com.customer.fivecanale.injection.module.EcomTrackViewModelModule;
import com.customer.fivecanale.injection.module.HelpViewModelModule;
import com.customer.fivecanale.injection.module.HistoryOrderDetailModelModule;
import com.customer.fivecanale.injection.module.HistoryOrderFilterModule;
import com.customer.fivecanale.injection.module.HistoryProductDetailViewModelModule;
import com.customer.fivecanale.injection.module.HomeViewModule;
import com.customer.fivecanale.injection.module.NotificationsViewModelModule;
import com.customer.fivecanale.injection.module.PaymentMethodModelModule;
import com.customer.fivecanale.injection.module.PreviewImageModelModule;
import com.customer.fivecanale.injection.module.ProductFilterModule;
import com.customer.fivecanale.injection.module.ProductListUseCaseModule;
import com.customer.fivecanale.injection.module.ProductListViewModelModule;
import com.customer.fivecanale.injection.module.ProfileDetailsViewModelModule;
import com.customer.fivecanale.injection.module.PromoCodesViewModelModule;
import com.customer.fivecanale.injection.module.RecentlyViewedViewModelModule;
import com.customer.fivecanale.injection.module.ReferFriendViewModelModel;
import com.customer.fivecanale.injection.module.ReviewProductModelModule;
import com.customer.fivecanale.injection.module.ReviewProductViewModule;
import com.customer.fivecanale.injection.module.SavedAddressListViewModule;
import com.customer.fivecanale.injection.module.SavedCardsModelModule;
import com.customer.fivecanale.injection.module.SelectReasonViewModelModule;
import com.customer.fivecanale.injection.module.SellerBottomSheetDaggerModule;
import com.customer.fivecanale.injection.module.SortBottomSheetModule;
import com.customer.fivecanale.injection.module.TrendingProductsViewModelModule;
import com.customer.fivecanale.injection.module.UpdateProfileViewModelModule;
import com.customer.fivecanale.injection.module.ViewModelModule;
import com.customer.fivecanale.injection.module.WalletAddMoneyModelModule;
import com.customer.fivecanale.injection.module.WalletModelModule;
import com.customer.fivecanale.injection.module.WebViewModelModule;
import com.customer.fivecanale.injection.module.WishListViewModelModule;
import com.customer.fivecanale.injection.usecasemodule.AddAddressUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.AddToCartUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.ApplyFilterUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.ApplyPromoCodesUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.BarCodeReadUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.CancelOrderUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.CancelReasonsUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.ChangeLanguageUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.ClearWishListUseCaseModel;
import com.customer.fivecanale.injection.usecasemodule.DeleteAddressUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.DeleteWishListProductUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.EditAddressUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.FilterUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.GetAllBrandsUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.GetAllPromoCodesUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.GetCartUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.GetCategoryUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.GetChatUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.GetNotificationsUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.GetOrderHistoryUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.GetPackageDetUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.GetProfileDetUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.GetRecentlyViewedUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.GetWebPageUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.GetWishListProductsUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.HelpUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.HistoryOrderDetailUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.IpAddressToLocationUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.MakeAddressDefaultUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.NotifyProductUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.OfferedProductsUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.OrderCountUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.PlaceOrderUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.PostMsgUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.ReOrderUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.RecentSearchSuggestionUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.SavedAddressListUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.SendOtpToUpdateProfileUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.SuggestionsUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.TrackingUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.TransactionEstimateUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.UpdateProfileUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.UploadImageUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.VerifyProfileOtpUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.ViewMoreSellersUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.WalletAddAmountUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.WalletAmtUseCaseModule;
import com.customer.fivecanale.injection.usecasemodule.WalletTransactionsUseCaseModule;
import com.customer.fivecanale.landing.HomeActivity;
import com.customer.fivecanale.landing.HomeActivityDaggerModule;
import com.customer.fivecanale.landing.profile.profiledetails.ProfileDetailsActivity;
import com.customer.fivecanale.landing.profile.profiledetails.updateprofile.UpdateProfileActivity;
import com.customer.fivecanale.landing.searchscreen.SearchActivity;
import com.customer.fivecanale.landing.searchscreen.SearchModel;
import com.customer.fivecanale.landing.searchscreen.SearchViewModelModule;
import com.customer.fivecanale.manageaddress.AddAddressActivity;
import com.customer.fivecanale.mqtt_chat.ChattingActivity;
import com.customer.fivecanale.notifications.NotificationsActivity;
import com.customer.fivecanale.orderdetails.HistoryOrderDetailActivity;
import com.customer.fivecanale.payment.PaymentMethodActivity;
import com.customer.fivecanale.pdp.ProductDetailsActivity;
import com.customer.fivecanale.preview.PreviewImageActivity;
import com.customer.fivecanale.productlist.ProductListingActivity;
import com.customer.fivecanale.promocode.PromoCodesActivity;
import com.customer.fivecanale.recentlyviewed.RecentlyViewedProductsActivity;
import com.customer.fivecanale.referfriend.ReferFriendActivity;
import com.customer.fivecanale.review.ReviewProductActivity;
import com.customer.fivecanale.savedcards.SavedCardsActivity;
import com.customer.fivecanale.search.CategoryActivity;
import com.customer.fivecanale.selectreason.SelectReasonActivity;
import com.customer.fivecanale.splash.SplashActivity;
import com.customer.fivecanale.splash.SplashViewModelModule;
import com.customer.fivecanale.tracking.EcomTrackingActivity;
import com.customer.fivecanale.trending.TrendingProductsActivity;
import com.customer.fivecanale.uiutil.barcodescanning.BarCodePreviewActivity;
import com.customer.fivecanale.uiutil.barcodescanning.bottomsheet.ProductNotFoundBtmSheetModule;
import com.customer.fivecanale.wallet.EcomWalletActivity;
import com.customer.fivecanale.wallet.EcomWalletDaggerModule;
import com.customer.fivecanale.wallet.EcomWalletModule;
import com.customer.fivecanale.webview.WebViewAct;
import com.customer.fivecanale.wishlist.WishListActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * We want Dagger.Android to create a Subcomponent which has a parent Component of whichever module
 * ActivityBindingModule is on, in our case that will be AppComponent. The beautiful part about this
 * setup is that you never need to tell AppComponent that it is going to have all these
 * subcomponents nor do you need to tell these subcomponents that AppComponent exists. We are also
 * telling Dagger.Android that this generated SubComponent needs to include the specified modules
 * and be aware of a scope annotation @ActivityScoped When Dagger.Android annotation processor runs
 * it will create 4 subcomponents for us.
 */
@Module
abstract class ActivityBindingModule {
  @ActivityScoped
  @ContributesAndroidInjector(
      modules = {
          SplashViewModelModule.class
      })
  abstract SplashActivity splashActivity();



  /*ECom*/

  @ActivityScoped
  @ContributesAndroidInjector(
      modules = {
          SearchModel.class,
          HomeActivityDaggerModule.class,
          ViewModelModule.class,
          HomeViewModule.class,
          IpAddressToLocationUseCaseModule.class,
          GetOrderHistoryUseCaseModule.class,
          GetCategoryUseCaseModule.class,
          SuggestionsUseCaseModule.class,
          RecentSearchSuggestionUseCaseModule.class,
          GetCartUseCaseModule.class,
          AddToCartUseCaseModule.class,
          PlaceOrderUseCaseModule.class,
          WalletAmtUseCaseModule.class,
          ApplyPromoCodesUseCaseModule.class,
          OrderCountUseCaseModule.class
      })
  abstract HomeActivity homeActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {OfferedProductsUseCaseModule.class,
      TrendingProductsViewModelModule.class})
  abstract TrendingProductsActivity trendingProductsActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {GetCategoryUseCaseModule.class,
      AllCategoryViewModelModule.class})
  abstract AllCategoryActivity bindAllViewActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {EcomLoginModelModule.class, EcomLoginViewModule.class})
  abstract EcomLoginActivity ecomLoginActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {EcomSignUpModelModule.class, EcomSignUpViewModule.class})
  abstract EcomSignUpActivity ecomSignUpActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {CategoryModelModule.class})
  abstract CategoryActivity categoryActivity();

  @ActivityScoped
  @ContributesAndroidInjector(
      modules = {EcomForgotPasswordModelModule.class, EcomForgotPasswordViewModule.class,
          VerifyProfileOtpUseCaseModule.class,
          SendOtpToUpdateProfileUseCaseModule.class})
  abstract EcomForgotPasswordActivity ecomForgotPasswordActivity();

  @ActivityScoped
  @ContributesAndroidInjector(
      modules = {EcomAllReviewsModelModule.class, EcomAllReviewsViewModule.class})
  abstract AllReviewsActivity allReviewsActivity();

  @ActivityScoped
  @ContributesAndroidInjector(
      modules = {
          ApplyFilterUseCaseModule.class,
          ProductListUseCaseModule.class,
          ProductListViewModelModule.class,
          SortBottomSheetModule.class
      })
  abstract ProductListingActivity productListActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {CatViewMoreViewModelModule.class,
      GetCategoryUseCaseModule.class})
  abstract CategoryViewMoreActivity categoryViewMoreActivity();

  @ActivityScoped
  @ContributesAndroidInjector(
      modules = {ProductFilterModule.class, ProductListUseCaseModule.class,
          FilterUseCaseModule.class})
  abstract ProductFilterActivity productFilterActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {ProfileDetailsViewModelModule.class,
      GetProfileDetUseCaseModule.class, UpdateProfileUseCaseModule.class,
      UploadImageUseCaseModule.class})
  abstract ProfileDetailsActivity profileDetailsActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {AddAddressUseCaseModule.class,
      AddAddressViewModelModule.class, EditAddressUseCaseModule.class})
  abstract AddAddressActivity addAddressDetailsActivity();

  @ActivityScoped
  @ContributesAndroidInjector(
      modules = {UpdateProfileViewModelModule.class, UpdateProfileUseCaseModule.class,
          SendOtpToUpdateProfileUseCaseModule.class})
  abstract UpdateProfileActivity updateProfileActivity();

  @ActivityScoped
  @ContributesAndroidInjector(
      modules = {SavedAddressListViewModule.class, DeleteAddressUseCaseModule.class,
          SavedAddressListUseCaseModule.class, MakeAddressDefaultUseCaseModule.class})
  abstract SavedAddressListActivity savedAddressListActivity();

  @ActivityScoped
  @ContributesAndroidInjector(
      modules = {GetWishListProductsUseCaseModule.class, ClearWishListUseCaseModel.class,
          SortBottomSheetModule.class,
          WishListViewModelModule.class, DeleteWishListProductUseCaseModule.class})
  abstract WishListActivity wishListActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {SearchViewModelModule.class, SearchModel.class,
      SuggestionsUseCaseModule.class, RecentSearchSuggestionUseCaseModule.class,
      GetCategoryUseCaseModule.class})
  abstract SearchActivity searchActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {EcomProductDetaillsModelModule.class,
      AttributesBottomSheetViewModule.class, AttributesBottomSheetDaggerModule.class,
      SellerBottomSheetDaggerModule.class, DeleteWishListProductUseCaseModule.class,
      NotifyProductUseCaseModule.class, AddToCartUseCaseModule.class,
      ViewMoreSellersUseCaseModule.class})
  abstract ProductDetailsActivity productDetailsActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {AllDetailsModelModule.class})
  abstract AllDetailsActivity allDetailsActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {ReviewProductModelModule.class,
      ReviewProductViewModule.class, UploadImageUseCaseModule.class})
  abstract ReviewProductActivity reviewProductActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {PreviewImageModelModule.class})
  abstract PreviewImageActivity previewImageActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {ReferFriendViewModelModel.class})
  abstract ReferFriendActivity referFriendActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {PaymentMethodModelModule.class,
      WalletAddAmountUseCaseModule.class, WalletAmtUseCaseModule.class,
      TransactionEstimateUseCaseModule.class})
  abstract PaymentMethodActivity paymentMethodActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {BarCodeReadUseCaseModule.class,
      BarCodePreviewViewModelModule.class, ProductNotFoundBtmSheetModule.class})
  abstract BarCodePreviewActivity barCodePreviewActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {RecentlyViewedViewModelModule.class,
      GetRecentlyViewedUseCaseModule.class})
  abstract RecentlyViewedProductsActivity recentlyViewedProductsActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {HistoryOrderDetailModelModule.class,
      HistoryOrderDetailUseCaseModule.class, ReOrderUseCaseModule.class})
  abstract HistoryOrderDetailActivity historyOrderDetailActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {HistoryProductDetailViewModelModule.class,
      GetPackageDetUseCaseModule.class, ReOrderUseCaseModule.class,
      HistoryOrderDetailUseCaseModule.class})
  abstract HistoryProductDetailActivity historyProductDetailActivity();

  @ActivityScoped
  @ContributesAndroidInjector(
      modules = {
          EcomCartDaggerModule.class, EcomCartViewModel.class, GetCartUseCaseModule.class,
          IpAddressToLocationUseCaseModule.class, AddToCartUseCaseModule.class,
          PlaceOrderUseCaseModule.class, ApplyPromoCodesUseCaseModule.class
      })
  abstract EcomCartActivity ecomCartActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {CancelOrderViewModelModule.class,
      CancelOrderUseCaseModule.class})
  abstract CancelOrderOrProductActivity cancelOrderOrProductActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {SelectReasonViewModelModule.class,
      CancelReasonsUseCaseModule.class})
  abstract SelectReasonActivity selectReasonActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {EcomTrackViewModelModule.class,
      TrackingUseCaseModule.class})
  abstract EcomTrackingActivity ecomTrackingActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {HistoryOrderFilterModule.class})
  abstract HistoryOrderFilterActivity historyOrderFilterActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {
      WalletAddMoneyModelModule.class})
  abstract EcomAddMoneyActivity ecomAddMoney();

  @ActivityScoped
  @ContributesAndroidInjector(
      modules = {
          EcomWalletDaggerModule.class, EcomWalletModule.class, WalletModelModule.class,
          WalletAmtUseCaseModule.class, WalletTransactionsUseCaseModule.class,
          WalletAddAmountUseCaseModule.class
      })
  abstract EcomWalletActivity ecomWalletActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {SavedCardsModelModule.class})
  abstract SavedCardsActivity savedCardsActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {NotificationsViewModelModule.class,
      GetNotificationsUseCaseModule.class})
  abstract NotificationsActivity notificationsActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {GetAllBrandsUseCaseModule.class,
      AllBrandsViewModelModule.class})
  abstract AllBrandsActivity allBrandsActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {ChangeLanViewModelModule.class,
      ChangeLanguageUseCaseModule.class})
  abstract ChangeLangActivity changeLangAct();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {HelpViewModelModule.class, HelpUseCaseModule.class})
  abstract HelpActivity helpAct();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {})
  abstract HelpSubCatActivity helpSubCat();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {PromoCodesViewModelModule.class,
      GetAllPromoCodesUseCaseModule.class})
  abstract PromoCodesActivity promoCodesActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {GetWebPageUseCaseModule.class, WebViewModelModule.class})
  abstract WebViewAct webViewAct();

  @ActivityScoped
  @ContributesAndroidInjector(modules = {ChatModelModule.class, UploadImageUseCaseModule.class,
      GetChatUseCaseModule.class, PostMsgUseCaseModule.class})
  abstract ChattingActivity chattingActivity();
}