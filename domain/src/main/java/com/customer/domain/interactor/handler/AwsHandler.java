package com.customer.domain.interactor.handler;

import io.reactivex.Observable;
import java.io.File;

public interface AwsHandler {

  Observable<String> uploadImageToAws(File file);
}
