import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("org.jetbrains.kotlin.kapt")
    id ("com.google.dagger.hilt.android")
}

android {
    namespace =    "com.example.serti"
    compileSdk=     34

    defaultConfig {
        applicationId = "com.example.serti"
        minSdk      =  21
        targetSdk   =  34
        versionCode =  1
        versionName =  "1.0"
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

val roomVersion     = "2.5.1"
val retrofitVersion = "2.9.0"
val coroutinesVer   = "1.7.3"

dependencies {
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.8.0")
    implementation ("androidx.core:core-ktx:1.11.0")
    implementation ("androidx.appcompat:appcompat:1.7.0")
    implementation ("com.google.android.material:material:1.9.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")

    // Hilt
    implementation ("com.google.dagger:hilt-android:2.44")
    kapt           ("com.google.dagger:hilt-android-compiler:2.44")

    // Retrofit + Gson
    implementation ("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // Room
    implementation ("androidx.room:room-runtime:$roomVersion")
    implementation ("androidx.room:room-ktx:$roomVersion")
    kapt           ("androidx.room:room-compiler:$roomVersion")

    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVer")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVer")

    // Glide
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    kapt           ("com.github.bumptech.glide:compiler:4.15.1")

    // Flexbox
    implementation ("com.google.android.flexbox:flexbox:3.0.0")

    //Tag
    implementation("com.nex3z:flow-layout:1.3.3")

    //Material
    implementation ("com.google.android.material:material:1.9.0")


}

kapt {
    correctErrorTypes = true
}


