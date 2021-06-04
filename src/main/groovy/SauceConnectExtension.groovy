import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.tasks.Internal


class SauceConnectExtension {
  @Internal
  String username = "$System.env.SAUCE_USERNAME"
  @Internal
  String key = "$System.env.SAUCE_ACCESS_KEY"
  @Internal
  String options = ""
}