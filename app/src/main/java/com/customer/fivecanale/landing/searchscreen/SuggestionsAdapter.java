package com.customer.fivecanale.landing.searchscreen;

import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.customer.domain.model.suggestion.SuggestionItemData;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ItemSuggestiuonBinding;
import java.util.ArrayList;

/*inflates the search suggestions list*/
public class SuggestionsAdapter extends
    RecyclerView.Adapter<SuggestionsAdapter.SuggestionsViewHolder> {
  private ArrayList<SuggestionItemData> mSuggestionData;
  private SuggestionItemClickListener mClickListener;

  SuggestionsAdapter(
      ArrayList<SuggestionItemData> suggestionData, SuggestionItemClickListener clickListener) {
    mSuggestionData = suggestionData;
    mClickListener = clickListener;
  }

  @NonNull
  @Override
  public SuggestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemSuggestiuonBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_suggestiuon, parent, false);
    return new SuggestionsViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull SuggestionsViewHolder holder, int position) {
    holder.binding.tvSuggestionProductName.setText(mSuggestionData.get(position).getProductName());
    holder.binding.tvSuggestionCatName.setText(
        String.format("%s %s",
            holder.binding.clSuggestionMain.getContext().getString(R.string.searchIn),
            mSuggestionData.get(position).getInSection()));
    holder.binding.ivSuggestionArrow.setOnClickListener(view -> mClickListener.onArrowClickListener(
        mSuggestionData.get(position).getProductName()));
    holder.binding.clSuggestionMain.setOnClickListener(
        view -> mClickListener.onSuggestionClickListener(
            mSuggestionData.get(position).getProductName(),
            mSuggestionData.get(position).getInSection(), mSuggestionData.get(position).isProduct(),
            mSuggestionData.get(position).getChildProductId(),
            mSuggestionData.get(position).getProductId()));
  }

  @Override
  public int getItemCount() {
    return EcomUtil.isEmptyArray(mSuggestionData) ? ZERO : mSuggestionData.size();
  }

  public interface SuggestionItemClickListener {
    /**
     * This method is using to handle up left arrow click events
     *
     * @param productName productName as searchQuery
     */
    void onArrowClickListener(String productName);

    /**
     * This method is using to handle list item click listener
     *
     * @param productName productName as searchQuery
     */
    void onSuggestionClickListener(String productName, String inText, boolean isProduct,
        String productId, String parentProductId);
  }

  static class SuggestionsViewHolder extends RecyclerView.ViewHolder {
    ItemSuggestiuonBinding binding;

    SuggestionsViewHolder(ItemSuggestiuonBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }
  }
}