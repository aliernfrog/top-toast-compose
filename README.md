# Top Toast
Simple toast library for Jetpack Compose

## [📁 JitPack](https://jitpack.io/#aliernfrog/top-toast-compose)

## 🍞 Example usage
```kotlin
val topToastManager = TopToastManager()
TopToastBase(background = MaterialTheme.colors.background, manager = topToastManager, content = {
    Button(
        content = { Text("Click me") },
        onClick = { topToastManager.showToast("This is a toast") }
    )
})
```
Check demo app for more examples
