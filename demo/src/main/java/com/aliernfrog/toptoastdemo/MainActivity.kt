package com.aliernfrog.toptoastdemo

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.aliernfrog.toptoast.component.TopToastHost
import com.aliernfrog.toptoast.enum.TopToastColor
import com.aliernfrog.toptoast.state.TopToastState
import com.aliernfrog.toptoastdemo.ui.theme.TopToastComposeTheme

class MainActivity : ComponentActivity() {
    private lateinit var topToastState: TopToastState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        topToastState = TopToastState()
        setContent {
            TopToastComposeTheme {
                MainColumn()
                TopToastHost(topToastState)
            }
        }
    }

    @Composable
    private fun MainColumn() {
        val context = LocalContext.current
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(Modifier.fillMaxWidth().height(getStatusBarHeight()).background(MaterialTheme.colorScheme.surfaceVariant))
            Spacer(Modifier.height(100.dp))
            Column(Modifier.width(IntrinsicSize.Max)) {
                DemoButton(label = "Default toast") {
                    topToastState.showToast(
                        text = """
                            Toasts hide after 5 seconds by default.
                            You can swipe to dismiss them manually
                        """.trimIndent()
                    )
                }
                DemoButton(label = "Custom timer toast") {
                    topToastState.showToast(
                        text = "This toast will hide after a minute, swipe to dismiss",
                        stayMs = 60000
                    )
                }
                DemoButton(label = "Success toast") {
                    topToastState.showToast(
                        text = "This can be shown when something succeeds",
                        icon = Icons.Rounded.Check,
                        iconTintColor = TopToastColor.PRIMARY
                    )
                }
                DemoButton(label = "Error toast") {
                    topToastState.showToast(
                        text = "This can be shown when something is wrong",
                        icon = Icons.Rounded.Close,
                        iconTintColor = TopToastColor.ERROR
                    )
                }
                DemoButton(label = "Clickable toast") {
                    topToastState.showToast(
                        text = "This will close the app when clicked",
                        onToastClick = {
                            (context as Activity).finish()
                        }
                    )
                }
            }
            Spacer(Modifier.height(100.dp))
        }
    }

    @Composable
    fun DemoButton(label: String, onClick: () -> Unit) {
        Button(
            content = { Text(label) },
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        )
    }

    @Composable
    fun getStatusBarHeight(): Dp {
        return WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    }
}