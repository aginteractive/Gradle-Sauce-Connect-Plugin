import org.gradle.api.NamedDomainObjectContainer

class SauceConnectExtension {
  String username = "$System.env.SAUCE_USERNAME"
  String key = "$System.env.SAUCE_ACCESS_KEY"
  String options = ""
}