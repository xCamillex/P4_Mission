plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  id("com.google.dagger.hilt.android")
  id("kotlin-kapt")
}

android {
  namespace = "com.aura"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.aura"
    minSdk = 24
    targetSdk = 33
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
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions {
    jvmTarget = "17"
  }
  buildFeatures {
    viewBinding = true
  }
}

dependencies {

  implementation("androidx.core:core-ktx:1.13.1")
  implementation("androidx.appcompat:appcompat:1.7.0")
  implementation("com.google.android.material:material:1.12.0")
  implementation("androidx.annotation:annotation:1.8.2")
  implementation("androidx.constraintlayout:constraintlayout:2.1.4")
  implementation("androidx.activity:activity-ktx:1.9.2")
  implementation("androidx.fragment:fragment-ktx:1.8.3")
  implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.6")
  implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.10")

  //lifecycle
  implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
  implementation ("androidx.activity:activity-ktx:1.9.2")
  implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")

  //coroutine
  implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
  testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

  //Hilt
  implementation ("com.google.dagger:hilt-android:2.51.1")
  kapt ("com.google.dagger:hilt-compiler:2.51.1")

  //retrofit + OkHttp3 + Gson
  implementation ("com.squareup.retrofit2:retrofit:2.11.0")
  implementation ("com.squareup.okhttp3:okhttp:4.12.0")
  implementation ("com.squareup.moshi:moshi:1.15.0")
  implementation ("com.squareup.moshi:moshi-kotlin:1.15.0")
  implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")
  implementation ("com.squareup.retrofit2:converter-gson:2.9.0")


  //tests
  testImplementation("junit:junit:4.13.2")
  androidTestImplementation("androidx.test.ext:junit:1.2.1")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
  testImplementation ("io.mockk:mockk:1.13.2")

  implementation("androidx.navigation:navigation-fragment-ktx:2.8.1")
  implementation ("androidx.navigation:navigation-ui-ktx:2.8.1" )
  // Mockito for mocking dependencies
  testImplementation("org.mockito:mockito-core:3.12.4")

  // Mockito Kotlin for easier mocking of Kotlin classes
  testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")

  // MockWebServer
  testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
}
