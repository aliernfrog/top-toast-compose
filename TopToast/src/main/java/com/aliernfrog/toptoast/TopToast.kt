package com.aliernfrog.toptoast

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*
import kotlin.concurrent.schedule

class TopToastManager {
    var isShowing = mutableStateOf(false)
    var text = mutableStateOf("")
    var icon: Painter? = null
    var iconId: Int? = null
    var iconBackgroundColor: Color = Color.Transparent
    var onClick: (() -> Unit)? = null

    private val timer = Timer()
    private var task: TimerTask? = null

    fun showToast(
        textToShow: String,
        iconToShow: Painter? = null,
        iconIdToShow: Int? = null,
        iconBackground: Color = Color.Transparent,
        stayMs: Long = 3000,
        onToastClick: (() -> Unit)? = null
    ) {
        task?.cancel()
        timer.purge()
        text.value = textToShow
        icon = iconToShow
        iconId = iconIdToShow
        iconBackgroundColor = iconBackground
        onClick = onToastClick
        isShowing.value = true
        task = timer.schedule(stayMs) { isShowing.value = false }
    }
}

@Composable
fun TopToastBase(
    content: @Composable () -> Unit,
    backgroundColor: Color = Color.Transparent,
    manager: TopToastManager = TopToastManager()
) {
    Box(Modifier.fillMaxSize().background(backgroundColor)) {
        content()
        AnimatedVisibility(manager.isShowing.value, enter = slideInVertically(), exit = slideOutVertically()) {
            TopToast(manager)
        }
    }
}

@Composable
fun TopToast(manager: TopToastManager) {
    var modifier = Modifier.clip(RoundedCornerShape(50.dp)).background(MaterialTheme.colors.background)
    if (manager.onClick != null) modifier = modifier.clickable { manager.onClick?.invoke() }
    Column(Modifier.fillMaxWidth().padding(top = 24.dp).padding(horizontal = 24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier.border(1.dp, MaterialTheme.colors.secondary, RoundedCornerShape(50.dp)).padding(16.dp).animateContentSize()) {
            if (manager.icon != null || manager.iconId != null) Image(
                painter = if (manager.icon != null) manager.icon!! else painterResource(manager.iconId!!),
                contentDescription = manager.text.value,
                Modifier.padding(end = 8.dp).size(25.dp).clip(CircleShape).background(manager.iconBackgroundColor).padding(5.dp).align(Alignment.CenterVertically)
            )
            Text(
                manager.text.value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}