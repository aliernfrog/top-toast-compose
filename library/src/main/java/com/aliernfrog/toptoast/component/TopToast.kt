package com.aliernfrog.toptoast.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aliernfrog.toptoast.state.TopToastState
import com.aliernfrog.toptoast.util.extensions.toastModifier

/**
 * Top toast
 * @param state [TopToastState] of this toast
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopToast(state: TopToastState) {
    val iconPainter = state.resolveIcon()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .systemBarsPadding()
            .padding(top = 8.dp)
            .padding(start = 24.dp, end = 24.dp, bottom = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SwipeToDismiss(
            state = rememberDismissState(confirmValueChange = {
                state.isShowing.value = false
                true
            }),
            background = {},
            dismissContent = {
                Row(Modifier.toastModifier(state)) {
                    if (iconPainter != null) Icon(
                        painter = iconPainter,
                        contentDescription = null,
                        tint = state.resolveIconTintColor(),
                        modifier = Modifier.padding(end = 8.dp).size(25.dp).padding(1.dp).align(Alignment.CenterVertically)
                    )
                    Text(
                        text = state.resolveText(),
                        lineHeight = 20.sp,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        )
    }
}