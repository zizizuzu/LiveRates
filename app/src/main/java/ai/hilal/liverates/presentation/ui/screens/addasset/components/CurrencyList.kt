package ai.hilal.liverates.presentation.ui.screens.addasset.components

import ai.hilal.liverates.presentation.ui.screens.addasset.CurrencyUiModel
import ai.hilal.liverates.presentation.ui.screens.addasset.FIAT_CURRENCY_TYPE
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CurrencyList(
    currencies: List<CurrencyUiModel>,
    onClick: (CurrencyUiModel) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(currencies.size) { index ->
            val currency = currencies[index]
            CurrencyItem(
                model = currency,
                onClick = onClick
            )
        }
    }
}