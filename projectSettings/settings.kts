import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.NodeJSBuildStep
import jetbrains.buildServer.configs.kotlin.buildSteps.nodeJS
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

/*
This Kotlin DSL script defines a TeamCity project hierarchy.
It includes VCS roots, build types, and configurations.

To debug or generate settings locally:
- Use Maven with the TeamCity configs plugin.
- Open this file in IntelliJ IDEA for further debugging.
*/

version = "2024.12"

project {
    // Register the VCS root
    vcsRoot(HttpsGithubComCSwebDTeamCityTestGitRefsHeadsMain)

    // Define the build configuration
    buildType(BuildAndTest)
}

object BuildAndTest : BuildType({
    name = "Build and Test" // Build configuration name

    // Associate the VCS root
    vcs {
        root(HttpsGithubComCSwebDTeamCityTestGitRefsHeadsMain)
    }

    // Define build steps
    steps {
        nodeJS {
            name = "Install Dependencies"
            shellScript = "npm install"
        }
        nodeJS {
            name = "Run Tests"
            shellScript = "npm run test"
        }
        nodeJS {
            name = "Build Application"
            shellScript = "npm run build"
        }
    }

    // Add a VCS trigger to build on changes
    triggers {
        vcs {
            quietPeriodMode = VcsTrigger.QuietPeriodMode.USE_DEFAULT
            branchFilter = "+:refs/heads/main"
        }
    }

    // Enable performance monitoring
    features {
        perfmon { }
    }
})

// Define the Git VCS root
object HttpsGithubComCSwebDTeamCityTestGitRefsHeadsMain : GitVcsRoot({
    name = "TeamCityTest Main Branch"
    url = "https://github.com/CSwebD/TeamCityTest.git"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*" // Monitor all branches
})
