package com.customer.fivecanale.util;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.text.HtmlCompat;
import com.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

/*binds the mData for events like focus change ,click errors etc */
public class BindingAdapter {
  @androidx.databinding.BindingAdapter(value = {"clearOnFocusAndDispatch",
      "hasFocus"}, requireAll = false)
  public static void setOnLayoutChangeListener(EditText view,
      final View.OnFocusChangeListener listener, final View.OnFocusChangeListener focusListener) {
    view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
          if (listener != null) {
            listener.onFocusChange(v, hasFocus);
          }
        } else {
          if (focusListener != null) {
            focusListener.onFocusChange(v, hasFocus);
          }
        }
      }
    });
  }

  @androidx.databinding.BindingAdapter("android:visibility")
  public static void setVisibility(View view, Boolean value) {
    if (view != null && value != null) {
      view.setVisibility(value ? View.VISIBLE : View.GONE);
    }
  }

  @androidx.databinding.BindingAdapter("android:vis")
  public static void setVis(View view, Boolean value) {
    if (view != null && value != null) {
      view.setVisibility(value ? View.VISIBLE : View.GONE);
    }
  }

  @androidx.databinding.BindingAdapter("android:visible")
  public static void setVisible(View view, Boolean value) {
    if (view != null && value != null) {
      view.setVisibility(value ? View.VISIBLE : View.GONE);
    }
  }

  @androidx.databinding.BindingAdapter("android:visibilityProductDetail")
  public static void setHistoryProdcuDetailVisible(View view, Boolean value) {
    if (view != null && value != null) {
      view.setVisibility(value ? View.VISIBLE : View.GONE);
    }
  }
  @androidx.databinding.BindingAdapter("android:visibilityHome")
  public static void setHomeScreenVisible(View view, Boolean value) {
    if (view != null && value != null) {
      view.setVisibility(value ? View.VISIBLE : View.GONE);
    }
  }

  /*@androidx.databinding.BindingAdapter("android:enable")
  public static void setEnabled(View view, Boolean value) {
    if (view != null && value != null) {
      view.setEnabled(value);
    }
  }*/

  @androidx.databinding.BindingAdapter({"android:src"})
  public static void setImageViewResource(ImageView imageView, int resource) {
    imageView.setImageResource(resource);
  }

  @androidx.databinding.BindingAdapter({"android:background"})
  public static void setBackgroundColor(View view, Boolean value) {
    view.setBackgroundColor(value ? view.getContext().getResources().getColor(R.color.app_color)
        : view.getContext().getResources().getColor(R.color.colorSilverChalice));
  }

  @androidx.databinding.BindingAdapter({"app:srcCompat"})
  public static void loadImage(ImageView view, String imageUrl) {
    if (imageUrl != null && !"".equals(imageUrl)) {
      RequestOptions options = new RequestOptions()
          .centerCrop()
          .placeholder(view.getContext().getResources().getDrawable(R.drawable.logo))
          .error(view.getContext().getResources().getDrawable(R.drawable.logo))
          .priority(Priority.HIGH);
      Glide.with(view.getContext())
          .load(imageUrl.trim())
          .apply(options)
          .into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable drawable,
                @Nullable Transition<? super Drawable>
                    transition) {
              view.setImageDrawable(drawable);
            }
          });
    }
  }

  @androidx.databinding.BindingAdapter("android:ErrorMessage")
  public static void serErrorMessage(TextInputLayout view, Boolean value) {
    if (view != null && value != null) {
      if (value) {
        view.setErrorEnabled(true);
        switch (view.getId()) {
          case R.id.tilSaveAddressName:
            view.setError(
                view.getContext().getResources().getString(R.string.signUpEnterValidName));
            break;
          case R.id.tilSaveAddressLine1:
            view.setError(view.getContext().getResources().getString(R.string.validAddressError));
            break;
          case R.id.tilSaveAddressArea:
            view.setError(view.getContext().getResources().getString(R.string.validAreaNameError));
            break;
          case R.id.tilSaveAddressCity:
            view.setError(view.getContext().getResources().getString(R.string.validCityNameError));
            break;
          case R.id.tilSaveAddressCountry:
            view.setError(
                view.getContext().getResources().getString(R.string.validCountryNameError));
            break;
          case R.id.tilSaveAddressPostalCode:
            view.setError(
                view.getContext().getResources().getString(R.string.validPostalCodeError));
            break;
        }
        if (view.getId() == R.id.tilProfileDetailsName) {
          view.setError(view.getContext().getResources().getString(R.string.signUpEnterValidName));
        } else if (view.getId() == R.id.tilProfileDetailsEMail) {
          view.setError(view.getContext().getResources().getString(R.string.enterValidEmail));
        }
        if (view.getId() == R.id.tiLoginEmail || view.getId() == R.id.tiSignUpEmail
            || view.getId() == R.id.etForgotPassEmail) {
          view.setError(view.getContext().getResources().getString(R.string.enterValidEmail));
        } else if (view.getId() == R.id.tiSignUpName) {
          view.setError(view.getContext().getResources().getString(R.string.signUpEnterValidName));
        } else if (view.getId() == R.id.tiSignUpPhoneNumber
            || view.getId() == R.id.tiLoginPhoneNumber) {
          view.setError(view.getContext().getResources().getString(R.string.enterValidPhoneNumber));
        } else if (view.getId() == R.id.tiSignUpPassword || view.getId() == R.id.tiSignUpConPassword
            || view.getId() == R.id.tiForgotPassNewPass
            || view.getId() == R.id.tiForgotPassRepeatNewPass
            || view.getId() == R.id.tiForgotPassCurrentPass) {
          view.setError(view.getContext().getResources().getString(R.string.enterValidPassword));
        }
      } else {
        if (view.getError() != null
            && !view.getError().toString().isEmpty()) {
          view.setErrorEnabled(false);
          view.setError(null);
        }
      }
    }
  }

  @androidx.databinding.BindingAdapter("android:ErrorExistMessage")
  public static void serErrorExitFieldsMessage(TextInputLayout view, Boolean value) {
    if (view != null) {
      if (value) {
        view.setErrorEnabled(true);
        if (view.getId() == R.id.tiSignUpEmail) {
          view.setError(view.getContext().getResources().getString(R.string.signUpEmailExist));
        } else if (view.getId() == R.id.tiSignUpPhoneNumber) {
          view.setError(view.getContext().getResources().getString(R.string.signUpPhoneNumExist));
        } else if (view.getId() == R.id.tiSignUpPassword) {
          view.setError(view.getContext().getResources().getString(R.string.invalidPassLength));
        } else if (view.getId() == R.id.tiSignUpConPassword) {
          view.setError(view.getContext().getResources().getString(R.string.passNotMatch));
        }
      } else {
        if (view.getError() != null
            && !view.getError().toString().isEmpty()) {
          view.setErrorEnabled(false);
          view.setError(null);
        }
      }
    }
  }

  @androidx.databinding.BindingAdapter("android:btnEnabled")
  public static void setEnable(View view, Boolean value) {
    if (view != null && value != null) {
      view.setEnabled(value);
    }
  }

  @androidx.databinding.BindingAdapter("android:floatingTint")
  public static void setBackGroundTintFloating(FloatingActionButton view, Boolean value) {
    if (view != null) {
      view.setBackgroundTintList(
          (value) ? ColorStateList.valueOf(
              view.getResources().getColor(R.color.allEastBayColor))
              : ColorStateList.valueOf(
                  view.getResources().getColor(R.color.cadetBlue)));
    }
  }

  @androidx.databinding.BindingAdapter("android:ErrorMsg")
  public static void setOnError(View view, String value) {
    if (view != null && value != null && !value.isEmpty()) {
      Snackbar.make(view, value, Snackbar.LENGTH_SHORT).show();
    }
  }

  @androidx.databinding.BindingAdapter("android:RadioButton")
  public static void checkUncheckRadioButton(AppCompatRadioButton view, Boolean value) {
    if (view != null && value != null) {
      view.setChecked(value);
    }
  }

  @androidx.databinding.BindingAdapter("android:checkBox")
  public static void checkOrUncheckBox(AppCompatCheckBox view, Boolean value) {
    if (view != null && value != null) {
      view.setChecked(value);
    }
  }

  @androidx.databinding.BindingAdapter("android:htmlText")
  public static void setHtmlText(AppCompatTextView view,String value) {
    if (view != null&&value!=null) {
      view.setText(HtmlCompat.fromHtml(value, 0));
    }
  }
}
