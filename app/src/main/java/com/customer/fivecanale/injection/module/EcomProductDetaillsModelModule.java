package com.customer.fivecanale.injection.module;

import androidx.lifecycle.ViewModel;
import com.customer.fivecanale.injection.ViewModelFactory;
import com.customer.fivecanale.injection.ViewModelKey;
import com.customer.fivecanale.pdp.ProductDetailsViewModel;
import com.customer.fivecanale.pdp.attributebottomsheet.AttributesBottomSheetViewModel;
import com.customer.fivecanale.pdp.sellerbottomsheet.SellerBottomSheetViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class EcomProductDetaillsModelModule extends ViewModelFactory {
  @Binds
  @IntoMap
  @ViewModelKey(ProductDetailsViewModel.class)
  protected abstract ViewModel homeViewModel(ProductDetailsViewModel productDetailsViewModel);

  @Binds
  @IntoMap
  @ViewModelKey(AttributesBottomSheetViewModel.class)
  protected abstract ViewModel attributeBottomSheet(
      AttributesBottomSheetViewModel attributesBottomSheetViewModel);

  @Binds
  @IntoMap
  @ViewModelKey(SellerBottomSheetViewModel.class)
  protected abstract ViewModel sellerBottomSheet(
      SellerBottomSheetViewModel attributesBottomSheetViewModel);
}