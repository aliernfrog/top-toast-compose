package com.aliernfrog.toptoast

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*
import kotlin.concurrent.schedule

object TopToastColorType {
    const val ERROR = 0
    const val PRIMARY = 1
}

/**
 * Manages TopToast
 */
class TopToastManager {
    /**
     * If any toast is being shown
     */
    var isShowing = mutableStateOf(false)

    /**
     * Current toast text
     */
    var text = mutableStateOf("")

    /**
     * Current painter of toast's icon
     */
    var icon: Painter? = null

    /**
     * Current drawable ID of toast's icon
     */
    var iconDrawableId: Int? = null

    /**
     * Current background color of toast
     */
    var iconBackgroundColor: Color = Color.Transparent

    /**
     * Current [color type][TopToastColorType] of toast
     */
    var iconBackgroundColorType: Int? = null

    /**
     * Current Unit to invoke on click
     */
    var onClick: (() -> Unit)? = null

    private val timer = Timer()
    private var task: TimerTask? = null

    /**
     * Shows a [TopToast]
     * @param text Text shown in toast
     * @param icon Painter of icon in toast
     * @param iconDrawableId Drawable ID of icon in toast
     * @param iconBackgroundColor Color shown behind icon in toast
     * @param iconBackgroundColorType [Color type][TopToastColorType] shown behind icon in toast
     * @param stayMs Duration of toast in milliseconds
     * @param onToastClick Unit to invoke on toast click
     */
    fun showToast(
        text: String,
        icon: Painter? = null,
        iconDrawableId: Int? = null,
        iconBackgroundColor: Color = Color.Transparent,
        iconBackgroundColorType: Int? = null,
        stayMs: Long = 3000,
        onToastClick: (() -> Unit)? = null
    ) {
        this.task?.cancel()
        this.timer.purge()
        this.text.value = text
        this.icon = icon
        this.iconDrawableId = iconDrawableId
        this.iconBackgroundColor = iconBackgroundColor
        this.iconBackgroundColorType = iconBackgroundColorType
        this.onClick = onToastClick
        this.isShowing.value = true
        this.task = timer.schedule(stayMs) { isShowing.value = false }
    }
}

/**
 * Where [TopToast]s will be shown
 * @param content Content shown behind toasts
 * @param backgroundColor Background color of this [TopToastBase]
 * @param manager [Manager ][TopToastManager] of this [TopToastBase]
 */
@Composable
fun TopToastBase(
    content: @Composable () -> Unit,
    backgroundColor: Color = Color.Transparent,
    manager: TopToastManager = TopToastManager()
) {
    Box(Modifier.fillMaxSize().background(backgroundColor)) {
        content()
        AnimatedVisibility(
            visible = manager.isShowing.value,
            enter = slideInVertically(initialOffsetY = { fullHeight -> -fullHeight }, animationSpec = tween(durationMillis = 500)) + fadeIn(animationSpec = tween(delayMillis = 270, durationMillis = 230)),
            exit = slideOutVertically(targetOffsetY = { fullHeight -> -fullHeight }, animationSpec = tween(durationMillis = 500)) + fadeOut(animationSpec = tween(durationMillis = 230))
        ) {
            TopToast(manager)
        }
    }
}

/**
 * Top toast
 * @param manager [Manager ][TopToastManager] of this [TopToast]
 */
@Composable
fun TopToast(manager: TopToastManager) {
    var modifier = Modifier.clip(RoundedCornerShape(50.dp)).background(MaterialTheme.colors.secondary)
    if (manager.onClick != null) modifier = modifier.clickable { manager.onClick?.invoke() }
    Column(Modifier.fillMaxWidth().padding(top = getStatusBarHeight()+8.dp).padding(horizontal = 24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier.border(1.dp, MaterialTheme.colors.background, RoundedCornerShape(50.dp)).padding(16.dp).animateContentSize()) {
            if (manager.icon != null || manager.iconDrawableId != null) Image(
                painter = if (manager.icon != null) manager.icon!! else painterResource(manager.iconDrawableId!!),
                contentDescription = manager.text.value,
                Modifier.padding(end = 8.dp).size(25.dp).clip(CircleShape).background(iconBackgroundColor(manager)).padding(5.dp).align(Alignment.CenterVertically)
            )
            Text(
                manager.text.value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
private fun iconBackgroundColor(manager: TopToastManager): Color {
    return when (manager.iconBackgroundColorType) {
        TopToastColorType.ERROR -> MaterialTheme.colors.error
        TopToastColorType.PRIMARY -> MaterialTheme.colors.primary
        else -> manager.iconBackgroundColor
    }
}

@Composable
private fun getStatusBarHeight(): Dp {
    return WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
}