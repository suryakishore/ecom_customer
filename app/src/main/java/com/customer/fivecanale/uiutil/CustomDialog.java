package com.customer.fivecanale.uiutil;

import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.MAIL_ID;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.R;
import com.customer.fivecanale.boarding.forgotpassword.ForgotPasswordCallBack;
import java.util.Objects;

/**
 * <h1>CustomDialog</h1>
 * <p>This class is used to show the custom dialog. </p>
 */
public class CustomDialog extends Dialog implements View.OnClickListener {
  private ForgotPasswordCallBack.ForgotPasswordDialogCallBack forgotPasswordDialogCallBack;
  private ForgotPasswordCallBack.ForgotPasswordEmailDialogCallBack
      forgotPasswordEmailDialogCallBack;
  private int dialogType;
  private Bundle bundle;

  /**
   * <h2>CustomDialog</h2>
   * <p>This constructor is used to store the initialize values.</p>
   *
   * @param context                           context
   * @param forgotPasswordDialogCallBack      this is for onclick of the forgot password.
   * @param forgotPasswordEmailDialogCallBack this is for onclick of th email dialog
   * @param dialogType                        this is for identify the dialog type.
   * @param bundle                            this is for receiving the bundle mData.
   */
  public CustomDialog(Context context,
      ForgotPasswordCallBack.ForgotPasswordDialogCallBack forgotPasswordDialogCallBack,
      ForgotPasswordCallBack.ForgotPasswordEmailDialogCallBack forgotPasswordEmailDialogCallBack,
      int dialogType, Bundle bundle) {
    super(context, R.style.alert_dialog);
    this.bundle = bundle;
    this.forgotPasswordDialogCallBack = forgotPasswordDialogCallBack;
    this.forgotPasswordEmailDialogCallBack = forgotPasswordEmailDialogCallBack;
    this.dialogType = dialogType;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    super.onCreate(savedInstanceState);
    switch (dialogType) {
      case ZERO:
        setContentView(R.layout.ecom_login_forgotpasswod_dialog);
        Objects.requireNonNull(this.getWindow()).setBackgroundDrawableResource(
            R.drawable.dialog_inset);
        this.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
        handleForgotPasswordDialog();
        break;
      case ONE:
        setContentView(R.layout.ecom_fp_email_dialog);
        Objects.requireNonNull(this.getWindow()).setBackgroundDrawableResource(
            R.drawable.dialog_inset);
        this.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
        handleForgotPasswordEmailDialog();
        break;
      default:
        break;
    }
  }

  /**
   * <h2>handleForgotPasswordEmailDialog</h2>
   * <p>this method is used to find the ids and register listener for the email dialog box</p>
   */
  private void handleForgotPasswordEmailDialog() {
    TextView tvForGotPassOk = findViewById(R.id.tvForGotPassOk);
    ImageView ivCross = findViewById(R.id.ivCross);
    TextView tvForGotPassMailId = findViewById(R.id.tvForGotPassMailId);
    String mailId = bundle.getString(MAIL_ID);
    tvForGotPassMailId.setText(mailId);
    tvForGotPassOk.setSelected(TRUE);
    tvForGotPassOk.setOnClickListener(this);
    ivCross.setOnClickListener(this);
  }

  /**
   * <h2>handleForgotPasswordDialog</h2>
   * <p>this method is used to find the ids and register listener for the forgotPassword dialog
   * box</p>
   */
  private void handleForgotPasswordDialog() {
    ImageView ivDialogChooseCancel = findViewById(R.id.ivDialogChooseCancel);
    TextView tvForgotPasswordPhone = findViewById(R.id.tvForgotPasswordPhone);
    TextView tvForgotPasswordEmail = findViewById(R.id.tvForgotPasswordEmail);
    ivDialogChooseCancel.setOnClickListener(this);
    tvForgotPasswordPhone.setOnClickListener(this);
    tvForgotPasswordEmail.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.ivDialogChooseCancel:
        super.cancel();
        break;
      case R.id.tvForgotPasswordPhone:
        forgotPasswordDialogCallBack.chooseEmailOrPhone(FALSE);
        super.cancel();
        break;
      case R.id.tvForgotPasswordEmail:
        forgotPasswordDialogCallBack.chooseEmailOrPhone(TRUE);
        super.cancel();
        break;
      case R.id.tvForGotPassOk:
      case R.id.ivCross:
        super.cancel();
        forgotPasswordEmailDialogCallBack.showLoginPage();
        break;
      default:
        break;
    }
  }
}