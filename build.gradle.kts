plugins {
    id("com.android.application") version "7.4.0" apply false
    id("com.android.library") version "7.4.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}

// Demo application removes all "."s from libraryVersion and converts it to a Int, then uses it as versionCode
val libraryVersion by extra { "1.1.1" }
val composeCompilerVersion by extra { "1.4.3" }
val composeVersion by extra { "1.4.0-beta02" }
val material3Version by extra { "1.1.0-alpha07" }