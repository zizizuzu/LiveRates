package ai.hilal.liverates.domain.usecase

import ai.hilal.liverates.domain.Response
import ai.hilal.liverates.domain.model.Currency
import ai.hilal.liverates.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetAllCurrenciesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(): Response<List<Currency>> {
        return repository.getAllAvailableCurrencies()
    }
}
