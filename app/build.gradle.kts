plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}



android {
    namespace = "com.example.faydemo"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.faydemo"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }

}

hilt {
    enableAggregatingTask = false
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.navigation)
    ksp(libs.hilt.android.compiler)

    //Util
    implementation(libs.kotlinx.coroutines)
    implementation(libs.timber)
    implementation(libs.androidx.icons)
    implementation(libs.coil)
    implementation(libs.coil.network)

    //database
    implementation(libs.room)
    ksp(libs.room.compiler)
    implementation(libs.sql.lite)

    // Ktor - Network
    implementation(libs.ktor.core)
    implementation(libs.ktor.cio)
    implementation(libs.ktor.json)
    implementation(libs.ktor.content.negotiation)
    implementation(libs.ktor.logging)
    implementation(libs.ktor.client.logging.jvm)
    implementation(libs.ktor.client.android)
    implementation(libs.kotlinx.serialization.json)
}