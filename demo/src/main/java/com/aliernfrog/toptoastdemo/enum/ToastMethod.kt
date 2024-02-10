package com.aliernfrog.toptoastdemo.enum

import android.widget.Toast
import com.aliernfrog.toptoast.state.TopToastState

enum class ToastMethod(
    val label: String,
    val description: String,
    val defaultDuration: Int,
    val execute: (
        topToastState: TopToastState,
        text: Any,
        icon: Any?,
        iconTintColor: Any,
        duration: Int,
        swipeToDismiss: Boolean,
        dismissOnClick: Boolean,
        onClick: (() -> Unit)?
    ) -> Unit
) {
    COMPOSE(
        label = "showToast",
        description = "Shows a TopToast in the TopToastHost. "+
                "This cannot be shown above alert dialogs and bottom sheets due to limitations.",
        defaultDuration = 3,
        execute = { topToastState, text, icon, iconTintColor, duration, swipeToDismiss, dismissOnClick, onClick ->
            topToastState.showToast(
                text = text,
                icon = icon,
                duration = duration.toLong()*1000,
                iconTintColor = iconTintColor,
                swipeToDismiss = swipeToDismiss,
                dismissOnClick = if (onClick == null) null else dismissOnClick,
                onToastClick = onClick
            )
        }
    ),

    ANDROID(
        label = "showAndroidToast",
        description = "Shows a TopToast using the Android Toast API. "+
                "This can be shown above alert dialogs and bottom sheets, but it cannot be interacted with.",
        defaultDuration = Toast.LENGTH_LONG,
        execute = { topToastState, text, icon, iconTintColor, duration, _, _, _ ->
            topToastState.showAndroidToast(
                text = text,
                icon = icon,
                iconTintColor = iconTintColor,
                duration = duration
            )
        }
    )
}