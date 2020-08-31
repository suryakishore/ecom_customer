package com.customer.fivecanale.landing.homescreen.paging;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.customer.domain.model.homesubcategory.HomeSubCategoryData;

public class PageListAdapter extends
    PagedListAdapter<HomeSubCategoryData, PageListAdapter.PagingViewHolder> {

  private static DiffUtil.ItemCallback<HomeSubCategoryData> DIFF_CALLBACK =
      new DiffUtil.ItemCallback<HomeSubCategoryData>() {

        @Override
        public boolean areItemsTheSame(HomeSubCategoryData oldItem, HomeSubCategoryData newItem) {

          return false;
        }

        @Override
        public boolean areContentsTheSame(HomeSubCategoryData oldItem,
            HomeSubCategoryData newItem) {
          // Don't use the "==" operator here. Either implement and use .equals(),
          // or write custom mData comparison logic here.
          return false;
        }
      };

  protected PageListAdapter() {
    super(DIFF_CALLBACK);
  }

  @NonNull
  @Override
  public PagingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull PagingViewHolder holder, int position) {

  }

  class PagingViewHolder extends RecyclerView.ViewHolder {

    public PagingViewHolder(@NonNull View itemView) {
      super(itemView);
    }
  }
}
