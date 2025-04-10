package ai.hilal.liverates.presentation.ui.screens.home.components

import ai.hilal.liverates.presentation.ui.screens.home.CurrencyUiModel
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
    modifier: Modifier = Modifier,
    onRemoveClick: (CurrencyUiModel) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(models.size, key = { models[it].from }) { index ->
            val model = models[index]
            CurrencyItem(
                item = model,
                onRemove = { onRemoveClick.invoke(model) }
            )
        }
    }
}