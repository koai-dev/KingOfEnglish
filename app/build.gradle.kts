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

    signingConfigs {
        register("release") {
            storeFile = file("key.jks")
            storePassword = "123456"
            keyAlias = "key0"
            keyPassword = "123456"
        }
    }
    defaultConfig {
        applicationId = "com.koai.kingofenglish"
        minSdk = 24
        targetSdk = 34
        versionCode = 20240607
        versionName = "1.0.4"
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            isCrunchPngs = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            resValue("string", "app_ads", "\"ca-app-pub-4786522373342788~2041354068\"")
            buildConfigField("String", "REWARD_ADS", "\"ca-app-pub-4786522373342788/6515167146\"")
        }
        debug {
            resValue("string", "app_ads", "\"ca-app-pub-3940256099942544~3347511713\"")
            buildConfigField("String", "REWARD_ADS", "\"ca-app-pub-3940256099942544/5224354917\"")
        }
    }

    setFlavorDimensions(arrayListOf("default"))

    productFlavors{
        create("dev"){
            dimension = "default"
            applicationIdSuffix = ".dev"
        }
        create("prod"){
            dimension = "default"
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
        buildConfig = true
    }
    packaging {
        dex {
            useLegacyPackaging = false
        }
    }
}

dependencies {
    implementation("com.koai:base:1.4.10")
    testImplementation("org.testng:testng:7.4.0")

    implementation("com.google.android.flexbox:flexbox:3.0.0")
    // admob
    implementation("com.google.android.gms:play-services-ads:23.0.0")

    // firebase
    implementation("com.facebook.android:facebook-android-sdk:16.2.0")
    implementation("com.google.firebase:firebase-perf-ktx:21.0.0")
    implementation("com.google.firebase:firebase-messaging-ktx:24.0.0")
    implementation("com.google.firebase:firebase-inappmessaging-display-ktx:21.0.0")
    implementation("com.google.firebase:firebase-config-ktx:22.0.0")
    implementation("com.google.firebase:firebase-auth-ktx:23.0.0")

    // worker
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    // room
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    testImplementation("androidx.room:room-testing:2.6.1")

    implementation("io.ktor:ktor-client-core:2.3.10")
    implementation("io.ktor:ktor-client-cio:2.3.10")
    implementation("io.ktor:ktor-client-websockets:2.3.10")

    implementation("com.airbnb.android:lottie:6.4.0")

}