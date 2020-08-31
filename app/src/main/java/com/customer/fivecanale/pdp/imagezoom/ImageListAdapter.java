package com.customer.fivecanale.pdp.imagezoom;

import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.bumptech.glide.Glide;
import com.customer.domain.model.common.ImageData;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ItemImageViewBinding;
import java.util.ArrayList;

/*
 * Purpose – This class Holds UI all the images .
 * @author 3Embed
 * Created on Feb 27, 2020
 * Modified on
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageViewHolder> {

  private ArrayList<ImageData> mImageData;
  private Context mContext;
  private OnImageClick mImageClick;
  private int mSelectedPosition = ZERO;

  ImageListAdapter(ArrayList<ImageData> imageData, OnImageClick imageClick) {
    mImageData = imageData;
    mImageClick = imageClick;
  }

  /**
   * This method is using to set selected item position
   *
   * @param position selected item position
   */
  void setSelectedIndex(int position) {
    mSelectedPosition = position;
  }

  @NonNull
  @Override
  public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    ItemImageViewBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()), R.layout.item_image_view, parent,
        false);
    return new ImageViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

    String imgUrl = mImageData.get(position).getExtraLarge();
    if (!TextUtils.isEmpty(imgUrl)) {
      Glide.with(mContext)
          .load(imgUrl.replace(" ", "%20"))
          .into(holder.mBinding.ivImage);
    }

    if (mSelectedPosition == position) {
      holder.mBinding.clMain.setBackground(
          mContext.getResources().getDrawable(R.drawable.bay_color_border));
    } else {
      holder.mBinding.clMain.setBackground(null);
    }

    holder.mBinding.clMain.setOnClickListener(view -> {
      mSelectedPosition = position;
      notifyDataSetChanged();
    });
    holder.mBinding.ivImage.setOnClickListener(view -> mImageClick.onImageClickListener(position));

  }

  @Override
  public int getItemCount() {
    return EcomUtil.isEmptyArray(mImageData) ? ZERO : mImageData.size();
  }

  public interface OnImageClick {
    /**
     * call back method of on image click
     *
     * @param position clicked position
     */
    void onImageClickListener(int position);
  }

  static class ImageViewHolder extends RecyclerView.ViewHolder {

    private ItemImageViewBinding mBinding;

    ImageViewHolder(ItemImageViewBinding binding) {
      super(binding.getRoot());
      mBinding = binding;

    }

  }
}
