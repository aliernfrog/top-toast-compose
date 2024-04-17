package com.aliernfrog.toptoastdemo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.aliernfrog.toptoast.component.TopToast
import com.aliernfrog.toptoast.component.TopToastHost
import com.aliernfrog.toptoast.enum.TopToastColor
import com.aliernfrog.toptoast.state.TopToastState
import com.aliernfrog.toptoastdemo.enum.ToastMethod
import com.aliernfrog.toptoastdemo.ui.component.SegmentedButtons
import com.aliernfrog.toptoastdemo.ui.component.form.FormSection
import com.aliernfrog.toptoastdemo.ui.theme.TopToastComposeTheme

class MainActivity : ComponentActivity() {
    private lateinit var topToastState: TopToastState
    private var swipeToDismiss: Boolean
        get() = topToastState.allowSwipeToDismiss
        set(value) { topToastState.allowSwipeToDismiss = value }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        topToastState = TopToastState(
            composeView = null,
            appTheme = {
                TopToastComposeTheme(content = it)
            }
        )
        topToastState.setComposeView(window.decorView)
        setContent {
            TopToastComposeTheme {
                AppContent()
                TopToastHost(topToastState)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun AppContent() {
        val uriHandler = LocalUriHandler.current
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        var dialogShown by remember { mutableStateOf(false) }

        var selectedToastMethod by rememberSaveable {
            mutableStateOf(ToastMethod.COMPOSE)
        }
        var toastText by rememberSaveable {
            mutableStateOf("Hello world!")
        }
        var toastDuration by rememberSaveable {
            mutableStateOf<Int?>(null)
        }
        var toastIcon by rememberSaveable {
            mutableStateOf<ImageVector?>(null)
        }
        var showDialogAfterToast by rememberSaveable {
            mutableStateOf(false)
        }
        var toastIconTintColor by rememberSaveable {
            mutableStateOf(TopToastColor.PRIMARY)
        }
        var onToastClick by rememberSaveable {
            mutableStateOf<Pair<String, (() -> Unit)?>>("Unclickable" to null)
        }
        var dismissToastOnClick by rememberSaveable {
            mutableStateOf(true)
        }

        LaunchedEffect(selectedToastMethod) {
            toastDuration = selectedToastMethod.defaultDuration
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.app_name)) },
                    scrollBehavior = scrollBehavior,
                    actions = {
                        var actionsShown by remember { mutableStateOf(false) }
                        Box {
                            IconButton(
                                onClick = { actionsShown = true }
                            ) {
                                Icon(Icons.Default.MoreVert, contentDescription = "More actions")
                            }
                            DropdownMenu(
                                expanded = actionsShown,
                                onDismissRequest = { actionsShown = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Show dialog") },
                                    onClick = {
                                        dialogShown = true
                                        actionsShown = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Show Android toast") },
                                    onClick = {
                                        Toast.makeText(applicationContext, toastText, Toast.LENGTH_SHORT).show()
                                        actionsShown = false
                                    }
                                )
                            }
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        selectedToastMethod.execute(
                            topToastState,
                            toastText,
                            toastIcon,
                            toastIconTintColor,
                            toastDuration ?: selectedToastMethod.defaultDuration,
                            swipeToDismiss,
                            dismissToastOnClick,
                            onToastClick.second
                        )
                        if (showDialogAfterToast) dialogShown = true
                    },
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Default.Check, contentDescription = null)
                }
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { paddingValues ->
            Column(
                Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .navigationBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FormSection(title = "Method") {
                    SegmentedButtons(
                        options = ToastMethod.entries.map { it.label },
                        selectedIndex = selectedToastMethod.ordinal,
                        onSelect = { selectedToastMethod = ToastMethod.entries[it] },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                    Text(
                        text = selectedToastMethod.description,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                FormSection(title = "General") {
                    OutlinedTextField(
                        value = toastText,
                        onValueChange = { toastText = it },
                        label = {
                            Text("Text")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )

                    AnimatedContent(
                        targetState = selectedToastMethod == ToastMethod.COMPOSE,
                        modifier = Modifier
                            .animateContentSize()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) { isSeconds ->
                        if (isSeconds) OutlinedTextField(
                            value = (toastDuration ?: "").toString(),
                            onValueChange = { toastDuration = it.toIntOrNull() },
                            label = {
                                Text("Duration (seconds)")
                            },
                            placeholder = {
                                Text(selectedToastMethod.defaultDuration.toString())
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                autoCorrectEnabled = null,
                                keyboardType = KeyboardType.Number
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) else SegmentedButtons(
                            options = listOf("Toast.LENGTH_SHORT", "Toast.LENGTH_LONG"),
                            selectedIndex = toastDuration ?: selectedToastMethod.defaultDuration,
                            onSelect = { toastDuration = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Crossfade(selectedToastMethod == ToastMethod.COMPOSE) { enabled ->
                        CheckBoxRow(
                            label = "Swipe to dismiss"+if (!enabled) "\nNot supported by Android Toast API" else "",
                            checked = enabled && swipeToDismiss,
                            onCheckedChange = { swipeToDismiss = it },
                            enabled = enabled
                        )
                    }

                    CheckBoxRow(
                        label = "Show dialog after showing toast",
                        checked = showDialogAfterToast,
                        onCheckedChange = { showDialogAfterToast = it }
                    )
                }

                FormSection(title = "Icon") {
                    Row(
                        modifier = Modifier
                            .height(IntrinsicSize.Max)
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.aligned(Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        listOf(
                            null,
                            Icons.Rounded.Check,
                            Icons.Rounded.Close,
                            Icons.Rounded.Info
                        ).forEach { icon ->
                            val onSelect = { toastIcon = icon }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.aligned(Alignment.Bottom),
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(50.dp)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = ripple(bounded = false),
                                        onClick = onSelect
                                    )
                            ) {
                                icon?.let {
                                    Icon(
                                        imageVector = it,
                                        contentDescription = null,
                                        tint = toastIconTintColor.getColor()
                                    )
                                } ?: Text("None")
                                RadioButton(
                                    selected = toastIcon == icon,
                                    onClick = onSelect
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .height(IntrinsicSize.Max)
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.aligned(Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        TopToastColor.entries.forEach { topToastColor ->
                            val color = topToastColor.getColor()
                            val onSelect = { toastIconTintColor = topToastColor }
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = ripple(bounded = false),
                                        onClick = onSelect
                                    )
                                    .size(45.dp)
                                    .padding(4.dp)
                                    .clip(CircleShape)
                                    .background(color),
                                contentAlignment = Alignment.Center
                            ) {
                                if (toastIconTintColor == topToastColor) Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = MaterialTheme.colorScheme.contentColorFor(color)
                                )
                            }
                        }
                    }
                }

                FormSection(title = "On click") {
                    AnimatedContent(targetState = selectedToastMethod == ToastMethod.COMPOSE) { available ->
                        if (!available) Text(
                            text = "Not supported by Android Toast API",
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        ) else Column {
                            listOf(
                                "Unclickable" to null,
                                "Do nothing" to {},
                                "Show dialog" to { dialogShown = true },
                                "Close app" to { finish() }
                            ).forEach { option ->
                                val onSelect = { onToastClick = option }
                                val selected = option.first == onToastClick.first
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable(onClick = onSelect)
                                        .padding(horizontal = 16.dp, vertical = 4.dp)
                                ) {
                                    RadioButton(
                                        selected = selected,
                                        onClick = onSelect,
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                    Text(text = option.first)
                                }
                            }
                            Crossfade(onToastClick.second != null) { enabled ->
                                CheckBoxRow(
                                    label = "Dismiss toast on click"+
                                            if (!enabled) "\nToast is unclickable" else "",
                                    checked = enabled && dismissToastOnClick,
                                    onCheckedChange = { dismissToastOnClick = it },
                                    enabled = enabled
                                )
                            }
                        }
                    }
                }

                FormSection(
                    title = "Info",
                    bottomDivider = false
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TopToast(
                            text = "top-toast-compose - v${BuildConfig.VERSION_NAME}",
                            icon = rememberVectorPainter(Icons.Rounded.Info),
                            iconTintColor = MaterialTheme.colorScheme.secondary,
                            onClick = {
                                uriHandler.openUri("https://github.com/aliernfrog/top-toast-compose")
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(60.dp))
                }
            }
        }


        if (dialogShown) AlertDialog(
            onDismissRequest = { dialogShown = false },
            title = { Text("TopToast") },
            text = {
                Text("Toasts shown using Android Toast API can show on top of bottom sheets and alert dialogs such as this")
            },
            confirmButton = {
                TextButton(onClick = { dialogShown = false }) {
                    Text("Dismiss")
                }
            }
        )
    }

    @Composable
    private fun CheckBoxRow(
        label: String,
        checked: Boolean,
        enabled: Boolean = true,
        onCheckedChange: (Boolean) -> Unit
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .run {
                    if (enabled) clickable { onCheckedChange(!checked) } else this
                }
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = label,
                modifier = Modifier.weight(1f).fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface.copy(
                    alpha = if (enabled) 1f else 0.5f
                )
            )
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange,
                enabled = enabled
            )
        }
    }
}