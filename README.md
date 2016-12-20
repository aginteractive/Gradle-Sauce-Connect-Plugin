# Gradle SauceConnect Plugin

This is SauceConnect plugin that downloads, starts and stops SauceLab's SauceConnect application.

# Requirements

  - SauceLabs Account
  - Gradle initialized project

# Running

For now the following commands allow to run the tasks defined in the plugin

    ./gradlew -q startSauceConnect
    ./gradlew -q stopSauceConnect

Example on how to use the plugin:
```
    def sauce = [
        username: "$System.env.SAUCE_USERNAME",
        key     : "$System.env.SAUCE_ACCESS_KEY"
    ]

    apply plugin: "SauceConnectPlugin"

    task "sauceTest"(type: Test) {
        dependsOn startSauceConnect

        // Your commands here !!!

        doLast {
            stopSauceConnect
        }
    }
```

# TODO

1. [DONE] Download archive artifact (must work on Darwin/Windows/Linux)
2. [DONE] Un-archive archive artifact (must work on Darwin/Windows/Linux)
3. [DONE] Rename un-archived artifact for easy access
4. [DONE] Open SauceConnect tunnel at background (must work on Darwin/Windows/Linux)
5. [DONE] Shutdown SauceConnect if it is running (must work on Darwin/Windows/Linux)
6. [] Inject SauceConnect options: do it as a string
7. [] Version handling for SauceConnect
