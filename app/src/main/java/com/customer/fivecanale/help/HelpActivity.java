package com.customer.fivecanale.help;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.R;
import com.customer.domain.model.help.HelpItemData;
import com.customer.fivecanale.util.EcomUtil;
import com.databinding.ActivityHelpBinding;
import dagger.android.support.DaggerAppCompatActivity;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * this show frequently asked questions.
 */
public class HelpActivity extends DaggerAppCompatActivity {
  @Inject
  ViewModelProvider.Factory mViewModelFactory;
  private ActivityHelpBinding mActivityHelpBinding;
  private HelpViewModel mHelpViewModel;
  private HelpAdapter mHelpAdapter;
  private ArrayList<HelpItemData> mHelpItemData = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mActivityHelpBinding = DataBindingUtil.setContentView(this,
        R.layout.activity_help);
    mHelpViewModel = ViewModelProviders.of(this, mViewModelFactory).get(
        HelpViewModel.class);
    mActivityHelpBinding.setViewmodel(mHelpViewModel);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorWhite));
    }
    ImageView imageView = mActivityHelpBinding.headerView.findViewById(R.id.backIv);
    imageView.setOnClickListener(view -> onBackPressed());
    subscribeHelpData();
    initialization();
  }

  /**
   * initialization for recyclerview and title
   */
  private void initialization() {
    TextView titleTv = mActivityHelpBinding.headerView.findViewById(R.id.tvTitle);
    String helpAndsupport = getResources().getString(R.string.faqs);
    titleTv.setText(helpAndsupport);
    mHelpAdapter = new HelpAdapter(mHelpItemData);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    mActivityHelpBinding.helpRv.setLayoutManager(layoutManager);
    mActivityHelpBinding.helpRv.setItemAnimator(new DefaultItemAnimator());
    mActivityHelpBinding.helpRv.setAdapter(mHelpAdapter);
    mHelpViewModel.callGetHelpApi();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    finish();
    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
  }

  /**
   * subscribe to help data
   */
  private void subscribeHelpData() {
    mHelpViewModel.getHelpData().observe(this, helpItemData -> setHelpList(helpItemData));
  }

  /**
   * set the help list
   *
   * @param arrayList list contains the help data.
   */
  private void setHelpList(ArrayList<HelpItemData> arrayList) {
    mActivityHelpBinding.ivEmpty.setVisibility(
        (EcomUtil.isEmptyArray(arrayList) ? View.VISIBLE : View.GONE));
    mActivityHelpBinding.tvEmptyFaqMsg.setVisibility(
        EcomUtil.isEmptyArray(arrayList) ? View.VISIBLE : View.GONE);
    mHelpItemData.addAll(arrayList);
    mHelpAdapter.notifyDataSetChanged();
  }
}
