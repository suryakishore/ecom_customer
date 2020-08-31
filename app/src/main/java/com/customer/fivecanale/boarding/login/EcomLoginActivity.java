package com.customer.fivecanale.boarding.login;

import static com.customer.fivecanale.util.EcomConstants.COUNTRY_CODE;
import static com.customer.fivecanale.util.EcomConstants.EMAIL_DIALOG_TYPE;
import static com.customer.fivecanale.util.EcomConstants.EMAIL_ID;
import static com.customer.fivecanale.util.EcomConstants.EMAIL_LOGIN;
import static com.customer.fivecanale.util.EcomConstants.E_MAIL;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.FB_LOGIN;
import static com.customer.fivecanale.util.EcomConstants.FIELDS;
import static com.customer.fivecanale.util.EcomConstants.FOUR;
import static com.customer.fivecanale.util.EcomConstants.GOOGLE_LOGIN;
import static com.customer.fivecanale.util.EcomConstants.ID;
import static com.customer.fivecanale.util.EcomConstants.IS_EMAIL;
import static com.customer.fivecanale.util.EcomConstants.IS_LOGIN;
import static com.customer.fivecanale.util.EcomConstants.IS_TO_FINISH;
import static com.customer.fivecanale.util.EcomConstants.IS_TO_FORGOT;
import static com.customer.fivecanale.util.EcomConstants.LOGIN_RC;
import static com.customer.fivecanale.util.EcomConstants.MOBILE_LOGIN;
import static com.customer.fivecanale.util.EcomConstants.NAME;
import static com.customer.fivecanale.util.EcomConstants.NORMAL_LOGIN;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.OTP_EXPIRY_TIME;
import static com.customer.fivecanale.util.EcomConstants.OTP_ID;
import static com.customer.fivecanale.util.EcomConstants.OTP_VIEWS;
import static com.customer.fivecanale.util.EcomConstants.PHONE_NUMBER;
import static com.customer.fivecanale.util.EcomConstants.PROFILE_PIC;
import static com.customer.fivecanale.util.EcomConstants.RC_GOOGLE_SIGN_IN;
import static com.customer.fivecanale.util.EcomConstants.REQUEST_FACEBOOK_FIELDS;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;
import static com.customer.fivecanale.util.EcomUtil.getFbUserProfilePic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.fivecanale.boarding.forgotpassword.EcomForgotPasswordActivity;
import com.customer.fivecanale.boarding.forgotpassword.ForgotPasswordCallBack;
import com.customer.fivecanale.boarding.register.EcomSignUpActivity;
import com.customer.fivecanale.uiutil.dialog.CustomDialogUtilBuilder;
import com.customer.fivecanale.util.EcomUtil;
import com.customer.fivecanale.util.manager.KeyboardDetector;
import com.customer.fivecanale.util.manager.KeyboardStatus;
import com.databinding.ActivityEcomLoginBinding;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.Arrays;
import java.util.Objects;
import javax.inject.Inject;
import org.json.JSONException;

/**
 * <h1>EcomLoginActivity</h1>
 * <P>This class is used to login through mail or phone number.</P>
 */
public class EcomLoginActivity extends DaggerAppCompatActivity implements
    ForgotPasswordCallBack.ForgotPasswordDialogCallBack,
    CustomDialogUtilBuilder.NumberOrEmailNotExistListener {
  private static final String TAG = EcomLoginActivity.class.getSimpleName();
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  @Inject
  UserInfoHandler userInfoHandler;
  private boolean mIsValidPhoneNum;
  private ActivityEcomLoginBinding mBinding;
  private EcomLoginViewModel mEcomLoginViewModel;
  private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
  private GoogleSignInClient mGoogleSignInClient;
  private GoogleSignInAccount mGoogleSignInAccount;
  private String mMail, mName, mProfilePic;
  private String mNumber = "";
  private CallbackManager mCallbackManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeView();
    initializeViewModel();
    subscribeShowTextClicked();
    subscribeOpenForgotPassword();
    subscribeOnError();
    subscribeOnClicks();
    checkKeyboardStatus();
  }

  /**
   * initialising the View using Data Binding
   */
  private void initializeView() {
    mBinding = DataBindingUtil.setContentView(
        this,
        R.layout.activity_ecom_login);
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestProfile()
        .build();
    mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    mCallbackManager = CallbackManager.Factory.create();
    LoginManager.getInstance().registerCallback(mCallbackManager,
        new FacebookCallback<LoginResult>() {
          @Override
          public void onSuccess(LoginResult loginResult) {
            // App code
            getUserProfile(loginResult.getAccessToken());
          }

          @Override
          public void onCancel() {
            // App code
          }

          @Override
          public void onError(FacebookException exception) {
            // App code
          }
        });
  }

  /**
   * <h2>initializeViewModel</h2>
   * <p>This method is used initialize the viewModel class for this activity.</p>
   */
  private void initializeViewModel() {
    mEcomLoginViewModel =
        ViewModelProviders.of(this, mViewModelFactory).get(EcomLoginViewModel.class);
    mBinding.setViewModel(mEcomLoginViewModel);//used to connect mBinding to viewModel
    //getting device details
    mEcomLoginViewModel.getDeviceDetails(EcomUtil.getDeviceId(this), userInfoHandler.getIpAddress());
    mBinding.tvLoginPhone.setSelected(TRUE);
    mBinding.ccpGetNumber.registerCarrierNumberEditText(mBinding.etLoginPhoneNumber);
    mBinding.ccpGetNumber.setPhoneNumberValidityChangeListener(isValidNumber -> {
      mIsValidPhoneNum = isValidNumber;
      mBinding.tvLogin.setEnabled(mIsValidPhoneNum);
      mEcomLoginViewModel.mIsValidPhoneNum = mIsValidPhoneNum;
      mBinding.etLoginPhoneNumber.setFilters(
          mIsValidPhoneNum && !Objects.requireNonNull(
              mBinding.etLoginPhoneNumber.getText()).toString().isEmpty()
              ?
              new InputFilter[]{new InputFilter.LengthFilter(
                  mBinding.etLoginPhoneNumber.getText().toString().length())}
              : new InputFilter[]{});
    });
    mBinding.etLoginEmail.requestFocus();
  }

  /**
   * <h2>subscribeOnClicks</h2>
   * <p>This method is used to listen all the clicks of the views.</p>
   */
  private void subscribeOnClicks() {
    mEcomLoginViewModel.onClick().observe(this, loginUiAction -> {
      switch (loginUiAction) {
        case CROSS_ICON:
          Intent intent = new Intent();
          setResult(Activity.RESULT_OK, intent);
          finish();
          overridePendingTransition(R.anim.stay, R.anim.slide_down);
          break;
        case PHONE_LOGIN:
          setPhoneViews();
          break;
        case FORGOT_PASSWORD:
          showDialog();
          break;
        case GOOGLE_LOGIN:
          googleLogin();
          break;
        case CREATE_ACCOUNT:
          mNumber = "";
          //  mMail="";
          openSignUpActivity();
          break;
        case LOGIN:
          onLogin();
          break;
        case FACEBOOK_LOGIN:
          doFaceBookLogin();
          break;
        case FINISH:
          finish();
      }
    });
  }

  /**
   * redirect to facebook for login
   */
  private void doFaceBookLogin() {
    mMail = null;
    LoginManager.getInstance().logOut();
    LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(E_MAIL));
  }

  /**
   * start the activity for the google login
   */
  private void googleLogin() {
    mGoogleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
    if (mGoogleSignInClient != null) {
      mGoogleSignInClient.signOut();
      mMail = null;
    }
    Intent googleLogin = mGoogleSignInClient.getSignInIntent();
    startActivityForResult(googleLogin, RC_GOOGLE_SIGN_IN);
  }

  /**
   * <h2>showDialog</h2>
   * <p>This method is used to show the dialog of the forgotPassword</p>
   */
  private void showDialog() {
    CustomDialogUtilBuilder.CustomDialogBuilder customDialogUtilBuilder =
        new CustomDialogUtilBuilder.CustomDialogBuilder(this,
            (ForgotPasswordCallBack.ForgotPasswordDialogCallBack) this, ONE);
    customDialogUtilBuilder.buildCustomDialog();
  }

  /**
   * <h2>subscribeOpenForgotPassword</h2>
   * <p>this method is used to subscribe to open the forgotPassword screen.</p>
   */
  private void subscribeOpenForgotPassword() {
    mEcomLoginViewModel.onVisibleOtpViews().observe(this,
        oTpHashMap -> {
          if (oTpHashMap != null) {
            openForgotPassword(oTpHashMap.get(OTP_ID),
                Integer.parseInt(
                    Objects.requireNonNull(oTpHashMap.get(OTP_EXPIRY_TIME))));
          }
        });
  }

  /**
   * <h2>checkKeyboardStatus</h2>
   * to check whether keyboard is open or close
   */
  private void checkKeyboardStatus() {
    new KeyboardDetector(this).observe().subscribe(new Observer<KeyboardStatus>() {
      @Override
      public void onSubscribe(Disposable d) {
        mCompositeDisposable.add(d);
      }

      @Override
      public void onNext(KeyboardStatus status) {
        if (status == KeyboardStatus.OPENED) {
          if (mBinding.vgLoginMailAndPass.getVisibility() == View.VISIBLE) {
            mBinding.nsvLoginScroll.smoothScrollTo(
                (int) (mBinding.clLoginInputs.getX()),
                (int) (mBinding.clLoginInputs.getY()));
          }
        }
      }

      @Override
      public void onError(Throwable e) {
      }

      @Override
      public void onComplete() {
      }
    });
  }

  /**
   * <h2>subscribeOnError</h2>
   * this method is to register for the mError message.
   */
  private void subscribeOnError() {
    mEcomLoginViewModel.onError().observe(this, this::showError);
  }

  /**
   * <h2>onLogin</h2>
   * <p>This method is executed when a uer clicks on the login button.</p>
   */
  private void onLogin() {
    if (mBinding.tvLogin.getText().toString().equals(getResources().getString(R.string.login))) {
      if (mBinding.tiLoginEmail.getVisibility() == View.VISIBLE) {
        mEcomLoginViewModel.callEmailOrPhoneNumSignInApi("", "",
            Objects.requireNonNull(mBinding.etLoginEmail.getText()).toString(),
            Objects.requireNonNull(mBinding.etLoginPassword.getText()).toString(), EMAIL_LOGIN,
            NORMAL_LOGIN, "", "");
      }
    } else {
      mEcomLoginViewModel.callEmailOrPhoneNumSignInApi(
          Objects.requireNonNull(
              mBinding.etLoginPhoneNumber.getText()).toString().replaceAll(" ",
              ""),
          String.format("+%s", mBinding.ccpGetNumber.getSelectedCountryCode()),
          Objects.requireNonNull(mBinding.etLoginEmail.getText()).toString(),
          Objects.requireNonNull(mBinding.etLoginPassword.getText()).toString(),
          MOBILE_LOGIN, NORMAL_LOGIN, "", "");
    }
  }

  /**
   * <h2>subscribeShowTextClicked</h2>
   * <p>This method is for registering to hide or show icon to Password editText. </p>
   */
  private void subscribeShowTextClicked() {
    mEcomLoginViewModel.onShowTextClicked().observe(this, isClicked -> {
      mBinding.ivLoginShow.setImageDrawable(
          !isClicked ? getResources().getDrawable(R.drawable.all_hide_password)
              : getResources().getDrawable(R.drawable.all_show_password));
      mBinding.etLoginPassword.setTransformationMethod(
          !isClicked ? null : new PasswordTransformationMethod());
      mBinding.etLoginPassword.setSelection(
          Objects.requireNonNull(mBinding.etLoginPassword.getText()).length());
    });
  }

  /**
   * <p>This method is used to show the snackBar message.</p>
   */
  public void showError(String error) {
    int dialogType = EMAIL_DIALOG_TYPE;
    try {
      dialogType = Integer.valueOf(error);
    } catch (Exception e) {
      e.getMessage();
    }
    CustomDialogUtilBuilder.CustomDialogBuilder customDialogUtilBuilder =
        new CustomDialogUtilBuilder.CustomDialogBuilder(this,
            (CustomDialogUtilBuilder.NumberOrEmailNotExistListener) this, dialogType);
    customDialogUtilBuilder.setEmailOrNumNotExistMsg(error);
    customDialogUtilBuilder.buildCustomDialog();
  }

  /**
   * <p>This method is executed when a user clicks on the phone icon.</p>
   */
  public void setPhoneViews() {
    mBinding.tvLogin.setText(
        (mBinding.tvLoginPhone.isSelected()) ? getResources().getString(R.string.allContinue)
            : getResources().getString(R.string.login));
    mBinding.vgLoginMailAndPass.setVisibility(
        (mBinding.tvLoginPhone.isSelected()) ? View.GONE : View.VISIBLE);
    mBinding.tvLoginPhone.setCompoundDrawablesRelativeWithIntrinsicBounds(
        mBinding.tvLoginPhone.isSelected() ? R.drawable.ic_phone : R.drawable.ic_login_mail, ZERO,
        ZERO, ZERO);
    mBinding.tvLoginPhone.setText(
        mBinding.tvLoginPhone.isSelected() ? getResources().getString(R.string.loginWithEmail)
            : getResources().getString(R.string.loginWithNumber));
    mBinding.vgPhoneNum.setVisibility(
        (mBinding.tvLoginPhone.isSelected()) ? View.VISIBLE : View.GONE);
    if (mBinding.tvLoginPhone.isSelected()) {
      mBinding.ivLoginShow.setVisibility(View.INVISIBLE);
      mBinding.etLoginPhoneNumber.requestFocus();
      mBinding.tvLogin.setEnabled(mIsValidPhoneNum);
    } else {
      if (Objects.requireNonNull(mBinding.etLoginPassword.getText()).toString().length()
          > ZERO && mBinding.vgLoginMailAndPass.getVisibility() == View.VISIBLE) {
        mBinding.ivLoginShow.setVisibility(View.VISIBLE);
      }
      mBinding.tvLogin.setEnabled((EcomUtil.isEmail(mBinding.etLoginEmail.getText())
          && mBinding.etLoginPassword.getText().toString().length() > FOUR) ? TRUE : FALSE);
    }
    mBinding.tvLoginPhone.setSelected((mBinding.tvLoginPhone.isSelected()) ? FALSE : TRUE);
  }

  /**
   * <h2>openForgotPassword</h2>
   * <p>This method is used to open the forgot password screen.</p>
   */
  public void openForgotPassword(String otpId, int otpExpiryTime) {
    Intent intent = new Intent(this, EcomForgotPasswordActivity.class);
    intent.putExtra(IS_LOGIN, TRUE);
    intent.putExtra(OTP_ID, otpId);
    intent.putExtra(OTP_EXPIRY_TIME, otpExpiryTime);
    intent.putExtra(OTP_VIEWS, TRUE);
    intent.putExtra(PHONE_NUMBER,
        Objects.requireNonNull(mBinding.etLoginPhoneNumber.getText()).toString().replaceAll(
            " ", ""));
    intent.putExtra(COUNTRY_CODE, mBinding.ccpGetNumber.getSelectedCountryCode());
    startActivityForResult(intent, LOGIN_RC);
  }

  /**
   * <h2>openSignUpActivity</h2>
   * <p>This method is used to open the signUp screen.</p>
   */
  public void openSignUpActivity() {
    Intent intent = new Intent(this, EcomSignUpActivity.class);
    if (mMail != null && !mMail.isEmpty()) {
      intent.putExtra(EMAIL_ID, mMail);
      intent.putExtra(NAME, mName);
      intent.putExtra(PROFILE_PIC, mProfilePic);
    }
    if (mNumber != null && !mNumber.isEmpty()) {
      intent.putExtra(PHONE_NUMBER, mNumber);
      intent.putExtra(COUNTRY_CODE, mBinding.ccpGetNumber.getSelectedCountryCodeAsInt());
    }
    startActivityForResult(intent, LOGIN_RC);
  }

  /**
   * <h2>chooseEmailOrPhone</h2>
   * <p>This method is used to open the forgot password screen.</p>
   */
  @Override
  public void chooseEmailOrPhone(boolean isEmail) {
    Intent intent = new Intent(this, EcomForgotPasswordActivity.class);
    intent.putExtra(IS_EMAIL, isEmail);
    intent.putExtra(IS_TO_FORGOT, TRUE);
    startActivity(intent);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mCompositeDisposable != null) {
      mCompositeDisposable.dispose();
    }
    if (mGoogleSignInClient != null) {
      mGoogleSignInClient.signOut();
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    if (mCallbackManager != null) {
      mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
    super.onActivityResult(requestCode, resultCode, data);
    if (data != null) {
      switch (requestCode) {
        case LOGIN_RC:
          if (resultCode == RESULT_OK) {
            boolean isToFinish = data.getBooleanExtra(IS_TO_FINISH, FALSE);
            if (isToFinish) {
              Intent intent = new Intent();
              setResult(RESULT_OK, intent);
              finish();
            }
          }
          break;
        case RC_GOOGLE_SIGN_IN:
          Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
          handleSignInResult(task);
          break;
        default:
          break;
      }
    }
  }

  /**
   * handle google sign in result
   */
  private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
    try {
      mGoogleSignInAccount = completedTask.getResult(ApiException.class);
      // Signed in successfully, show authenticated UI.
      mMail = Objects.requireNonNull(mGoogleSignInAccount).getEmail();
      mName = mGoogleSignInAccount.getDisplayName();
      mProfilePic = mGoogleSignInAccount.getPhotoUrl() != null
          ? mGoogleSignInAccount.getPhotoUrl().toString() : "";
      EcomUtil.printLog("exe" + "mName" + mName + "url " + mProfilePic);
      mEcomLoginViewModel.callEmailOrPhoneNumSignInApi("", "",
          Objects.requireNonNull(mGoogleSignInAccount.getEmail()),
          "", EMAIL_LOGIN,
          GOOGLE_LOGIN, mGoogleSignInAccount.getId(), "");
    } catch (ApiException e) {
      EcomUtil.printLog("exe" + e.getLocalizedMessage());
    }
  }

  /**
   * get the user details from facebook
   */
  private void getUserProfile(AccessToken currentAccessToken) {
    GraphRequest request = GraphRequest.newMeRequest(
        currentAccessToken, (object, response) -> {
          try {
            EcomUtil.printLog("object" + object.toString());
            String email = object.getString(E_MAIL);
            mMail = email;
            String id = object.getString(ID);
            mProfilePic = getFbUserProfilePic(id);
            EcomUtil.printLog("mProfilePic" + mProfilePic);
            mName = object.getString(NAME);
            mEcomLoginViewModel.callEmailOrPhoneNumSignInApi("", "",
                Objects.requireNonNull(email),
                "", EMAIL_LOGIN,
                FB_LOGIN, "", id);
          } catch (JSONException e) {
            e.printStackTrace();
          }
        });
    Bundle parameters = new Bundle();
    parameters.putString(FIELDS, REQUEST_FACEBOOK_FIELDS);
    request.setParameters(parameters);
    request.executeAsync();
  }

  @Override
  public void onNumberOrMail(boolean retryOrSignUp) {
    EcomUtil.printLog("exe" + "retryOrSignUp" + retryOrSignUp);
    if (retryOrSignUp) {
      if (mBinding.tvLogin.getText().toString().equals(getResources().getString(R.string.login))) {
        mMail = Objects.requireNonNull(mBinding.etLoginEmail.getText()).toString();
      } else {
        mNumber = Objects.requireNonNull(mBinding.etLoginPhoneNumber.getText()).toString();
      }
      EcomUtil.printLog("exe" + "mNumber" + mNumber);
      openSignUpActivity();
    }
  }
}