package com.customer.fivecanale.addresslist;

import static com.customer.fivecanale.addresslist.SavedAddressUiAction.BACK;
import static com.customer.fivecanale.util.EcomConstants.NONE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.text.TextUtils;
import android.view.View;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.DeleteAddressUseCase;
import com.customer.domain.interactor.GetAddressUseCase;
import com.customer.domain.interactor.MakeAddressDefaultUseCase;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.domain.model.getaddress.AddressListItemData;
import com.customer.fivecanale.util.BackButtonClickListener;
import com.customer.fivecanale.util.EcomUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import javax.inject.Inject;

/*
 * Purpose – This class Holds All the Business logic of SaveAddressListActivity.
 * @author 3Embed
 * Created on Dec 10, 2019
 * Modified on
 */
public class SavedAddressListViewModel extends ViewModel implements BackButtonClickListener {
  public ObservableField progressVisible = new ObservableField<>(false);
  public ObservableField<Boolean> mIsEmpty = new ObservableField<>(false);
  public ObservableField<String> mError = new ObservableField<>();
  @Inject
  UserInfoHandler userInfoHandler;
  private GetAddressUseCase mGetAddressUseCase;
  private UseCaseHandler mHandler;
  private DeleteAddressUseCase mDeleteAddressUseCase;
  private MutableLiveData<ArrayList<AddressListItemData>> mArrayListMutableLiveData =
      new MutableLiveData<>();
  private MutableLiveData<Integer> mAddressListLiveData =
      new MutableLiveData<>();
  private MutableLiveData<SavedAddressUiAction> mUiActionLiveData = new MutableLiveData<>();
  private ArrayList<AddressListItemData> mAddressListItemData = new ArrayList<>();
  private MakeAddressDefaultUseCase mMakeAddressDefaultUseCase;
  private boolean mIsEmptyList;
  int lastSelectedPosition = NONE;

  @Inject
  public SavedAddressListViewModel(GetAddressUseCase getAddressUseCase,
      MakeAddressDefaultUseCase makeAddressDefaultUseCase,
      DeleteAddressUseCase deleteAddressUseCase,
      UseCaseHandler handler) {
    this.mDeleteAddressUseCase = deleteAddressUseCase;
    this.mGetAddressUseCase = getAddressUseCase;
    this.mHandler = handler;
    this.mMakeAddressDefaultUseCase = makeAddressDefaultUseCase;
  }

  /** This method is using for subscribing to User generate token data */
  void onGenerateTokenUpdate() {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    compositeDisposable.add(
        userInfoHandler
            .getGenerateTokenObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(request -> {
              if (request) {
                //   callGenerateToken(request);
              }
            }));
  }

  /**
   * this method is using to get Address list
   */
  void getAddressList() {
    progressVisible.set(true);
    DisposableSingleObserver<GetAddressUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<GetAddressUseCase.ResponseValues>() {
          @Override
          public void onSuccess(GetAddressUseCase.ResponseValues responseValues) {
            progressVisible.set(false);
            if (responseValues.getData().getData() != null
                && responseValues.getData().getData().size() > ZERO) {
              mIsEmpty.set(false);
              mIsEmptyList = false;
              EcomUtil.printLog("AddressList Success" + responseValues.getData().getData().size());
              mAddressListItemData.clear();
              mAddressListItemData.addAll(responseValues.getData().getData());
              mArrayListMutableLiveData.postValue(mAddressListItemData);
            } else {
              mIsEmpty.set(true);
              mIsEmptyList = true;
            }
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(false);
            mIsEmpty.set(true);
            mIsEmptyList = true;
            mError.set(e.getMessage());
            EcomUtil.printLog("AddressList Fail" + e.getMessage());
          }
        };
    mHandler.execute(mGetAddressUseCase, new GetAddressUseCase.RequestValues(),
        disposableSingleObserver);
  }

  /**
   * This method is using to get whether addressList is empty or not
   *
   * @return isEmptyValue
   */
  boolean getIsEmptyValue() {
    return mIsEmptyList;
  }

  /**
   * This method is using to delete item from address list
   *
   * @param addressId id of address need to delete
   */
  void deleteAddress(String addressId) {
    progressVisible.set(true);
    DisposableSingleObserver<DeleteAddressUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<DeleteAddressUseCase.ResponseValues>() {
          @Override
          public void onSuccess(DeleteAddressUseCase.ResponseValues responseValues) {
            EcomUtil.printLog("AddressList Success");
            progressVisible.set(false);
            mAddressListItemData.remove(new AddressListItemData(addressId));
            if (EcomUtil.isEmptyArray(mAddressListItemData)) {
              mIsEmpty.set(true);
            }
            mArrayListMutableLiveData.postValue(mAddressListItemData);
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(false);
            mError.set(e.getMessage());
            EcomUtil.printLog("AddressList Fail" + e.getMessage());
          }
        };
    mHandler.execute(mDeleteAddressUseCase, new DeleteAddressUseCase.RequestValues(addressId),
        disposableSingleObserver);
  }

  /**
   * This method is using to make the address as default
   *
   * @param addressId address information make address as default
   */
  void makeAddressAsDefault(String addressId, String prevDefAddressId, int lastSelectedAddress) {
    progressVisible.set(true);
    DisposableSingleObserver<MakeAddressDefaultUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<MakeAddressDefaultUseCase.ResponseValues>() {
          @Override
          public void onSuccess(MakeAddressDefaultUseCase.ResponseValues responseValues) {
            EcomUtil.printLog("MakeAddressDefault Success");
            progressVisible.set(false);
            lastSelectedPosition = lastSelectedAddress;
            pushUpdatedData(addressId, prevDefAddressId);
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(false);
            EcomUtil.printLog("MakeAddressDefault Fail" + e.getMessage());
            mError.set(e.getMessage());
            lastSelectedPosition = lastSelectedAddress;
            pushUpdatedData(prevDefAddressId, prevDefAddressId);
          }
        };
    mHandler.execute(mMakeAddressDefaultUseCase,
        new MakeAddressDefaultUseCase.RequestValues(addressId),
        disposableSingleObserver);
  }

  /**
   * THis method is using to update the address list
   *
   * @param addressId id to update addressList
   */
  private void pushUpdatedData(String addressId, String prevDefAddressId) {
    if (!TextUtils.isEmpty(prevDefAddressId)) {
      AddressListItemData prevData = new AddressListItemData(prevDefAddressId);
      int prevIndex = mAddressListItemData.indexOf(prevData);
      mAddressListItemData.get(prevIndex).setDefault(false);
    }
    if (!TextUtils.isEmpty(addressId)) {
      AddressListItemData data = new AddressListItemData(addressId);
      int index = mAddressListItemData.indexOf(data);
      mAddressListItemData.get(index).setDefault(true);
      mAddressListLiveData.setValue(index);
    }
    mArrayListMutableLiveData.setValue(mAddressListItemData);
  }

  /**
   * This method is using to get AddressList Mutable mData
   *
   * @return AddressList Mutable mData
   */
  MutableLiveData<ArrayList<AddressListItemData>> getAddressListLiveData() {
    return mArrayListMutableLiveData;
  }

  /**
   * This method is using to get AddressList Mutable mData
   *
   * @return AddressList Mutable mData
   */
  MutableLiveData<Integer> getAddressListData() {
    return mAddressListLiveData;
  }

  /**
   * This method is using to Listen Ui action
   *
   * @return Ui action Mutable mData
   */
  MutableLiveData<SavedAddressUiAction> getUiActionListener() {
    return mUiActionLiveData;
  }

  @Override
  public void backButtonClickListener(View view) {
    mUiActionLiveData.setValue(BACK);
  }
}
