package com.customer.fivecanale.webview;

import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.TERMS;
import static com.customer.fivecanale.util.EcomConstants.TERMS_PRIVACY_API;
import static com.customer.fivecanale.util.EcomConstants.TITLE;
import static com.customer.fivecanale.util.EcomConstants.URL;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.R;
import com.databinding.WebviewBinding;
import dagger.android.support.DaggerAppCompatActivity;
import javax.inject.Inject;

/**
 * Created by dell on 13-Apr-18.
 */
public class WebViewAct extends DaggerAppCompatActivity {
  boolean isloading = false;
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  private android.webkit.WebView webView;
  private ProgressBar progress;
  private WebviewBinding mWebviewBinding;
  private WebViewModel mWebViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.webview);
    initializeView();
    initializeViewModel();
    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
    String url = "", title = "";
    Intent intent = getIntent();
    if (intent != null) {
      if (intent.hasExtra(URL)) {
        url = intent.getStringExtra(URL);
      }
      if (intent.hasExtra(TITLE)) {
        title = intent.getStringExtra(TITLE);
      }
    }
    ImageView backIv = findViewById(R.id.backIv);
    backIv.setVisibility(View.VISIBLE);
    backIv.setOnClickListener(view -> onBackPressed());
    TextView titleTv = findViewById(R.id.tvTitle);
    titleTv.setText(title);
    webView = (android.webkit.WebView) findViewById(R.id.webView);
    if (intent.getBooleanExtra(TERMS_PRIVACY_API, FALSE)) {
      webView.setVisibility(View.GONE);
      mWebviewBinding.svWebView.setVisibility(View.VISIBLE);
      mWebViewModel.isTerms = intent.getBooleanExtra(TERMS, FALSE);
      mWebViewModel.callWebPageData();
    } else {
      webView.setSaveFromParentEnabled(true);
      webView.getSettings().setJavaScriptEnabled(true);
      webView.getSettings().setDomStorageEnabled(true);
      webView.getSettings().setPluginState(WebSettings.PluginState.ON);
      webView.setWebViewClient(new MyWebViewClient());
      webView.getSettings().setAllowFileAccess(true);
      webView.setWebChromeClient(new WebChromeClient() {
      });
      if (Build.VERSION.SDK_INT >= 21) {
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
      }
      progress = (ProgressBar) findViewById(R.id.progressBar);
      progress.setVisibility(View.GONE);
      webView.loadUrl(url);
    }
  }

  /**
   * Initialising the View using Data Binding.
   */
  private void initializeView() {
    mWebviewBinding = DataBindingUtil.setContentView(this,
        R.layout.webview);
  }

  /**
   * <p>This method is used initialize the viewModel class for this activity.</p>
   */
  private void initializeViewModel() {
    mWebViewModel = ViewModelProviders.of(this, mViewModelFactory).get(
        WebViewModel.class);
    mWebviewBinding.setViewmodel(mWebViewModel);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    try {
      if (webView != null) {
        webView.destroy();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onBackPressed() {
    finish();
    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
  }

  private class MyWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
      if (url.contains(".pdf") && !isloading && !url.contains("docs.google.com")) {
        url = "http://docs.google.com/gview?embedded=true&url=" + url;
        isloading = true;
      }
      view.loadUrl(url);
      return true;
    }

    @Override
    public void onPageFinished(android.webkit.WebView view, String url) {
      progress.setVisibility(View.GONE);
      WebViewAct.this.progress.setProgress(100);
      super.onPageFinished(view, url);
    }

    @Override
    public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
      progress.setVisibility(View.VISIBLE);
      WebViewAct.this.progress.setProgress(0);
      super.onPageStarted(view, url, favicon);
    }
  }
}
