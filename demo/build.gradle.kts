plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

val libraryVersionName: String by rootProject.extra
val libraryVersionCode: Int by rootProject.extra

val coreVersion: String by rootProject.extra
val composeCompilerVersion: String by rootProject.extra
val composeMaterialVersion: String by rootProject.extra
val composeMaterial3Version: String by rootProject.extra

android {
    namespace = "com.aliernfrog.toptoastdemo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.aliernfrog.toptoastdemo"
        minSdk = 21
        targetSdk = 34
        versionCode = libraryVersionCode
        versionName = libraryVersionName
        vectorDrawables { useSupportLibrary = true }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
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
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = composeCompilerVersion
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:$coreVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.5")
    implementation("androidx.activity:activity-compose:1.9.2")
    implementation("androidx.compose.ui:ui:$composeMaterialVersion")
    implementation("androidx.compose.material:material:$composeMaterialVersion")
    implementation("androidx.compose.material3:material3:$composeMaterial3Version")
    implementation(project(":library"))
}