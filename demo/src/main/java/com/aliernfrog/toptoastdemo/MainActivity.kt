package com.aliernfrog.toptoastdemo

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.aliernfrog.toptoast.TopToastBase
import com.aliernfrog.toptoast.TopToastColorType
import com.aliernfrog.toptoast.TopToastManager
import com.aliernfrog.toptoastdemo.ui.theme.TopToastComposeTheme

class MainActivity : ComponentActivity() {
    private lateinit var topToastManager: TopToastManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        topToastManager = TopToastManager()
        setContent {
            TopToastComposeTheme {
                TopToastBase(backgroundColor = MaterialTheme.colorScheme.background, manager = topToastManager, content = {
                    MainColumn()
                })
            }
        }
    }

    @Composable
    private fun MainColumn() {
        val context = LocalContext.current
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(Modifier.fillMaxWidth().height(getStatusBarHeight()).background(Color.Black))
            Spacer(Modifier.height(100.dp))
            Column(Modifier.width(IntrinsicSize.Max)) {
                Button(content = { Text("Normal toast") }, onClick = { topToastManager.showToast("This is a normal toast") }, modifier = Modifier.fillMaxWidth())
                Button(content = { Text("Toast with icon") }, onClick = {
                    topToastManager.showToast("This is a toast with icon",
                        iconDrawableId = R.drawable.check,
                        iconTintColorType = TopToastColorType.PRIMARY
                    )
                }, modifier = Modifier.fillMaxWidth())
                Button(content = { Text("Clickable toast") }, onClick = {
                    topToastManager.showToast("This will close the app when clicked", onToastClick = {
                        (context as Activity).finish()
                    })
                }, modifier = Modifier.fillMaxWidth())
            }
            Spacer(Modifier.height(100.dp))
        }
    }

    @Composable
    fun getStatusBarHeight(): Dp {
        return WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    }
}