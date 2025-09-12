@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myapplication.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    verticalPadding: Dp = 15.dp,
    navigationIcon: @Composable (() -> Unit) = {},
    actionIcon: @Composable (() -> Unit) = {}
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(vertical = verticalPadding)
    ) {
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.BottomStart
        ) {
            navigationIcon.invoke()
        }

        Box(
            modifier = Modifier
                .weight(2f),
            contentAlignment = Alignment.TopCenter
        ) {
            title.invoke()
        }


        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.BottomEnd
        ) {
            actionIcon.invoke()
        }
    }
}