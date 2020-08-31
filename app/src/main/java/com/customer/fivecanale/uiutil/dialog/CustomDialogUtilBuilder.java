package com.customer.fivecanale.uiutil.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.R;
import com.customer.fivecanale.boarding.forgotpassword.ForgotPasswordCallBack;
import com.customer.fivecanale.util.EcomUtil;

import java.util.Objects;

import static com.customer.fivecanale.util.EcomConstants.EMAIL_DIALOG_TYPE;
import static com.customer.fivecanale.util.EcomConstants.EMAIL_SUCC_DIALOG_TYPE;
import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.FORGOT_PASSWORD_DIALOG_TYPE;
import static com.customer.fivecanale.util.EcomConstants.OUT_OF_STOCK_DIALOG_TYPE;
import static com.customer.fivecanale.util.EcomConstants.PHONE_DIALOG_TYPE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.VERSION_UPDATE_DIALOG_TYPE;

public class CustomDialogUtilBuilder {
  private Activity mActivity;
  private DialogOutOfStockNotifyListener mDialogOutOfStockNotifyListener;
  private NumberOrEmailNotExistListener mNumberOrEmailNotExistListener;
  private VersionClickListener mVersionClickListener;
  private ForgotPasswordCallBack.ForgotPasswordDialogCallBack mForgotPasswordDialogCallBack;
  private ForgotPasswordCallBack.ForgotPasswordEmailDialogCallBack
      mForgotPasswordEmailDialogCallBack;
  private int mDialogType;
  private String mMailId;
  private boolean mIsUpDateMandatory;
  private String mEmailOrNumNotExistMsg;

  private CustomDialogUtilBuilder(CustomDialogBuilder customDialogBuilder) {
    mActivity = customDialogBuilder.mActivity;
    mDialogOutOfStockNotifyListener = customDialogBuilder.mDialogOutOfStockNotifyListener;
    this.mForgotPasswordDialogCallBack = customDialogBuilder.mForgotPasswordDialogCallBack;
    this.mForgotPasswordEmailDialogCallBack =
        customDialogBuilder.mForgotPasswordEmailDialogCallBack;
    this.mNumberOrEmailNotExistListener =
        customDialogBuilder.mNumberOrEmailNotExistListener;
    this.mVersionClickListener =
        customDialogBuilder.mVersionClickListener;
    this.mDialogType = customDialogBuilder.mDialogType;
    this.mMailId = customDialogBuilder.mailId;
    this.mIsUpDateMandatory = customDialogBuilder.mIsUpDateMandatory;
    this.mEmailOrNumNotExistMsg = customDialogBuilder.emailOrNumNotExistMsg;
    switch (mDialogType) {
      case OUT_OF_STOCK_DIALOG_TYPE:
        showOutOfStockDialog();
        break;
      case FORGOT_PASSWORD_DIALOG_TYPE:
        showForGotPassDialog();
        break;
      case EMAIL_SUCC_DIALOG_TYPE:
        emailSuccessDialog();
        break;
      case EMAIL_DIALOG_TYPE:
        emailOrPhoneNotExistDialog(true);
        break;
      case PHONE_DIALOG_TYPE:
        emailOrPhoneNotExistDialog(false);
        break;
      case VERSION_UPDATE_DIALOG_TYPE:
        updateAppDialog();
        break;
    }
  }

  private void showOutOfStockDialog() {
    final Dialog dialog = new Dialog(mActivity);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(TRUE);
    dialog.setContentView(R.layout.dialog_outofstock_notify);
    Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(
        new ColorDrawable(android.graphics.Color.TRANSPARENT));
    AppCompatTextView tvSubmit = dialog.findViewById(R.id.tvSubmit);
    AppCompatEditText etEMail = dialog.findViewById(R.id.etEMail);
    if (mMailId != null && !mMailId.isEmpty()) {
      etEMail.setText(mMailId);
      etEMail.setEnabled(FALSE);
      tvSubmit.setTextColor(mActivity.getResources().getColor(R.color.colorProductFreeSpeechBlue));
    }
    AppCompatImageView ivCross = dialog.findViewById(R.id.ivCross);
    etEMail.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }

      @Override
      public void afterTextChanged(Editable editable) {
        ivCross.setVisibility(editable.toString().isEmpty() ? View.GONE : View.VISIBLE);
        tvSubmit.setTextColor(
            EcomUtil.isEmail(Objects.requireNonNull(etEMail.getText()).toString())
                ? mActivity.getResources().getColor(R.color.colorProductFreeSpeechBlue)
                : mActivity.getResources().getColor(R.color.colorProductSeekBackground));
        tvSubmit.setEnabled(
            EcomUtil.isEmail(Objects.requireNonNull(etEMail.getText()).toString()));
      }
    });
    ivCross.setOnClickListener(view -> etEMail.setText(""));
    tvSubmit.setOnClickListener(view -> {
      if (EcomUtil.isEmail(Objects.requireNonNull(etEMail.getText()).toString())) {
        mDialogOutOfStockNotifyListener.onNotifyMail(
            Objects.requireNonNull(etEMail.getText()).toString());
        dialog.dismiss();
      }
    });
    dialog.show();
  }

  private void showForGotPassDialog() {
    final Dialog dialog = new Dialog(mActivity);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(TRUE);
    dialog.setContentView(R.layout.ecom_login_forgotpasswod_dialog);
    Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(
        new ColorDrawable(android.graphics.Color.TRANSPARENT));
    Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(
        R.drawable.dialog_inset);
    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
    ImageView ivDialogChooseCancel = dialog.findViewById(R.id.ivDialogChooseCancel);
    TextView tvForgotPasswordPhone = dialog.findViewById(R.id.tvForgotPasswordPhone);
    TextView tvForgotPasswordEmail = dialog.findViewById(R.id.tvForgotPasswordEmail);
    ivDialogChooseCancel.setOnClickListener(view -> dialog.dismiss());
    tvForgotPasswordPhone.setOnClickListener(view -> {
      dialog.dismiss();
      mForgotPasswordDialogCallBack.chooseEmailOrPhone(FALSE);
    });
    tvForgotPasswordEmail.setOnClickListener(view -> {
      dialog.dismiss();
      mForgotPasswordDialogCallBack.chooseEmailOrPhone(TRUE);
    });
    dialog.show();
  }

  private void emailSuccessDialog() {
    final Dialog dialog = new Dialog(mActivity);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(TRUE);
    dialog.setContentView(R.layout.ecom_fp_email_dialog);
    Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(
        R.drawable.dialog_inset);
    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
    TextView tvForGotPassOk = dialog.findViewById(R.id.tvForGotPassOk);
    ImageView ivCross = dialog.findViewById(R.id.ivCross);
    TextView tvForGotPassMailId = dialog.findViewById(R.id.tvForGotPassMailId);
    tvForGotPassMailId.setText(this.mMailId);
    tvForGotPassOk.setSelected(TRUE);
    ivCross.setOnClickListener(view -> {
      dialog.dismiss();
      mForgotPasswordEmailDialogCallBack.showLoginPage();
    });
    tvForGotPassOk.setOnClickListener(view -> {
      dialog.dismiss();
      mForgotPasswordEmailDialogCallBack.showLoginPage();
    });
    dialog.show();
  }

  /**
   * This method is used to show the error dialog when the phone number or email not exist form the
   * login response.
   */
  private void emailOrPhoneNotExistDialog(boolean isEmailNotValid) {
    final Dialog dialog = new Dialog(mActivity);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(false);
    dialog.setContentView(R.layout.dialog_logout);
    Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(
        new ColorDrawable(android.graphics.Color.TRANSPARENT));
    AppCompatTextView tvLogout = dialog.findViewById(R.id.tvLogout);
    AppCompatTextView tvLogoutMessage = dialog.findViewById(R.id.tvLogoutMessage);
    AppCompatTextView tvLogoutCancel = dialog.findViewById(R.id.tvLogoutCancel);
    tvLogoutMessage.setText(mActivity.getResources().getString((isEmailNotValid ?
            R.string.emailNotExist : R.string.phoneNotExist)));
    tvLogout.setText(mActivity.getResources().getString(R.string.signUp));
    tvLogoutCancel.setText(mActivity.getResources().getString(R.string.reTry));
    tvLogout.setOnClickListener(v -> {
      mNumberOrEmailNotExistListener.onNumberOrMail(TRUE);
      dialog.dismiss();
    });
    tvLogoutCancel.setOnClickListener(v -> {
      dialog.dismiss();
    });
    dialog.show();
  }

  /**
   * This method is used to show the  dialog when the user want to update the app
   */
  private void updateAppDialog() {
    final Dialog dialog = new Dialog(mActivity);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(mIsUpDateMandatory ? false : TRUE);
    dialog.setContentView(R.layout.dialog_logout);
    Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(
        new ColorDrawable(android.graphics.Color.TRANSPARENT));
    AppCompatTextView tvLogout = dialog.findViewById(R.id.tvLogout);
    AppCompatTextView tvLogoutMessage = dialog.findViewById(R.id.tvLogoutMessage);
    AppCompatTextView tvLogoutCancel = dialog.findViewById(R.id.tvLogoutCancel);
    tvLogoutMessage.setText(mActivity.getResources().getString(R.string.newUpdate));
    tvLogout.setText(mActivity.getResources().getString(R.string.updated));
    tvLogout.setOnClickListener(v -> {
      mVersionClickListener.onVersionChange(TRUE);
    });
    tvLogoutCancel.setOnClickListener(v -> {
      if (!mIsUpDateMandatory) {
        dialog.dismiss();
      } else {
        Toast.makeText(mActivity, mActivity.getResources().getString(R.string.updateError),
            Toast.LENGTH_LONG).show();
      }
    });
    dialog.show();
  }

  public interface DialogOutOfStockNotifyListener {
    void onNotifyMail(String mail);
  }

  public interface NumberOrEmailNotExistListener {
    void onNumberOrMail(boolean retryOrSignUp);
  }

  public interface VersionClickListener {
    void onVersionChange(boolean versonChange);
  }

  public static class CustomDialogBuilder {
    private Activity mActivity;
    private DialogOutOfStockNotifyListener mDialogOutOfStockNotifyListener;
    private NumberOrEmailNotExistListener mNumberOrEmailNotExistListener;
    private VersionClickListener mVersionClickListener;
    private ForgotPasswordCallBack.ForgotPasswordDialogCallBack mForgotPasswordDialogCallBack;
    private ForgotPasswordCallBack.ForgotPasswordEmailDialogCallBack
        mForgotPasswordEmailDialogCallBack;
    private int mDialogType;
    private String mailId;
    private String emailOrNumNotExistMsg;
    private boolean mIsUpDateMandatory;

    public CustomDialogBuilder(Activity activity,
        DialogOutOfStockNotifyListener dialogOutOfStockNotifyListener, int dialogType) {
      mActivity = activity;
      mDialogOutOfStockNotifyListener = dialogOutOfStockNotifyListener;
      this.mDialogType = dialogType;
    }

    public CustomDialogBuilder(Activity activity,
        NumberOrEmailNotExistListener numberOrEmailNotExistListener, int dialogType) {
      mActivity = activity;
      mNumberOrEmailNotExistListener = numberOrEmailNotExistListener;
      this.mDialogType = dialogType;
    }

    public CustomDialogBuilder(Activity activity,
        VersionClickListener versionClickListener, int dialogType) {
      mActivity = activity;
      mVersionClickListener = versionClickListener;
      this.mDialogType = dialogType;
    }

    public CustomDialogBuilder(Activity activity,
        ForgotPasswordCallBack.ForgotPasswordEmailDialogCallBack forgotPasswordDialogCallBack,
        int dialogType) {
      mActivity = activity;
      mForgotPasswordEmailDialogCallBack = forgotPasswordDialogCallBack;
      this.mDialogType = dialogType;
    }

    public CustomDialogBuilder(Activity activity,
        ForgotPasswordCallBack.ForgotPasswordDialogCallBack forgotPasswordDialogCallBack,
        int dialogType) {
      mActivity = activity;
      mForgotPasswordDialogCallBack = forgotPasswordDialogCallBack;
      this.mDialogType = dialogType;
    }

    public String getMailId() {
      return mailId;
    }

    public void setMailId(String mailId) {
      this.mailId = mailId;
    }

    public String getEmailOrNumNotExistMsg() {
      return emailOrNumNotExistMsg;
    }

    public void setEmailOrNumNotExistMsg(String emailOrNumNotExistMsg) {
      this.emailOrNumNotExistMsg = emailOrNumNotExistMsg;
    }

    public boolean isUpDateMandatory() {
      return mIsUpDateMandatory;
    }

    public void setUpDateMandatory(boolean upDateMandatory) {
      mIsUpDateMandatory = upDateMandatory;
    }

    public void buildCustomDialog() {
      new CustomDialogUtilBuilder(this);
    }
  }
}