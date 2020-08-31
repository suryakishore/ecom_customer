package com.customer.fivecanale.savedcards;

import static com.appscrip.stripe.Constants.ENGLISH;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;

import android.view.View;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.appscrip.stripe.AccountsDelegate;
import com.appscrip.stripe.UserAccounts;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.fivecanale.ApplicationManager;
import com.customer.fivecanale.util.BackButtonClickListener;
import com.customer.fivecanale.util.EcomUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import javax.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

/**
 * view model class for the saved cards
 */
public class SavedCardViewModel extends ViewModel implements BackButtonClickListener {
  @Inject
  public UserInfoHandler mUserInfoHandler;
  public ObservableField<Boolean> progressVisible = new ObservableField<>(FALSE);
  private ArrayList<SavedCardsData> mSavedCardsData = new ArrayList<>();
  private MutableLiveData<ArrayList<SavedCardsData>> mSavedCardsLiveData = new MutableLiveData<>();
  private MutableLiveData<Boolean> mBackLiveData = new MutableLiveData<>();

  @Inject
  SavedCardViewModel() {
  }

  /**
   * used to get the cards
   */
  void getCards() {
    progressVisible.set(TRUE);
    UserAccounts.INSTANCE.getCards(ApplicationManager.getInstance(), mUserInfoHandler.getToken(),
        ENGLISH, mUserInfoHandler.userId(), new AccountsDelegate() {
          @Override
          public void onSuccess(@NotNull Object successData) {
            progressVisible.set(FALSE);
            JSONArray jsonArray = (JSONArray) successData;
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<SavedCardsData>>() {
            }.getType();
            mSavedCardsData.clear();
            mSavedCardsData = gson.fromJson(jsonArray.toString(), listType);
            mSavedCardsLiveData.postValue(mSavedCardsData);
          }

          @Override
          public void onFailure(@NotNull String failure) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("exe" + "failure" + failure);
          }
        });
  }

  /**
   * used to delete the card
   */
  void deleteCard(String cardId) {
    progressVisible.set(TRUE);
    UserAccounts.INSTANCE.deleteCard(ApplicationManager.getInstance(), mUserInfoHandler.getToken(),
        ENGLISH, cardId, new AccountsDelegate() {
          @Override
          public void onSuccess(@NotNull Object successData) {
            getCards();
            EcomUtil.printLog("exe" + "successData" + successData);
          }

          @Override
          public void onFailure(@NotNull String failure) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("exe" + "failureDelete" + failure);
          }
        });
  }

  /**
   * notify saved cards data comes
   *
   * @return mutable live data of saved cards
   */
  MutableLiveData<ArrayList<SavedCardsData>> getSavedCardsLIveData() {
    return mSavedCardsLiveData;
  }

  /**
   * notify saved cards data comes
   *
   * @return mutable live data of saved cards
   */
  MutableLiveData<Boolean> onBackClickLiveData() {
    return mBackLiveData;
  }

  @Override
  public void backButtonClickListener(View view) {
    mBackLiveData.postValue(TRUE);
  }
}


