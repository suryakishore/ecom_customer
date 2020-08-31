package com.customer.domain.repository;

import com.customer.domain.interactor.GetProductOnQrCodeUseCase;
import io.reactivex.Single;

public interface GetProductOnQrCodeRepository {

  Single<GetProductOnQrCodeUseCase.ResponseValues> getProductOnQrCode(String qrCode);
}
