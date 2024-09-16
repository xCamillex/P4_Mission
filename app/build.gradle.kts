plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
  id("kotlin-kapt")
  id("com.google.dagger.hilt.android")

}

android {
  namespace = "com.aura"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.aura"
    minSdk = 24
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
  }

  packaging {
    // Option 1: Exclude all META-INF/INDEX.LIST files
    resources.excludes.add ("META-INF/INDEX.LIST")
    resources.excludes.add("META-INF/io.netty.versions.properties")

    // Option 2: Merge META-INF/INDEX.LIST files
    //merge 'META-INF/INDEX.LIST'
  }
}

dependencies {

  implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.5")
  implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.5")
  implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.5")
  implementation("androidx.activity:activity-ktx:1.9.2")
  implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.10")
  implementation("androidx.navigation:navigation-fragment-ktx:2.8.0")
  implementation("androidx.navigation:navigation-ui-ktx:2.8.0")
  implementation("androidx.core:core-ktx:1.13.1")
  implementation("androidx.appcompat:appcompat:1.7.0")
  implementation("com.google.android.material:material:1.12.0")
  implementation("androidx.annotation:annotation:1.8.2")
  implementation("androidx.constraintlayout:constraintlayout:2.1.4")
  implementation("com.google.firebase:firebase-crashlytics-buildtools:3.0.2")
  implementation("androidx.activity:activity:1.9.2")
  testImplementation("junit:junit:4.13.2")
  androidTestImplementation("androidx.test.ext:junit:1.2.1")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

  implementation("androidx.fragment:fragment-ktx:1.8.3")

  val ktorVersion ="2.3.10"
  implementation("io.ktor:ktor-server-core:$ktorVersion")
  implementation("io.ktor:ktor-server-netty:$ktorVersion")
  implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
  implementation("io.ktor:ktor-server-swagger:$ktorVersion")
  implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
  implementation("io.ktor:ktor-server-cors:$ktorVersion")

  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

  // Hilt
  implementation("com.google.dagger:hilt-android:2.51.1")
  kapt("com.google.dagger:hilt-android-compiler:2.51.1")

  // Retrofit + Moshi
  implementation("com.squareup.retrofit2:retrofit:2.11.0")
  implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
  implementation("com.squareup.moshi:moshi-kotlin:1.15.0")
  implementation("com.squareup.moshi:moshi-kotlin-codegen:1.12.0")

}
kapt { correctErrorTypes = true }