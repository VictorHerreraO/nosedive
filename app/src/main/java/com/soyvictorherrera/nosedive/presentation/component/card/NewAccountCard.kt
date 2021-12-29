package com.soyvictorherrera.nosedive.presentation.component.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.TrendingUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.domain.model.UserScoreModel
import com.soyvictorherrera.nosedive.presentation.theme.Forest_Green
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme

@Composable
fun NewAccountAlertCard(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                imageVector = Icons.Sharp.TrendingUp,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                colorFilter = ColorFilter.tint(color = Forest_Green)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.alert_new_account_title),
                    fontSize = 18.sp
                )
                Text(
                    text = stringResource(R.string.alert_new_account_desc, UserScoreModel.REQUIRED_COUNT),
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}

@Preview
@Composable
fun NewAccountAlertCardPreview() {
    NosediveTheme {
        NewAccountAlertCard()
    }
}
