package com.customer.fivecanale.landing.searchscreen;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.R;
import dagger.android.support.DaggerAppCompatActivity;

/*
 * Purpose – This Activity is using to launch the SearchFragment in full page.
 * @author 3Embed
 * Created on DEC 16, 2019
 * Modified on
 */
public class SearchActivity extends DaggerAppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_launcher);
  }
}
