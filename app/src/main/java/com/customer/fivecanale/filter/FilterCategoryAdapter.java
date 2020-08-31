package com.customer.fivecanale.filter;

import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Context;
import android.os.Build.VERSION_CODES;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.customer.domain.model.filter.FilterList;
import com.customer.domain.model.filter.FilterListData;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ItemFilterCategoryBinding;
import java.util.ArrayList;

/*
 * Purpose – This class Holds Filter category item
 * with other layers.
 * @author 3Embed
 * Created on Dec 05, 2019
 * Modified on
 */
public class FilterCategoryAdapter extends
    RecyclerView.Adapter<FilterCategoryAdapter.FilterViewHolder> {

  private Context mContext;
  private ArrayList<FilterList> mFilterLists;
  private int mLastSelected = ZERO;
  private FilterCatClickListener mFilterCatClickListener;

  FilterCategoryAdapter(ArrayList<FilterList> filterLists) {
    this.mFilterLists = filterLists;
  }

  @NonNull
  @Override
  public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    mFilterCatClickListener = (FilterCatClickListener) mContext;
    ItemFilterCategoryBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_filter_category, parent, false);
    return new FilterViewHolder(binding);
  }

  @RequiresApi(api = VERSION_CODES.M)
  @Override
  public void onBindViewHolder(@NonNull FilterViewHolder holder, int position) {

    holder.binding.selectedSideBar.setVisibility(
        position == mLastSelected ? View.VISIBLE : View.GONE);

    holder.binding.clMainLayout.setBackgroundColor(
        position == mLastSelected ? mContext.getColor(R.color.white)
            : mContext.getColor(R.color.desert_storm));
    holder.binding.tvCatName.setTextAppearance(mContext,
        position == mLastSelected ? R.style.Text_14sp_BlackApprox_poppins_bold
            : R.style.Text_14sp_colorBlackApprox_PoppinsRegular);

    holder.binding.tvCatName.setText(mFilterLists.get(position).getName());

    holder.binding.clMainLayout.setOnClickListener(v -> {
      if (mLastSelected != position) {
        mLastSelected = position;
        notifyDataSetChanged();
        mFilterCatClickListener.onCategoryClickListener(mFilterLists.get(position).getData(),
            mFilterLists.get(position).getFilterType());
      }
    });
  }

  @Override
  public int getItemCount() {
    return EcomUtil.isEmptyArray(mFilterLists) ? ZERO : mFilterLists.size();
  }

  interface FilterCatClickListener {
    /**
     * this method is using to handle filter category click listener
     *
     * @param filterListData filter list
     * @param type           name of main category
     */
    void onCategoryClickListener(ArrayList<FilterListData> filterListData, int type);
  }

  static class FilterViewHolder extends RecyclerView.ViewHolder {

    ItemFilterCategoryBinding binding;

    FilterViewHolder(ItemFilterCategoryBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }
  }
}
