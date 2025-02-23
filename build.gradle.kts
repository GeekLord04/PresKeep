buildscript {
    val agp_version by extra("8.1.1")
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.8.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.49" apply false
    id ("com.android.library") version "8.0.2" apply false
}