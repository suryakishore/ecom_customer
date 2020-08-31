package com.customer.remote.http;

import android.util.ArrayMap;
import com.customer.remote.http.model.request.AddToWishListRequest;
import com.customer.remote.http.model.request.NotifyProductAvailabilityRequest;
import com.customer.remote.http.model.request.ProductRateAndReviewRequest;
import com.customer.remote.http.model.request.ReportReviewRequest;
import com.customer.remote.http.model.request.ReviewLikeDisLikeRequest;
import com.customer.remote.http.model.response.BaoCodeResponseDetails;
import com.customer.remote.http.model.response.CommonModel;
import com.customer.remote.http.model.response.brands.AllBrandsListDetails;
import com.customer.remote.http.model.response.filter.FilterDetails;
import com.customer.remote.http.model.response.getratable.RatableDetails;
import com.customer.remote.http.model.response.homeSubCategory.HomeCatDetails;
import com.customer.remote.http.model.response.newHome.HomeListDetails;
import com.customer.remote.http.model.response.notification.NotificationDetails;
import com.customer.remote.http.model.response.pdp.ProductDetails;
import com.customer.remote.http.model.response.productCategory.CategoryDetail;
import com.customer.remote.http.model.response.productListing.ProductListingDetails;
import com.customer.remote.http.model.response.recentlyviewed.RecentlyViewedDetails;
import com.customer.remote.http.model.response.recentsearch.RecentSearchDetails;
import com.customer.remote.http.model.response.sellerlist.SellerDetails;
import com.customer.remote.http.model.response.suggestions.SuggestionListDetails;
import com.customer.remote.http.model.response.wishlist.WishListDetails;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface SecApi {
  @GET("home/page")
  Single<HomeListDetails> getHomePageNewApi(@Header("authorization") String authorization,
      @Header("storeCategoryId") String storeCatId, //1:-Grocery, 8:-E-Com
      @Header("language") String language);

  @GET("product/details")
  Single<ProductDetails> getProductDetails(@Header("authorization") String authorization,
      @Header("language") String language,
      @Header("ipAddress") String ipAddress,
      @Header("latitude") double latitude,
      @Header("longitude") double longitude,
      @Header("platform") int platform,
      @Header("city") String city,
      @Header("country") String country,
      @QueryMap(encoded = true) ArrayMap<String, String> options);

  @GET("category/list")
  Single<CategoryDetail> getProductCategory(@Header("authorization") String authorization,
      @Header("STORECATEGORYID") String storeCatId,
      @Header("language") String language,
      @Header("from") int from,
      @Header("to") int to);

  @GET("category/list")
  Single<CategoryDetail> getProductCatById(@Header("authorization") String authorization,
      @Header("STORECATEGORYID") String storeCatId,
      @Header("CATEGORYID") String categoryId,
      @Header("language") String language,
      @Header("storetype") int storetype,
      @Header("from") int from,
      @Header("to") int to);

  /*
    categoryList/
  */
  @GET("category/list")
  Single<CategoryDetail> getSubSubCat(@Header("authorization") String authorization,
      @Header("STORECATEGORYID") String storeCatId,
      @Header("language") String language,
      @Header("CATEGORYID") String categoryId,
      @Header("subCategoryId") String subCategoryId,
      @Header("from") int from,
      @Header("to") int to);

  @POST("searchFilter/")
  Single<ProductListingDetails> getProductList(@Header("authorization") String authorization,
      @Header("popularStatus") int popularStatus,
      @Header("STORECATEGORYID") String storeCatId,
      @Header("filterType") int filterType,
      @Header("language") String language,
      @Body String body);

  @GET("subCategoryProducts")
  Single<HomeCatDetails> getHomeSubCat(@Header("authorization") String authorization,
      @Header("STORECATEGORYID") String storeType,
      @Header("language") String language,
      @Header("size") int size,
      @Header("skip") int limit);

  @GET("filter")
  Single<FilterDetails> getFilterParams(@Header("authorization") String authorization,
      @Header("STORECATEGORYID") String storeCatId,
      @Header("level") String level,
      @Header("language") String language,
      @Header("filtertype") int filterType,
      @QueryMap(encoded = true) ArrayMap<String, String> options);

  @GET("searchFilter/")
  Single<ProductListingDetails> getProducts(@Header("authorization") String authorization,
      @Header("STORECATEGORYID") String storeCatId,
      @Header("language") String language,
      @Header("ipAddress") String ipAddress,
      @Header("platform") int platform,
      @Header("latitude") double latitude,
      @Header("longitude") double longitude,
      @Header("city") String city,
      @Header("country") String country,
      @Header("searchType") String searchType,
      @QueryMap(encoded = true) ArrayMap<String, String> options);

  @GET("searchFilter/")
  Single<ProductListingDetails> getProducts(@Header("authorization") String authorization,
      @Header("STORECATEGORYID") String storeCatId,
      @Header("language") String language,
      @Header("ipAddress") String ipAddress,
      @Header("platform") int platform,
      @Header("searchType") String searchType,
      @Header("searchIn") String searchIn,
      @Header("latitude") double latitude,
      @Header("longitude") double longitude,
      @Header("city") String city,
      @Header("country") String country,
      @QueryMap(encoded = true) ArrayMap<String, String> options);

  @GET("wishList/")
  Single<WishListDetails> getWishList(@Header("authorization") String authorization,
      @Header("STORECATEGORYID") String storeCatId,
      @Header("language") String language,
      @Header("sortType") int sortType);

  @GET("wishList/")
  Single<WishListDetails> getWishList(@Header("authorization") String authorization,
      @Header("STORECATEGORYID") String storeCatId,
      @Header("language") String language,
      @Header("sortType") int sortType,
      @Header("text") String searchQuery);

  @PATCH("favourite/product/")
  Single<CommonModel> removeProductFromWishList(
      @Header("authorization") String authorization,
      @Header("STORECATEGORYID") String storeCatId,
      @Header("language") String language,
      @Body AddToWishListRequest request);

  @GET("suggestions/")
  Single<SuggestionListDetails> getSuggestionList(
      @Header("authorization") String authorization,
      @Header("STORECATEGORYID") String storeCatId,
      @Header("language") String language,
      @QueryMap(encoded = true) ArrayMap<String, String> query);

  @POST("productReviewRating/")
  Single<CommonModel> reviewProduct(@Header("authorization") String authorization,
      @Header("language") String language,
      @Header("ipAddress") String ipAddress,
      @Header("latitude") double latitude,
      @Header("longitude") double longitude,
      @Body ProductRateAndReviewRequest reviewRequest);

  @GET("ratableAttribute/")
  Single<RatableDetails> getRatableProducts(@Header("authorization") String authorization,
      @Header("language") String language,
      @QueryMap(encoded = true) ArrayMap<String, String> productId);

  @GET("offer/products")
  Single<HomeCatDetails> getOfferProducts(@Header("authorization") String authorization,
      @Header("STORECATEGORYID") String storeCatId,
      @Header("logintype") String logintype,
      @Header("storetype") int storetype,
      @Header("skip") int skip,
      @Header("size") int size);

  @POST("likeDislikeReview/")
  Single<CommonModel> reviewLikeDisLike(@Header("authorization") String authorization,
      @Header("language") String language,
      @Body ReviewLikeDisLikeRequest request);

  @POST("reportReview/")
  Single<CommonModel> reportReview(@Header("authorization") String authorization,
      @Header("language") String language,
      @Body ReportReviewRequest reviewRequest);

  @GET("product/review/")
  Single<ProductDetails> getAllReviews(@Header("authorization") String authorization,
      @Header("language") String language,
      @QueryMap(encoded = true) ArrayMap<String, String> queryParam);

  @POST("favourite/product/")
  Single<CommonModel> addProductToWishList(@Header("authorization") String authorization,
      @Header("language") String language,
      @Body AddToWishListRequest request);

  @PATCH("wishList/")
  Single<CommonModel> clearAllWishListItems(@Header("authorization") String authorization);

  @POST("product/notify")
  Single<CommonModel> notifyProductAvailability(@Header("authorization") String authorization,
      @Body NotifyProductAvailabilityRequest request);

  @GET("scan/products/")
  Single<BaoCodeResponseDetails> getProductOnQrCode(@Header("authorization") String authorization,
      @Header("language") String language,
      @QueryMap(encoded = true) ArrayMap<String, String> queryParam);

  @GET("seller/list")
  Single<SellerDetails> getSellerList(@Header("authorization") String authorization,
      @Header("language") String language,
      @Header("loginType") String loginType,
      @QueryMap(encoded = true) ArrayMap<String, String> queryParam);

  @GET("recent/search")
  Single<RecentSearchDetails> getRecentSearchSuggestion(
      @Header("authorization") String authorization,
      @Header("storeCategoryId") String storeCategoryId);

  @GET("searchFilter/")
  Single<ProductListingDetails> applyFilter(@Header("authorization") String authorization,
      @Header("STORECATEGORYID") String storeCatId,
      @Header("language") String language,
      @Header("ipAddress") String ipAddress,
      @Header("platform") int platform,
      @Header("searchType") int searchType,
      @Header("latitude") double latitude,
      @Header("longitude") double longitude,
      @Header("city") String city,
      @Header("country") String country,
      @QueryMap(encoded = true) ArrayMap<String, String> options);

  @GET("user/recentview")
  Single<RecentlyViewedDetails> getAllRecentlyViewedProducts(
      @Header("authorization") String authorization,
      @Header("language") String language,
      @Header("loginType") String loginType,
      @Header("storeCategoryId") String storeCategoryId);

  @GET("user/notification")
  Single<NotificationDetails> getNotifications(@Header("authorization") String authorization,
      @Header("appName") String appName,
      @QueryMap(encoded = true) ArrayMap<String, String> queryParam);

  @GET("brand/list")
  Single<AllBrandsListDetails> getAllBrandsApi(@Header("authorization") String authorization,
      @Header("storeCategoryId") String storeCatId,
      @Header("language") String language,
      @Header("FROM") String from,
      @Header("TO") String to);
}
