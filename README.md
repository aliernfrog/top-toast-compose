# Top Toast
Simple toast library for Jetpack Compose

## [📁 JitPack](https://jitpack.io/#aliernfrog/top-toast-compose)
⚠️ 0.0.11 and higher use Material 3

## 🍞 Example usage
```kotlin
val topToastManager = TopToastManager()
TopToastBase(background = MaterialTheme.colorScheme.background, manager = topToastManager, content = {
    Button(
        content = { Text("Click me") },
        onClick = { topToastManager.showToast("This is a toast") }
    )
})
```
Check demo app for more examples
