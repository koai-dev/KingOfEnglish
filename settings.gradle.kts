pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://jitpack.io")
        maven(url = "${extra["mavenRepo"]}"){
            credentials{
                username = extra["username"].toString()
                password = extra["token"].toString()
            }
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven(url = "${extra["mavenRepo"]}"){
            credentials{
                username = extra["username"].toString()
                password = extra["token"].toString()
            }
        }
    }
}

rootProject.name = "KingOfEnglish"
include(":app")
 