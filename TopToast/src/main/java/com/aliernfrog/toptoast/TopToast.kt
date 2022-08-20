package com.aliernfrog.toptoast

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
    var iconId: Int? = null

    /**
     * Current background color of toast
     */
    var iconBackground: Color = Color.Transparent

    /**
     * Current [color type][TopToastColorType] of toast
     */
    var iconBackgroundType: Int? = null

    /**
     * Current Unit to invoke on click
     */
    var onClick: (() -> Unit)? = null

    private val timer = Timer()
    private var task: TimerTask? = null

    /**
     * Shows a [TopToast]
     * @param textString Text shown in toast
     * @param iconPainter Painter of icon in toast
     * @param iconDrawableId Drawable ID of icon in toast
     * @param iconBackgroundColor Color shown behind icon in toast
     * @param iconBackgroundColorType [Color type][TopToastColorType] shown behind icon in toast
     * @param stayMs Duration of toast in milliseconds
     * @param onToastClick Unit to invoke on toast click
     */
    fun showToast(
        textString: String,
        iconPainter: Painter? = null,
        iconDrawableId: Int? = null,
        iconBackgroundColor: Color = Color.Transparent,
        iconBackgroundColorType: Int? = null,
        stayMs: Long = 3000,
        onToastClick: (() -> Unit)? = null
    ) {
        task?.cancel()
        timer.purge()
        text.value = textString
        icon = iconPainter
        iconId = iconDrawableId
        iconBackground = iconBackgroundColor
        iconBackgroundType = iconBackgroundColorType
        onClick = onToastClick
        isShowing.value = true
        task = timer.schedule(stayMs) { isShowing.value = false }
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
        AnimatedVisibility(manager.isShowing.value, enter = slideInVertically(initialOffsetY = { fullHeight -> -fullHeight }), exit = slideOutVertically(targetOffsetY = { fullHeight -> -fullHeight })) {
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
    Column(Modifier.fillMaxWidth().padding(top = 24.dp).padding(horizontal = 24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier.border(1.dp, MaterialTheme.colors.background, RoundedCornerShape(50.dp)).padding(16.dp).animateContentSize()) {
            if (manager.icon != null || manager.iconId != null) Image(
                painter = if (manager.icon != null) manager.icon!! else painterResource(manager.iconId!!),
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
    return when (manager.iconBackgroundType) {
        TopToastColorType.ERROR -> MaterialTheme.colors.error
        TopToastColorType.PRIMARY -> MaterialTheme.colors.primary
        else -> manager.iconBackground
    }
}