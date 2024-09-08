plugins {
    id("com.android.application") version "8.5.2" apply false
    id("com.android.library") version "8.5.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.25" apply false
}

val libraryVersionName by extra { "2.1.0-alpha01" }
val libraryVersionCode by extra { 21001 }

val coreVersion by extra { "1.13.1" }
val composeCompilerVersion by extra { "1.5.15" }
val composeMaterialVersion by extra { "1.7.0" }
val composeMaterial3Version by extra { "1.3.0" }