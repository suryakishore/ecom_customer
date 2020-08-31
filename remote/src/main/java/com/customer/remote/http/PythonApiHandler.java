package com.customer.remote.http;

import com.customer.remote.http.model.request.AddToWishListRequest;
import com.customer.remote.http.model.request.ApplyFilterRequest;
import com.customer.remote.http.model.request.CategoryByIdRequest;
import com.customer.remote.http.model.request.CategoryQueryParameterRequest;
import com.customer.remote.http.model.request.GetSellerListRequest;
import com.customer.remote.http.model.request.Header;
import com.customer.remote.http.model.request.HomeSubCategoryRequest;
import com.customer.remote.http.model.request.NotifyProductAvailabilityRequest;
import com.customer.remote.http.model.request.PdpRequest;
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
import com.customer.remote.http.model.response.recentsearch.RecentSearchSuggestion;
import com.customer.remote.http.model.response.sellerlist.SellerDetails;
import com.customer.remote.http.model.response.suggestions.SuggestionListDetails;
import com.customer.remote.http.model.response.wishlist.WishListDetails;
import io.reactivex.Single;

public interface PythonApiHandler {

  Single<HomeListDetails> homeNewPageApi(Header header);

  Single<ProductDetails> getProductDetails(Header header, PdpRequest pdpRequest);

  Single<CategoryDetail> getProductCategory(Header header, CategoryByIdRequest request);

  Single<ProductListingDetails> getProductList(Header header,
      CategoryQueryParameterRequest request);

  Single<HomeCatDetails> getHomeSubCat(Header header, HomeSubCategoryRequest request);

  Single<FilterDetails> getFilterParams(Header header, CategoryQueryParameterRequest request);

  Single<ProductListingDetails> getCatProductList(Header header,
      CategoryQueryParameterRequest request);

  Single<ProductListingDetails> getFilteredProductList(Header header,
      CategoryQueryParameterRequest request);

  Single<WishListDetails> getWishList(Header header, int sortType, String searchQuery);

  Single<CommonModel> deleteWishListProduct(Header header,
      AddToWishListRequest request);

  Single<SuggestionListDetails> getSuggestions(Header header, String searchItem);

  Single<HomeCatDetails> getOfferProducts(Header header, int skip, int size);

  Single<CommonModel> rateAndReviewProduct(Header header,
      ProductRateAndReviewRequest reviewRequest);

  Single<RatableDetails> getRatableAttributes(Header header, String productId);

  Single<CommonModel> reviewLikeDisLike(Header header, ReviewLikeDisLikeRequest request);

  Single<CommonModel> reportReview(Header header, ReportReviewRequest request);

  Single<ProductDetails> getAllReviews(Header header, String parentProductId, String skip,
      String limit);

  Single<CommonModel> addProductToWishList(Header header, AddToWishListRequest request);

  Single<CommonModel> clearAllWishListItems(Header header);

  Single<CommonModel> notifyProductAvailability(String token,
      NotifyProductAvailabilityRequest request);

  Single<BaoCodeResponseDetails> getProductOnQrCode(Header header, String qrCode);

  Single<SellerDetails> getSellerList(Header header, GetSellerListRequest request);

  Single<NotificationDetails> getNotificationsList(Header header, String  from,String to);

  Single<RecentSearchDetails> getRecentSearchSuggestion(Header header);

  Single<ProductListingDetails> applyFilter(Header header, ApplyFilterRequest request);

  Single<RecentlyViewedDetails> getAllRecentlyViewedProducts(Header header);


  Single<AllBrandsListDetails> allBrandsApi(Header header,String from,String to);


  Single<AllBrandsListDetails> changeLanguage(Header header);

}
