package com.customer.fivecanale.uiutil.barcodescanning;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.GetProductOnQrCodeUseCase;
import com.customer.domain.model.BarCodeResponseData;
import com.customer.fivecanale.uiutil.barcodescanning.bottomsheet.ProductNotFoundDelegate;
import com.customer.fivecanale.util.EcomUtil;
import io.reactivex.observers.DisposableSingleObserver;
import javax.inject.Inject;

/*handles the logic for bar code */
public class BarCodeViewModel extends ViewModel implements ProductNotFoundDelegate {
  private UseCaseHandler mHandler;
  private GetProductOnQrCodeUseCase mGetProductOnQrCodeUseCase;
  private MutableLiveData<BarCodeResponseData> mBarCodeMutableLiveData = new MutableLiveData<>();
  private MutableLiveData<BarCodeScannerUiAction> mUiActionMutableLiveData =
      new MutableLiveData<>();

  @Inject
  public BarCodeViewModel(UseCaseHandler handler,
      GetProductOnQrCodeUseCase getProductOnQrCodeUseCase) {
    this.mHandler = handler;
    this.mGetProductOnQrCodeUseCase = getProductOnQrCodeUseCase;
  }

  /**
   * This method is using to get product that match to the given qr code
   */
  void getProductDetails(String barCodeId) {
    DisposableSingleObserver<GetProductOnQrCodeUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<GetProductOnQrCodeUseCase.ResponseValues>() {

          @Override
          public void onSuccess(GetProductOnQrCodeUseCase.ResponseValues responseValues) {
            EcomUtil.printLog("TodayOfferData Success");
            BarCodeResponseData data = responseValues.getData();
            data.setIsSuccess(true);
            mBarCodeMutableLiveData.setValue(data);
          }

          @Override
          public void onError(Throwable e) {
            EcomUtil.printLog("TodayOfferData Fail" + e.getMessage());
            mBarCodeMutableLiveData.setValue(new BarCodeResponseData(false));
          }
        };
    mHandler.execute(mGetProductOnQrCodeUseCase,
        new GetProductOnQrCodeUseCase.RequestValues(barCodeId),
        disposableSingleObserver);
  }

  /**
   * This method is using to get BarCode api response observer
   *
   * @return response observer
   */
  MutableLiveData<BarCodeResponseData> getBarCodeMutableLiveData() {
    return mBarCodeMutableLiveData;
  }

  /**
   * This method is using to get Ui action Listener
   *
   * @return ui action emitter
   */
  MutableLiveData<BarCodeScannerUiAction> getUiActionMutableLiveData() {
    return mUiActionMutableLiveData;
  }

  @Override
  public void onCloseBtnClickListener() {
    mUiActionMutableLiveData.postValue(BarCodeScannerUiAction.CLOSE);
  }

  @Override
  public void onScanAgainBtnClickListener() {
    mUiActionMutableLiveData.postValue(BarCodeScannerUiAction.CONTINUE);
  }
}