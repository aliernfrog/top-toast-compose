# Top Toast
Simple toast library for Jetpack Compose

## [📁 JitPack](https://jitpack.io/#aliernfrog/top-toast-compose)
⚠️ 0.0.11 and higher use Material 3

## 🍞 Example usage
```kotlin
val topToastState = remember { TopToastState() }
TopToastBase(
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
Check demo app for more examples


## TODO
+ [ ] Find a way to show toasts above dialogs