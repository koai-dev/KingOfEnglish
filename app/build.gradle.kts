plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.firebase-perf")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.koai.kingofenglish"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.koai.kingofenglish"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
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
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation("com.koai:base:1.4.5")
    testImplementation("org.testng:testng:7.4.0")

    implementation("com.google.android.flexbox:flexbox:3.0.0")
    // admob
    implementation("com.google.android.gms:play-services-ads:23.0.0")

    // firebase
    implementation("com.facebook.android:facebook-android-sdk:16.2.0")
    implementation("com.google.firebase:firebase-perf-ktx:20.5.2")
    implementation("com.google.firebase:firebase-messaging-ktx:23.4.1")
    implementation("com.google.firebase:firebase-inappmessaging-display-ktx:20.4.2")
    implementation("com.google.firebase:firebase-config-ktx:21.6.3")
    implementation("com.google.firebase:firebase-auth-ktx:22.3.1")

    // worker
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    // room
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    testImplementation("androidx.room:room-testing:2.6.1")
}
