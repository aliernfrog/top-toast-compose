plugins {
    id("com.android.application") version "8.1.0" apply false
    id("com.android.library") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}

val libraryVersionName by extra { "1.3.3" }
val libraryVersionCode by extra { 133 }

val composeCompilerVersion by extra { "1.5.1" }
val composeVersion by extra { "1.6.0-alpha02" }
val material3Version by extra { "1.2.0-alpha04" }