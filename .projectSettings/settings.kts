import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.NodeJSBuildStep
import jetbrains.buildServer.configs.kotlin.buildSteps.nodeJS
import jetbrains.buildServer.configs.kotlin.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.triggers.vcs

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2024.12"

project {

    buildType(BuildAndTest)
}

object BuildAndTest : BuildType({
    name = "Build and Test"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        nodeJS {
            name = "Install Dependencies"
            id = "nodejs_runner"
            shellScript = "npm install"
            dockerImagePlatform = NodeJSBuildStep.ImagePlatform.Windows
            dockerPull = true
            dockerRunParameters = """--rm --memory="512m""""
        }
        nodeJS {
            name = "Run Tests"
            id = "nodejs_runner_1"
            shellScript = "npm run test"
            dockerImagePlatform = NodeJSBuildStep.ImagePlatform.Windows
            dockerRunParameters = """--rm --memory="512m""""
        }
        nodeJS {
            name = "Build Application"
            id = "nodejs_runner_2"
            shellScript = "npm run build"
            dockerImagePlatform = NodeJSBuildStep.ImagePlatform.Windows
        }
    }

    triggers {
        vcs {
        }
        vcs {
            quietPeriodMode = VcsTrigger.QuietPeriodMode.USE_DEFAULT
            branchFilter = "+:refs/heads/main"
        }
    }

    features {
        perfmon {
        }
    }
})
