package com.customer.fivecanale.productlist;

import static com.customer.fivecanale.productlist.ProductListUiAction.BACK;
import static com.customer.fivecanale.productlist.ProductListUiAction.CART_UPDATE;
import static com.customer.fivecanale.productlist.ProductListUiAction.FILTER;
import static com.customer.fivecanale.productlist.ProductListUiAction.HIDE_SHIMMER;
import static com.customer.fivecanale.productlist.ProductListUiAction.SORT;
import static com.customer.fivecanale.util.EcomConstants.ADD_CART;
import static com.customer.fivecanale.util.EcomConstants.BRAND_FLOW;
import static com.customer.fivecanale.util.EcomConstants.DELETE_CART;
import static com.customer.fivecanale.util.EcomConstants.EMPTY;
import static com.customer.fivecanale.util.EcomConstants.OFFER_FLOW;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.SEARCH_FLOW;
import static com.customer.fivecanale.util.EcomConstants.SEARCH_TYPE;
import static com.customer.fivecanale.util.EcomConstants.SUB_CATEGORY_FLOW;
import static com.customer.fivecanale.util.EcomConstants.SUB_SUB_CATEGORY_FLOW;
import static com.customer.fivecanale.util.EcomConstants.UPDATE_CART;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.text.TextUtils;
import androidx.core.util.Pair;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.FilterUseCase;
import com.customer.domain.interactor.FilteredProductsUseCase;
import com.customer.domain.interactor.FilteredProductsUseCase.ResponseValues;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.domain.model.common.ProductsData;
import com.customer.fivecanale.sort.SortViewModel;
import com.customer.fivecanale.util.EcomUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import javax.inject.Inject;

/*
 * Purpose – This class holds all the logic of ProductListingActivity  and acting as an Mediator
 * between Activity and other layers
 * products which listed in this page.
 * @author 3Embed
 * Created on Nov 25, 2019
 * Modified on
 */
public class ProductListViewModel extends ViewModel implements SortViewModel {
  public ObservableField<Boolean> progressVisible = new ObservableField<>(false);
  public ObservableField<Boolean> isDataFound = new ObservableField<>(false);
  public ObservableField<Boolean> filter = new ObservableField<>(true);
  @Inject
  public UserInfoHandler mUserInfoHandler;
  HashMap<Integer, Set<String>> filterValues;
  private UseCaseHandler mCaseHandler;
  private FilteredProductsUseCase mFilteredProductsUseCase;
  private FilterUseCase mFilterUseCase;
  private MutableLiveData<Pair<Integer, ArrayList<ProductsData>>> mPairMutableLiveData =
      new MutableLiveData<>();
  private DisposableSingleObserver<ResponseValues> mDisposableSingleObserver;
  private DisposableSingleObserver<FilterUseCase.ResponseValues> mFilterObservable;
  private MutableLiveData<ProductListUiAction> mUiActionMutableLiveData = new MutableLiveData<>();
  private String mCatName, mSubCatName, mSubSubCatName, mBrandName, mOfferId, mSearchQuery;
  private int mFlowLevel;

  @Inject
  public ProductListViewModel(UseCaseHandler handler,
      FilteredProductsUseCase filteredProductsUseCase, FilterUseCase filterUseCase) {
    this.mFilteredProductsUseCase = filteredProductsUseCase;
    this.mFilterUseCase = filterUseCase;
    this.mCaseHandler = handler;
  }

  /**
   * Making API call to get ProductList
   */
  private void getProductList() {
    mDisposableSingleObserver =
        new DisposableSingleObserver<FilteredProductsUseCase.ResponseValues>() {
          @Override
          public void onSuccess(FilteredProductsUseCase.ResponseValues responseValues) {
            EcomUtil.printLog("FilterAPI Fail Success");
            if (responseValues.getData().getProducts() != null
                && responseValues.getData().getProducts().size() > ZERO) {
              isDataFound.set(false);
              filter.set(false);
              mPairMutableLiveData.postValue(
                  Pair.create(responseValues.getData().getPenCount(),
                      responseValues.getData().getProducts()));
            }
          }

          @Override
          public void onError(Throwable e) {
            isDataFound.set(true);
            filter.set(true);
            mUiActionMutableLiveData.postValue(HIDE_SHIMMER);
          }
        };
  }

  /**
   * this method is using to store basic mData
   *
   * @param catName       category Name
   * @param subCatName    Sub category Name
   * @param subSubCatName Sub SubCategory Name
   */
  private void initializeData(String catName, String subCatName, String subSubCatName) {
    this.mCatName = catName;
    this.mSubCatName = subCatName;
    this.mSubSubCatName = subSubCatName;
  }

  /**
   * This method is using to get all the products based subSubCategoryName
   *
   * @param catName       category Name to get SubCategories
   * @param subCatName    subcategory Name to get SubSubCategories
   * @param subSubCatName SubSubCategory Name to get all the products under this SubCategory
   */
  void getSubSubCatFilterProduct(String catName, String subCatName, String subSubCatName,
      int sortType, String page) {
    mFlowLevel = SUB_SUB_CATEGORY_FLOW;
    initializeData(catName, subCatName, subSubCatName);
    getProductList();
    mCaseHandler.execute(mFilteredProductsUseCase,
        new FilteredProductsUseCase.RequestValues(this.mCatName, this.mSubCatName,
            this.mSubSubCatName,
            sortType, page),
        mDisposableSingleObserver);
  }

  /**
   * This method is using to get all the products based subCategoryName
   *
   * @param catName    category Name to get SubCategories
   * @param subCatName subcategory Name to get all the products under this sub category
   */
  public void getSubCatFilterProduct(String catName, String subCatName, String subSubCatName,
      int sortType, String page) {
    mFlowLevel = SUB_CATEGORY_FLOW;
    EcomUtil.printLog("exe" + "mFlowLevel" + mFlowLevel);
    initializeData(catName, subCatName, EMPTY);
    getProductList();
    if (filterValues != null && filterValues.size() > ZERO) {
      mCaseHandler.execute(mFilteredProductsUseCase,
          new FilteredProductsUseCase.RequestValues(catName, subCatName, subSubCatName,
              filterValues, sortType,
              page),
          mDisposableSingleObserver);
    } else {
      mCaseHandler.execute(mFilteredProductsUseCase,
          new FilteredProductsUseCase.RequestValues(catName, subCatName, subSubCatName, sortType,
              page),
          mDisposableSingleObserver);
    }
  }

  /**
   * This method is using to get all the products based search word
   *
   * @param searchQuery word entered by user
   */
  public void getSearchProduct(String searchQuery, int sortType) {
    getProductList();
    mCaseHandler.execute(mFilteredProductsUseCase,
        new FilteredProductsUseCase.RequestValues(searchQuery, SEARCH_TYPE, String.valueOf(ONE)),
        mDisposableSingleObserver);
  }

  @Override
  public void wishLIstSort(int type) {
  }

  /*sets the brand name*/
  void setBrandName(String brandName) {
    this.mFlowLevel = BRAND_FLOW;
    this.mBrandName = brandName;
  }

  /*sets the search query*/
  void setSearchQuery(String searchQuery) {
    this.mFlowLevel = SEARCH_FLOW;
    this.mSearchQuery = searchQuery;
  }

  /*sets the offer id */
  void setOfferId(String offerId) {
    this.mFlowLevel = OFFER_FLOW;
    this.mOfferId = offerId;
  }

  /**
   * THis method is using to get Products under this Brans
   *
   * @param name name to get product
   */
  void getBrandProduct(String name, int type, String inText) {
    getProductList();
    mCaseHandler.execute(mFilteredProductsUseCase,
        new FilteredProductsUseCase.RequestValues(name, type, inText),
        mDisposableSingleObserver);
  }

  /**
   * Back button click listener
   */
  public void onBackIconClicked() {
    mUiActionMutableLiveData.setValue(BACK);
  }

  /**
   * this method is to get all the products based on Filter value
   *
   * @param filterValues filter values to get product list
   */
  void getFilteredProducts(HashMap<Integer, Set<String>> filterValues, int sortType) {
    getProductList();
    if (TextUtils.isEmpty(mCatName)) {
      mCaseHandler.execute(mFilteredProductsUseCase,
          new FilteredProductsUseCase.RequestValues(filterValues, sortType, String.valueOf(ONE)),
          mDisposableSingleObserver);
    } else if (!TextUtils.isEmpty(mCatName) && !TextUtils.isEmpty(mSubCatName)) {
      mCaseHandler.execute(mFilteredProductsUseCase,
          new FilteredProductsUseCase.RequestValues(mCatName, mSubCatName, filterValues,
              sortType, String.valueOf(ONE)),
          mDisposableSingleObserver);
    }
  }

  /**
   * Mutable live mData for subscribing in UI
   *
   * @return product list and penCount observable LiveData
   */
  MutableLiveData<Pair<Integer, ArrayList<ProductsData>>> getProductListLiveData() {
    return mPairMutableLiveData;
  }

  public void onSortIconClicked() {
    mUiActionMutableLiveData.postValue(SORT);
  }

  /**
   * On Filter button click listener
   */
  public void onFilterIconClicked() {
    mUiActionMutableLiveData.postValue(FILTER);
  }

  /*
   * notify activity when a view is  clicked
   */
  public MutableLiveData<ProductListUiAction> onClick() {
    return mUiActionMutableLiveData;
  }

  /**
   * Disposing mDisposableSingleObserver
   */
  public void setDisposableSingleObserver() {
    if (mDisposableSingleObserver != null) {
      mDisposableSingleObserver.dispose();
    }
  }

  /*creates the filter observable*/
  void createFilterObservable(int sortType, HashMap<Integer, Set<String>> filterValues) {
    applyFilter();
    switch (mFlowLevel) {
      case SUB_CATEGORY_FLOW:
        mCaseHandler.execute(mFilterUseCase,
            new FilterUseCase.RequestValues(filterValues, mCatName, mSubCatName, sortType),
            mFilterObservable);
        break;
      case SUB_SUB_CATEGORY_FLOW:
        mCaseHandler.execute(mFilterUseCase,
            new FilterUseCase.RequestValues(filterValues, mCatName, mSubCatName, mSubSubCatName,
                sortType), mFilterObservable);
        break;
      case SEARCH_FLOW:
        mCaseHandler.execute(mFilterUseCase,
            new FilterUseCase.RequestValues(sortType, mSearchQuery, filterValues),
            mFilterObservable);
        break;
      case BRAND_FLOW:
        mCaseHandler.execute(mFilterUseCase,
            new FilterUseCase.RequestValues(mBrandName, filterValues, sortType),
            mFilterObservable);
        break;
      case OFFER_FLOW:
        mCaseHandler.execute(mFilterUseCase,
            new FilterUseCase.RequestValues(filterValues, sortType, mOfferId),
            mFilterObservable);
        break;
    }
  }

  /*applies the filter*/
  private void applyFilter() {
    boolean isFilterApplied = true;
    mFilterObservable =
        new DisposableSingleObserver<FilterUseCase.ResponseValues>() {
          @Override
          public void onSuccess(FilterUseCase.ResponseValues responseValues) {
            EcomUtil.printLog("FilterAPI Fail Success");
            if (responseValues.getData().getProducts() != null
                && responseValues.getData().getProducts().size() > ZERO) {
              isDataFound.set(false);
              filter.set(false);
              mPairMutableLiveData.postValue(
                  Pair.create(responseValues.getData().getPenCount(),
                      responseValues.getData().getProducts()));
            }
          }

          @Override
          public void onError(Throwable e) {
            isDataFound.set(true);
            filter.set(false);
            mUiActionMutableLiveData.postValue(HIDE_SHIMMER);
          }
        };
  }

  /** This method is using for subscribing to User cart data */
  void onCartUpdate() {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    compositeDisposable.add(
        mUserInfoHandler
            .getCartDataObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(cartData -> {
              switch (cartData.getAction()) {
                case ADD_CART:
                  mUiActionMutableLiveData.setValue(CART_UPDATE);
                  break;
                case UPDATE_CART:
                  mUiActionMutableLiveData.setValue(CART_UPDATE);
                  break;
                case DELETE_CART:
                  mUiActionMutableLiveData.setValue(CART_UPDATE);
                  break;
                default:
                  break;
              }
            }));
  }
}