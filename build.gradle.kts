// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.kotlin.kapt) apply false
}

buildscript {
    configurations.all {
        resolutionStrategy.eachDependency {
            when (requested.name) {
                "javapoet" -> useVersion("1.13.0")
            }
        }
    }
}
