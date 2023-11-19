package com.aliernfrog.toptoast.component

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = state.isShowing,
        modifier = modifier,
        enter = slideInVertically(initialOffsetY = { fullHeight -> -fullHeight }, animationSpec = tween(durationMillis = 500)) + fadeIn(animationSpec = tween(delayMillis = 250, durationMillis = 250)),
        exit = slideOutVertically(targetOffsetY = { fullHeight -> -fullHeight }, animationSpec = tween(durationMillis = 500)) + fadeOut(animationSpec = tween(durationMillis = 150))
    ) {
        if (state.allowSwipeToDismiss) SwipeToDismissBox(
            state = rememberDismissState(confirmValueChange = {
                val dismissed = it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd
                if (dismissed) state.dismissToast()
                true
            }),
            backgroundContent = {},
            content = {
                TopToast(state)
            }
        )
        else TopToast(state)
    }
}