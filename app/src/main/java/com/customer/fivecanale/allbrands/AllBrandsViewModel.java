package com.customer.fivecanale.allbrands;

import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.TWENTY;

import android.util.Pair;
import android.view.View;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.AllBrandsUseCase;
import com.customer.domain.model.home.CategoryData;
import com.customer.fivecanale.util.BackButtonClickListener;
import com.customer.fivecanale.util.EcomUtil;
import io.reactivex.observers.DisposableSingleObserver;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * view model class for the notifications activity.
 */
public class AllBrandsViewModel extends ViewModel implements BackButtonClickListener {
  public ObservableField<Boolean> progressVisible = new ObservableField(FALSE);
  private UseCaseHandler mHandler;
  private AllBrandsUseCase mAllBrandsUseCase;
  private MutableLiveData<Boolean> mBack = new MutableLiveData<>();
  private MutableLiveData<Pair<Integer, ArrayList<CategoryData>>> mListMutableLiveData =
      new MutableLiveData<>();
  private ArrayList<CategoryData> mCategoryData = new ArrayList<>();

  /**
   * constructor for this class
   */
  @Inject
  public AllBrandsViewModel(UseCaseHandler handler,
      AllBrandsUseCase allBrandsUseCase) {
    this.mHandler = handler;
    this.mAllBrandsUseCase = allBrandsUseCase;
  }

  /**
   * call the reorder  api
   *
   * @param from from index
   * @Param to index.
   */
  void callGetAllBrandsApi(String from, String to) {
    progressVisible.set(TRUE);
    DisposableSingleObserver<AllBrandsUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<AllBrandsUseCase.ResponseValues>() {
          @Override
          public void onSuccess(AllBrandsUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            mListMutableLiveData.postValue(Pair.create(responseValues.getData().getPenCount(),
                responseValues.getData().getData()));
            if (responseValues.getData() != null) {
              if (Integer.parseInt(to) > TWENTY) {
                mCategoryData.addAll(responseValues.getData().getData());
              } else {
                mCategoryData.clear();
                mCategoryData.addAll(responseValues.getData().getData());
              }
              EcomUtil.printLog(
                  "exe" + "conut" + responseValues.getData().getPenCount() + "mCategoryData"
                      + mCategoryData.size());
              mListMutableLiveData.postValue(
                  Pair.create(responseValues.getData().getPenCount(), mCategoryData));
            }
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("AllBrands Fail" + e.getMessage());
          }
        };
    mHandler.execute(mAllBrandsUseCase,
        new AllBrandsUseCase.RequestValues(from, to),
        disposableSingleObserver);
  }

  /**
   * notify when notification data comes..
   */
  MutableLiveData<Pair<Integer, ArrayList<CategoryData>>> getAllBrandsData() {
    return mListMutableLiveData;
  }

  @Override
  public void backButtonClickListener(View view) {
    mBack.postValue(TRUE);
  }

  /**
   * subscribe to  click on views.
   */
  MutableLiveData<Boolean> onBackClicked() {
    return mBack;
  }
}
