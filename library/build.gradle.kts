plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

val libraryVersionName: String by rootProject.extra

val coreVersion: String by rootProject.extra
val composeCompilerVersion: String by rootProject.extra
val composeVersion: String by rootProject.extra
val material3Version: String by rootProject.extra

android {
    namespace = "com.aliernfrog.toptoast"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = composeCompilerVersion
    }
}

dependencies {
    implementation("androidx.core:core-ktx:$coreVersion")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material3:material3:$material3Version")
    implementation("androidx.savedstate:savedstate-ktx:1.2.1")
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "aliernfrog"
            artifactId = "top-toast-compose"
            version = libraryVersionName

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}