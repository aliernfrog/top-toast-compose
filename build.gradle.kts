plugins {
    id("com.android.application") version "8.1.4" apply false
    id("com.android.library") version "8.1.4" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20" apply false
}

val libraryVersionName by extra { "1.3.5-alpha01" }
val libraryVersionCode by extra { 135 }

val coreVersion by extra { "1.12.0" }
val composeCompilerVersion by extra { "1.5.4" }
val composeVersion by extra { "1.6.0-beta01" }
val material3Version by extra { "1.2.0-alpha11" }