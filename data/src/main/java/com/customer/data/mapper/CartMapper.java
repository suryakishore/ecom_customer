package com.customer.data.mapper;

import static com.customer.data.utils.DataConstants.LOWER_BOUND;

import android.text.TextUtils;
import android.util.Log;
import com.customer.data.repository.observable.CartDataObservable;
import com.customer.data.utils.DataUtils;
import com.customer.domain.model.getcart.CartAccoutingData;
import com.customer.domain.model.getcart.CartAttributesData;
import com.customer.domain.model.getcart.CartData;
import com.customer.domain.model.getcart.CartDeliveryData;
import com.customer.domain.model.getcart.CartLogsData;
import com.customer.domain.model.getcart.CartOfferData;
import com.customer.domain.model.getcart.CartPickupAddressData;
import com.customer.domain.model.getcart.CartProductItemData;
import com.customer.domain.model.getcart.CartSellerData;
import com.customer.domain.model.getcart.CartUserData;
import com.customer.domain.model.getcart.QuantityData;
import com.customer.remote.http.model.response.getcart.CartAccountingDetails;
import com.customer.remote.http.model.response.getcart.CartAttributesDetails;
import com.customer.remote.http.model.response.getcart.CartDeliveryDetails;
import com.customer.remote.http.model.response.getcart.CartDetails;
import com.customer.remote.http.model.response.getcart.CartLogsDetails;
import com.customer.remote.http.model.response.getcart.CartOfferDetails;
import com.customer.remote.http.model.response.getcart.CartPickupAddressDetails;
import com.customer.remote.http.model.response.getcart.CartProductItemDetails;
import com.customer.remote.http.model.response.getcart.CartSellerDetails;
import com.customer.remote.http.model.response.getcart.CartUserDetails;
import com.customer.remote.http.model.response.getcart.QuantityDetails;
import com.data.cache.DatabaseManager;
import com.data.cache.entities.UserCart;
import java.util.ArrayList;

public class CartMapper {
  DatabaseManager mDatabaseManager;

  public CartData mapper(CartDetails cartDetails, DatabaseManager databaseManager) {
    mDatabaseManager = databaseManager;
    return new CartData(cartDetails.getNotes(), cartDetails.getCartTotalTax(),
        cartDetails.getStoreTypeId(), cartDetails.getSubTotal(),
        convertToCartLogData(cartDetails.getCartLogs()), cartDetails.getCartType(),
        cartDetails.getSeqId(), CommonMapper.convertCartTaxDataList(cartDetails.getTaxData()),
        cartDetails.getStoreType(), cartDetails.getGrandTotal(), cartDetails.getCurrencySymbol(),
        cartDetails.getStoreCategory(),
        CommonMapper.convertCartTaxDataList(cartDetails.getTaxList()),
        cartDetails.getDeliveryFee(), cartDetails.getUserTypeMsg(),
        cartDetails.getStoreCategoryId(), cartDetails.getSubtotal(),
        cartDetails.getTotalCartquantity(), cartDetails.getCartTypeInTxt(),
        cartDetails.getTotalDiscount(), cartDetails.get_id(), cartDetails.getUserType(),
        cartDetails.getTotalAppliedTaxOnCart(), convertToCartUserData(cartDetails.getUser()),
        cartDetails.getCurrencyCode(), convertToCartSellerData(cartDetails.getSellers()),
        convertToAccountAddressData(cartDetails.getAccounting()));
  }

  private CartLogsData convertToCartLogData(CartLogsDetails cartLogsDetails) {
    CartLogsData cartLogsData = null;
    if (cartLogsDetails != null) {
      cartLogsData = new CartLogsData(cartLogsDetails.getTimeStampIso(),
          cartLogsDetails.getStatusMsg(), cartLogsDetails.getUserType(),
          cartLogsDetails.getUserId(), cartLogsDetails.getStatus(), cartLogsDetails.getTimestamp());
    }
    return cartLogsData;
  }

  private CartUserData convertToCartUserData(CartUserDetails cartUserDetails) {
    CartUserData cartUserData = null;
    if (cartUserDetails != null) {
      cartUserData = new CartUserData(cartUserDetails.getFirstName(), cartUserDetails.getCity(),
          cartUserDetails.getCountryCode(), cartUserDetails.getMobile(),
          cartUserDetails.getUserIp(), cartUserDetails.getUserPostCode(),
          cartUserDetails.getUserId(), cartUserDetails.getUserLong(),
          cartUserDetails.getUserLat(), cartUserDetails.getEmail());
    }
    return cartUserData;
  }

  private ArrayList<CartSellerData> convertToCartSellerData(
      ArrayList<CartSellerDetails> cartSellerDetails) {
    ArrayList<CartSellerData> cartSellerData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(cartSellerDetails)) {
      for (CartSellerDetails details : cartSellerDetails) {
        CartSellerData data = new CartSellerData(details.getTargetAmtForFreeDelivery(),
            details.getSellerCartValue(), details.getFreeDelivery(),
            details.getFullFillMentCenterId(), details.getMinOrder(), details.getDriverType(),
            convertToCartProductData(details.getProducts()), details.getFullfilledBy(),
            CommonMapper.convertToPickUpAddressData(details.getPickupAddress()),
            details.getAutoDispatch(), details.getAutoAcceptOrders(),
            details.getIsInventoryCheck(), details.getName());
        cartSellerData.add(data);
      }
    }
    return cartSellerData;
  }

  private CartPickupAddressData convertToPickUpAddressData(
      CartPickupAddressDetails addressDetails) {
    CartPickupAddressData cartPickupAddressData = null;
    if (addressDetails != null) {
      cartPickupAddressData = new CartPickupAddressData(addressDetails.getCountry(),
          addressDetails.getAddress(), addressDetails.getLocality(),
          addressDetails.getCityId(),
          addressDetails.getSublocality_level_2(), addressDetails.getSublocality_level_1(),
          addressDetails.getRoute(), addressDetails.getAdministrative_area_level_2(),
          addressDetails.getCityName(), addressDetails.getAdministrative_area_level_1(),
          addressDetails.getPostal_code());
    }
    return cartPickupAddressData;
  }

  private CartAccoutingData convertToAccountAddressData(
      CartAccountingDetails cartAccountingDetails) {
    CartAccoutingData cartAccoutingData = null;
    if (cartAccountingDetails != null) {
      cartAccoutingData = new CartAccoutingData(cartAccountingDetails.getUnitPrice(),
          cartAccountingDetails.getOfferDiscount(), cartAccountingDetails.getTaxableAmount(),
          cartAccountingDetails.getDeliveryFee(),
          cartAccountingDetails.getFinalUnitPrice(), cartAccountingDetails.getAddOnsAmount(),
          CommonMapper.convertCartTaxDataList(cartAccountingDetails.getTax()),
          cartAccountingDetails.getSubTotal(),
          cartAccountingDetails.getTaxAmount(), cartAccountingDetails.getPromoDiscount(),
          cartAccountingDetails.getFinalTotal());
    }
    return cartAccoutingData;
  }

  private CartOfferData convertToOfferData(
      CartOfferDetails cartOfferDetails) {
    CartOfferData cartOfferData = null;
    if (cartOfferDetails != null) {
      cartOfferData = new CartOfferData(cartOfferDetails.getOfferType(),
          cartOfferDetails.getOfferId(), cartOfferDetails.getOfferValue(),
          cartOfferDetails.getOfferTitle());
    }
    return cartOfferData;
  }

  private ArrayList<CartProductItemData> convertToCartProductData(
      ArrayList<CartProductItemDetails> cartProductItemDetails) {
    ArrayList<CartProductItemData> cartProductItemData = new ArrayList<>();
    ArrayList<UserCart> cartList = new ArrayList<>();
    if (!DataUtils.isEmptyArray(cartProductItemDetails) && cartProductItemDetails.size() > 0) {
      for (CartProductItemDetails details : cartProductItemDetails) {
        int quantity = details.getQuantity() != null && TextUtils.isDigitsOnly(
            details.getQuantity().getValue()) ? Integer.parseInt(details.getQuantity().getValue())
            : LOWER_BOUND;
        UserCart cart = new UserCart(details.get_id(), details.getStoreId(), details.getName(),
            quantity);
        cartList.add(cart);
        CartProductItemData data = new CartProductItemData(details.getUpcNumber(),
            details.getColor(), details.getOriginalPrice(),
            details.getDiscount(), details.getFinalPrice(),
            details.getAisle(), details.getPackageType(), details.getCentralProductId(),
            details.getOfferType(), details.getOfferValue(),
            convertToCartDelData(details.getDeliveryDetails()),
            details.getUnitId(), details.getStoreName(), details.getInStock(),
            details.getOfferTitle(), CommonMapper.convertToImage(details.getImages()),
            details.getBrandName(), convertToQuantityData(details.getQuantity()),
            details.getOriginalPriceTotal(), details.getAddOns(), details.getCurrencySymbol(),
            CommonMapper.convertCartTaxDataList(details.getTax()), details.getStoreId(),
            CommonMapper.convertCartTaxDataList(details.getProductTaxArray()), details.getShelf(),
            details.getOfferDiscount(), details.getIsCentral(),
            details.getTotalAppliedTaxOnProduct(),
            details.getDiscountedPrice(), details.getDirections(), details.getName(),
            details.getOfferId(), convertToCartAttributeData(details.getAttributes()),
            details.get_id(), details.getCurrencyCode(),
            convertToAccountAddressData(details.getAccounting()),
            convertToOfferData(details.getOfferDetails()), details.getAvailableQuantity());
        cartProductItemData.add(data);
      }
    }
    mDatabaseManager.cart().insertCartItems(cartList);
    CartDataObservable.getInstance().postData(new CartData(6, "", 0));
    return cartProductItemData;
  }

  private CartDeliveryData convertToCartDelData(CartDeliveryDetails cartDeliveryDetails) {
    CartDeliveryData cartDeliveryData = null;
    if (cartDeliveryDetails != null) {
      cartDeliveryData = new CartDeliveryData(cartDeliveryDetails.getDeliveryFee(),
          cartDeliveryDetails.getDeliveryByDeliveryPartner(),
          cartDeliveryDetails.getDeliveryByFleetDriver(), cartDeliveryDetails.getTime());
    }
    return cartDeliveryData;
  }

  private QuantityData convertToQuantityData(QuantityDetails quantityDetails) {
    QuantityData quantityData = null;
    if (quantityDetails != null) {
      quantityData = new QuantityData(quantityDetails.getUnit(), quantityDetails.getValue());
    }
    return quantityData;
  }

  private ArrayList<CartAttributesData> convertToCartAttributeData(
      ArrayList<CartAttributesDetails> cartAttributesDetails) {
    ArrayList<CartAttributesData> cartAttributesData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(cartAttributesDetails)) {
      for (CartAttributesDetails details : cartAttributesDetails) {
        CartAttributesData data = new CartAttributesData(details.getAttrname(), details.getValue());
        cartAttributesData.add(data);
      }
    }
    return cartAttributesData;
  }
}
