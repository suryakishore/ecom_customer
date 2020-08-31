package com.customer.fivecanale.uiutil.dialog;

import static com.customer.fivecanale.util.EcomConstants.CANCEL;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;
import static com.customer.fivecanale.util.EcomConstants.TWO;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import com.R;
import com.customer.fivecanale.util.EcomUtil;
import java.util.Objects;

/*
 * Purpose – This class holds different types of reusable Dialogs
 * @author 3Embed
 * Created on Nov 25, 2019
 * Modified on
 */
public class CustomDialogUtil {
  /**
   * This method is using to show custom dialog
   *
   * @param activity      activity reference to show dialog
   * @param clickListener clickListener class subclass reference
   */
  public static void showDialog(int value, Activity activity, String msg,
      DialogCallBackListener clickListener) {
    final Dialog dialog = new Dialog(activity);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(false);
    dialog.setContentView(R.layout.dialog_logout);
    Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(
        new ColorDrawable(android.graphics.Color.TRANSPARENT));
    AppCompatTextView tvLogout = dialog.findViewById(R.id.tvLogout);
    AppCompatTextView tvLogoutMessage = dialog.findViewById(R.id.tvLogoutMessage);
    AppCompatTextView tvLogoutCancel = dialog.findViewById(R.id.tvLogoutCancel);
    if (value == ONE) {
      tvLogoutMessage.setText(activity.getResources().getString(R.string.deleteCardWarning));
      tvLogout.setText(activity.getResources().getString(R.string.delete));
    } else if (value == TWO) {
      tvLogoutMessage.setText(
          String.format("%s %s", activity.getResources().getString(R.string.addMoneyConfirmation),
              msg));
      tvLogout.setText(activity.getResources().getString(R.string.confirm));
    }
    tvLogoutCancel.setOnClickListener(v -> dialog.dismiss());
    tvLogout.setOnClickListener(v -> {
      clickListener.onLogOutClickListener();
      dialog.dismiss();
    });
    dialog.show();
  }

  /**
   * This method is using to show custom dialog
   *
   * @param activity                 activity reference to show dialog
   * @param outOfStockNotifyListener clickListener class subclass reference
   */
  public static void showOutOfStockNotifyDialog(Activity activity,
      DialogOutOfStockNotifyListener outOfStockNotifyListener) {
    final Dialog dialog = new Dialog(activity);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(TRUE);
    dialog.setContentView(R.layout.dialog_outofstock_notify);
    Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(
        new ColorDrawable(android.graphics.Color.TRANSPARENT));
    AppCompatTextView tvSubmit = dialog.findViewById(R.id.tvSubmit);
    AppCompatEditText etEMail = dialog.findViewById(R.id.etEMail);
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
                ? activity.getResources().getColor(R.color.colorProductFreeSpeechBlue)
                : activity.getResources().getColor(R.color.colorProductSeekBackground));
        tvSubmit.setEnabled(
            EcomUtil.isEmail(Objects.requireNonNull(etEMail.getText()).toString()));
      }
    });
    ivCross.setOnClickListener(view -> etEMail.setText(""));
    tvSubmit.setOnClickListener(view -> {
      if (EcomUtil.isEmail(Objects.requireNonNull(etEMail.getText()).toString())) {
        outOfStockNotifyListener.onNotifyMail(
            Objects.requireNonNull(etEMail.getText()).toString());
        dialog.dismiss();
      }
    });
    dialog.show();
  }

  /**
   * This method is using to show basic alert Dialog
   *
   * @param activity     activity reference to show dialog
   * @param clickHandler clickListener class subclass reference
   * @param title        Alert dialog title
   * @param message      Alert dialog message
   */
  public static void basicAlertDialog(Activity activity,
      SimpleAlertDialogClickHandler clickHandler, String title, String message, int type) {
    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
    builder.setTitle(title);
    builder.setMessage(message);
    builder.setPositiveButton(R.string.ok, (dialog, id) -> {
      clickHandler.onOkClickListener(type);
    });
    builder.setNegativeButton(R.string.allCancel,
        (dialog, id) -> {
          dialog.dismiss();
          clickHandler.onOkClickListener(CANCEL);
        });
    AlertDialog dialog = builder.create();
    dialog.show();
  }

  public interface DialogOutOfStockNotifyListener {
    void onNotifyMail(String mail);
  }

  public interface DialogCallBackListener {
    void onLogOutClickListener();
  }

  public interface SimpleAlertDialogClickHandler {
    /**
     * This method is using as ok button click callback
     */
    void onOkClickListener(int type);
  }
}
