package ai.hilal.liverates.domain.usecase

import ai.hilal.liverates.domain.repository.CurrencyRepository
import javax.inject.Inject

class AddCurrencyUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    operator fun invoke(currencyPair: Pair<String, String>) {
        repository.addRate(currencyPair)
    }
}