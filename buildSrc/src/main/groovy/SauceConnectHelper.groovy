package io.johnroach

import org.gradle.internal.os.OperatingSystem
import org.gradle.nativeplatform.platform.Architecture

trait SauceConnectHelper {
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
