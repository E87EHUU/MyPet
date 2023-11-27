plugins {
    val gradleVersion: String by System.getProperties()
    val kotlinVersion: String by System.getProperties()
    val hiltVersion: String by System.getProperties()
    val androidxNavigationVersion: String by System.getProperties()
    val kspVersion: String by System.getProperties()
    val googleSecretsVersion: String by System.getProperties()

    id("com.android.application") version gradleVersion apply false
    id("org.jetbrains.kotlin.android") version kotlinVersion apply false
    id("com.google.dagger.hilt.android") version hiltVersion apply false
    id("androidx.navigation.safeargs") version androidxNavigationVersion apply false
    id("com.google.devtools.ksp") version kspVersion apply false
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version googleSecretsVersion apply false
}