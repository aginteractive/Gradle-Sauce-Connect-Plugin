# Gradle SauceConnect Plugin

This is SauceConnect plugin that downloads, starts and stops SauceLab's SauceConnect application.

# Requirements

  - SauceLabs Account
  - Gradle initialized project

# Publishing

For now the following will publish the plugins:

    ./gradlew build publishPlugins

# TODO

- [ ] [#1](https://github.com/aginteractive/Gradle-Sauce-Connect-Plugin/issues/1) - Inject SauceConnect options: do it as a string
- [x] [#2](https://github.com/aginteractive/Gradle-Sauce-Connect-Plugin/issues/2) - Version handling for SauceConnect
- [x] [#3](https://github.com/aginteractive/Gradle-Sauce-Connect-Plugin/issues/3) - Check if SauceConnect was previously downloaded
- [x] Download archive artifact (must work on Darwin/Windows/Linux)
- [x] Un-archive archive artifact (must work on Darwin/Windows/Linux)
- [x] Rename un-archived artifact for easy access
- [x] Open SauceConnect tunnel at background (must work on Darwin/Windows/Linux)
- [x] Shutdown SauceConnect if it is running (must work on Darwin/Windows/Linux)
