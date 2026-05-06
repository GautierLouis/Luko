rootProject.name = "Learn-Chinese"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

//Server
include(":server")
//Shared (library - server)
include(":api-contracts")
//App
include(":composeApp")
include(":desktop")
include(":library:app")
//Feature
include(":library:feature:base-ui")
include(":library:feature:home")
include(":library:feature:feed")
include(":library:feature:login")
include(":library:feature:profile")
include(":library:feature:learning")
include(":library:feature:dictionary")
//Domain
include(":library:domain")
include(":library:domain:auth")
//Data
include(":library:data:network")
include(":library:data:database")
include(":library:data:preferences")
//Core
include(":library:core:ads")
include(":library:core:tracking")
include(":library:core:logger")
include(":library:core:utils")
include(":library:core:permission")
include(":library:core:firebase")
include(":library:core:navigation")
//Design-System
include(":library:design-system")
