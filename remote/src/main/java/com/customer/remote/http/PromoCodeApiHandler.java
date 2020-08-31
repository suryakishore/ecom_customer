package com.customer.remote.http;

import com.customer.remote.http.model.request.AddToWishListRequest;
import com.customer.remote.http.model.request.ApplyFilterRequest;
import com.customer.remote.http.model.request.ApplyPromoCodeRequest;
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
import com.customer.remote.http.model.response.promocode.ApplyPromoCodeListData;
import com.customer.remote.http.model.response.promocode.PromoCodeListDetails;
import com.customer.remote.http.model.response.recentlyviewed.RecentlyViewedDetails;
import com.customer.remote.http.model.response.recentsearch.RecentSearchDetails;
import com.customer.remote.http.model.response.sellerlist.SellerDetails;
import com.customer.remote.http.model.response.suggestions.SuggestionListDetails;
import com.customer.remote.http.model.response.wishlist.WishListDetails;
import io.reactivex.Single;
import java.util.ArrayList;

public interface PromoCodeApiHandler {

  Single<PromoCodeListDetails> getPromoCodes(Header header, String countryId, String cityId,
      String cartId, String storeId);
  Single<ApplyPromoCodeListData> applyPromoCode(Header header, ApplyPromoCodeRequest applyPromoCodeRequest);

}
