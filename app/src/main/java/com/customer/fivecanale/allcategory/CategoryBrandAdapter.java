package com.customer.fivecanale.allcategory;

import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.customer.domain.model.common.CategoryDataModel;
import com.customer.fivecanale.util.EcomUtil;
import java.util.ArrayList;

/*
 * Purpose – This class Holds Categories Brand Item UI.
 * @author 3Embed
 * Created on Nov 15, 2019
 * Modified on
 */
public class CategoryBrandAdapter extends
    RecyclerView.Adapter<CategoryBrandAdapter.CatBrandViewHolder> {

  private ArrayList<CategoryDataModel> mCategoryDataModels;

  @NonNull
  @Override
  public CatBrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_brand,
        parent, false);
    return new CatBrandViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull CatBrandViewHolder holder, int position) {

  }

  @Override
  public int getItemCount() {
    return EcomUtil.isEmptyArray(mCategoryDataModels) ? ZERO : mCategoryDataModels.size();
  }

  static class CatBrandViewHolder extends RecyclerView.ViewHolder {

    CatBrandViewHolder(View view) {
      super(view);
    }
  }
}
