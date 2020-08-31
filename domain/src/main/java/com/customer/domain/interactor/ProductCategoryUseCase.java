package com.customer.domain.interactor;

import android.text.TextUtils;
import com.customer.domain.executor.PostExecutionThread;
import com.customer.domain.executor.ThreadExecutor;
import com.customer.domain.model.productcategory.CategoryData;
import com.customer.domain.repository.ProductCategoryRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class ProductCategoryUseCase extends
    UseCase<ProductCategoryUseCase.RequestValues, ProductCategoryUseCase.ResponseValues> {

  private ProductCategoryRepository mRepository;

  @Inject
  public ProductCategoryUseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread,
      ProductCategoryRepository homePageRepository) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = homePageRepository;
  }

  @Override
  protected Single<ResponseValues> buildUseCaseObservable(RequestValues requestValues) {
    if (!TextUtils.isEmpty(requestValues.mCatId)) {
      return mRepository.getProductCategory(requestValues.mCatId,
          requestValues.mSubCatId);
    } else {
      return mRepository.getProductCategory(requestValues.from,
          requestValues.to);
    }
  }

  public static class RequestValues implements UseCase.RequestValues {
    private String mCatId;
    private String mSubCatId;
    private int from;
    private int to;

    public RequestValues(int from, int to) {
      this.from = from;
      this.to = to;
    }

    public RequestValues(String catId,int from, int to) {
      this.mCatId = catId;
    }

    public RequestValues(String catId, String subCatId,int from, int to) {
      this.mCatId = catId;
      this.mSubCatId = subCatId;
    }

  }

  public static class ResponseValues implements UseCase.ResponseValue {
    private CategoryData mData;

    public ResponseValues(CategoryData data) {
      this.mData = data;
    }

    public CategoryData getData() {
      return mData;
    }

    public void setData(CategoryData data) {
      this.mData = data;
    }
  }
}
