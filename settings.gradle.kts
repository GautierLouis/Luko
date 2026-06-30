rootProject.name = "Luko"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
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
include(":androidApp")
include(":desktopApp")
include(":library:app")
//Feature
include(":library:feature:base-ui")
include(":library:feature:home")
include(":library:feature:feed")
include(":library:feature:profile")
include(":library:feature:learning")
include(":library:feature:sessions")
include(":library:feature:dictionary")
include(":library:feature:ui:core")
include(":library:feature:ui:drawing")
include(":library:feature:ui:navigation")
include(":library:feature:ui:design-system")
include(":library:feature:ui:onboarding")
//Domain
include(":library:domain")
//Data
include(":library:data:network")
include(":library:data:database")
include(":library:data:preferences")
//Core
include(":library:core:ads")
include(":library:core:tracking")
include(":library:core:utils")
include(":library:core:permission")
include(":library:core:firebase")
