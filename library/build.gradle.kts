import java.net.URI
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    `maven-publish`
}

val libraryVersionName: String by rootProject.extra

android {
    namespace = "com.aliernfrog.toptoast"
    compileSdk = 36
    buildToolsVersion = "36.1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    buildFeatures {
        compose = true
    }

    publishing {
        singleVariant("release")
    }
}

dependencies {
    implementation(libs.androidx.ktx)
    implementation(libs.androidx.savedstate.ktx)

    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                groupId = "aliernfrog"
                artifactId = "top-toast-compose"
                version = libraryVersionName
                from(components["release"])
            }
        }

        val githubPackagesURL = System.getenv("GITHUB_PACKAGES_URL")

        if (
            !System.getenv("GITHUB_TOKEN").isNullOrEmpty()
            && !githubPackagesURL.isNullOrEmpty()
        ) repositories {
            maven {
                name = "GitHubPackages"
                url = URI(githubPackagesURL)
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
}