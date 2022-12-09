package com.aliernfrog.toptoast.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aliernfrog.toptoast.state.TopToastState
import com.aliernfrog.toptoast.util.extensions.toastModifier

/**
 * Top toast
 * @param state [TopToastState] of this toast
 */
@Composable
fun TopToast(state: TopToastState) {
    val iconPainter = state.getToastIconPainter()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = getStatusBarHeight()+8.dp)
            .padding(start = 24.dp, end = 24.dp, bottom = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(Modifier.toastModifier(state)) {
            if (iconPainter != null) Icon(
                painter = iconPainter,
                contentDescription = state.text.value,
                tint = state.iconTintColor.getColor(),
                modifier = Modifier.padding(end = 8.dp).size(25.dp).padding(1.dp).align(Alignment.CenterVertically)
            )
            Text(
                text = state.text.value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
private fun getStatusBarHeight(): Dp {
    return WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
}