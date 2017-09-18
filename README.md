# Gradle SauceConnect Plugin

This is SauceConnect plugin that downloads, starts and stops SauceLab's SauceConnect application.

# Requirements

  - SauceLabs Account
  - Gradle initialized project

# Publishing

For now the following will publish the plugins:

```
    ./gradlew build publishPlugins
```

# Using the plugin

```
plugins {
    id "com.saucelabs.SauceConnectPlugin" version "0.0.14"
}

sauceconnect {
  username = "$System.env.SAUCE_USERNAME"
  key = "$System.env.SAUCE_ACCESS_KEY"
  options = "-v"
}

def sauce = [
    username: sauceconnect.username,
    key     : sauceconnect.key,
]

task "sauceTest"(dependsOn: startSauceConnect, type: Test) {

    outputs.upToDateWhen { false }  // Always run tests

    // Run in parallel depending on how good your machine is
    maxParallelForks Math.max(2, Runtime.runtime.availableProcessors().intdiv(2))

    reports {
        html.destination = reporting.file("$name/tests")
        junitXml.destination = file("$buildDir/test-results/$name")
    }

    systemProperty "sauce.username", sauce.username
    systemProperty "sauce.key", sauce.key
    systemProperty "testDriver", "sauce"
    systemProperty "geb.build.reportsDir", reporting.file("$name/geb")

}

// This is to make sure that SauceConnect is shutdown
sauceTest.finalizedBy stopSauceConnect

```

For a full working example that uses Geb-Spock test stack feel free to browse [Gradle-Sauce-Connect-Plugin-Example](https://github.com/aginteractive/Gradle-Sauce-Connect-Plugin-Example).

## Contributing

Contributions are welcome. Please see [CONTRIBUTING.md](CONTRIBUTING.md) for
details.
