package com.customer.domain.repository;

import io.reactivex.Observable;
import java.io.File;

public interface AwsRepository {
  Observable<String> uploadImageToAws(File file);
}
