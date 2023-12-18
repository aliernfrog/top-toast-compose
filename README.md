# TopToast
[![GitHub Packages](https://img.shields.io/github/v/tag/aliernfrog/top-toast-compose?label=GitHub)](https://github.com/aliernfrog/top-toast-compose/packages) 
[![JitPack](https://jitpack.io/v/aliernfrog/top-toast-compose.svg)](https://jitpack.io/#aliernfrog/top-toast-compose)

Toast library for Jetpack Compose

## 👀 Try it out
You can try TopToast by downloading demo application from [releases](https://github.com/aliernfrog/top-toast-compose/releases)

## 📥 Installation
You can get the library from GitHub Packages or JitPack
### GitHub Packages
- Create a GitHub PAT with `read:packages` scope
- Put the PAT and your GitHub username in global/project `gradle.properties`:
  ```
  gpr.user=MyUserName
  gpr.key=MyPAT
  ```
  or supply `GITHUB_ACTOR` (username) and `GITHUB_TOKEN` (PAT) in environment variables
- Add maven repository: (Kotlin)
  ```kts
  maven(url = "https://maven.pkg.github.com/aliernfrog/top-toast-compose") {
      credentials {
        username = providers.gradleProperty("gpr.user").orNull ?: System.getenv("GITHUB_ACTOR")
        password = providers.gradleProperty("gpr.key").orNull ?: System.getenv("GITHUB_TOKEN")
      }
  }
  ```
- Add dependency: (Kotlin)
  ```kts
  implementation("aliernfrog:top-toast-compose:<VERSION>")
  ```

### JitPack
> [!WARNING]
> As JitPack now fails to build with no reason, latest versions available on JitPack are `1.3.4` and `1.3.5-alpha01`.
> It may or may not be updated in the future.
- Add maven repository: (Kotlin)
  ```kts
  maven(url = "https://jitpack.io")
  ```
- Add dependency: (Kotlin)
  ```kts
  implementation("com.github.aliernfrog:top-toast-compose:<VERSION>")
  ```

## 🍞 Example usage
```kotlin
val topToastState = remember {
    TopToastState(
        // Required for Android type toasts, you can set it to null and set it later using `setComposeView()`
        composeView = window.decorView,
        // Used in Android type toasts, you can set it to null and set it later using `setAppTheme()`
        appTheme = { toastContent ->
            MyAppTheme(toastContent)
        }
    )
}

Box(
    modifier = Modifier.background(MaterialTheme.colorScheme.surface)
) {
    Column {
        Button(
            content = {
                Text("Click me")
            },
            onClick = {
                // Shows an interactive toast in TopToastHost
                topToastState.showToast("This is a toast")
            }
        )
        Button(
            content = {
                Text("Android toast")
            },
            onClick = {
                // Shows a toast using Android Toast APIs, visible on top of modals and dialogs
                // Cannot be interacted with
                topToastState.showToast("This is a toast")
            }
        )
    }
    TopToastHost(topToastState)
}
```
Check demo application for more examples
