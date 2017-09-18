package com.saucelabs

import java.io.File
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.Input


class StartSauceConnectTask extends DefaultTask implements SauceConnectHelper {
    String command
    String ready = "Sauce Connect is up, you may start your tests."
    String directory = "$project.buildDir"

    String username
    String key
    String options

    def getSauceCommand() {
        if(getOSType() == "win32") {
            return "sc.exe " + "/u " + username + " /k " + key + " /d " + directory + "\\sc\\bin\\sc.pid " + options
        } else {
            return "./sc " + "-u " + username + " -k " + key + " --pidfile=" + directory + "/sc/bin/sc.pid " + options
        }
    }

    @TaskAction
    def spawnSauceConnectProcess() {
        username = project.sauceconnect.username
        key = project.sauceconnect.key
        options = project.sauceconnect.options
        ProcessBuilder builder = new ProcessBuilder(getSauceCommand().split(' '))
        builder.redirectErrorStream(true)

        if (getOSType() == "win32") {
            directory = directory + "\\sc\\bin"
        } else {
            directory = directory + "/sc/bin"
        }

        builder.directory(new File(directory))
        Process process = builder.start()

        InputStream stdout = process.getInputStream()
        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout))

        def line
        while ((line = reader.readLine()) != null) {
            println line
            if (line.contains(ready)) {
                println "SauceConnect is ready"
                break;
            }
        }
    }
}
