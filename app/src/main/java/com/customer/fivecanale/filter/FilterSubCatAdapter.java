package com.customer.fivecanale.filter;

import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.PRICE_TYPE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.customer.domain.model.filter.FilterListData;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ItemFilterSubCatBinding;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/*
 * Purpose – This class Holds Filter Sub Item UI.
 * @author 3Embed
 * Created on Nov 15, 2019
 * Modified on
 */
public class FilterSubCatAdapter extends
    RecyclerView.Adapter<FilterSubCatAdapter.FilterSubCatViewHolder> {
  private Set<String> mSelectedData = new HashSet<>();
  private ArrayList<FilterListData> mFilterListData;
  private int mType;
  private OnFilteredDataSelected mClickListener;
  private int mLastSelected = -1;

  FilterSubCatAdapter(ArrayList<FilterListData> filterListData, int type,
      OnFilteredDataSelected clickListener) {
    this.mFilterListData = filterListData;
    this.mType = type;
    this.mClickListener = clickListener;
  }

  @NonNull
  @Override
  public FilterSubCatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemFilterSubCatBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_filter_sub_cat, parent, false);
    return new FilterSubCatViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull FilterSubCatViewHolder holder, int position) {
    if (mFilterListData.get(position).isSelected()) {
      mSelectedData.add(mFilterListData.get(position).getName());
    }
    holder.mBinding.cbSubCatName.setVisibility(mType == PRICE_TYPE ? View.GONE : View.VISIBLE);
    holder.mBinding.rbSubCatName.setVisibility(mType == PRICE_TYPE ? View.VISIBLE : View.GONE);
    if (mType == PRICE_TYPE) {
      if (mFilterListData.get(position).isSelected()) {
        mLastSelected = position;
      }
      mFilterListData.get(position).setSelected(mLastSelected == position);
      holder.mBinding.rbSubCatName.setChecked(mFilterListData.get(position).isSelected());
      holder.mBinding.rbSubCatName.setText(mFilterListData.get(position).getName());
      holder.mBinding.rbSubCatName.setOnClickListener(view -> {
        mSelectedData.clear();
        mSelectedData.add(mFilterListData.get(position).getName());
        mSelectedData.add(String.format("%s", mFilterListData.get(position).getMinPrice()));
        mSelectedData.add(String.format("%s", mFilterListData.get(position).getMaxPrice()));
        mClickListener.onFilteredDataSelectedListener(mType, mSelectedData);
        if (position != mLastSelected) {
          mFilterListData.get(position).setSelected(true);
          if (mLastSelected != -1) {
            mFilterListData.get(mLastSelected).setSelected(false);
          }
          mLastSelected = position;
          notifyDataSetChanged();
        }
      });
    } else {
      holder.mBinding.cbSubCatName.setText(mFilterListData.get(position).getName());
      holder.mBinding.cbSubCatName.setChecked(mFilterListData.get(position).isSelected());
      holder.mBinding.cbSubCatName.setOnClickListener(v -> {
        mFilterListData.get(position).setSelected(holder.mBinding.cbSubCatName.isChecked());
        if (holder.mBinding.cbSubCatName.isChecked()) {
          mSelectedData.add(mFilterListData.get(position).getName());
        } else {
          mSelectedData.remove(mFilterListData.get(position).getName());
        }
        mClickListener.onFilteredDataSelectedListener(mType, mSelectedData);
      });
    }
  }

  @Override
  public int getItemCount() {
    return EcomUtil.isEmptyArray(mFilterListData) ? ZERO : mFilterListData.size();
  }

  public interface OnFilteredDataSelected {
    /**
     * This method is using to maintain the selected filter values
     *
     * @param type       selected filter value
     * @param filterList selected sub value
     */
    void onFilteredDataSelectedListener(int type, Set<String> filterList);
  }

  static class FilterSubCatViewHolder extends RecyclerView.ViewHolder {
    ItemFilterSubCatBinding mBinding;

    FilterSubCatViewHolder(ItemFilterSubCatBinding binding) {
      super(binding.getRoot());
      this.mBinding = binding;
    }
  }
}