package com.soyvictorherrera.nosedive.presentation.component.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.soyvictorherrera.nosedive.presentation.theme.Alto
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme

@Composable
fun ProminentTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colors.primarySurface,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = AppBarDefaults.TopAppBarElevation
) {
    Column {
        TopAppBar(
            title = {},
            modifier = modifier,
            navigationIcon = navigationIcon,
            actions = actions,
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            elevation = elevation
        )
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .height(72.dp)
                .fillMaxWidth()
                .background(backgroundColor)
        ) {
            Spacer(modifier = Modifier.width(72.dp))
            title.invoke()
        }
    }
}

@Composable
fun DefaultProminentTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
) {
    ProminentTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.h5.copy(color = Alto),
                modifier = Modifier.paddingFromBaseline(bottom = 28.dp)
            )
        },
        modifier = modifier,
        navigationIcon = navigationIcon,
        elevation = 0.dp
    )
}

@Preview
@Composable
fun ProminentTopAppBarPreview() {
    NosediveTheme {
        ProminentTopAppBar(
            title = {
                Text(
                    text = "¡Hola Mundo!",
                    style = MaterialTheme.typography.h6.copy(color = Alto),
                    modifier = Modifier.paddingFromBaseline(bottom = 28.dp)
                )
            },
            navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Sharp.ArrowBack, contentDescription = null)
                }
            },
            elevation = 0.dp
        )
    }
}
