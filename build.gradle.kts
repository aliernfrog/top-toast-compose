plugins {
    id("com.android.application") version "7.4.0" apply false
    id("com.android.library") version "7.4.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}

val libraryVersionName by extra { "1.2.0" }
val libraryVersionCode by extra { 120 }

val composeCompilerVersion by extra { "1.4.3" }
val composeVersion by extra { "1.4.0-beta02" }
val material3Version by extra { "1.1.0-alpha07" }