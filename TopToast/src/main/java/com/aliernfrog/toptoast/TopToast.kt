package com.aliernfrog.toptoast

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
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
     * Current [Painter] of toast icon
     */
    var icon: Painter? = null

    /**
     * Current drawable ID of toast icon
     */
    var iconDrawableId: Int? = null

    /**
     * Current [ImageVector] of toast icon
     */
    var iconImageVector: ImageVector? = null

    /**
     * Current tint color of toast icon
     */
    var iconTintColor: Color = Color.Transparent

    /**
     * Current tint [color type][TopToastColorType] of toast icon
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
     * @param icon [Painter] of icon in toast
     * @param iconDrawableId Drawable ID of icon in toast
     * @param iconImageVector [ImageVector] of icon in toast
     * @param iconTintColor Tint color of icon in toast
     * @param iconTintColorType Tint [color type][TopToastColorType] of icon in toast
     * @param stayMs Duration of toast in milliseconds
     * @param onToastClick Unit to invoke on toast click
     */
    fun showToast(
        text: String,
        icon: Painter? = null,
        iconDrawableId: Int? = null,
        iconImageVector: ImageVector? = null,
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
        this.iconImageVector = iconImageVector
        this.iconTintColor = iconTintColor
        this.iconTintColorType = iconTintColorType
        this.onClick = onToastClick
        this.isShowing.value = true
        this.task = timer.schedule(stayMs) { isShowing.value = false }
    }
}

/**
 * Where [TopToast]s will be shown
 * @param backgroundColor Background color of this [TopToastBase]
 * @param manager [TopToastManager] of this [TopToastBase]
 * @param content Content shown behind toasts
 */
@Composable
fun TopToastBase(
    backgroundColor: Color = Color.Transparent,
    manager: TopToastManager = TopToastManager(),
    content: @Composable () -> Unit
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
    var modifier = Modifier.shadow(10.dp, RoundedCornerShape(30.dp)).clip(RoundedCornerShape(30.dp)).background(MaterialTheme.colorScheme.surface)
    if (manager.onClick != null) modifier = modifier.clickable(interactionSource = remember { MutableInteractionSource() }, indication = rememberRipple(color = MaterialTheme.colorScheme.onBackground), onClick = manager.onClick!!)
    Column(Modifier.fillMaxWidth().padding(top = getStatusBarHeight()+8.dp).padding(start = 24.dp, end = 24.dp, bottom = 10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier.border(1.dp, MaterialTheme.colorScheme.onBackground.copy(0.1f), RoundedCornerShape(30.dp)).padding(16.dp).animateContentSize()) {
            if (manager.icon != null || manager.iconDrawableId != null || manager.iconImageVector != null) Icon(
                painter = if (manager.icon != null) manager.icon!! else if (manager.iconDrawableId != null) painterResource(manager.iconDrawableId!!) else rememberVectorPainter(manager.iconImageVector!!),
                contentDescription = manager.text.value,
                tint = iconTintColor(manager),
                modifier = Modifier.padding(end = 8.dp).size(25.dp).padding(3.dp).align(Alignment.CenterVertically)
            )
            Text(
                manager.text.value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
private fun iconTintColor(manager: TopToastManager): Color {
    return when (manager.iconTintColorType) {
        TopToastColorType.ERROR -> MaterialTheme.colorScheme.error
        TopToastColorType.PRIMARY -> MaterialTheme.colorScheme.primary
        else -> manager.iconTintColor
    }
}

@Composable
private fun getStatusBarHeight(): Dp {
    return WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
}