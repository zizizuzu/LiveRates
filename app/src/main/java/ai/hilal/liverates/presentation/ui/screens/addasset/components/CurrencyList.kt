package ai.hilal.liverates.presentation.ui.screens.addasset.components

import ai.hilal.liverates.presentation.ui.screens.addasset.CurrencyUiModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CurrencyList(
    models: List<CurrencyUiModel>,
    onClick: (CurrencyUiModel) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(models.size, {models[it].from}) { index ->
            val currency = models[index]
            CurrencyItem(
                model = currency,
                onClick = onClick
            )
        }
    }
}