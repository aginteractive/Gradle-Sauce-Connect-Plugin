package io.johnroach

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.DefaultTask

class SauceConnectPlugin implements Plugin<Project> {

    void apply(Project project) {
        project.ext.set("artifactName", "")
        project.extensions.create("sauceAuth", SauceConnectAuthPluginExtension)
        project.task("getSauceAuth") << {
            description "Makes sure that required Saucelabs Authentication requirements are met"
            if (project.sauceAuth.username && project.sauceAuth.key){
                println "Collected SauceLabs authentication requirements..."
            } else {
                def authError = "Failed to acquire SauceLabs username and key.\n" +
                                "Please either set environment variables SAUCE_USERNAME and SAUCE_ACCESS_KEY \n" +
                                "or set sauceAuth.username and sauceAuth.key\n"
                throw new GradleException(authError)
            }
        }

        project.task(["type": SauceConnectDownloadTask, "dependsOn": [project.tasks.getSauceAuth]], "downloadSauceConnect") {
            description "Download SauceConnect"
        }

        project.task("unArchiveSauceConnect", "dependsOn": project.tasks.downloadSauceConnect) {
            description "Unarchiving SauceConnect artifacts"
            outputs.upToDateWhen { false }
            doLast{
                println "Unarchiving SauceConnect artifact..."
                project.copy{
                    from project.zipTree(project.file("$project.buildDir/" + project.artifactName))
                    into project.file("$project.buildDir/")
                }
                println "Unarchived to $project.buildDir/"
                String artifactNameWithoutExtension = project.artifactName.minus(".tar").minus(".gz").minus(".zip")
                ant.move(file: "$project.buildDir/" + artifactNameWithoutExtension, toFile: "$project.buildDir/sc")
            }
        }

        project.task("startSauceConnect", "type": StartSauceConnectTask, "dependsOn": project.tasks.unArchiveSauceConnect) {
        }

        project.task("stopSauceConnect", "type": StopSauceConnectTask) {
        }

    }
}
