package com.customer.fivecanale.allcategory;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.customer.domain.model.productcategory.SubCategoryData;
import com.customer.fivecanale.search.SubSubCategoryAdapter;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ItemAllCatSubCategoryBinding;
import java.util.ArrayList;

/*
 * Purpose – This class Holds SubCategories Item UI.
 * @author 3Embed
 * Created on Nov 15, 2019
 * Modified on
 */
public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.SubCatViewHolder> {

  private ArrayList<SubCategoryData> mSubCategoryData;
  private SubCatClickListener mSubCatClickListener;
  private String mCatName;
  private Context mContext;

  SubCategoryAdapter(ArrayList<SubCategoryData> subCategoryData,
      SubCatClickListener subCatClickListener, String catName) {
    this.mSubCategoryData = subCategoryData;
    this.mSubCatClickListener = subCatClickListener;
    mCatName = catName;
  }

  @NonNull
  @Override
  public SubCatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    ItemAllCatSubCategoryBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_all_cat_sub_category, parent, false);
    return new SubCatViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull SubCatViewHolder holder, int position) {
    holder.mBinding.tvSubCatName.setText(mSubCategoryData.get(position).getSubCategoryName());
    if (!EcomUtil.isEmptyArray(mSubCategoryData.get(position).getSubSubCategory())) {
      holder.mBinding.rvSubSubCatList.setAdapter(
          new SubSubCategoryAdapter(mSubCategoryData.get(position).getSubSubCategory(),
              mSubCatClickListener, mSubCategoryData.get(position).getSubCategoryName()));
    }
    holder.mBinding.tvSubCatName.setCompoundDrawablesWithIntrinsicBounds(
        null, null,
        EcomUtil.isEmptyArray(mSubCategoryData.get(position).getSubSubCategory()) ? null :
            mContext.getResources().getDrawable(R.drawable.ic_gray_down_arrow), null);
    holder.mBinding.rvSubSubCatList.setVisibility(
        mSubCategoryData.get(position).isClicked() ? VISIBLE : GONE);
    holder.mBinding.clCategoryMain.setOnClickListener(
        view -> handleClickEvents(position, holder));

  }

  /**
   * This message is using to handle item click events logic
   *
   * @param position position of item being clicked
   * @param holder   holder reference to handle UI
   */
  private void handleClickEvents(int position, SubCatViewHolder holder) {
    if (EcomUtil.isEmptyArray(mSubCategoryData.get(position).getSubSubCategory())) {
      mSubCatClickListener.onSubCatClickListener(mCatName,
          mSubCategoryData.get(position).getSubCategoryName());
    } else {
      mSubCategoryData.get(position).setClicked(!mSubCategoryData.get(position).isClicked());
      holder.mBinding.rvSubSubCatList.setVisibility(
          mSubCategoryData.get(position).isClicked() ? VISIBLE : GONE);
      holder.mBinding.tvSubCatName.setCompoundDrawablesWithIntrinsicBounds(
          null, null,
          mSubCategoryData.get(position).isClicked() ? mContext.getResources().getDrawable(
              R.drawable.ic_gray_up_arrow) :
              mContext.getResources().getDrawable(R.drawable.ic_gray_down_arrow), null);
    }
  }

  @Override
  public int getItemCount() {
    return EcomUtil.isEmptyArray(mSubCategoryData) ? ZERO : mSubCategoryData.size();
  }

  public interface SubCatClickListener extends SubSubCategoryAdapter.SubSubCatItemClickListener {

    void onSubCatClickListener(String catName, String subCatName);
  }

  static class SubCatViewHolder extends RecyclerView.ViewHolder {

    ItemAllCatSubCategoryBinding mBinding;

    SubCatViewHolder(ItemAllCatSubCategoryBinding binding) {
      super(binding.getRoot());
      mBinding = binding;
    }
  }
}