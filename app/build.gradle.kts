plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.devtools.ksp")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.gms.google-services")
}

android {
    val compileSdkVersion: String by System.getProperties()

    namespace = "com.example.mypet.app"
    compileSdk = compileSdkVersion.toInt()

    defaultConfig {
        val minSdkVersion: String by System.getProperties()
        val targetSdkVersion: String by System.getProperties()

        applicationId = "com.example.mypet"
        minSdk = minSdkVersion.toInt()
        targetSdk = targetSdkVersion.toInt()
        versionCode = 1
        versionName = "0.0.1"

        android.buildFeatures.buildConfig = true
   }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.majorVersion
    }
}

dependencies {
    val hiltVersion: String by System.getProperties()

    val androidxAppCompatVersion: String by System.getProperties()
    val androidxMaterialVersion: String by System.getProperties()
    val androidxConstraintlayoutVersion: String by System.getProperties()
    val androidxLifecycleVersion: String by System.getProperties()
    val androidxNavigationVersion: String by System.getProperties()
    val androidxRoomVersion: String by System.getProperties()
    val androidxRecyclerViewVersion: String by System.getProperties()

    val viewBindingDelegateVersion: String by System.getProperties()

    val retrofit2Version: String by System.getProperties()

    val glideVersion: String by System.getProperties()

    val yandexMapVersion: String by System.getProperties()

    val dataStoreVersion: String by System.getProperties()

    implementation("com.google.dagger:hilt-android:$hiltVersion")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    implementation("androidx.appcompat:appcompat:$androidxAppCompatVersion")
    implementation("com.google.android.material:material:$androidxMaterialVersion")
    implementation("androidx.constraintlayout:constraintlayout:$androidxConstraintlayoutVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$androidxLifecycleVersion")
    implementation("androidx.navigation:navigation-fragment-ktx:$androidxNavigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$androidxNavigationVersion")
    implementation("androidx.recyclerview:recyclerview:$androidxRecyclerViewVersion")

    implementation("androidx.room:room-runtime:$androidxRoomVersion")
    kapt("androidx.room:room-compiler:$androidxRoomVersion")
    implementation("androidx.room:room-ktx:$androidxRoomVersion")

    implementation("com.squareup.retrofit2:retrofit:$retrofit2Version")
    implementation("com.squareup.retrofit2:converter-simplexml:$retrofit2Version")

    implementation("com.github.bumptech.glide:glide:$glideVersion")
    kapt("com.github.bumptech.glide:compiler:$glideVersion")

    implementation("com.github.kirich1409:viewbindingpropertydelegate-noreflection:$viewBindingDelegateVersion")

    //implementation("com.yandex.android:maps.mobile:$yandexMapVersion")

    implementation("androidx.datastore:datastore-preferences:$dataStoreVersion")

    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))
    implementation("com.google.firebase:firebase-auth")
}