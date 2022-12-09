package com.aliernfrog.toptoast.enum

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Some colors which can be used in toasts, will be parsed from MaterialTheme.colorScheme
 */
enum class TopToastColor {
    PRIMARY {
        @Composable override fun getColor(): Color = MaterialTheme.colorScheme.primary
    },

    SECONDARY {
        @Composable override fun getColor(): Color = MaterialTheme.colorScheme.secondary
    },

    TERTIARY {
        @Composable override fun getColor(): Color = MaterialTheme.colorScheme.tertiary
    },

    SURFACE_VARIANT {
        @Composable override fun getColor(): Color = MaterialTheme.colorScheme.surfaceVariant
    },

    SURFACE_TINT {
        @Composable override fun getColor(): Color = MaterialTheme.colorScheme.surfaceTint
    },

    ON_SURFACE {
        @Composable override fun getColor(): Color = MaterialTheme.colorScheme.onSurface
    },

    ERROR {
        @Composable override fun getColor(): Color = MaterialTheme.colorScheme.error
    };

    @Composable
    abstract fun getColor(): Color
}