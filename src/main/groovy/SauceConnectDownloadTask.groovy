package com.saucelabs

import groovy.json.JsonSlurper
import java.io.File
import java.security.MessageDigest
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.gradle.internal.os.OperatingSystem
import org.gradle.nativeplatform.platform.Architecture

class SauceConnectDownloadTask extends DefaultTask implements SauceConnectHelper{

    int KB = 1024
    int MB = 1024*KB
    def artifactName

    Map getSauceVersionInformation() {
        def sauceData
        try {
            sauceData = new URL("https://saucelabs.com/versions.json").getText()
            def jsonSlurper = new JsonSlurper()
            return jsonSlurper.parseText(sauceData)
        } catch(Exception e) {
            println "Couldn't connect to SauceLabs versions API. Exception:\n " + e.toString()
        }
    }

    String getArtifactName(String downloadURL) {
        String[] splitURL = downloadURL.split('/')
        return  splitURL[splitURL.size()-1]
    }

    String getCheckSum(File file) {
        def messageDigest = MessageDigest.getInstance("SHA1")
        file.eachByte(MB) { byte[] buf, int bytesRead ->
            messageDigest.update(buf, 0, bytesRead)
        }
        return new BigInteger(1, messageDigest.digest()).toString(16).padLeft( 40, '0' )
    }

    Boolean scArtifactExists(File scArtifact, String expectedHash) {
      Map sauceVersions = getSauceVersionInformation()
      if(scArtifact.isFile()) {
        // We need to make sure that downloaded file has correct hash
        def artifactCheckSum = getCheckSum(scArtifact)
        if(expectedHash == artifactCheckSum) {
          return true
        } else {
          return false
        }
      } else {
        return false
      }
    }

    @TaskAction
    def downloadSauceConnect() {
        def buildFolder = new File("$project.buildDir")
        def sauceConnectOSType = getOSType()
        Map sauceVersions = getSauceVersionInformation()
        String fileHash
        String sourceUrl
        String target
        if (!buildFolder.exists()){
            buildFolder.mkdirs()
        }
        sourceUrl = sauceVersions["Sauce Connect"][sauceConnectOSType].download_url
        fileHash = sauceVersions["Sauce Connect"][sauceConnectOSType].sha1
        artifactName = getArtifactName(sourceUrl)
        project.ext.set("artifactName", artifactName)
        target = "$buildFolder/" + artifactName
        def targetFile = new File(target)
        if (!scArtifactExists(targetFile, fileHash)) {
            println "Downloading SauceConnect..."
            ant.get(src: sourceUrl, dest: target)
            println "Downloaded SauceConnect to " + target
            def artifactCheckSum = getCheckSum(new File(target))
            assert artifactCheckSum == fileHash : "Incorrect SauceConnect artifact checksum."
        } else {
          println "SauceConnect already exists. Not downloading."
        }

    }
}
