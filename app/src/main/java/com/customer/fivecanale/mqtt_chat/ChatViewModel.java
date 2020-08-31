package com.customer.fivecanale.mqtt_chat;

import static com.appscrip.stripe.Constants.DATA;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.TWO;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.BuildConfig;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.GetCustomerChatUseCase;
import com.customer.domain.interactor.PostChatUseCase;
import com.customer.domain.interactor.UploadImageUseCase;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.domain.model.chat.GetChatData;
import com.customer.fivecanale.util.EcomUtil;
import com.google.gson.Gson;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import javax.inject.Inject;
import org.json.JSONObject;

/**
 * view model class for the notifications activity.
 */
public class ChatViewModel extends ViewModel {
  public ObservableField<Boolean> progressVisible = new ObservableField(FALSE);
  @Inject
  public UserInfoHandler mUserInfoHandler;
  private UseCaseHandler mHandler;
  private MutableLiveData<Boolean> mBack = new MutableLiveData<>();
  private GetCustomerChatUseCase mGetCustomerChatUseCase;
  private PostChatUseCase mPostChatUseCase;
  private UploadImageUseCase mUploadImageUseCase;
  private Observer<JSONObject> observer;
  private MutableLiveData<ArrayList<GetChatData>> mLiveData = new MutableLiveData<>();
  private MutableLiveData<String> mImageData = new MutableLiveData<>();
  private ArrayList<GetChatData> mChatDataArry = new ArrayList<>();

  /**
   * constructor for this class
   */
  @Inject
  public ChatViewModel(UseCaseHandler handler,
      GetCustomerChatUseCase getCustomerChatUseCase, PostChatUseCase postChatUseCase,
      UploadImageUseCase uploadImageUseCase) {
    this.mHandler = handler;
    this.mGetCustomerChatUseCase = getCustomerChatUseCase;
    this.mPostChatUseCase = postChatUseCase;
    this.mUploadImageUseCase = uploadImageUseCase;
    initializeRxJava();
  }

  private void initializeRxJava() {
    observer = new Observer<JSONObject>() {
      @Override
      public void onSubscribe(Disposable d) {
      }

      @Override
      public void onNext(JSONObject jsonObject) {
        try {
          Gson gson = new Gson();
          EcomUtil.printLog("exe" + "jsonObject" + jsonObject.toString());
          if (jsonObject.has(DATA)) {
            GetChatData chatData = gson.fromJson(jsonObject.getString(DATA), GetChatData.class);
            chatData.setCustProType(TWO);
            if (mChatDataArry.size() > ZERO && !mChatDataArry.contains(chatData)) {
              mChatDataArry.add(chatData);
              mLiveData.postValue(mChatDataArry);
            } else if (mChatDataArry.size() == ZERO) {
              mChatDataArry.add(chatData);
              mLiveData.postValue(mChatDataArry);
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
          EcomUtil.printLog("Caught : " + e.getMessage());
        }
      }

      @Override
      public void onError(Throwable e) {
      }

      @Override
      public void onComplete() {
      }
    };
    ChatDataObervable.getInstance().subscribe(observer);
  }

  /**
   * call the get chat api
   */
  void callGetChatApi(String bookingId, String pageNo, String storeId) {
    progressVisible.set(TRUE);
    DisposableSingleObserver<GetCustomerChatUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<GetCustomerChatUseCase.ResponseValues>() {
          @Override
          public void onSuccess(GetCustomerChatUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            if (responseValues.getData() != null) {
              EcomUtil.printLog("chat Data" + responseValues.getData().getData());
              mChatDataArry.clear();
              for (int i = 0; i < responseValues.getData().getData().size(); i++) {
                if (responseValues.getData().getData().get(i).getFromID()
                    .equals(mUserInfoHandler.userId())) {
                  responseValues.getData().getData().get(i).setCustProType(1);
                } else if (responseValues.getData().getData().get(i).getFromID()
                    .equals(storeId)) {
                  responseValues.getData().getData().get(i).setCustProType(2);
                } else {
                  responseValues.getData().getData().remove(i);
                }
                mChatDataArry.add(responseValues.getData().getData().get(i));
              }
              Collections.reverse(mChatDataArry);
              mLiveData.postValue(mChatDataArry);
            }
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("Notification Fail" + e.getMessage());
          }
        };
    mHandler.execute(mGetCustomerChatUseCase,
        new GetCustomerChatUseCase.RequestValues(bookingId, pageNo),
        disposableSingleObserver);
  }

  /**
   * call the post chat api
   */
  void callPostMsgApi(String payLoad, String storeId, String orderId, int msgType) {
    progressVisible.set(TRUE);
    DisposableSingleObserver<PostChatUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<PostChatUseCase.ResponseValues>() {
          @Override
          public void onSuccess(PostChatUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            GetChatData chatData = new GetChatData();
            chatData.setContentType(msgType);
            chatData.setTargetId(storeId);
            chatData.setFromID(mUserInfoHandler.userId());
            chatData.setContent(payLoad);
            chatData.setCustProType(1);
            mChatDataArry.add(chatData);
            mLiveData.postValue(mChatDataArry);
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("Notification Fail" + e.getMessage());
          }
        };
    mHandler.execute(mPostChatUseCase,
        new PostChatUseCase.RequestValues(payLoad, msgType, storeId, orderId),
        disposableSingleObserver);
  }

  /**
   * This method is using to upload image
   */
  public void uploadImage(File file) {
    DisposableSingleObserver<UploadImageUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<UploadImageUseCase.ResponseValues>() {
          @Override
          public void onSuccess(UploadImageUseCase.ResponseValues responseValues) {
            EcomUtil.printLog(
                "ProfileDet Success" + responseValues.getData().getImageUrl());
            mImageData.postValue(responseValues.getData().getImageUrl());
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("ProfileDet Fail" + e.getMessage());
          }
        };
    mHandler.execute(mUploadImageUseCase,
        new UploadImageUseCase.RequestValues(BuildConfig.BUCKET_NAME, file,
            "" + System.currentTimeMillis()),
        disposableSingleObserver);
  }

  /**
   * notify when chat data comes..
   */
  MutableLiveData<ArrayList<GetChatData>> getChatData() {
    return mLiveData;
  }

  /**
   * notify when chat image data comes..
   */
  MutableLiveData<String> getImageData() {
    return mImageData;
  }

  /**
   * subscribe to  click on views.
   */
  MutableLiveData<Boolean> onBackClicked() {
    return mBack;
  }
}
