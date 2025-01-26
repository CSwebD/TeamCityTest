import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.nodeJS
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

project {
    vcsRoot(HttpsGithubComCSwebDTeamCityTestGitRefsHeadsMain)

    buildType(BuildAndTestWithoutDocker)
}

object BuildAndTestWithoutDocker : BuildType({
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

object HttpsGithubComCSwebDTeamCityTestGitRefsHeadsMain : GitVcsRoot({
    name = "TeamCityTest Main Branch"
    url = "https://github.com/CSwebD/TeamCityTest.git"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
})
