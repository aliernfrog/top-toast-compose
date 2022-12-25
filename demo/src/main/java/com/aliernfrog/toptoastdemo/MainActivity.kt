package com.aliernfrog.toptoastdemo

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
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
                TopToastHost(
                    state = topToastState,
                    modifier = Modifier.zIndex(10f).fillMaxSize().background(MaterialTheme.colorScheme.surface)
                ) {
                    MainColumn()
                }
            }
        }
    }

    @Composable
    private fun MainColumn() {
        val context = LocalContext.current
        var dialogShown by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(Modifier.fillMaxWidth().height(getStatusBarHeight()).background(Color.Black))
            Spacer(Modifier.height(100.dp))
            Column(Modifier.width(IntrinsicSize.Max)) {
                Button(content = { Text("Normal toast") }, onClick = { topToastState.showToast("This is a normal toast") }, modifier = Modifier.fillMaxWidth())
                Button(content = { Text("Toast with icon") }, onClick = {
                    topToastState.showToast("This is a toast with icon",
                        icon = R.drawable.check,
                        iconTintColor = TopToastColor.PRIMARY
                    )
                }, modifier = Modifier.fillMaxWidth())
                Button(content = { Text("Clickable toast") }, onClick = {
                    topToastState.showToast("This will close the app when clicked", onToastClick = {
                        (context as Activity).finish()
                    })
                }, modifier = Modifier.fillMaxWidth())
                Button(content = { Text("Show dialog") }, onClick = {
                    dialogShown = true
                    topToastState.showToast("Dialog shown")
                }, modifier = Modifier.fillMaxWidth())
            }
            Spacer(Modifier.height(100.dp))
        }
        if (dialogShown) AlertDialog(
            onDismissRequest = { dialogShown = false },
            confirmButton = {
                Button(onClick = { dialogShown = false }) {
                    Text("Close")
                }
            },
            title = { Text("Best dialog") },
            text = { Text("Real explanatory") }
        )
    }

    @Composable
    fun getStatusBarHeight(): Dp {
        return WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    }
}