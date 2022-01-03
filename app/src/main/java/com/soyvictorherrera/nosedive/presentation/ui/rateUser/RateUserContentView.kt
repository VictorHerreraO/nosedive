package com.soyvictorherrera.nosedive.presentation.ui.rateUser

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ExpandLess
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.presentation.component.common.NoTitleTopAppBar
import com.soyvictorherrera.nosedive.presentation.component.profile.UserPhoto
import com.soyvictorherrera.nosedive.presentation.component.rate.RatingBar
import com.soyvictorherrera.nosedive.presentation.theme.Grey
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme
import com.soyvictorherrera.nosedive.presentation.theme.Wild_Watermelon

@Composable
fun RateUserContentView(
    modifier: Modifier = Modifier
) = Scaffold(modifier = modifier,
    topBar = {
        NoTitleTopAppBar(onNavigateBack = { /*TODO*/ })
    },
    content = {
        RateUserContent(
            modifier = Modifier.padding(start = 32.dp, end = 32.dp, bottom = 64.dp)
        )
    }
)

@Composable
fun RateUserContent(
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    UserPhoto(
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        modifier = Modifier.size(162.dp)
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = "¿Cómo calificas a Jessica?",
        fontSize = 24.sp
    )

    Spacer(modifier = Modifier.weight(1f))

    Icon(
        imageVector = Icons.Sharp.ExpandLess,
        tint = Grey,
        contentDescription = "",
        modifier = Modifier.size(24.dp)
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = "Selecciona y desliza hacia arriba",
        fontSize = 12.sp,
        color = Grey
    )

    Spacer(modifier = Modifier.height(16.dp))

    RatingBar(
        rating = 2,
        modifier = Modifier
            .height(64.dp)
            .fillMaxWidth()
    )

}

@Preview
@Composable
fun RateUserContentViewPreview() {
    NosediveTheme {
        RateUserContentView()
    }
}
