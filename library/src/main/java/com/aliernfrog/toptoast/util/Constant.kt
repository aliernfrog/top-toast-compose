package com.aliernfrog.toptoast.util

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

object TopToastDefaults {
    val shape = RoundedCornerShape(24.dp)

    val elevation = 4.dp

    @get:Composable
    val containerColor
        get() = MaterialTheme.colorScheme.surfaceContainerHighest
}

@Suppress("unused")
@Deprecated("Use TopToastDefaults.shape instead", ReplaceWith("TopToastDefaults.shape"))
val TopToastShape = TopToastDefaults.shape

@Suppress("unused")
@Deprecated("Use TopToastDefaults.elevation instead", ReplaceWith("TopToastDefaults.elevation"))
val TopToastElevation = TopToastDefaults.elevation