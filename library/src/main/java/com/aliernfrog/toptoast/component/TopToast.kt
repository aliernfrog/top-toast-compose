package com.aliernfrog.toptoast.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aliernfrog.toptoast.state.TopToastState
import com.aliernfrog.toptoast.util.TopToastDefaults

/**
 * Top toast
 * @param state [TopToastState] of this toast
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TopToast(
    modifier: Modifier = Modifier,
    state: TopToastState? = null,
    text: String = state?.resolveText() ?: "",
    icon: Painter? = state?.resolveIcon(),
    iconTintColor: Color = state?.resolveIconTintColor() ?: MaterialTheme.colorScheme.primary,
    containerColor: Color = TopToastDefaults.containerColor,
    textColor: Color = containerColor.let {
        if (it == TopToastDefaults.containerColor) MaterialTheme.colorScheme.onSurface
        else MaterialTheme.colorScheme.contentColorFor(it)
    },
    onClick: (() -> Unit)? = state?.onClick
) {
    Row(
        modifier = modifier
            .padding(TopToastDefaults.elevation+1.4.dp) // avoid shadow getting cropped
            .shadow(
                elevation = TopToastDefaults.elevation,
                shape = TopToastDefaults.shape
            )
            .clip(TopToastDefaults.shape)
            .background(containerColor)
            .animateContentSize()
            .run { onClick?.let {
                clickable(
                    interactionSource = null,
                    indication = ripple(color = MaterialTheme.colorScheme.onBackground),
                    onClick = it
                )
            } ?: this }
            .padding(
                horizontal = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = iconTintColor,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(26.dp)
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.labelLargeEmphasized.copy(
                lineHeight = 18.sp
            ),
            color = textColor,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(vertical = 12.dp)
        )
    }
}