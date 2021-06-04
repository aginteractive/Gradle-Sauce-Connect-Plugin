package com.saucelabs

import java.io.File
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.internal.os.OperatingSystem
import org.gradle.nativeplatform.platform.Architecture


class StopSauceConnectTask extends DefaultTask implements SauceConnectHelper {

    @Internal
    String username = "$System.env.SAUCE_USERNAME"
    @Internal
    String key = "$System.env.SAUCE_ACCESS_KEY"
    @Internal
    String directory = "$project.buildDir"

    @Internal
    String getPIDNumber() {
        String pidFilePath = ""
        if(getOSType() == "win32") {
            pidFilePath = directory + "\\sc\\bin\\sc.pid"
        } else {
            pidFilePath = directory + "/sc/bin/sc.pid"
        }
        File pidFile = new File(pidFilePath)
        return pidFile.readLines().get(0)
    }

    @Internal
    String getSauceCommand() {
        if (getOSType() == "win32") {
            return "Taskkill /PID " + getPIDNumber() + " /F"
        } else {
            return "kill " + getPIDNumber()
        }
    }

    @TaskAction
    def spawnStopSauceConnectProcess() {
        ProcessBuilder builder = new ProcessBuilder(getSauceCommand().split(' '))
        builder.redirectErrorStream(true)
        builder.directory(new File(directory))
        Process process = builder.start()

        InputStream stdout = process.getInputStream()
        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout))

        def line
        while ((line = reader.readLine()) != null) {
            println line
            if (line.contains("")) {
                println "SauceConnect is shutdown"
                break;
            }
        }
    }
}
