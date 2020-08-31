package com.customer.fivecanale.landing.homescreen.adapters;

import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.customer.domain.model.homesubcategory.HomeSubCategoryData;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ItemHomeScreenBinding;
import java.util.ArrayList;

/*
 * Purpose – This class holds SubCategory UI Items which will be displayed in HomePage.
 * @author 3Embed
 * Created on Nov 02, 2019
 * Modified on
 */
public class HomeSubCatAdapter extends RecyclerView.Adapter<HomeSubCatAdapter.SubCatViewHolder> {

  private ArrayList<HomeSubCategoryData> mSubCategoryData = new ArrayList<>();
  private Context mContext;
  private SubCatClickListener mSubCatClickListener;

  public HomeSubCatAdapter(ArrayList<HomeSubCategoryData> subCategoryData,
      SubCatClickListener subCatClickListener) {
    mSubCategoryData.clear();
    this.mSubCategoryData.addAll(subCategoryData);
    this.mSubCatClickListener = subCatClickListener;
  }

  @NonNull
  @Override
  public SubCatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    ItemHomeScreenBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_home_screen, parent, false);
    return new SubCatViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull SubCatViewHolder holder, int position) {

    holder.mBinding.headerHomeTv.setVisibility(View.VISIBLE);
    EcomUtil.printLog("HomeSubCatName" + mSubCategoryData.get(position).getSubCategoryName());
    holder.mBinding.tvHomeProductViewMore.setVisibility(View.VISIBLE);
    holder.mBinding.tvHomeProductViewMore.setOnClickListener(
        view -> mSubCatClickListener.onHomeSubCatClickListener(
            mSubCategoryData.get(position).getCatName(),
            mSubCategoryData.get(position).getSubCategoryName()));

    holder.mBinding.headerHomeTv.setText(mSubCategoryData.get(position).getSubCategoryName());
    holder.mBinding.rvHomeList.setLayoutManager(
        new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
    SubCatProductAdapter adapter = new SubCatProductAdapter(
        mSubCategoryData.get(position).getSubCategory());
    holder.mBinding.rvHomeList.setAdapter(adapter);

  }

  @Override
  public int getItemCount() {
    return EcomUtil.isEmptyArray(mSubCategoryData) ? ZERO : mSubCategoryData.size();
  }

  /**
   * Click Listener for Sub Category items
   */
  public interface SubCatClickListener {

    /**
     * Click Listener call back method for sub category items
     */
    void onHomeSubCatClickListener(String catName, String subCatName);
  }

  /**
   * View Holder for SubCategory item
   */
  static class SubCatViewHolder extends RecyclerView.ViewHolder {

    ItemHomeScreenBinding mBinding;

    SubCatViewHolder(ItemHomeScreenBinding binding) {
      super(binding.getRoot());
      mBinding = binding;

    }
  }
}