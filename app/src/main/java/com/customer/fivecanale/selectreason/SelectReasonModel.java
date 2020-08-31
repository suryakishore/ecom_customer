package com.customer.fivecanale.selectreason;

import static com.customer.fivecanale.selectreason.SelectReasonUiAction.CROSS;
import static com.customer.fivecanale.selectreason.SelectReasonUiAction.SAVE;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;

import android.view.View;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.GetCancelReasonsUseCase;
import com.customer.domain.model.cancelreasons.CancelReasonsItemData;
import com.customer.fivecanale.util.BackButtonClickListener;
import com.customer.fivecanale.util.EcomUtil;
import io.reactivex.observers.DisposableSingleObserver;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * view model class for the select reason activity.
 */
public class SelectReasonModel extends ViewModel implements BackButtonClickListener {
  public ObservableField<Boolean> progressVisible = new ObservableField<>(FALSE);
  private MutableLiveData<SelectReasonUiAction> mClick = new MutableLiveData<>();
  private GetCancelReasonsUseCase mGetCancelReasonsUseCase;
  private UseCaseHandler mHandler;
  private MutableLiveData<ArrayList<CancelReasonsItemData>> mListMutableLiveData =
      new MutableLiveData<>();

  @Inject
  public SelectReasonModel(GetCancelReasonsUseCase cancelReasonsUseCase,
      UseCaseHandler useCaseHandler) {
    this.mGetCancelReasonsUseCase = cancelReasonsUseCase;
    this.mHandler = useCaseHandler;
  }

  /**
   * call the get reasons order  api
   */
  void callGetReasonsApi() {
    progressVisible.set(TRUE);
    DisposableSingleObserver<GetCancelReasonsUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<GetCancelReasonsUseCase.ResponseValues>() {
          @Override
          public void onSuccess(GetCancelReasonsUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("CancelOrder Succ");
            mListMutableLiveData.postValue(responseValues.getData().getReasonData());
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("CancelOrder Fail" + e.getMessage());
          }
        };
    mHandler.execute(mGetCancelReasonsUseCase,
        new GetCancelReasonsUseCase.RequestValues(),
        disposableSingleObserver);
  }

  /**
   * listen when we click on save
   */
  public void save() {
    mClick.postValue(SAVE);
  }

  @Override
  public void backButtonClickListener(View view) {
    mClick.postValue(CROSS);
  }

  /**
   * subscribe to  click on views.
   */
  public MutableLiveData<SelectReasonUiAction> onClick() {
    return mClick;
  }

  /**
   * subscribe to  click on views.
   */
  MutableLiveData<ArrayList<CancelReasonsItemData>> onGetReasons() {
    return mListMutableLiveData;
  }
}
