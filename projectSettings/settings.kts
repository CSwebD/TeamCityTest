import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.nodeJS
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

/*
This Kotlin DSL script defines two build configurations:
1. Build and Test with Docker (original).
2. Build and Test Without Docker (new, simplified configuration).
*/

// Define the project
project {
    // Register the VCS root
    vcsRoot(HttpsGithubComCSwebDTeamCityTestGitRefsHeadsMain)

    // Register the build configurations
    buildType(BuildAndTestWithDocker)
    buildType(BuildAndTestWithoutDocker)
}

// Build Configuration: With Docker
object BuildAndTestWithDocker : BuildType({
    name = "Build and Test with Docker"

    // Associate the VCS root
    vcs {
        root(HttpsGithubComCSwebDTeamCityTestGitRefsHeadsMain)
    }

    // Build steps
    steps {
        nodeJS {
            name = "Install Dependencies"
            shellScript = "npm install"
            dockerImagePlatform = NodeJSBuildStep.ImagePlatform.Windows
            dockerPull = true
        }
        nodeJS {
            name = "Run Tests"
            shellScript = "npm run test"
            dockerImagePlatform = NodeJSBuildStep.ImagePlatform.Windows
        }
    }
})

// Build Configuration: Without Docker
object BuildAndTestWithoutDocker : BuildType({
    name = "Build and Test Without Docker"

    // Associate the VCS root
    vcs {
        root(HttpsGithubComCSwebDTeamCityTestGitRefsHeadsMain)
    }

    // Build steps
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
})

// Define the Git VCS root
object HttpsGithubComCSwebDTeamCityTestGitRefsHeadsMain : GitVcsRoot({
    name = "TeamCityTest Main Branch"
    url = "https://github.com/CSwebD/TeamCityTest.git"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
})
