plugins {
    alias(libs.plugins.android.application)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.10"
}
val localProperties = rootProject.file("local.properties")
    .readLines()
    .filter { it.contains("=") }
    .associate {
        it.substringBefore("=") to it.substringAfter("=")
    }
android {
    namespace = "com.example.protege_dds"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.protege_dds"
        buildConfigField(
            "String",
            "SUPABASE_URL",
            "\"${localProperties["SUPABASE_URL"]}\""
        )

        buildConfigField(
            "String",
            "SUPABASE_PUBLISHABLE_KEY",
            "\"${localProperties["SUPABASE_PUBLISHABLE_KEY"]}\""
        )
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = true
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    implementation("io.github.jan-tennert.supabase:postgrest-kt:3.2.6")
    implementation("io.ktor:ktor-client-android:3.2.3")
    implementation("io.github.jan-tennert.supabase:auth-kt:3.2.6")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.5")
}