package rs.pglisovic.jwplayerdemoandroidapp

enum class Feature(val title: String, val available: Boolean, val hasNote: Boolean) {

    SUBTITLES("Subtitles", true, true),
    SCRUBBINGWITHPREVIEW("Scrubbing with preview", false, false),
    TAPKEYTOSCRUP("Tap/key to scrup", true, true),
    PLAYLIST("Playlist (with Next in Playlist preview)", true, false),
    FULLSCREEN("Force Wide Fullscreen", true, false),
    STARTVIDEOATSECOND("Play video from 10 to 20 second.", true, false),
    SOCIALSHARINGCUSTOMURL("Social sharing custom URL", true, false),
    INPLAYERNOTIFICATIONS("In player notifications(annotations)", false, false),
    AUTOPLAY("Autoplay", true, false),
    LOOPVIDEO("Loop video", true, false),
    CONTROLVISIBILITYOFCONTROLS("Control visibility of controls", true, false),
    DRMSUPPORT("DRM support", true, true),
    PLAYBACKRATES("Playback rates (Slow, Normal, Fast)", true, false),
    PLAYBACKQUALITY("Playback quality", true, false),
    FREEWHEELADS("Freewheel advertising", true, true),
    ADS("Ads (Pre, Mid, Post rolls)", true, true),
    OVERLAYADS("Overlay ads", true, false),
    CASTING("Casting", true, false),
    CASTINGADS("Casting ads (pre / mid / post rolls)", false, false),
    EVENTHEARTBEAT("Event heartbeat", false, false),
    DOWNLOADVIDEO("Download video (like spotify etc)", false, false),
    SKINNING("Skinning", true, false);

}