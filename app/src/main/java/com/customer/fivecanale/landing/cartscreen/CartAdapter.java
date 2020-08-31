package com.customer.fivecanale.landing.cartscreen;

import static com.customer.fivecanale.util.EcomConstants.FOUR;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.THREE;
import static com.customer.fivecanale.util.EcomConstants.TWO;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.bumptech.glide.Glide;
import com.customer.domain.model.getcart.CartAccoutingData;
import com.customer.domain.model.getcart.CartOfferData;
import com.customer.domain.model.getcart.CartProductItemData;
import com.customer.fivecanale.SelectItem;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ItemCartBinding;
import java.util.ArrayList;

/*inflates the cart items list*/
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
  private SelectItem mSelectItem;
  private ArrayList<CartProductItemData> mCartProductItemData;
  private Context mContext;
  private QuantityClick mQuantityClick;
  private boolean mConfirmOrder;

  public CartAdapter(
      ArrayList<CartProductItemData> cartProductItemData, QuantityClick quantityClick,
      SelectItem selectItem,
      boolean confirmOrder) {
    mCartProductItemData = cartProductItemData;
    this.mQuantityClick = quantityClick;
    this.mConfirmOrder = confirmOrder;
    this.mSelectItem = selectItem;
  }

  @NonNull
  @Override
  public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    ItemCartBinding cartBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
        R.layout.item_cart, parent, false);
    return new CartViewHolder(cartBinding);
  }

  @Override
  public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
    CartProductItemData data = mCartProductItemData.get(position);
    holder.mBinding.clProductItem.setEnabled(data.isEnable());
    holder.mBinding.tvCartProductName.setText(data.getName());
    if (data.getAttributes() != null && data.getAttributes().size() > ZERO) {
      StringBuilder attributeName = new StringBuilder();
      for (int i = ZERO; i < data.getAttributes().size(); i++) {
        if (data.getAttributes().get(i).getValue() != null && !data.getAttributes().get(
            i).getValue().isEmpty()) {
          attributeName.append(data.getAttributes().get(i).getAttrname()).append(":").append(
              data.getAttributes().get(i).getValue()).append(" ");
        }
        if (i == ONE) {
          break;
        }
      }
      holder.mBinding.tvCartSizeAndColor.setText(attributeName.toString());
      holder.mBinding.tvCartMore.setVisibility(
          data.getAttributes().size() > TWO ? View.VISIBLE : View.GONE);
    }
    holder.mBinding.tvSellerName.setText(data.getStoreName());
    CartAccoutingData accoutingData = data.getAccounting();
    EcomUtil.printLog(
        "exe" + "accoutingData.getFinalUnitPrice()" + accoutingData.getFinalUnitPrice());
    if (accoutingData.getFinalUnitPrice() != null) {
      holder.mBinding.tvCartFinalPrice.setText(
          mConfirmOrder ? String.format("%s %s * %s %s", data.getQuantity().getValue(),
              data.getQuantity().getUnit(), data.getCurrencySymbol(),
              String.format("%.2f", Float.parseFloat(accoutingData.getFinalTotal()))) :
              String.format("%s%s", data.getCurrencySymbol(),
                  String.format("%.2f", Float.parseFloat(accoutingData.getFinalTotal()))));
    }
    CartOfferData cartOfferData = data.getOfferDetails();
    if (cartOfferData.getOfferId() != null && !cartOfferData.getOfferId().isEmpty()) {
      holder.mBinding.tvCartBasePrice.setText(
          String.format("%s%s", data.getCurrencySymbol(), accoutingData.getUnitPrice()));
      if (cartOfferData.getOfferType() != null && !cartOfferData.getOfferType().isEmpty()) {
        if (Integer.parseInt(cartOfferData.getOfferType()) == ZERO) {
          holder.mBinding.tvCartProductDiscount.setText(
              String.format("%s %s", cartOfferData.getOfferValue(),
                  mContext.getResources().getString(R.string.pdpFlat)));
        } else if (Integer.parseInt(cartOfferData.getOfferType()) == ONE) {
          holder.mBinding.tvCartProductDiscount.setText(
              String.format("%s %s %s", cartOfferData.getOfferValue(), "%",
                  mContext.getResources().getString(R.string.pdpOff)));
        }
      }
    }
    holder.mBinding.tvCartProductQuantity.setText(
        String.format("%s %s", data.getQuantity().getValue(), data.getQuantity().getUnit()));
    holder.mBinding.tvCartRemove.setVisibility(mConfirmOrder ? View.GONE : View.VISIBLE);
    holder.mBinding.tvCartProductQuantity.setVisibility(mConfirmOrder ? View.GONE : View.VISIBLE);
    String imageUrl = data.getImages().getMedium();
    if (!TextUtils.isEmpty(imageUrl)) {
      Glide.with(mContext)
          .load(imageUrl.replace(" ", "%20"))
          .into(holder.mBinding.ivCartProductImg);
    }
    holder.mBinding.tvCartProductQuantity.setOnClickListener(view -> {
      if (mQuantityClick != null && !mConfirmOrder) {
        mQuantityClick.onItemClick(holder.mBinding.tvCartProductQuantity,
            data.getQuantity().getUnit(), position, ZERO);
      }
    });
    holder.mBinding.tvCartMore.setOnClickListener(view -> {
      if (mQuantityClick != null) {
        mQuantityClick.onItemClick(null,
            null, holder.getAdapterPosition(), FOUR);
      }
    });
    holder.mBinding.tvCartRemove.setOnClickListener(view -> {
      if (mQuantityClick != null) {
        mQuantityClick.onItemClick(null,
            null, holder.getAdapterPosition(), THREE);
      }
    });
    holder.mBinding.clProductItem.setOnClickListener(view -> mSelectItem.onSelectItem(position));
  }

  @Override
  public int getItemCount() {
    return EcomUtil.isEmptyArray(mCartProductItemData) ? ZERO : mCartProductItemData.size();
  }

  static class CartViewHolder extends RecyclerView.ViewHolder {
    ItemCartBinding mBinding;

    CartViewHolder(ItemCartBinding binding) {
      super(binding.getRoot());
      mBinding = binding;
      EcomUtil.strikeThroughText(mBinding.tvCartBasePrice);
    }
  }
}