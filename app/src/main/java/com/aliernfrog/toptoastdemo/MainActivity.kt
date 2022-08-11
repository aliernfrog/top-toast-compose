package com.aliernfrog.toptoastdemo

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.aliernfrog.toptoast.TopToastBase
import com.aliernfrog.toptoast.TopToastManager
import com.aliernfrog.toptoastdemo.ui.theme.TopToastComposeTheme

class MainActivity : ComponentActivity() {
    private lateinit var topToastManager: TopToastManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topToastManager = TopToastManager()
        setContent {
            TopToastComposeTheme {
                TopToastBase(backgroundColor = MaterialTheme.colors.background, manager = topToastManager, content = {
                    MainColumn()
                })
            }
        }
    }

    @Composable
    private fun MainColumn() {
        val context = LocalContext.current
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(100.dp))
            Button(content = { Text("Normal toast") }, onClick = { topToastManager.showToast("This is a normal toast") })
            Button(content = { Text("Clickable toast") }, onClick = {
                topToastManager.showToast("This will close the app when clicked", onToastClick = {
                    (context as Activity).finish()
                })
            })
        }
    }
}