plugins {
    id("com.android.application") version "8.0.1" apply false
    id("com.android.library") version "8.0.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.21" apply false
}

val libraryVersionName by extra { "1.3.1" }
val libraryVersionCode by extra { 131 }

val composeCompilerVersion by extra { "1.4.7" }
val composeVersion by extra { "1.4.3" }
val material3Version by extra { "1.1.0" }