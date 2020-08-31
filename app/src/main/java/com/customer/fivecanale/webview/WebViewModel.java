package com.customer.fivecanale.webview;

import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.GetWebPageDataUseCase;
import com.customer.fivecanale.util.EcomUtil;
import io.reactivex.observers.DisposableSingleObserver;
import javax.inject.Inject;

/**
 * view model class for the notifications activity.
 */
public class WebViewModel extends ViewModel {
  public ObservableField<Boolean> progressVisible = new ObservableField(FALSE);
  public ObservableField<String> termsOrPrivacyData = new ObservableField();
  boolean isTerms;
  private UseCaseHandler mHandler;
  private GetWebPageDataUseCase mWebPageDataUseCase;

  /**
   * constructor for this class
   */
  @Inject
  public WebViewModel(UseCaseHandler handler,
      GetWebPageDataUseCase getWebPageDataUseCase) {
    this.mHandler = handler;
    this.mWebPageDataUseCase = getWebPageDataUseCase;
  }

  /**
   * call the reorder  api
   */
  void callWebPageData() {
    progressVisible.set(TRUE);
    DisposableSingleObserver<GetWebPageDataUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<GetWebPageDataUseCase.ResponseValues>() {
          @Override
          public void onSuccess(GetWebPageDataUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            if (responseValues.getData() != null) {
              termsOrPrivacyData.set(isTerms ? responseValues.getData().getTermsObj()
                  : responseValues.getData().getPrivacyObj());
            }
          }
          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("webView Fail" + e.getMessage());
          }
        };
    mHandler.execute(mWebPageDataUseCase,
        new GetWebPageDataUseCase.RequestValues(),
        disposableSingleObserver);
  }
}
