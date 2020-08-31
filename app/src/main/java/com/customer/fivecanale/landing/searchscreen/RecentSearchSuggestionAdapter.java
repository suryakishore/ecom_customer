package com.customer.fivecanale.landing.searchscreen;

import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.customer.domain.model.recentsearch.RecentSearchSuggestionData;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ItemRecentSearchSuggestionBinding;
import java.util.ArrayList;

/*
 * Purpose – This class is using to show recently searched suggestion list
 * @author 3Embed
 * Created on Jan 25, 2019
 * Modified on
 */
public class RecentSearchSuggestionAdapter extends
    RecyclerView.Adapter<RecentSearchSuggestionAdapter.RecentSearchSuggestionViewHolder> {

  private ArrayList<RecentSearchSuggestionData> mSearchSuggestionData;
  private RecentSuggestionClickListener mClickListener;

  RecentSearchSuggestionAdapter(
      ArrayList<RecentSearchSuggestionData> searchSuggestionData,
      RecentSuggestionClickListener clickListener) {
    this.mSearchSuggestionData = searchSuggestionData;
    this.mClickListener = clickListener;
  }

  @NonNull
  @Override
  public RecentSearchSuggestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    ItemRecentSearchSuggestionBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_recent_search_suggestion, parent, false);
    return new RecentSearchSuggestionViewHolder(binding);

  }

  @Override
  public void onBindViewHolder(@NonNull RecentSearchSuggestionViewHolder holder, int position) {

    holder.mBinding.tvRecentSuggestion.setText(mSearchSuggestionData.get(position).getSearchText());
    holder.mBinding.tvSuggestionCatName.setText(mSearchSuggestionData.get(position).getSearchIn());
    holder.mBinding.tvSuggestionCatName.setVisibility(
        TextUtils.isEmpty(mSearchSuggestionData.get(position).getSearchIn()) ? View.GONE
            : View.VISIBLE);
    holder.mBinding.ivSuggestionArrow.setOnClickListener(
        view -> mClickListener.onArrowClickListener(
            mSearchSuggestionData.get(position).getSearchText()));
    holder.mBinding.clRecentMain.setOnClickListener(
        view -> mClickListener.onRecentSuggestionClickListener(
            mSearchSuggestionData.get(position).getSearchText(),
            mSearchSuggestionData.get(position).getSearchIn()));
  }

  @Override
  public int getItemCount() {
    return EcomUtil.isEmptyArray(mSearchSuggestionData) ? ZERO : mSearchSuggestionData.size();
  }

  static class RecentSearchSuggestionViewHolder extends RecyclerView.ViewHolder {

    ItemRecentSearchSuggestionBinding mBinding;

    RecentSearchSuggestionViewHolder(ItemRecentSearchSuggestionBinding binding) {
      super(binding.getRoot());
      this.mBinding = binding;

    }
  }

  interface RecentSuggestionClickListener {
    /**
     * This method is using to handle recent search suggestion click listener
     *
     * @param searchQuery search query text
     * @param inText      category text
     */
    void onRecentSuggestionClickListener(String searchQuery, String inText);

    void onArrowClickListener(String productName);
  }
}
