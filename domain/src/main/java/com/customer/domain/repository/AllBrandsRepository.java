package com.customer.domain.repository;

import com.customer.domain.interactor.AllBrandsUseCase;
import com.customer.domain.interactor.WalletAmtUseCase;
import io.reactivex.Single;

public interface AllBrandsRepository {
  Single<AllBrandsUseCase.ResponseValues> allBrands(String from,String to);

}
