plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

val libraryVersionName: String by rootProject.extra
val libraryVersionCode: Int by rootProject.extra

val coreVersion: String by rootProject.extra
val composeCompilerVersion: String by rootProject.extra
val composeVersion: String by rootProject.extra
val material3Version: String by rootProject.extra

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

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:$coreVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.material3:material3:$material3Version")
    implementation(project(":library"))
}