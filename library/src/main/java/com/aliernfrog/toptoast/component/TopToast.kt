package com.aliernfrog.toptoast.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aliernfrog.toptoast.state.TopToastState
import com.aliernfrog.toptoast.util.TopToastElevation
import com.aliernfrog.toptoast.util.TopToastShape

/**
 * Top toast
 * @param state [TopToastState] of this toast
 */
@Composable
fun TopToast(
    modifier: Modifier = Modifier,
    state: TopToastState? = null,
    text: String = state?.resolveText() ?: "",
    icon: Painter? = state?.resolveIcon(),
    iconTintColor: Color = state?.resolveIconTintColor() ?: MaterialTheme.colorScheme.primary,
    onClick: (() -> Unit)? = state?.onClick
) {
    Row(
        modifier = modifier
            .padding(TopToastElevation+1.4.dp) // avoid shadow getting cropped
            .shadow(TopToastElevation, TopToastShape)
            .clip(TopToastShape)
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
            .animateContentSize()
            .run { onClick?.let {
                clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(color = MaterialTheme.colorScheme.onBackground),
                    onClick = it
                )
            } ?: this }
            .padding(
                horizontal = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let { Icon(
            painter = icon,
            contentDescription = null,
            tint = iconTintColor,
            modifier = Modifier
                .padding(end = 8.dp)
                .size(26.dp)
        ) }
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge.copy(
                lineHeight = 18.sp
            ),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(vertical = 12.dp)
        )
    }
}