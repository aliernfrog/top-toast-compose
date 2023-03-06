# TopToast
Simple toast library for Jetpack Compose
- ⚠️ 0.0.11 and higher use Material 3
- 👀 You can try TopToast by downloading demo application from [releases](https://github.com/aliernfrog/top-toast-compose/releases)

## [📁 JitPack](https://jitpack.io/#aliernfrog/top-toast-compose)
[![](https://jitpack.io/v/aliernfrog/top-toast-compose.svg)](https://jitpack.io/#aliernfrog/top-toast-compose)

## 🍞 Example usage
```kotlin
val topToastState = remember { TopToastState() }
Box(
    modifier = Modifier.background(MaterialTheme.colorScheme.surface)
) {
    Button(
        content = { Text("Click me") },
        onClick = { topToastState.showToast("This is a toast") }
    )
    TopToastHost(topToastState)
}
```
Check demo application for more examples
