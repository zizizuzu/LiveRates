package ai.hilal.liverates.domain.di

import ai.hilal.liverates.domain.repository.CurrencyRepository
import ai.hilal.liverates.domain.usecase.GetSelectedRatesUseCase
import ai.hilal.liverates.domain.usecase.RemoveCurrencyUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideGetSelectedCurrenciesUseCase(
        repository: CurrencyRepository
    ): GetSelectedRatesUseCase = GetSelectedRatesUseCase(repository)

    @Provides
    fun provideRemoveCurrencyUseCase(
        repository: CurrencyRepository
    ): RemoveCurrencyUseCase = RemoveCurrencyUseCase(repository)
}