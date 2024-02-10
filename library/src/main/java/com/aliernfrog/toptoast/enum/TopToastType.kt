package com.aliernfrog.toptoast.enum

import android.widget.Toast
import com.aliernfrog.toptoast.component.TopToastHost

/**
 * Types of toasts
 */
@Deprecated("This is no longer needed as showAndroidToast is now a separate method, will be removed in next releases.")
enum class TopToastType {

    /**
     * Uses [TopToastHost], can be dismissed by swiping and can respond to clicks. Some dialogs might appear above this.
     */
    INTERACTIVE,

    /**
     * Uses Android's [Toast] with custom view, can not be dismissed and can not respond to clicks. Shows up on top of all components but system ones.
     */
    ANDROID
}