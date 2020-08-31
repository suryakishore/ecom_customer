package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.GetChatRepositoryImpl;
import com.customer.data.repository.PostMsgRepositoryImpl;
import com.customer.domain.interactor.GetCustomerChatUseCase;
import com.customer.domain.interactor.PostChatUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.GetCustomerChatRepository;
import com.customer.domain.repository.PostChatRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class PostMsgUseCaseModule {

  @Provides
  PostChatRepository provideRepository(PostMsgRepositoryImpl addAddressRepository) {
    return addAddressRepository;
  }

  @Provides
  public UseCase<PostChatUseCase.RequestValues, PostChatUseCase.ResponseValues> getLoginDetailsUseCase(
      PostChatUseCase addAddressUseCase) {
    return addAddressUseCase;
  }
}
