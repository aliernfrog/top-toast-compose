package com.aliernfrog.toptoast.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
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
     * Current tint [color][TopToastColor] of toast icon
     */
    var iconTintColor: TopToastColor = TopToastColor.PRIMARY

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
     * @param stayMs Duration of toast in milliseconds
     * @param onToastClick Unit to invoke on toast click
     */
    fun showToast(
        text: String,
        icon: Painter? = null,
        iconDrawableId: Int? = null,
        iconImageVector: ImageVector? = null,
        iconTintColor: TopToastColor = TopToastColor.PRIMARY,
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
        this.onClick = onToastClick
        this.isShowing.value = true
        this.task = timer.schedule(stayMs) { isShowing.value = false }
    }

    /**
     * Gets the [Painter] of toast icon by checking related values
     */
    @Composable
    fun getToastIconPainter(): Painter? {
        return if (this.icon != null) this.icon
        else if (this.iconDrawableId != null) painterResource(this.iconDrawableId!!)
        else if (this.iconImageVector != null) rememberVectorPainter(this.iconImageVector!!)
        else null
    }
}