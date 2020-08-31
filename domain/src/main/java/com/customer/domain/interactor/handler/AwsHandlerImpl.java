package com.customer.domain.interactor.handler;

import com.customer.domain.repository.AwsRepository;
import io.reactivex.Observable;
import java.io.File;
import javax.inject.Inject;

public class AwsHandlerImpl implements AwsHandler {

  private AwsRepository mRepository;

  @Inject
  public AwsHandlerImpl(AwsRepository awsRepository) {
    this.mRepository = awsRepository;
  }

  public Observable<String> uploadImageToAws(File file) {
    return mRepository.uploadImageToAws(file);
  }
}
