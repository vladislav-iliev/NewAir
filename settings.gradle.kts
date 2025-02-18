pluginManagement {
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
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "NewAir"
include(":app")
includeBuild(file("../air/", PathValidation.DIRECTORY)) {
    dependencySubstitution {
        substitute(module("com.vladislaviliev.air:readings")).using(project(":readings"))
        substitute(module("com.vladislaviliev.air:readings-testing")).using(project(":readings-testing"))
    }
}
