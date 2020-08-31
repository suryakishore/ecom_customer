package com.customer.fivecanale.selectreason;

import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.MINUS_ONE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.customer.domain.model.cancelreasons.CancelReasonsItemData;
import com.databinding.ItemSelectReasonBinding;
import java.util.ArrayList;

/**
 * adapter class for the select reason activity to show the all reasons to cancel the product.
 */
public class SelectReasonAdapter extends
    RecyclerView.Adapter<SelectReasonAdapter.SelectReasonViewHolder> {
  private static int unCheckedPos = -1;
  private static int checkedPos = -1;
  private ArrayList<CancelReasonsItemData> mReasonsItemDataArrayList;
  private Context mContext;

  public SelectReasonAdapter(
      ArrayList<CancelReasonsItemData> reasonsItemDataArrayList) {
    mReasonsItemDataArrayList = reasonsItemDataArrayList;
  }

  @NonNull
  @Override
  public SelectReasonAdapter.SelectReasonViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    mContext = parent.getContext();
    ItemSelectReasonBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_select_reason, parent, false);
    return new SelectReasonViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull SelectReasonAdapter.SelectReasonViewHolder holder,
      int position) {
    CancelReasonsItemData cancelReasonsItemData = mReasonsItemDataArrayList.get(position);
    holder.binding.radioButton.setText(cancelReasonsItemData.getReason());
    holder.binding.radioButton.setChecked(cancelReasonsItemData.isChecked() ? TRUE : FALSE);
    holder.binding.radioButton.setOnClickListener(view -> {
      holder.binding.radioButton.setChecked(cancelReasonsItemData.isChecked() ? FALSE : TRUE);
      if (cancelReasonsItemData.isChecked()) {
        cancelReasonsItemData.setChecked(FALSE);
      } else {
        unCheckedPos = checkedPos;
        if (unCheckedPos != MINUS_ONE) {
          CancelReasonsItemData unCheckedCancelReasonsData = mReasonsItemDataArrayList.get(unCheckedPos);
          unCheckedCancelReasonsData.setChecked(FALSE);
          notifyItemChanged(unCheckedPos);
        }
        checkedPos = position;
        cancelReasonsItemData.setChecked(TRUE);
      }
    });
  }

  @Override
  public int getItemCount() {
    return mReasonsItemDataArrayList != null ? mReasonsItemDataArrayList.size() : ZERO;
  }

  public class SelectReasonViewHolder extends RecyclerView.ViewHolder {
    ItemSelectReasonBinding binding;

    public SelectReasonViewHolder(@NonNull ItemSelectReasonBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }
  }
}
