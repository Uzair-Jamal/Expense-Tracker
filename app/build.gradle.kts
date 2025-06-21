plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id ("kotlin-kapt")
    id("kotlin-parcelize")


}

android {
    namespace = "com.andriod.mobileappdevelopertask"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.andriod.mobileappdevelopertask"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }



    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        android.applicationVariants.all {
            outputs.all {
                val buildType = buildType.name
                val version = versionName ?: "1.0"
                val newApkName = "Expense-Tracker_${buildType}_v${version}.apk"

                (this as com.android.build.gradle.internal.api.BaseVariantOutputImpl).outputFileName = newApkName
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    lint {
        disable += "NullSafeMutableLiveData"
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_18)
        freeCompilerArgs.add("-Xuse-k2=false")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // ViewModel LiveData
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.core.i18n)

    // Room Database
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Navigation
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    // material
    implementation (libs.material.v1110)

    // chart
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")

    //responsive SDP
    implementation (libs.sdp.android)
    implementation (libs.ssp.android)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}