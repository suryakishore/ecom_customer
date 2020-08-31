package com.customer.fivecanale.landing.homescreen.adapters;

import static com.customer.fivecanale.util.EcomConstants.BANNER_HORIZONTAL;
import static com.customer.fivecanale.util.EcomConstants.BRAND_HORIZONTAL;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_LIST_HORIZONTAL;
import static com.customer.fivecanale.util.EcomConstants.PRODUCT_TYPE;
import static com.customer.fivecanale.util.EcomConstants.RECENT_HORIZONTAL;
import static com.customer.fivecanale.util.EcomConstants.SMALL_HORIZONTAL_CIRCULAR;
import static com.customer.fivecanale.util.EcomConstants.SUB_PRODUCT_LIST_HORIZONTAL;
import static com.customer.fivecanale.util.EcomConstants.THREE_THOUSAND;
import static com.customer.fivecanale.util.EcomConstants.TWO;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.viewpager.widget.ViewPager;
import com.R;
import com.customer.domain.model.home.ListData;
import com.customer.fivecanale.uiutil.SpacesItemDecoration;
import com.customer.fivecanale.uiutil.ViewPagerScrollChangeListener;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ItemHomeBannerBinding;
import com.databinding.ItemHomeCategoriesWithDetailsBinding;
import com.databinding.ItemHomeScreenBinding;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/*
 * Purpose – This class Holds all the UI elements display in the HomePage.
 * @author 3Embed
 * Created on Nov 10, 2019
 * Modified on
 */
public class HomeAdapter extends RecyclerView.Adapter<ViewHolder> {
  private ArrayList<ListData> mListData;
  private Context mContext;
  private HomeAdapterClickListener mHomeAdapterClickListener;

  public HomeAdapter(ArrayList<ListData> listData, Fragment clickListener) {
    this.mListData = listData;
    this.mHomeAdapterClickListener = (HomeAdapterClickListener) clickListener;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    switch (viewType) {
      case SMALL_HORIZONTAL_CIRCULAR:
        ItemHomeScreenBinding binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()),
            R.layout.item_home_screen, parent, false);
        return new CategoryViewHolder(binding);
      case BANNER_HORIZONTAL:
        ItemHomeBannerBinding homeBannerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()),
            R.layout.item_home_banner, parent, false);
        return new BannerViewHolder(homeBannerBinding);
      case BRAND_HORIZONTAL:
        ItemHomeScreenBinding brandBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()),
            R.layout.item_home_screen, parent, false);
        return new BrandViewHolder(brandBinding);
      case PRODUCT_TYPE:
        ItemHomeCategoriesWithDetailsBinding categoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()),
            R.layout.item_home_categories_with_details, parent, false);
        return new ProdCatViewHolder(categoryBinding);
      case RECENT_HORIZONTAL:
      case SUB_PRODUCT_LIST_HORIZONTAL:
      case PRODUCT_LIST_HORIZONTAL:
        ItemHomeScreenBinding horizontalProductBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()),
            R.layout.item_home_screen, parent, false);
        return new HorizontalProductHolder(horizontalProductBinding);
      default:
        ItemHomeScreenBinding generalBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()),
            R.layout.item_home_screen, parent, false);
        return new GeneralViewHolder(generalBinding);
    }
  }

  @Override
  public int getItemViewType(int position) {
    return mListData != null ? mListData.get(position).getType() : ZERO;
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    if (holder instanceof CategoryViewHolder) {
      CategoryViewHolder categoryViewHolder = (CategoryViewHolder) holder;
      CategoriesAdapter categoriesAdapter =
          new CategoriesAdapter(mListData.get(position).getCategoryData(),
              mHomeAdapterClickListener);
      categoryViewHolder.mBinding.rvHomeList.setLayoutManager(
          new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
      categoryViewHolder.mBinding.rvHomeList.setAdapter(categoriesAdapter);
      categoriesAdapter.notifyDataSetChanged();
    } else if (holder instanceof BannerViewHolder) {
      BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
      bannerViewHolder.setIsRecyclable(false);
      ViewPagerAdapter viewPagerAdapter =
          new ViewPagerAdapter(mListData.get(position).getCategoryData(),
              mHomeAdapterClickListener);
      bannerViewHolder.mHomeBannerBinding.vpHomeBanner.setAdapter(viewPagerAdapter);
      bannerViewHolder.mHomeBannerBinding.vpHomeBanner.setClipToPadding(false);
      bannerViewHolder.mHomeBannerBinding.vpHomeBanner.setPadding(60, 10, 60, 0);
      bannerViewHolder.mHomeBannerBinding.vpHomeBanner.setPageMargin(20);
      ViewPagerScrollChangeListener.onScrollChangeListener(
          viewPagerAdapter, bannerViewHolder.mHomeBannerBinding.llHomeDots,
          bannerViewHolder.mHomeBannerBinding.vpHomeBanner, mContext
      );
      setPageListener(bannerViewHolder.mHomeBannerBinding.vpHomeBanner, mListData.get(
          position).getCategoryData().size());
    } else if (holder instanceof BrandViewHolder) {
      BrandViewHolder brandViewHolder = (BrandViewHolder) holder;
      brandViewHolder.mBrandBinding.headerHomeTv.setVisibility(View.VISIBLE);
      brandViewHolder.mBrandBinding.tvHomeProductViewMore.setVisibility(View.VISIBLE);
      brandViewHolder.mBrandBinding.headerHomeTv.setText(
          mContext.getResources().getString(R.string.topBrand));
      TopBrandsAdapter topBrandsAdapter =
          new TopBrandsAdapter(mListData.get(position).getCategoryData(),
              mHomeAdapterClickListener);
      brandViewHolder.mBrandBinding.rvHomeList.setLayoutManager(
          new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
      brandViewHolder.mBrandBinding.rvHomeList.setAdapter(topBrandsAdapter);
      topBrandsAdapter.notifyDataSetChanged();
      brandViewHolder.mBrandBinding.tvHomeProductViewMore.setOnClickListener(
          view -> mHomeAdapterClickListener.viewMoreAllBrandsClick());
    } else if (holder instanceof ProdCatViewHolder) {
      ProdCatViewHolder prodCatViewHolder = (ProdCatViewHolder) holder;
      SubCategoriesAdapter subCategoriesAdapter =
          new SubCategoriesAdapter(mListData.get(position).getCategoryData(),
              mListData.get(position).getCatName(), mHomeAdapterClickListener);
      prodCatViewHolder.mCategoryBinding.rvHomeItemSubCategories.setAdapter(subCategoriesAdapter);
      int spacingInPixels = mContext.getResources().getDimensionPixelSize(R.dimen.one_dp);
      prodCatViewHolder.mCategoryBinding.rvHomeItemSubCategories.addItemDecoration(
          new SpacesItemDecoration(spacingInPixels, mContext));
      prodCatViewHolder.mCategoryBinding.tvHomeItemCategoriesName.setText(
          mListData.get(position).getCatName());
      prodCatViewHolder.mCategoryBinding.tvHomeItemCategoriesViewMore.setOnClickListener(
          v -> catViewMoreClickHandler(position));
      if (!EcomUtil.isEmptyArray(mListData.get(position).getOffers())) {
        MediumSliderAdapter adapter = new MediumSliderAdapter(mListData.get(position).getOffers(),
            mHomeAdapterClickListener);
        prodCatViewHolder.mCategoryBinding.rvHomeSlidingPanner.setAdapter(adapter);
        if (mListData.get(position).getOffers().size() > TWO) {
          ((ProdCatViewHolder) holder).mCategoryBinding.rvHomeSlidingPanner.scrollToPosition(ONE);
        }
      }
    } else if (holder instanceof HorizontalProductHolder) {
      HorizontalProductHolder viewHolder = (HorizontalProductHolder) holder;
      viewHolder.mHorizontalProductBinding.headerHomeTv.setVisibility(View.VISIBLE);
      viewHolder.mHorizontalProductBinding.tvHomeProductViewMore.setOnClickListener(
          view -> {
            if (mListData.get(position).getType() == PRODUCT_LIST_HORIZONTAL) {
              mHomeAdapterClickListener.onTodayDealsClickListener(
                  mListData.get(position).getCatName());
            } else if (mListData.get(position).getType() == RECENT_HORIZONTAL) {
              mHomeAdapterClickListener.onRecentlyViewedViewMoreClick();
            } else {
              mHomeAdapterClickListener.onSubCategoryProductsViewMoreClickListener(
                  mListData.get(position).getCatName(),
                  mListData.get(position).getSubCatName());
            }
          });
      /*here we are reusing for Today's deals and second api sub category list, so we setting the
      name dynamically*/
      String name = mListData.get(position).getType() == SUB_PRODUCT_LIST_HORIZONTAL
          ? mListData.get(position).getSubCatName() : mListData.get(position).getCatName();
      viewHolder.mHorizontalProductBinding.headerHomeTv.setText(name);
      viewHolder.mHorizontalProductBinding.tvHomeProductViewMore.setVisibility(View.VISIBLE);
      viewHolder.mHorizontalProductBinding.rvHomeList.setLayoutManager(
          new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
      ProductsListAdapter listAdapter =
          new ProductsListAdapter(mListData.get(position).getCategoryData());
      viewHolder.mHorizontalProductBinding.rvHomeList.setAdapter(listAdapter);
    }
  }

  /**
   * this method is useful for auto scrolling for banners
   *
   * @param viewPager view pager reference
   * @param count     total number of items
   */
  private void setPageListener(ViewPager viewPager, int count) {
    Timer timer;
    TimerTask timerTask = new TimerTask() {
      @Override
      public void run() {
        viewPager.post(() -> {
          viewPager.setCurrentItem((count - ONE == viewPager.getCurrentItem()) ? ZERO
              : (viewPager.getCurrentItem() + ONE));
        });
      }
    };
    timer = new Timer();
    timer.schedule(timerTask, THREE_THOUSAND, THREE_THOUSAND);
  }

  /**
   * ViewMore button Click Handler for category Item
   *
   * @param position position which is clicked
   */
  private void catViewMoreClickHandler(int position) {
    mHomeAdapterClickListener.catViewMoreOnClickListener(mListData.get(position).getId(),
        mListData.get(position).getCatName());
  }

  @Override
  public int getItemCount() {
    return EcomUtil.isEmptyArray(mListData) ? ZERO : mListData.size();
  }

  /**
   * Click Listener for HomePage Items
   */
  public interface HomeAdapterClickListener extends
      SubCategoriesAdapter.SubCatClickListener, CategoriesAdapter.CategoryOnClickListener,
      TopBrandsAdapter.BrandItemClickListener, MediumSliderAdapter.CategoryOfferClickListener,
      ViewPagerAdapter.ViewPagerItemClickListener {
    /**
     * Category Item Click listener callback
     *
     * @param catId   categoryId for getting subCategories under this Category
     * @param catName categoryName for getting Products under this Category
     */
    void catViewMoreOnClickListener(String catId, String catName);

    /**
     * This method is using to handle today deals view more click listener
     */
    void onTodayDealsClickListener(String headerName);

    void onSubCategoryProductsViewMoreClickListener(String catName, String subCatName);

    void onRecentlyViewedViewMoreClick();

    void viewMoreAllBrandsClick();
  }

  /**
   * Holds Main category list UI in Home page
   */
  class CategoryViewHolder extends ViewHolder {
    ItemHomeScreenBinding mBinding;

    CategoryViewHolder(@NonNull ItemHomeScreenBinding binding) {
      super(binding.getRoot());
      this.mBinding = binding;
    }
  }

  /**
   * Holds Ui for Brand Images List
   */
  class BrandViewHolder extends ViewHolder {
    ItemHomeScreenBinding mBrandBinding;

    BrandViewHolder(@NonNull ItemHomeScreenBinding brandBinding) {
      super(brandBinding.getRoot());
      this.mBrandBinding = brandBinding;
    }
  }

  /**
   * Holds the UI of Plain Banner Images in Home Page
   */
  class BannerViewHolder extends ViewHolder {
    ItemHomeBannerBinding mHomeBannerBinding;

    BannerViewHolder(@NonNull ItemHomeBannerBinding homeBannerBinding) {
      super(homeBannerBinding.getRoot());
      this.mHomeBannerBinding = homeBannerBinding;
    }
  }

  /**
   * Holds UI of Products Category List
   */
  class ProdCatViewHolder extends ViewHolder {
    ItemHomeCategoriesWithDetailsBinding mCategoryBinding;

    ProdCatViewHolder(@NonNull ItemHomeCategoriesWithDetailsBinding categoryBinding) {
      super(categoryBinding.getRoot());
      this.mCategoryBinding = categoryBinding;
    }
  }

  /**
   * Holds Products list UI which will be displayed in Home
   */
  class HorizontalProductHolder extends ViewHolder {
    ItemHomeScreenBinding mHorizontalProductBinding;

    HorizontalProductHolder(@NonNull ItemHomeScreenBinding horizontalProductBinding) {
      super(horizontalProductBinding.getRoot());
      this.mHorizontalProductBinding = horizontalProductBinding;
    }
  }

  class GeneralViewHolder extends ViewHolder {
    ItemHomeScreenBinding mGeneralBinding;

    GeneralViewHolder(@NonNull ItemHomeScreenBinding generalBinding) {
      super(generalBinding.getRoot());
      this.mGeneralBinding = generalBinding;
    }
  }
}