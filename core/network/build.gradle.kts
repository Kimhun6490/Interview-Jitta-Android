plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("kapt")
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.apollo3)
}

android {
    namespace = "com.mamafarm.android.network"
    compileSdk = 34

    defaultConfig {
        minSdk = 31

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
apollo {
    service("service") {
        generateKotlinModels.set(true)
        packageName.set("com.mamafarm.android.network")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //APOLLO
    implementation(libs.apollo3.runtime)
    implementation(libs.apollo3.rx)

    //HILT
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)

    //PAGING DATA
    implementation(libs.paging.runtime)
}
kapt {
    correctErrorTypes = true
}