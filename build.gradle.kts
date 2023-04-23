plugins {
    id("com.android.application") version "7.4.2" apply false
    id("com.android.library") version "7.4.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.20" apply false
}

val libraryVersionName by extra { "1.2.0" }
val libraryVersionCode by extra { 120 }

val composeCompilerVersion by extra { "1.4.6" }
val composeVersion by extra { "1.4.2" }
val material3Version by extra { "1.1.0-rc01" }