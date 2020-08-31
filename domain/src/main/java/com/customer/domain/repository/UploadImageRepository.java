package com.customer.domain.repository;

import com.customer.domain.interactor.GetProfileDetailsUseCase;
import com.customer.domain.interactor.UploadImageUseCase;
import io.reactivex.Single;
import java.io.File;

public interface UploadImageRepository {

  Single<UploadImageUseCase.ResponseValues> uploadImage(String folder, File file,String fileName);
}
