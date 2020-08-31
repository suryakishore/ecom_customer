package com.customer.fivecanale.promocode;

import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;

import android.view.View;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.GetAllPromoCodesUseCase;
import com.customer.domain.model.promocode.PromoCodeData;
import com.customer.fivecanale.util.BackButtonClickListener;
import com.customer.fivecanale.util.EcomUtil;
import io.reactivex.observers.DisposableSingleObserver;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * view model class for the notifications activity.
 */
public class PromoCodesViewModel extends ViewModel implements BackButtonClickListener {
  public ObservableField<Boolean> progressVisible = new ObservableField(FALSE);
  private UseCaseHandler mHandler;
  private GetAllPromoCodesUseCase mGetAllPromoCodesUseCase;
  private MutableLiveData<Boolean> mBack = new MutableLiveData<>();
  private MutableLiveData<ArrayList<PromoCodeData>> mListMutableLiveData =
      new MutableLiveData<>();

  /**
   * constructor for this class
   */
  @Inject
  public PromoCodesViewModel(UseCaseHandler handler,
      GetAllPromoCodesUseCase getAllPromoCodesUseCase) {
    this.mHandler = handler;
    this.mGetAllPromoCodesUseCase = getAllPromoCodesUseCase;
  }

  /**
   * call the promo codes  api
   */
  void callGetAllPromoCodesApi(String countryId, String cityId, String cartId,
      String storeId) {
    progressVisible.set(TRUE);
    DisposableSingleObserver<GetAllPromoCodesUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<GetAllPromoCodesUseCase.ResponseValues>() {
          @Override
          public void onSuccess(GetAllPromoCodesUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            if (responseValues.getData() != null) {
              mListMutableLiveData.postValue(responseValues.getData().getData());
            }
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("promoCodes Fail" + e.getMessage());
          }
        };
    mHandler.execute(mGetAllPromoCodesUseCase,
        new GetAllPromoCodesUseCase.RequestValues(countryId, cityId, cartId, storeId),
        disposableSingleObserver);
  }

  /**
   * notify when notification data comes..
   */
  MutableLiveData<ArrayList<PromoCodeData>> getAllPromoCodesData() {
    return mListMutableLiveData;
  }

  @Override
  public void backButtonClickListener(View view) {
    mBack.postValue(TRUE);
  }

  /**
   * subscribe to  click on views.
   */
  MutableLiveData<Boolean> onCrossClicked() {
    return mBack;
  }
}
