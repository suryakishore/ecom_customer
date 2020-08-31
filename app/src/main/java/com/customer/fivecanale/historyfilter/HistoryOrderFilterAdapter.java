package com.customer.fivecanale.historyfilter;

import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.customer.domain.model.filter.FilterListData;
import com.databinding.ItemFilterSubCatBinding;
import java.util.ArrayList;

/**
 * adapter class for the history filter.
 */
public class HistoryOrderFilterAdapter extends
    RecyclerView.Adapter<HistoryOrderFilterAdapter.ViewHolder> {
  private ArrayList<FilterListData> mFilterListData;
  private OnHistoryFilteredDataSelected mOnHistoryFilteredDataSelected;
  private int mLastSelected = -1;

  HistoryOrderFilterAdapter(
      ArrayList<FilterListData> filterListData,
      OnHistoryFilteredDataSelected onHistoryFilteredDataSelected) {
    mFilterListData = filterListData;
    mOnHistoryFilteredDataSelected = onHistoryFilteredDataSelected;
  }

  @NonNull
  @Override
  public HistoryOrderFilterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    ItemFilterSubCatBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_filter_sub_cat, parent, false);
    return new ViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull HistoryOrderFilterAdapter.ViewHolder holder, int position) {
    holder.mBinding.cbSubCatName.setVisibility(View.GONE);
    holder.mBinding.rbSubCatName.setVisibility(View.VISIBLE);
    if (mFilterListData.get(position).isSelected()) {
      mLastSelected = position;
    }
    mFilterListData.get(position).setSelected(mLastSelected == position);
    holder.mBinding.rbSubCatName.setChecked(mFilterListData.get(position).isSelected());
    holder.mBinding.rbSubCatName.setText(mFilterListData.get(position).getName());
    holder.mBinding.rbSubCatName.setOnClickListener(view -> {
      mOnHistoryFilteredDataSelected.onFilteredDataSelectedListener(
          position,mFilterListData.get(position).getName(), mFilterListData.get(position).getTimeStamp());
      if (position != mLastSelected) {
        mFilterListData.get(position).setSelected(true);
        if (mLastSelected != -1) {
          mFilterListData.get(mLastSelected).setSelected(false);
        }
        mLastSelected = position;
        notifyDataSetChanged();
      }
    });
  }

  @Override
  public int getItemCount() {
    return mFilterListData != null ? mFilterListData.size() : ZERO;
  }

  public interface OnHistoryFilteredDataSelected {
    /**
     * This method is using to maintain the selected filter values
     */
    void onFilteredDataSelectedListener(int position,String name, String timeStamp);
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    ItemFilterSubCatBinding mBinding;

    public ViewHolder(@NonNull ItemFilterSubCatBinding binding) {
      super(binding.getRoot());
      this.mBinding = binding;
    }
  }
}
