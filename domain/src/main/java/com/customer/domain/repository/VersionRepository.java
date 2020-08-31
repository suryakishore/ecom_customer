package com.customer.domain.repository;

import com.customer.domain.interactor.VersionUseCase;
import io.reactivex.Single;

public interface VersionRepository {
  Single<VersionUseCase.ResponseValues> versionUpdate(int type, String version);
}
