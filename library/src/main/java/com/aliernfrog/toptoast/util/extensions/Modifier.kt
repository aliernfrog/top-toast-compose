package com.aliernfrog.toptoast.util.extensions

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.aliernfrog.toptoast.component.TopToast
import com.aliernfrog.toptoast.util.toastShape

/**
 * Modifier for the [TopToast] component.
 */
fun Modifier.toastModifier(
    onClick: (() -> Unit)?
): Modifier = composed {
    var modifier = this
        .shadow(2.dp, toastShape).clip(toastShape)
        .background(MaterialTheme.colorScheme.surface)
    if (onClick != null) modifier = modifier.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(color = MaterialTheme.colorScheme.onBackground),
        onClick = onClick
    )
    modifier
        .border(
            width = 0.1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(0.15f),
            shape = toastShape
        )
        .padding(16.dp)
        .animateContentSize()
}