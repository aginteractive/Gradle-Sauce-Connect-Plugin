package com.saucelabs

class SauceConnectAuthPluginExtension {
    def String username = "$System.env.SAUCE_USERNAME"
    def String key = "$System.env.SAUCE_ACCESS_KEY"
}
