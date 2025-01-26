import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.nodeJS
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

project {
    vcsRoot(HttpsGithubComCSwebDTeamCityTestGitRefsHeadsMain)

    // Original build configuration
    buildType(BuildAndTest)

    // New build configuration without Docker
    buildType(BuildAndTestNoDocker)
}

// Original Build Configuration
object BuildAndTest : BuildType({
    name = "Build and Test with Docker"
    vcs {
        root(HttpsGithubComCSwebDTeamCityTestGitRefsHeadsMain)
    }
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

// New Build Configuration Without Docker
object BuildAndTestNoDocker : BuildType({
    name = "Build and Test Without Docker"
    vcs {
        root(HttpsGithubComCSwebDTeamCityTestGitRefsHeadsMain)
    }
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

// Git VCS Root
object HttpsGithubComCSwebDTeamCityTestGitRefsHeadsMain : GitVcsRoot({
    name = "TeamCityTest Main Branch"
    url = "https://github.com/CSwebD/TeamCityTest.git"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
})
