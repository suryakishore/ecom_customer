package com.customer.fivecanale.landing.homescreen;

import static com.customer.fivecanale.util.EcomConstants.FIVE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.HomePageUseCase;
import com.customer.domain.interactor.HomeSubCatUseCase;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.domain.model.home.ListData;
import com.customer.fivecanale.util.EcomUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import javax.inject.Inject;

/*
 * Purpose – This class Holds all the Business Logic of HomePage.
 * @author 3Embed
 * Created on Nov 10, 2019
 * Modified on
 */
public class HomeViewModel extends ViewModel {
  public ObservableField<Boolean> mProgressVisible = new ObservableField<>(false);
  @Inject
  public UserInfoHandler mUserInfoHandler;
  private HomePageUseCase mHomePageUseCase;
  private UseCaseHandler mHandler;
  private MutableLiveData<ArrayList<ListData>> mListMutableLiveData = new MutableLiveData<>();
  private MutableLiveData<Integer> mNotificationContData = new MutableLiveData<>();
  private HomeSubCatUseCase mHomeSubCatUseCase;
  private ArrayList<ListData> mListData = new ArrayList<>();
  private int mSubCatPenCount;
  private int mSize = FIVE;
  private int mSkip = ZERO;
  private boolean mCanLoad = true;

  @Inject
  public HomeViewModel(HomePageUseCase homePageUseCase, HomeSubCatUseCase homeSubCatUseCase,
      UseCaseHandler handler) {
    this.mHomePageUseCase = homePageUseCase;
    this.mHomeSubCatUseCase = homeSubCatUseCase;
    this.mHandler = handler;
  }

  /*refreshes the home page with latest mData */
  void refreshHomePage() {
    mListData.clear();
    mListMutableLiveData.setValue(mListData);
    mSize = FIVE;
    mSkip = ZERO;
    callHomeApi();
  }

  /** This method is using for subscribing to User notification data */
  void onNotificationCountUpdate() {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    compositeDisposable.add(
        mUserInfoHandler
            .getNotificationCountObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(count -> {
              mNotificationContData.postValue(count);
            }));
  }

  /**
   * This method is using to get Home page mData from API
   */
  void callHomeApi() {
    DisposableSingleObserver<HomePageUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<HomePageUseCase.ResponseValues>() {
          @Override
          public void onSuccess(HomePageUseCase.ResponseValues responseValues) {
            if (!EcomUtil.isEmptyArray(responseValues.getData().getList())) {
              mListData.addAll(responseValues.getData().getList());
              mSubCatPenCount = responseValues.getData().getTotalCatCount();
              mListMutableLiveData.postValue(mListData);
            }
          }

          @Override
          public void onError(Throwable e) {
            EcomUtil.printLog("HomeFragment Fail" + e.getMessage());
          }
        };
    mHandler.execute(mHomePageUseCase, new HomePageUseCase.RequestValues(),
        disposableSingleObserver);
  }

  /**
   * This method is get LIveData object that can emit Home page Data
   *
   * @return home page mData observable LiveData object
   */
  MutableLiveData<ArrayList<ListData>> getListMutableLiveData() {
    return mListMutableLiveData;
  }

  /**
   * This Method is using to get SubCategory products
   */
  void callHomeSubCatApi() {
    mProgressVisible.set(true);
    DisposableSingleObserver<HomeSubCatUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<HomeSubCatUseCase.ResponseValues>() {
          @Override
          public void onSuccess(HomeSubCatUseCase.ResponseValues responseValues) {
            EcomUtil.printLog("HomeFragment Success");
            mProgressVisible.set(false);
            mSkip = mSize;
            mSize += mSize;
            mCanLoad = true;
            if (!EcomUtil.isEmptyArray(responseValues.getData().getList())) {
              mListData.addAll(responseValues.getData().getList());
              mListMutableLiveData.postValue(mListData);
            }
          }

          @Override
          public void onError(Throwable e) {
            mProgressVisible.set(false);
            EcomUtil.printLog("HomeFragment Fail" + e.getMessage());
          }
        };
    if (mCanLoad && mSize <= mSubCatPenCount) {
      mCanLoad = false;
      mHandler.execute(mHomeSubCatUseCase, new HomeSubCatUseCase.RequestValues(mSize, mSkip),
          disposableSingleObserver);
    } else if (mSize >= mSubCatPenCount) {
      mProgressVisible.set(false);
    }
  }

  /**
   * notify notification count data
   */
  public MutableLiveData<Integer> getCount() {
    return mNotificationContData;
  }
}