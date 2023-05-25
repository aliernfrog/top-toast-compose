package com.aliernfrog.toptoast.state

import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.findViewTreeSavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.aliernfrog.toptoast.component.TopToast
import com.aliernfrog.toptoast.enum.TopToastColor
import com.aliernfrog.toptoast.enum.TopToastType
import java.util.*
import kotlin.concurrent.schedule

/**
 * State of TopToasts
 * @param composeView Compose view used for [TopToastType.ANDROID] toasts, can also be set later using [TopToastState.setComposeView]
 * @param defaultType [TopToastType] to use when not specified.
 */
class TopToastState(
    private var composeView: View?,
    private val defaultType: TopToastType = TopToastType.INTERACTIVE
) {
    /**
     * Whether any interactive toast is being shown
     */
    var isShowing by mutableStateOf(false)

    /**
     * Current interactive toast text
     */
    var text by mutableStateOf<Any>("")

    /**
     * Current interactive toast icon
     */
    var icon by mutableStateOf<Any?>(null)

    /**
     * Current tint [color][TopToastColor] of interactive toast icon
     */
    var iconTintColor: Any = TopToastColor.PRIMARY

    /**
     * Current Unit to invoke on interactive toast click
     */
    var onClick: (() -> Unit)? = null

    private val timer = Timer()
    private var task: TimerTask? = null

    /**
     * Shows a [TopToast]
     * @param text Text shown in toast, can be a [String] or [Int] representing a string constant
     * @param icon [Painter] of icon in toast, can be a [Painter], [ImageVector] or [Int] representing a drawable constant
     * @param iconTintColor Tint color of icon in toast, can be a [Color] or [TopToastColor]
     * @param stayMs Duration of toast in milliseconds, only for [TopToastType.INTERACTIVE]
     * @param type [TopToastType] of toast, defaults to [defaultType]
     * @param dismissOnClick Whether to dismiss the toast on click, only for [TopToastType.INTERACTIVE]
     * @param onToastClick Unit to invoke on toast click, only for [TopToastType.INTERACTIVE]
     */
    @Suppress("DEPRECATION")
    fun showToast(
        text: Any,
        icon: Any? = null,
        iconTintColor: Any = TopToastColor.PRIMARY,
        stayMs: Long = 3000,
        type: TopToastType = defaultType,
        dismissOnClick: Boolean? = null,
        onToastClick: (() -> Unit)? = null
    ) {
        this.task?.cancel()
        this.timer.purge()
        when (type) {
            TopToastType.INTERACTIVE -> {
                this.text = text
                this.icon = icon
                this.iconTintColor = iconTintColor
                this.onClick = buildToastClickUnit(
                    dismissOnClick = dismissOnClick,
                    onClick = onToastClick,
                    onToastDismissRequest = {
                        dismissToast()
                    }
                )
                this.isShowing = true
                this.task = timer.schedule(stayMs) { dismissToast() }
            }
            TopToastType.ANDROID -> {
                val toastView = composeView
                if (toastView == null) {
                    Log.w("TopToast", "composeView is required for ANDROID type toasts, but it is null")
                } else {
                    dismissToast()
                    val topToastView = ComposeView(toastView.context)
                    topToastView.setContent {
                        TopToast(
                            text = resolveText(text),
                            icon = resolveIcon(icon),
                            iconTintColor = resolveIconTintColor(iconTintColor)
                        )
                    }
                    topToastView.setViewTreeLifecycleOwner(toastView.findViewTreeLifecycleOwner())
                    topToastView.setViewTreeSavedStateRegistryOwner(toastView.findViewTreeSavedStateRegistryOwner())
                    val toast = Toast(toastView.context)
                    toast.setGravity(Gravity.TOP, 0, 0)
                    toast.duration = Toast.LENGTH_LONG
                    toast.view = topToastView
                    toast.show()
                }
            }
        }
    }

    /**
     * Dismisses [TopToastType.INTERACTIVE] toast
     */
    fun dismissToast() {
        isShowing = false
    }

    /**
     * Sets composeView
     */
    fun setComposeView(composeView: View) {
        this.composeView = composeView
    }

    /**
     * Resolves text of toast
     */
    @Composable
    fun resolveText(toResolve: Any = this.text): String {
        return when (toResolve) {
            is String -> toResolve
            is Int -> stringResource(toResolve)
            else -> throw IllegalArgumentException()
        }
    }

    /**
     * Resolves icon of toast
     */
    @Composable
    fun resolveIcon(toResolve: Any? = this.icon): Painter? {
        if (toResolve == null) return null
        return when (toResolve) {
            is Painter -> toResolve
            is ImageVector -> rememberVectorPainter(toResolve)
            is Int -> painterResource(toResolve)
            else -> throw IllegalArgumentException()
        }
    }

    /**
     * Resolves icon tint color of toast
     */
    @Composable
    fun resolveIconTintColor(toResolve: Any = this.iconTintColor): Color {
        return when (toResolve) {
            is Color -> toResolve
            is TopToastColor -> toResolve.getColor()
            else -> throw IllegalArgumentException()
        }
    }
}

private fun buildToastClickUnit(
    dismissOnClick: Boolean?,
    onClick: (() -> Unit)?,
    onToastDismissRequest: () -> Unit
): (() -> Unit)? {
    val shouldDismissOnClick = if (onClick == null) (dismissOnClick ?: false) else (dismissOnClick ?: true)
    if (!shouldDismissOnClick && onClick == null) return null
    return {
        if (shouldDismissOnClick) onToastDismissRequest()
        onClick?.invoke()
    }
}