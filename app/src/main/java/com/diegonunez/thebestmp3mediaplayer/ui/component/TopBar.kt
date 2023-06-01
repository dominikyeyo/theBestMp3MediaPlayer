package com.diegonunez.thebestmp3mediaplayer.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    leadingIcon: @Composable () -> Unit,
    title: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null
) {

    Box(modifier = modifier.fillMaxWidth()) {

        Box(modifier = Modifier
            .align(alignment = Alignment.CenterStart)
            .padding(16.dp)

        ) {
            leadingIcon()
        }

        Box(modifier = Modifier.align(alignment = Alignment.Center)
            .wrapContentSize()
        ) {
            title?.invoke()
        }

        Box(modifier = Modifier
            .align(alignment = Alignment.CenterEnd)
            .padding(16.dp)

        ) {
            if (trailingIcon == null) {
                Spacer(modifier = Modifier.requiredWidth(width = 26.dp))
            } else {
                trailingIcon()
            }
        }

    }

}