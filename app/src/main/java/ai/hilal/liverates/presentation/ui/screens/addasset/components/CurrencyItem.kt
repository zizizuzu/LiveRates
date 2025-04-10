package ai.hilal.liverates.presentation.ui.screens.addasset.components

import ai.hilal.liverates.R
import ai.hilal.liverates.presentation.ui.screens.addasset.CurrencyUiModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CurrencyItem(
    model: CurrencyUiModel,
    onClick: (CurrencyUiModel) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(true, onClick = { onClick(model) }),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (model.isSelected)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = model.from,
                fontSize = 26.sp,
                maxLines = 1
            )

            Text(
                text = "to",
                fontSize = 22.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_us),
                        contentDescription = "USD",
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                    )
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = model.to,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}