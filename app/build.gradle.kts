import com.android.build.api.variant.BuildConfigField
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.geekster.preskeep"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.geekster.preskeep"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

//        BuildConfigField("String", "BASE_URL", properties.getProperty("BASE_URL"))

        buildConfigField("String", "PROJECT_ID", properties.getProperty("PROJECT_ID"))
        buildConfigField("String", "PREFS_TOKEN_FILE", properties.getProperty("PREFS_TOKEN_FILE"))
        buildConfigField("String", "DATABASE_ID", properties.getProperty("DATABASE_ID"))
        buildConfigField("String", "COLLECTION_ID", properties.getProperty("COLLECTION_ID"))
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
    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity:1.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //AppWrite
    implementation("io.appwrite:sdk-for-android:4.0.1")

    //Hilt
    val hilt_version="2.49"
    implementation("com.google.dagger:hilt-android:$hilt_version")
    kapt("com.google.dagger:hilt-compiler:$hilt_version")

    //Coroutines
    val core_coroutines_version = "1.7.3"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$core_coroutines_version")
    val coroutines_version = "1.7.3"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version")

    //Jetpack Navigation
    val navigation_version = "2.7.7"
    implementation("androidx.navigation:navigation-fragment-ktx:$navigation_version")
    implementation("androidx.navigation:navigation-ui-ktx:$navigation_version")

    //ViewModel
    val lifecycle_version = "2.7.0"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")

    val edittext_version = "1.0.7"
    implementation("com.github.thomhurst:ExpandableHintText:${edittext_version}")

    val otp_view_version = "3.1.0"
    implementation("com.github.mukeshsolanki.android-otpview-pinview:otpview:$otp_view_version")

    implementation("com.hbb20:ccp:2.7.3")

    //Bottom navigation
    implementation("com.github.qamarelsafadi:CurvedBottomNavigation:0.1.3")

    //Coil
    implementation("io.coil-kt:coil:2.7.0")


}