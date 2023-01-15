package com.aliernfrog.toptoast.component

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aliernfrog.toptoast.state.TopToastState

/**
 * [TopToast]s will be shown inside this component
 * @param state: [TopToastState] of toasts inside this host
 * @param modifier: [Modifier] of this host
 */
@Composable
fun TopToastHost(
    state: TopToastState,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = state.isShowing.value,
        modifier = modifier,
        enter = slideInVertically(initialOffsetY = { fullHeight -> -fullHeight }, animationSpec = tween(durationMillis = 500)) + fadeIn(animationSpec = tween(delayMillis = 250, durationMillis = 250)),
        exit = slideOutVertically(targetOffsetY = { fullHeight -> -fullHeight }, animationSpec = tween(durationMillis = 500)) + fadeOut(animationSpec = tween(durationMillis = 150))
    ) {
        TopToast(state)
    }
}