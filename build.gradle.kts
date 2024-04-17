plugins {
    id("com.android.application") version "8.3.2" apply false
    id("com.android.library") version "8.3.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.23" apply false
}

val libraryVersionName by extra { "2.0.0" }
val libraryVersionCode by extra { 200 }

val coreVersion by extra { "1.13.0" }
val composeCompilerVersion by extra { "1.5.12" }
val composeMaterialVersion by extra { "1.7.0-alpha07" }
val composeMaterial3Version by extra { "1.3.0-alpha05" }