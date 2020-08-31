package com.customer.fivecanale.changelang;

import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.ChangeLanguageUseCase;
import com.customer.domain.model.changelan.ChangeLanDetails;
import com.customer.fivecanale.util.EcomUtil;
import io.reactivex.observers.DisposableSingleObserver;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * view model class for the change language.
 */
public class ChangeLanViewModel extends ViewModel {
  public ObservableField<Boolean> progressVisible = new ObservableField(FALSE);
  private ChangeLanguageUseCase mChangeLanguageUseCase;
  private UseCaseHandler mHandler;
  private MutableLiveData<ArrayList<ChangeLanDetails>> mLiveData = new MutableLiveData<>();

  @Inject
  public ChangeLanViewModel(ChangeLanguageUseCase changeLanguageUseCase,
      UseCaseHandler handler) {
    this.mChangeLanguageUseCase = changeLanguageUseCase;
    this.mHandler = handler;
  }

  /**
   * call the get languages  api
   */
  void callGetLanguagesApi() {
    progressVisible.set(TRUE);
    DisposableSingleObserver<ChangeLanguageUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<ChangeLanguageUseCase.ResponseValues>() {
          @Override
          public void onSuccess(ChangeLanguageUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            ArrayList<ChangeLanDetails> changeLanDetailsArrayList = new ArrayList<>();
            if (responseValues.getData() != null) {
              changeLanDetailsArrayList.clear();
              changeLanDetailsArrayList.addAll(responseValues.getData().getData());
              if (changeLanDetailsArrayList.size() > ZERO) {
                mLiveData.postValue(changeLanDetailsArrayList);
              }
            }
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("AllBrands Fail" + e.getMessage());
          }
        };
    mHandler.execute(mChangeLanguageUseCase,
        new ChangeLanguageUseCase.RequestValues(),
        disposableSingleObserver);
  }

  /**
   * notify activity when change Language data comes
   */
  MutableLiveData<ArrayList<ChangeLanDetails>> getLanguageData() {
    return mLiveData;
  }
}
