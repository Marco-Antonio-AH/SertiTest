pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        id ("com.android.application")            version "8.0.2"
        id ("org.jetbrains.kotlin.android")      version "1.8.0"
        id ("org.jetbrains.kotlin.kapt")         version "1.8.0"
        id ("com.google.dagger.hilt.android")    version "2.44"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}


rootProject.name = "Serti"
include(":app")
