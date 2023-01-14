# TopToast
Simple toast library for Jetpack Compose
- ⚠️ 0.0.11 and higher use Material 3
- 👀 You can try TopToast by downloading demo application from [releases](https://github.com/aliernfrog/top-toast-compose/releases)

## [📁 JitPack](https://jitpack.io/#aliernfrog/top-toast-compose)
[![](https://jitpack.io/v/aliernfrog/top-toast-compose.svg)](https://jitpack.io/#aliernfrog/top-toast-compose)

## 🍞 Example usage
```kotlin
val topToastState = remember { TopToastState() }
TopToastHost(
    modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.surface)
) {
    Button(
        content = { Text("Click me") },
        onClick = { topToastState.showToast("This is a toast") }
    )
}
```
Check demo application for more examples


## 📋 TODO
+ [ ] Find a way to show toasts above dialogs
