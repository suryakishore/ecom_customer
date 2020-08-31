package com.customer.fivecanale.boarding.forgotpassword;

/**
 * <p>This interface call back is used to show the forgot password dialog</p>
 */
public interface ForgotPasswordCallBack {
  interface ForgotPasswordEmailDialogCallBack {
    void showLoginPage();
  }

  interface ForgotPasswordDialogCallBack {
    void chooseEmailOrPhone(boolean isEmail);
  }
}
