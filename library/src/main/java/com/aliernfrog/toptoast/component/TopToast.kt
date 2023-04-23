package com.aliernfrog.toptoast.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aliernfrog.toptoast.state.TopToastState
import com.aliernfrog.toptoast.util.extensions.toastModifier

/**
 * Top toast
 * @param state [TopToastState] of this toast
 */
@Composable
fun TopToast(
    state: TopToastState? = null,
    text: String = state?.resolveText() ?: "",
    icon: Painter? = state?.resolveIcon(),
    iconTintColor: Color = state?.resolveIconTintColor() ?: MaterialTheme.colorScheme.primary,
    onClick: (() -> Unit)? = state?.onClick
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .systemBarsPadding()
            .padding(top = 8.dp, bottom = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .toastModifier(onClick)
        ) {
            if (icon != null) Icon(
                painter = icon,
                contentDescription = null,
                tint = iconTintColor,
                modifier = Modifier.padding(end = 8.dp).size(25.dp).padding(1.dp).align(Alignment.CenterVertically)
            )
            Text(
                text = text,
                lineHeight = 20.sp,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}