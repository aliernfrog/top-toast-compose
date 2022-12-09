package com.aliernfrog.toptoast.component

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aliernfrog.toptoast.state.TopToastState

/**
 * [TopToast]s will be shown inside this component, above [content]
 * @param state: [TopToastState] of toasts inside this host
 * @param modifier: [Modifier] of this host
 * @param content: Content shown
 */
@Composable
fun TopToastHost(
    state: TopToastState,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(modifier) {
        content()
        AnimatedVisibility(
            visible = state.isShowing.value,
            enter = slideInVertically(initialOffsetY = { fullHeight -> -fullHeight }, animationSpec = tween(durationMillis = 500)) + fadeIn(animationSpec = tween(delayMillis = 250, durationMillis = 250)),
            exit = slideOutVertically(targetOffsetY = { fullHeight -> -fullHeight }, animationSpec = tween(durationMillis = 500)) + fadeOut(animationSpec = tween(durationMillis = 150))
        ) {
            TopToast(state)
        }
    }
}