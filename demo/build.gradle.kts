plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

val libraryVersionName: String by rootProject.extra
val libraryVersionCode: Int by rootProject.extra
val composeCompilerVersion: String by rootProject.extra
val composeVersion: String by rootProject.extra
val material3Version: String by rootProject.extra

android {
    namespace = "com.aliernfrog.toptoastdemo"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.aliernfrog.toptoastdemo"
        minSdk = 21
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
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
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material3:material3:$material3Version")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.1")
    implementation(project(":library"))
}