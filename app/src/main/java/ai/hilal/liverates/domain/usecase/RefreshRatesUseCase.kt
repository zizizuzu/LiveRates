package ai.hilal.liverates.domain.usecase

import ai.hilal.liverates.domain.repository.CurrencyRepository
import javax.inject.Inject

class RefreshRatesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    operator fun invoke() {
        return repository.refreshRates()
    }
}