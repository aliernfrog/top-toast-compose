package com.aliernfrog.toptoast

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
     * Current tint color of toast icon
     */
    var iconTintColor: Color = Color.Transparent

    /**
     * Current [color type][TopToastColorType] of toast icon's tint
     */
    var iconTintColorType: Int? = null

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
     * @param iconTintColor Tint color of icon in toast
     * @param iconTintColorType Tint [color type][TopToastColorType] of icon in toast
     * @param stayMs Duration of toast in milliseconds
     * @param onToastClick Unit to invoke on toast click
     */
    fun showToast(
        text: String,
        icon: Painter? = null,
        iconDrawableId: Int? = null,
        iconTintColor: Color = Color.Transparent,
        iconTintColorType: Int? = null,
        stayMs: Long = 3000,
        onToastClick: (() -> Unit)? = null
    ) {
        this.task?.cancel()
        this.timer.purge()
        this.text.value = text
        this.icon = icon
        this.iconDrawableId = iconDrawableId
        this.iconTintColor = iconTintColor
        this.iconTintColorType = iconTintColorType
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
            enter = slideInVertically(initialOffsetY = { fullHeight -> -fullHeight }, animationSpec = tween(durationMillis = 500)) + fadeIn(animationSpec = tween(delayMillis = 250, durationMillis = 250)),
            exit = slideOutVertically(targetOffsetY = { fullHeight -> -fullHeight }, animationSpec = tween(durationMillis = 500)) + fadeOut(animationSpec = tween(durationMillis = 150))
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
    var modifier = Modifier.shadow(10.dp, RoundedCornerShape(30.dp)).clip(RoundedCornerShape(30.dp)).background(MaterialTheme.colors.secondary)
    if (manager.onClick != null) modifier = modifier.clickable { manager.onClick?.invoke() }
    Column(Modifier.fillMaxWidth().padding(top = getStatusBarHeight()+8.dp).padding(start = 24.dp, end = 24.dp, bottom = 10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier.padding(16.dp).animateContentSize()) {
            if (manager.icon != null || manager.iconDrawableId != null) Image(
                painter = if (manager.icon != null) manager.icon!! else painterResource(manager.iconDrawableId!!),
                contentDescription = manager.text.value,
                colorFilter = ColorFilter.tint(iconTintColor(manager)),
                modifier = Modifier.padding(end = 8.dp).size(25.dp).padding(3.dp).align(Alignment.CenterVertically)
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
private fun iconTintColor(manager: TopToastManager): Color {
    return when (manager.iconTintColorType) {
        TopToastColorType.ERROR -> MaterialTheme.colors.error
        TopToastColorType.PRIMARY -> MaterialTheme.colors.primary
        else -> manager.iconTintColor
    }
}

@Composable
private fun getStatusBarHeight(): Dp {
    return WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
}