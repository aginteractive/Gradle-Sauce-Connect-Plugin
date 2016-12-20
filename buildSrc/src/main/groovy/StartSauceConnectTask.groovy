package io.johnroach

import java.io.File
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction


class StartSauceConnectTask extends DefaultTask implements SauceConnectHelper {
    String command
    String ready = "Sauce Connect is up, you may start your tests."
    String directory = "$project.buildDir"

    def getSauceCommand() {
        if(getOSType() == "win32") {
            return "sc.exe --pidfile " + directory + "\\sc\\bin\\sc.pid"
        } else {
            return "./sc --pidfile " + directory + "/sc/bin/sc.pid"
        }
    }

    @TaskAction
    def spawnSauceConnectProcess() {

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
