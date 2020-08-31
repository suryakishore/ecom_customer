package com.customer.fivecanale.addresslist;

import static com.customer.fivecanale.util.EcomConstants.NONE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.customer.domain.model.getaddress.AddressListItemData;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ItemAddressBinding;
import java.util.ArrayList;

/*
 * Purpose – This class Holds list of Address added by user.
 * @author 3Embed
 * Created on Dec 10, 2019
 * Modified on
 */
public class AddressListAdapter extends
    RecyclerView.Adapter<AddressListAdapter.AddressListViewHolder> {
  private ArrayList<AddressListItemData> mListItemData;
  private OnAddressItemClickListener mClickListener;
  private int mLastSelectedPosition = NONE;
  private String mDefaultAddressId;
  private Context mContext;
  private boolean mIsFromCart;

  AddressListAdapter(
      ArrayList<AddressListItemData> listItemData, OnAddressItemClickListener clickListener,
      boolean isFromCart, int lastSelectedPosition) {
    this.mListItemData = listItemData;
    this.mClickListener = clickListener;
    this.mIsFromCart = isFromCart;
    this.mLastSelectedPosition = lastSelectedPosition;
  }

  @NonNull
  @Override
  public AddressListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    ItemAddressBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_address, parent, false);
    return new AddressListViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull AddressListViewHolder holder, int position) {
    if (mListItemData.get(position).isDefault()) {
      mDefaultAddressId = mListItemData.get(position).getId();
    }
    setSelectableBackground(holder, position == mLastSelectedPosition);
    setDrawables(holder, position);
    holder.mBinding.ivDeleteAddress.setEnabled(!mListItemData.get(position).isDefault());
    holder.mBinding.tvAddressDefault.setVisibility(
        mListItemData.get(position).isDefault() ? View.VISIBLE : View.INVISIBLE);
    holder.mBinding.tvMakeDefault.setVisibility(!mIsFromCart ? (!mListItemData.get(
        position).isDefault()
        ? View.VISIBLE : View.INVISIBLE) : View.GONE);
    holder.mBinding.tvName.setText(mListItemData.get(position).getName());
    holder.mBinding.tvAddressType.setText(mListItemData.get(position).getTaggedAs());
    holder.mBinding.tvPhNum.setText(
        String.format("+%s %s", mListItemData.get(position).getMobileNumberCode(),
            mListItemData.get(position).getMobileNumber()));
    holder.mBinding.tvProfileAddress.setText(
        String.format("%s,%s,%s,%s", mListItemData.get(position).getAddLine1(),
            mListItemData.get(position).getLocality(), mListItemData.get(position).getCity(),
            mListItemData.get(position).getCountry()));
    holder.mBinding.ivEditAddress.setOnClickListener(
        view -> mClickListener.onEditClickListener(mListItemData.get(position)));
    holder.mBinding.ivDeleteAddress.setOnClickListener(
        view -> mClickListener.onDeleteClickListener(mListItemData.get(position).getId()));
    if (mIsFromCart) {
      holder.mBinding.cvAddress.setOnClickListener(view -> {
        int copyOfLastCheckedPosition = mLastSelectedPosition;
        mLastSelectedPosition = position;
        if (mListItemData.get(position).isDefault()) {
          mClickListener.selectAddress(mListItemData.get(position));
          notifyItemChanged(copyOfLastCheckedPosition);
          notifyItemChanged(mLastSelectedPosition);
        } else {
          mClickListener.makeAddressDefault(mListItemData.get(position).getId(),
              mDefaultAddressId, mLastSelectedPosition);
        }
      });
    } else {
      holder.mBinding.tvMakeDefault.setOnClickListener(view -> {
        if (mListItemData.get(position).isDefault()) {
          mClickListener.selectAddress(mListItemData.get(position));
        } else {
          mClickListener.makeAddressDefault(mListItemData.get(position).getId(),
              mDefaultAddressId, mLastSelectedPosition);
        }
      });
    }

  }

  /**
   * set background of ard selectable
   * @param isSelected boolean
   */
  private void setSelectableBackground(AddressListViewHolder holder, boolean isSelected) {
    holder.mBinding.cvAddress.setCardBackgroundColor(ContextCompat.getColor(mContext, isSelected
            ? R.color.addressGreenBack : R.color.white));
    holder.mBinding.tvAddressType.setBackgroundColor(ContextCompat.getColor(mContext, isSelected
            ? R.color.colorStatusGreen : R.color.wildSand));
    holder.mBinding.tvAddressType.setTextColor(ContextCompat.getColor(mContext, isSelected
            ? R.color.white : R.color.colorBlack));
  }

  @Override
  public int getItemCount() {
    return EcomUtil.isEmptyArray(mListItemData) ? ZERO : mListItemData.size();
  }

  /**
   * This method is using to set Image drawables
   */
  private void setDrawables(AddressListViewHolder holder, int position) {
    Drawable deleteIcon = mListItemData.get(position).isDefault()
        ? mContext.getResources().getDrawable(
        R.drawable.address_non_active_delete) : mContext.getResources().getDrawable(
        R.drawable.ic_delete);
    holder.mBinding.ivDeleteAddress.setImageDrawable(deleteIcon);
  }

  public interface OnAddressItemClickListener {
    /**
     * This method is using to listen for delete action click
     *
     * @param addressId addressId of the Address to be delete
     */
    void onDeleteClickListener(String addressId);

    /**
     * This method is using to listen for Edit click action of address elements
     *
     * @param addressListItemData address to be Edit
     */
    void onEditClickListener(AddressListItemData addressListItemData);

    void makeAddressDefault(String addressId, String prevDefAddressId, int lastSelectedPosition);

    void selectAddress(AddressListItemData addressListItemData);

  }

  static class AddressListViewHolder extends RecyclerView.ViewHolder {
    ItemAddressBinding mBinding;

    AddressListViewHolder(ItemAddressBinding binding) {
      super(binding.getRoot());
      this.mBinding = binding;
    }
  }
}