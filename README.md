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

- [ ] #1 - Inject SauceConnect options: do it as a string
- [ ] #2 - Version handling for SauceConnect
- [ ] #3 - Check if SauceConnect was previously downloaded
- [x] Download archive artifact (must work on Darwin/Windows/Linux)
- [x] Un-archive archive artifact (must work on Darwin/Windows/Linux)
- [x] Rename un-archived artifact for easy access
- [x] Open SauceConnect tunnel at background (must work on Darwin/Windows/Linux)
- [x] Shutdown SauceConnect if it is running (must work on Darwin/Windows/Linux)

