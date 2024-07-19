plugins {
    id("com.android.application") version "8.2.2" apply false
    id("com.android.library") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
}

val libraryVersionName by extra { "2.0.1" }
val libraryVersionCode by extra { 20110 }

val coreVersion by extra { "1.12.0" }
val composeCompilerVersion by extra { "1.5.8" }
val composeVersion by extra { "1.6.0" }
val material3Version by extra { "1.2.0" }