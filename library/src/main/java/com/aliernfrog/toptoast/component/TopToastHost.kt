package com.aliernfrog.toptoast.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aliernfrog.toptoast.state.TopToastState

/**
 * [TopToast]s will be shown inside this component
 * @param state: [TopToastState] of toasts inside this host
 * @param modifier: [Modifier] of this host
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopToastHost(
    state: TopToastState,
    modifier: Modifier = Modifier,
    toast: @Composable (TopToastState) -> Unit = {
        TopToast(state = it)
    }
) {
    AnimatedVisibility(
        visible = state.isShowing,
        modifier = modifier,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 500)
        ) + fadeIn(
            animationSpec = tween(delayMillis = 200, durationMillis = 300)
        ) + scaleIn(
            animationSpec = tween(durationMillis = 500)
        ),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 500)
        ) + fadeOut(
            animationSpec = tween(durationMillis = 200)
        ) + scaleOut(
            animationSpec = tween(durationMillis = 500)
        )
    ) {
        Box(
            modifier = Modifier
                .systemBarsPadding()
                .fillMaxWidth()
                .padding(top = 8.dp, start = 24.dp, end = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            if (state.allowSwipeToDismiss) SwipeToDismissBox(
                state = rememberSwipeToDismissBoxState(
                    confirmValueChange = {
                        val dismissed =
                            it == SwipeToDismissBoxValue.StartToEnd || it == SwipeToDismissBoxValue.EndToStart
                        if (dismissed) state.dismissToast()
                        true
                    }
                ),
                backgroundContent = {},
                content = {
                    toast(state)
                }
            )
            else toast(state)
        }
    }
}