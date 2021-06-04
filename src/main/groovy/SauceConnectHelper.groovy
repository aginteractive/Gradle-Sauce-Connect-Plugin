package com.saucelabs

import org.gradle.internal.os.OperatingSystem
import org.gradle.nativeplatform.platform.Architecture
import org.gradle.api.tasks.Internal

trait SauceConnectHelper {
    @Internal
    String getOSType() {
        String osType
        String arch = System.getProperty("os.arch")
        if(OperatingSystem.current().isMacOsX()) {
            osType = "osx"
        } else if (OperatingSystem.current().isLinux()) {
            if (arch.contains("64")){
                osType = "linux"
            } else {
                osType = "linux32"
            }
        } else if (OperatingSystem.current().isWindows()) {
            osType = "win32"
        }
        return osType
    }
}
