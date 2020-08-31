package com.customer.fivecanale.landing.searchscreen;

import static android.view.View.GONE;
import static com.customer.fivecanale.util.EcomConstants.BUFFERING_TIME;
import static com.customer.fivecanale.util.EcomConstants.CATEGORY_LIST;
import static com.customer.fivecanale.util.EcomConstants.CATEGORY_NAME;
import static com.customer.fivecanale.util.EcomConstants.EMPTY;
import static com.customer.fivecanale.util.EcomConstants.IN_TEXT;
import static com.customer.fivecanale.util.EcomConstants.SEARCH_QUERY;
import static com.customer.fivecanale.util.EcomConstants.ZERO;
import static com.customer.remote.http.RemoteConstants.PARENT_PRODUCT_ID;
import static com.customer.remote.http.RemoteConstants.PRODUCT_ID;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.customer.domain.model.productcategory.SubCategoryData;
import com.customer.fivecanale.category.CategoryViewMoreActivity;
import com.customer.fivecanale.dagger.ActivityScoped;
import com.customer.fivecanale.landing.HomeActivity;
import com.customer.fivecanale.landing.searchscreen.CategorySearchAdapter.CategoryClickListener;
import com.customer.fivecanale.pdp.ProductDetailsActivity;
import com.customer.fivecanale.productlist.ProductListingActivity;
import com.customer.fivecanale.search.CategoryActivity;
import com.customer.fivecanale.util.EcomUtil;
import com.customer.fivecanale.util.RxTextView;
import com.customer.fivecanale.util.manager.KeyboardDetector;
import com.customer.fivecanale.util.manager.KeyboardStatus;
import com.databinding.FragmentEcomSearchBinding;
import dagger.android.support.DaggerFragment;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import org.jetbrains.annotations.NotNull;

/*
 * Purpose – This class Holds Ui for product search.
 * @author 3Embed
 * Created on Nov 12, 2019
 * Modified on
 */
@ActivityScoped
public class SearchScreenFragment extends DaggerFragment implements CategoryClickListener,
    SuggestionsAdapter.SuggestionItemClickListener,
    RecentSearchSuggestionAdapter.RecentSuggestionClickListener {
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  private CategorySearchAdapter mCategorySearchAdapter;
  private FragmentEcomSearchBinding mBinding;
  private boolean mKeyBoardStat = false;

  @Inject
  public SearchScreenFragment() {
  }

  @Override
  public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ecom_search, container, false);
    View view = mBinding.getRoot();
    initialize();
    checkKeyboardStatus();
    showKeyboard();
    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
  }

  @Override
  public void setMenuVisibility(boolean menuVisible) {
    super.setMenuVisibility(menuVisible);
    if (menuVisible && mBinding != null) {
      showKeyBoard();
    }
  }

  /**
   * This method is using to show keyboard when the fragment is alive
   */
  private void showKeyBoard() {
    InputMethodManager inputMethodManager = (InputMethodManager) Objects.requireNonNull(
        getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,
        InputMethodManager.HIDE_IMPLICIT_ONLY);
    mBinding.incSearchHeader.etHomeSearch.requestFocus();
  }

  /**
   * This method is using to initialize all the basic resources
   */
  private void initialize() {
    mBinding.incSearchHeader.ivHomeAppLogo.setVisibility(GONE);
    mBinding.incSearchHeader.ivSearchNotification.setVisibility(GONE);
    if (getActivity() instanceof SearchActivity || getActivity() instanceof ProductListingActivity
        || getActivity() instanceof CategoryViewMoreActivity) {
      mBinding.incSearchHeader.ivHomeAppLogo.setVisibility(View.VISIBLE);
      mBinding.incSearchHeader.ivHomeAppLogo.setImageDrawable(
          getResources().getDrawable(R.drawable.toolbar_back));
      mBinding.tvCatHeader.setVisibility(GONE);
      mBinding.viewDivider.setVisibility(View.GONE);
      mBinding.rvCategoryList.setVisibility(GONE);
      mBinding.incSearchHeader.etHomeSearch.requestFocus();
      mBinding.incSearchHeader.ivHomeAppLogo.setOnClickListener(view -> {
        if (getActivity() instanceof SearchActivity
            || getActivity() instanceof ProductListingActivity
            || getActivity() instanceof CategoryViewMoreActivity) {
          getActivity().finish();
          EcomUtil.hideSoftKeyboard(mBinding.incSearchHeader.etHomeSearch);
        }
      });
    } else {
      mBinding.tvCatHeader.setText(getResources().getString(R.string.shopByCategory));
    }
    mBinding.incSearchHeader.etHomeSearch.setOnEditorActionListener((v, actionId, event) -> {
      if (actionId == EditorInfo.IME_ACTION_SEARCH) {
        reDirectToSearch(
            Objects.requireNonNull(mBinding.incSearchHeader.etHomeSearch.getText()).toString(),
            EMPTY);
        return true;
      }
      return false;
    });
    SearchViewModel searchViewModel = ViewModelProviders.of(this, mViewModelFactory).get(
        SearchViewModel.class);
    mBinding.setViewModel(searchViewModel);
    searchViewModel.getRecentSuggestions();
    searchViewModel.getRecentSugLiveData().observe(Objects.requireNonNull(getActivity()),
        recentSearchSuggestionData -> {
          RecentSearchSuggestionAdapter adapter = new RecentSearchSuggestionAdapter(
              recentSearchSuggestionData, this);
          mBinding.rvRecentSearchSuggestion.setAdapter(adapter);
        });
    searchViewModel.getLiveData().observe(getViewLifecycleOwner(), homePageData -> {
      mBinding.rvCategoryList.setVisibility(mKeyBoardStat ? GONE : View.VISIBLE);
      mBinding.tvCatHeader.setText(getResources().getString(R.string.shopByCategory));
      mCategorySearchAdapter = new CategorySearchAdapter(homePageData.getData(), this);
      mBinding.rvCategoryList.setAdapter(mCategorySearchAdapter);
      mCategorySearchAdapter.notifyDataSetChanged();
    });
    searchViewModel.getEmptyScreen().observe(Objects.requireNonNull(getActivity()), aBoolean -> {
      mBinding.incEmptyScreen.clEmptyScreenMain.setVisibility(View.VISIBLE);
      mBinding.tvCatHeader.setVisibility(GONE);
      mBinding.viewDivider.setVisibility(GONE);
    });
    searchViewModel.getSuggestionLiveData().observe(getViewLifecycleOwner(),
        suggestionData -> {
          hideEmptyScreen();
          SuggestionsAdapter adapter = new SuggestionsAdapter(suggestionData, this);
          mBinding.rvSearchSuggestion.setAdapter(adapter);
        });
    if (getActivity() instanceof HomeActivity) {
      searchViewModel.callProductCategoryApi();
    }
    RxTextView.textChanges(mBinding.incSearchHeader.etHomeSearch)
        .debounce(BUFFERING_TIME, TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new io.reactivex.Observer<CharSequence>() {
          @Override
          public void onSubscribe(Disposable d) {
          }

          @Override
          public void onNext(CharSequence charSequence) {
            mBinding.incSearchHeader.tvSearchCancel.setVisibility(
                TextUtils.isEmpty(charSequence.toString()) ? GONE : View.VISIBLE);
            mBinding.rvRecentSearchSuggestion.setVisibility(mKeyBoardStat
                && TextUtils.isEmpty(charSequence.toString()) ? View.VISIBLE : GONE);
            try {
              mBinding.tvRecentSearchHeading.setVisibility(mBinding.rvRecentSearchSuggestion
                  .getAdapter().getItemCount() > ZERO ? View.VISIBLE : GONE);
            } catch (NullPointerException e) {
              mBinding.tvRecentSearchHeading.setVisibility(GONE);
            }
            mBinding.rvSearchSuggestion.setVisibility(
                TextUtils.isEmpty(charSequence.toString()) ? GONE : View.VISIBLE);
            if (!TextUtils.isEmpty(charSequence.toString())) {
              searchViewModel.getSuggestions(charSequence.toString());
            } else {
              hideEmptyScreen();
            }
          }

          @Override
          public void onError(Throwable e) {
          }

          @Override
          public void onComplete() {
          }
        });
    mBinding.incSearchHeader.tvSearchCancel.setOnClickListener(
        view -> {
          mBinding.incEmptyScreen.clEmptyScreenMain.setVisibility(View.VISIBLE);
          mBinding.rvRecentSearchSuggestion.setVisibility(GONE);
          mBinding.tvRecentSearchHeading.setVisibility(GONE);
          mBinding.tvCatHeader.setVisibility(View.VISIBLE);
          mBinding.viewDivider.setVisibility(View.VISIBLE);
          mBinding.incSearchHeader.etHomeSearch.setText("");
        }
    );
  }

  /**
   * <h2>checkKeyboardStatus</h2>
   * to check whether keyboard is open or close
   */
  private void checkKeyboardStatus() {
    new KeyboardDetector(getActivity()).observe().subscribe(new Observer<KeyboardStatus>() {
      @Override
      public void onSubscribe(Disposable d) {
      }

      @Override
      public void onNext(KeyboardStatus status) {
        mKeyBoardStat = status == KeyboardStatus.OPENED;
        if (TextUtils.isEmpty(mBinding.incSearchHeader.etHomeSearch.getText())) {
          if (getActivity() != null && (getActivity() instanceof SearchActivity
              || getActivity() instanceof ProductListingActivity
              || getActivity() instanceof CategoryViewMoreActivity)) {
            mBinding.rvRecentSearchSuggestion.setVisibility(
                status == KeyboardStatus.OPENED ? View.VISIBLE : View.VISIBLE);
            try {
              mBinding.tvRecentSearchHeading.setVisibility(mBinding.rvRecentSearchSuggestion
                  .getAdapter().getItemCount() > ZERO ? View.VISIBLE : View.GONE);
            } catch (NullPointerException e) {
              mBinding.tvRecentSearchHeading.setVisibility(
                  mBinding.tvCatHeader.getVisibility() == View.VISIBLE
                      ? View.GONE : View.VISIBLE);
            }
            mBinding.rvCategoryList.setVisibility(
                status == KeyboardStatus.OPENED ? GONE : GONE);
            mBinding.tvCatHeader.setVisibility(
                status == KeyboardStatus.OPENED ? GONE : GONE);
            mBinding.viewDivider.setVisibility(
                status == KeyboardStatus.OPENED ? GONE : GONE);
          } else {
            mBinding.rvRecentSearchSuggestion.setVisibility(
                status == KeyboardStatus.OPENED ? View.VISIBLE : GONE);
            if (status == KeyboardStatus.OPENED) {
              try {
                mBinding.tvRecentSearchHeading.setVisibility(mBinding.rvRecentSearchSuggestion
                    .getAdapter().getItemCount() > ZERO ? View.VISIBLE : GONE);
              } catch (NullPointerException e) {
                mBinding.tvRecentSearchHeading.setVisibility(GONE);
              }
            } else {
              mBinding.tvRecentSearchHeading.setVisibility(GONE);
            }
            mBinding.rvCategoryList.setVisibility(
                status == KeyboardStatus.OPENED ? GONE : View.VISIBLE);
            mBinding.tvCatHeader.setVisibility(
                status == KeyboardStatus.OPENED ? GONE : View.VISIBLE);
            if (mBinding.tvCatHeader.getVisibility() == View.VISIBLE) {
              mBinding.tvRecentSearchHeading.setVisibility(GONE);
            }
            mBinding.viewDivider.setVisibility(
                status == KeyboardStatus.OPENED ? GONE : View.VISIBLE);
          }
        }
      }

      @Override
      public void onError(Throwable e) {
      }

      @Override
      public void onComplete() {
      }
    });
  }

  /*hides the empty screen*/
  private void hideEmptyScreen() {
    mBinding.incEmptyScreen.clEmptyScreenMain.setVisibility(GONE);
    mBinding.tvCatHeader.setVisibility(mKeyBoardStat ? GONE : View.VISIBLE);
    mBinding.tvRecentSearchHeading.setVisibility(mKeyBoardStat
        ? mBinding.tvRecentSearchHeading.getVisibility() : GONE);
    mBinding.viewDivider.setVisibility(mKeyBoardStat ? GONE : View.VISIBLE);
  }

  @Override
  public void itemClickListener(ArrayList<SubCategoryData> subCategoryData, String categoryName) {
    Intent intent = new Intent(getActivity(), CategoryActivity.class);
    intent.putParcelableArrayListExtra(CATEGORY_LIST, subCategoryData);
    intent.putExtra(CATEGORY_NAME, categoryName);
    startActivity(intent);
  }

  /**
   * This method is using to redirect to ProductListing
   *
   * @param searchQuery searchQuery for getting products
   */
  private void reDirectToSearch(String searchQuery, String inText) {
    EcomUtil.hideSoftKeyboard(mBinding.incSearchHeader.etHomeSearch);
    Intent intent = new Intent(getActivity(), ProductListingActivity.class);
    intent.putExtra(SEARCH_QUERY, searchQuery);
    intent.putExtra(IN_TEXT, inText);
    startActivity(intent);
  }

  @Override
  public void onArrowClickListener(String productName) {
    mBinding.incSearchHeader.etHomeSearch.setText(productName);
    mBinding.incSearchHeader.etHomeSearch.setSelection(productName.length());
  }

  @Override
  public void onSuggestionClickListener(String productName, String inText, boolean isProduct,
      String productID, String parentProduct) {
    EcomUtil.printLog(
        "exe" + "productID" + productID + "parentProduct" + parentProduct + "isProduct"
            + isProduct);
    EcomUtil.hideSoftKeyboard(mBinding.incSearchHeader.clSearchHeader);
    if (isProduct) {
      Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
      intent.putExtra(PARENT_PRODUCT_ID, parentProduct);
      intent.putExtra(PRODUCT_ID, productID);
      startActivity(intent);
    } else {
      reDirectToSearch(productName, inText);
    }
  }

  @Override
  public void onRecentSuggestionClickListener(String searchQuery, String inText) {
    EcomUtil.hideSoftKeyboard(mBinding.incSearchHeader.etHomeSearch);
    reDirectToSearch(searchQuery, inText);
  }

  /**
   * used to open the keyboard
   */
  public void showKeyboard() {
    EcomUtil.showSoftKeyboard(mBinding.incSearchHeader.etHomeSearch);
  }

  @Override
  public void onDetach() {
    super.onDetach();
    EcomUtil.hideSoftKeyboard(mBinding.incSearchHeader.etHomeSearch);
  }
}