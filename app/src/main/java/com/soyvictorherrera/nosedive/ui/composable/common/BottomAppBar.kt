package com.soyvictorherrera.nosedive.ui.composable.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.People
import androidx.compose.material.icons.sharp.PersonAdd
import androidx.compose.material.icons.sharp.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.ui.theme.NosediveTheme

@Composable
fun BottomAppBar(
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 64.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colors.surface),
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            this.actions()
        }
    }
}

@Composable
fun BottomAppBarAction(
    icon: ImageVector,
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.size(64.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.onSurface)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Text(text = label, fontSize = 10.sp)
        }
    }
}

@Composable
fun DefaultBottomAppBar(
    onFriendsClick: () -> Unit,
    onRateClick: () -> Unit,
    onAddFriendClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(modifier = modifier) {
        BottomAppBarAction(
            icon = Icons.Sharp.People,
            label = stringResource(R.string.action_friends),
            onClick = onFriendsClick
        )

        Spacer(modifier = Modifier.weight(1f))

        BottomAppBarAction(
            icon = Icons.Sharp.Star,
            label = stringResource(R.string.action_rate),
            onClick = onRateClick
        )

        Spacer(modifier = Modifier.weight(1f))

        BottomAppBarAction(
            icon = Icons.Sharp.PersonAdd,
            label = stringResource(R.string.action_add_friend),
            onClick = onAddFriendClick
        )
    }
}

@Preview
@Composable
fun BottomAppBarPreview() {
    NosediveTheme {
        DefaultBottomAppBar(
            onFriendsClick = {},
            onRateClick = {},
            onAddFriendClick = {}
        )
    }
}