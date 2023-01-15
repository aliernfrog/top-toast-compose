package com.aliernfrog.toptoast.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.aliernfrog.toptoast.component.TopToast
import com.aliernfrog.toptoast.enum.TopToastColor
import java.util.*
import kotlin.concurrent.schedule

/**
 * State of TopToasts
 */
class TopToastState {
    /**
     * If any toast is being shown
     */
    var isShowing = mutableStateOf(false)

    /**
     * Current toast text
     */
    var text = mutableStateOf<Any>("")

    /**
     * Current toast icon
     */
    var icon = mutableStateOf<Any?>(null)

    /**
     * Current tint [color][TopToastColor] of toast icon
     */
    var iconTintColor: Any = TopToastColor.PRIMARY

    /**
     * Current Unit to invoke on click
     */
    var onClick: (() -> Unit)? = null

    private val timer = Timer()
    private var task: TimerTask? = null

    /**
     * Shows a [TopToast]
     * @param text Text shown in toast, can be a [String] or [Int] representing a string constant
     * @param icon [Painter] of icon in toast, can be a [Painter], [ImageVector] or [Int] representing a drawable constant
     * @param iconTintColor Tint color of icon in toast, can be a [Color] or [TopToastColor]
     * @param stayMs Duration of toast in milliseconds
     * @param onToastClick Unit to invoke on toast click
     */
    fun showToast(
        text: Any,
        icon: Any? = null,
        iconTintColor: Any = TopToastColor.PRIMARY,
        stayMs: Long = 3000,
        onToastClick: (() -> Unit)? = null
    ) {
        this.task?.cancel()
        this.timer.purge()
        this.text.value = text
        this.icon.value = icon
        this.iconTintColor = iconTintColor
        this.onClick = onToastClick
        this.isShowing.value = true
        this.task = timer.schedule(stayMs) { isShowing.value = false }
    }

    /**
     * Resolves text of toast
     */
    @Composable
    fun resolveText(): String {
        return when (val text = this.text.value) {
            is String -> text
            is Int -> stringResource(text)
            else -> throw IllegalArgumentException()
        }
    }

    /**
     * Resolves icon of toast
     */
    @Composable
    fun resolveIcon(): Painter? {
        val icon = this.icon.value ?: return null
        return when (icon) {
            is Painter -> icon
            is ImageVector -> rememberVectorPainter(icon)
            is Int -> painterResource(icon)
            else -> throw IllegalArgumentException()
        }
    }

    /**
     * Resolves icon tint color of toast
     */
    @Composable
    fun resolveIconTintColor(): Color {
        return when (val color = this.iconTintColor) {
            is Color -> color
            is TopToastColor -> color.getColor()
            else -> throw IllegalArgumentException()
        }
    }
}