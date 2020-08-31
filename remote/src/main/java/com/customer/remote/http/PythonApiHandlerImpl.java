package com.customer.remote.http;

import static com.customer.remote.http.RemoteConstants.APP_NAME;
import static com.customer.remote.http.RemoteConstants.E_COMMERCE_TYPE;
import static com.customer.remote.http.RemoteConstants.FILTER_TYPE;
import static com.customer.remote.http.RemoteConstants.FROM;
import static com.customer.remote.http.RemoteConstants.LIMIT;
import static com.customer.remote.http.RemoteConstants.PARENT_PRODUCT_ID;
import static com.customer.remote.http.RemoteConstants.PRODUCT_ID;
import static com.customer.remote.http.RemoteConstants.QR_CODE;
import static com.customer.remote.http.RemoteConstants.RETAILER_LOGIN_TYPE;
import static com.customer.remote.http.RemoteConstants.SEARCH_ITEM;
import static com.customer.remote.http.RemoteConstants.SKIP;
import static com.customer.remote.http.RemoteConstants.TO;

import android.text.TextUtils;
import android.util.ArrayMap;
import com.customer.remote.http.model.query.FilterQueryParam;
import com.customer.remote.http.model.query.RequestBodyMapper;
import com.customer.remote.http.model.request.AddToWishListRequest;
import com.customer.remote.http.model.request.ApplyFilterRequest;
import com.customer.remote.http.model.request.CategoryByIdRequest;
import com.customer.remote.http.model.request.CategoryQueryParameterRequest;
import com.customer.remote.http.model.request.GetSellerListRequest;
import com.customer.remote.http.model.request.Header;
import com.customer.remote.http.model.request.HomeSubCategoryRequest;
import com.customer.remote.http.model.request.MapToRequestQueryParamMapper;
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
import com.customer.remote.http.model.response.sellerlist.SellerDetails;
import com.customer.remote.http.model.response.suggestions.SuggestionListDetails;
import com.customer.remote.http.model.response.wishlist.WishListDetails;
import io.reactivex.Single;

public class PythonApiHandlerImpl implements PythonApiHandler {
  private SecApi secApi;

  PythonApiHandlerImpl(SecApi secApi) {
    this.secApi = secApi;
  }

  @Override
  public Single<HomeListDetails> homeNewPageApi(Header header) {
    return secApi.getHomePageNewApi(header.getToken(), header.getStoreCatId(),
        header.getLanguage());
  }

  @Override
  public Single<ProductDetails> getProductDetails(Header header, PdpRequest pdpRequest) {
    ArrayMap<String, String> hashReq = null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
      hashReq = new ArrayMap<>();
    }
    assert hashReq != null;
    hashReq.put(PRODUCT_ID, pdpRequest.getProductId());
    hashReq.put(PARENT_PRODUCT_ID, pdpRequest.getParentProductId());
    return secApi.getProductDetails(header.getToken(),
        header.getLanguage(), header.getIpAddress(),
        header.getLatitude(), header.getLongitude(),
        header.getPlatform(), header.getCity(), header.getCountry(), hashReq);
  }

  @Override
  public Single<CategoryDetail> getProductCategory(Header header, CategoryByIdRequest request) {
    if (!TextUtils.isEmpty(request.getSubCategoryId())) {
      return secApi.getSubSubCat(header.getToken(), header.getStoreCatId(),
          header.getLanguage(), request.getCategoryId(), request.getSubCategoryId(),
          request.getFrom(), request.getTo());
    } else if (!TextUtils.isEmpty(request.getCategoryId())) {
      return secApi.getProductCatById(header.getToken(), header.getStoreCatId(),
          request.getCategoryId(),
          header.getLanguage(),
          E_COMMERCE_TYPE, request.getFrom(), request.getTo());
    } else {
      return secApi.getProductCategory(header.getToken(), header.getStoreCatId(),
          header.getLanguage(), request.getFrom(), request.getTo());
    }
  }

  @Override
  public Single<ProductListingDetails> getProductList(Header header,
      CategoryQueryParameterRequest request) {
    return secApi.getProducts(header.getToken(), header.getStoreCatId(), header.getLanguage(),
        header.getIpAddress(), header.getPlatform(),
        header.getLatitude(),
        header.getLongitude(), header.getCity(), header.getCountry(),
        RequestBodyMapper.getSearchType(request),
        RequestBodyMapper.getBodyMapper(request, request.getSortType()));
  }

  public Single<ProductListingDetails> applyFilter(Header header, ApplyFilterRequest request) {
    return secApi.applyFilter(header.getToken(), header.getStoreCatId(), header.getLanguage(),
        header.getIpAddress(), header.getPlatform(), FILTER_TYPE,
        header.getLatitude(),
        header.getLongitude(), header.getCity(), header.getCountry(),
        FilterQueryParam.mapToQueryParamMapper(request, request.getSortType()));
  }

  @Override
  public Single<HomeCatDetails> getHomeSubCat(Header header, HomeSubCategoryRequest request) {
    return secApi.getHomeSubCat(header.getToken(), header.getStoreCatId(),
        header.getLanguage(), request.getSize(),
        request.getLimit());
  }

  @Override
  public Single<FilterDetails> getFilterParams(Header header,
      CategoryQueryParameterRequest request) {
    String defaultLevel = "1";
    return secApi.getFilterParams(header.getToken(), header.getStoreCatId(), defaultLevel,
        header.getLanguage(), 1,
        RequestBodyMapper.getBodyMapper(request, request.getSortType()));
  }

  @Override
  public Single<ProductListingDetails> getCatProductList(Header header,
      CategoryQueryParameterRequest request) {
    return secApi.getProducts(header.getToken(), header.getStoreCatId(),
        header.getLanguage(), header.getIpAddress(), header.getPlatform(), header.getLatitude(),
        header.getLongitude(), header.getCity(), header.getCountry(),
        RequestBodyMapper.getSearchType(request),
        RequestBodyMapper.getBodyMapper(request, request.getSortType()));
  }

  @Override
  public Single<ProductListingDetails> getFilteredProductList(Header header,
      CategoryQueryParameterRequest request) {
    if (TextUtils.isEmpty(request.getInText())) {
      return secApi.getProducts(header.getToken(), header.getStoreCatId(),
          header.getLanguage(), header.getIpAddress(), header.getPlatform(),
          header.getLatitude(),
          header.getLongitude(), header.getCity(), header.getCountry(),
          RequestBodyMapper.getSearchType(request),
          MapToRequestQueryParamMapper.mapToQueryParamMapper(request,
              request.getSortType()));
    } else {
      return secApi.getProducts(header.getToken(), header.getStoreCatId(),
          header.getLanguage(), header.getIpAddress(), header.getPlatform(),
          RequestBodyMapper.getSearchType(request), request.getInText(), header.getLatitude(),
          header.getLongitude(), header.getCity(), header.getCountry(),
          MapToRequestQueryParamMapper.mapToQueryParamMapper(request,
              request.getSortType()));
    }
  }

  @Override
  public Single<WishListDetails> getWishList(Header header, int sortType, String searchQuery) {
    if (TextUtils.isEmpty(searchQuery)) {
      return secApi.getWishList(header.getToken(), header.getStoreCatId(), header.getLanguage(),
          sortType);
    } else {
      return secApi.getWishList(header.getToken(), header.getStoreCatId(), header.getLanguage(),
          sortType, searchQuery);
    }
  }

  @Override
  public Single<CommonModel> deleteWishListProduct(Header header,
      AddToWishListRequest request) {
    return secApi.removeProductFromWishList(header.getToken(), header.getStoreCatId(),
        header.getLanguage(), request);
  }

  @Override
  public Single<SuggestionListDetails> getSuggestions(Header header, String searchItem) {
    ArrayMap<String, String> hashReq = null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
      hashReq = new ArrayMap<>();
    }
    assert hashReq != null;
    hashReq.put(SEARCH_ITEM, searchItem);
    return secApi.getSuggestionList(header.getToken(), header.getStoreCatId(), header.getLanguage(),
        hashReq);
  }

  @Override
  public Single<CommonModel> rateAndReviewProduct(Header header,
      ProductRateAndReviewRequest reviewRequest) {
    return secApi.reviewProduct(header.getToken(), header.getLanguage(),
        header.getIpAddress(), header.getLatitude(), header.getLongitude(), reviewRequest);
  }

  @Override
  public Single<RatableDetails> getRatableAttributes(Header header, String productId) {
    ArrayMap<String, String> hashReq = null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
      hashReq = new ArrayMap<>();
    }
    assert hashReq != null;
    hashReq.put(PRODUCT_ID, productId);
    return secApi.getRatableProducts(header.getToken(), header.getLanguage(), hashReq);
  }

  @Override
  public Single<CommonModel> reviewLikeDisLike(Header header, ReviewLikeDisLikeRequest request) {
    return secApi.reviewLikeDisLike(header.getToken(), header.getLanguage(), request);
  }

  @Override
  public Single<HomeCatDetails> getOfferProducts(Header header, int skip, int size) {
    return secApi.getOfferProducts(header.getToken(), header.getStoreCatId(), RETAILER_LOGIN_TYPE,
        E_COMMERCE_TYPE, skip, size);
  }

  @Override
  public Single<CommonModel> reportReview(Header header, ReportReviewRequest request) {
    return secApi.reportReview(header.getToken(), header.getLanguage(), request);
  }

  @Override
  public Single<ProductDetails> getAllReviews(Header header, String parentProductId, String skip,
      String limit) {
    ArrayMap<String, String> hashReq = null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
      hashReq = new ArrayMap<>();
    }
    assert hashReq != null;
    hashReq.put(PARENT_PRODUCT_ID, parentProductId);
    hashReq.put(SKIP, skip);
    hashReq.put(LIMIT, limit);
    return secApi.getAllReviews(header.getToken(), header.getLanguage(), hashReq);
  }

  @Override
  public Single<CommonModel> addProductToWishList(Header header, AddToWishListRequest request) {
    return secApi.addProductToWishList(header.getToken(), header.getLanguage(),
        request);
  }

  @Override
  public Single<CommonModel> clearAllWishListItems(Header header) {
    return secApi.clearAllWishListItems(header.getToken());
  }

  @Override
  public Single<CommonModel> notifyProductAvailability(String token,
      NotifyProductAvailabilityRequest request) {
    return secApi.notifyProductAvailability(token, request);
  }

  @Override
  public Single<BaoCodeResponseDetails> getProductOnQrCode(Header header, String qrCode) {
    ArrayMap<String, String> hashReq = null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
      hashReq = new ArrayMap<>();
    }
    assert hashReq != null;
    hashReq.put(QR_CODE, qrCode);
    return secApi.getProductOnQrCode(header.getToken(), header.getLanguage(), hashReq);
  }

  @Override
  public Single<SellerDetails> getSellerList(Header header, GetSellerListRequest request) {
    ArrayMap<String, String> hashReq = null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
      hashReq = new ArrayMap<>();
    }
    assert hashReq != null;
    hashReq.put(PRODUCT_ID, request.getProductId());
    hashReq.put(PARENT_PRODUCT_ID, request.getParentProductId());
    return secApi.getSellerList(header.getToken(), header.getLanguage(),
        RETAILER_LOGIN_TYPE, hashReq);
  }

  @Override
  public Single<NotificationDetails> getNotificationsList(Header header, String from, String to) {
    ArrayMap<String, String> hashReq = null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
      hashReq = new ArrayMap<>();
    }
    assert hashReq != null;
    hashReq.put(FROM, from);
    hashReq.put(TO, to);
    return secApi.getNotifications(header.getToken(), APP_NAME, hashReq);
  }

  @Override
  public Single<RecentlyViewedDetails> getAllRecentlyViewedProducts(Header header) {
    return secApi.getAllRecentlyViewedProducts(header.getToken(),
        header.getLanguage(), RETAILER_LOGIN_TYPE, header.getStoreCatId());
  }

  @Override
  public Single<AllBrandsListDetails> allBrandsApi(Header header, String from, String to) {
    return secApi.getAllBrandsApi(header.getToken(), header.getStoreCatId(),
        header.getLanguage(), from, to);
  }

  @Override
  public Single<AllBrandsListDetails> changeLanguage(Header header) {
    return null;
  }

  @Override
  public Single<RecentSearchDetails> getRecentSearchSuggestion(Header header) {
    return secApi.getRecentSearchSuggestion(header.getToken(), header.getStoreCatId());
  }
}