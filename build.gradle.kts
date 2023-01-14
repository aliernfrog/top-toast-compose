plugins {
    id("com.android.application") version "7.4.0" apply false
    id("com.android.library") version "7.4.0" apply false
    id("org.jetbrains.kotlin.android") version "1.7.20" apply false
}

// Demo application removes all "."s from libraryVersionand converts it to a Int, then uses it as versionCode
val libraryVersion by extra { "1.0.0" }
val composeCompilerVersion by extra { "1.3.2" }
val composeVersion by extra { "1.4.0-alpha03" }
val material3Version by extra { "1.1.0-alpha04" }