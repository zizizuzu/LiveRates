package ai.hilal.liverates.domain.usecase

import ai.hilal.liverates.domain.model.CurrencyRate
import ai.hilal.liverates.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetSelectedRatesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    operator fun invoke(): StateFlow<List<CurrencyRate>> {
        return repository.ratesFlow
    }
}