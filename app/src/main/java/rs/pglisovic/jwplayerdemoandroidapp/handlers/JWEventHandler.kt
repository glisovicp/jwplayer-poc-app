package rs.pglisovic.jwplayerdemoandroidapp.handlers

import android.content.Context
import android.util.Log
import android.widget.TextView
import com.longtailvideo.jwplayer.JWPlayerView
import com.longtailvideo.jwplayer.events.*
import com.longtailvideo.jwplayer.events.listeners.AdvertisingEvents
import com.longtailvideo.jwplayer.events.listeners.VideoPlayerEvents

/**
 * Outputs all JW Player Events to logging, with the exception of time events.
 */
class JWEventHandler(context: Context, jwPlayerView: JWPlayerView, outputTV: TextView) : VideoPlayerEvents.OnSetupErrorListener,
    VideoPlayerEvents.OnPlaylistListener,
    VideoPlayerEvents.OnPlaylistItemListener,
    VideoPlayerEvents.OnPlayListener,
    VideoPlayerEvents.OnPauseListener,
    VideoPlayerEvents.OnBufferListener,
    VideoPlayerEvents.OnIdleListener,
    VideoPlayerEvents.OnErrorListener,
    VideoPlayerEvents.OnSeekListener,
    VideoPlayerEvents.OnTimeListener,
    VideoPlayerEvents.OnFullscreenListener,
    VideoPlayerEvents.OnAudioTracksListener,
    VideoPlayerEvents.OnAudioTrackChangedListener,
    VideoPlayerEvents.OnCaptionsListListener,
    VideoPlayerEvents.OnMetaListener,
    VideoPlayerEvents.OnPlaylistCompleteListener,
    VideoPlayerEvents.OnCompleteListener,
    VideoPlayerEvents.OnLevelsChangedListener,
    VideoPlayerEvents.OnLevelsListener,
    VideoPlayerEvents.OnCaptionsChangedListener,
    VideoPlayerEvents.OnControlsListener,
    VideoPlayerEvents.OnDisplayClickListener,
    VideoPlayerEvents.OnMuteListener,
    VideoPlayerEvents.OnSeekedListener,
    VideoPlayerEvents.OnVisualQualityListener,
    VideoPlayerEvents.OnFirstFrameListener,
    AdvertisingEvents.OnAdClickListener,
    AdvertisingEvents.OnAdCompleteListener,
    AdvertisingEvents.OnAdSkippedListener,
    AdvertisingEvents.OnAdErrorListener,
    AdvertisingEvents.OnAdImpressionListener,
    AdvertisingEvents.OnAdTimeListener,
    AdvertisingEvents.OnAdPauseListener,
    AdvertisingEvents.OnAdPlayListener,
    AdvertisingEvents.OnAdScheduleListener,
    AdvertisingEvents.OnBeforePlayListener,
    AdvertisingEvents.OnBeforeCompleteListener {


    private val TAG = JWEventHandler::class.java.name

    private val context: Context
    private val jwPlayerView: JWPlayerView
    private val outputTV: TextView

    init {
        // Subscribe to all JW Player events
        jwPlayerView.addOnFirstFrameListener(this)
        jwPlayerView.addOnSetupErrorListener(this)
        jwPlayerView.addOnPlaylistListener(this)
        jwPlayerView.addOnPlaylistItemListener(this)
        jwPlayerView.addOnPlayListener(this)
        jwPlayerView.addOnPauseListener(this)
        jwPlayerView.addOnBufferListener(this)
        jwPlayerView.addOnIdleListener(this)
        jwPlayerView.addOnErrorListener(this)
        jwPlayerView.addOnSeekListener(this)
        jwPlayerView.addOnTimeListener(this)
        jwPlayerView.addOnFullscreenListener(this)
        jwPlayerView.addOnLevelsChangedListener(this)
        jwPlayerView.addOnLevelsListener(this)
        jwPlayerView.addOnCaptionsListListener(this)
        jwPlayerView.addOnCaptionsChangedListener(this)
        //  jwPlayerView.addOnRelatedCloseListener(this);
        //  jwPlayerView.addOnRelatedOpenListener(this);
        //  jwPlayerView.addOnRelatedPlayListener(this);
        jwPlayerView.addOnControlsListener(this)
        jwPlayerView.addOnDisplayClickListener(this)
        jwPlayerView.addOnMuteListener(this)
        jwPlayerView.addOnVisualQualityListener(this)
        jwPlayerView.addOnSeekedListener(this)
        jwPlayerView.addOnAdClickListener(this)
        jwPlayerView.addOnAdCompleteListener(this)
        jwPlayerView.addOnAdSkippedListener(this)
        jwPlayerView.addOnAdErrorListener(this)
        jwPlayerView.addOnAdImpressionListener(this)
        jwPlayerView.addOnAdTimeListener(this)
        jwPlayerView.addOnAdPauseListener(this)
        jwPlayerView.addOnAdPlayListener(this)
        jwPlayerView.addOnMetaListener(this)
        jwPlayerView.addOnPlaylistCompleteListener(this)
        jwPlayerView.addOnCompleteListener(this)
        jwPlayerView.addOnBeforePlayListener(this)
        jwPlayerView.addOnBeforeCompleteListener(this)
        jwPlayerView.addOnAdScheduleListener(this)

        this.context = context
        this.jwPlayerView = jwPlayerView
        this.outputTV = outputTV
    }

    private fun updateOutput(output: String) {
        Log.i(TAG, output)
//        outputTV.text = output
    }

    /**
     * Regular playback events below here
     */

    override fun onBuffer(bufferEvent: BufferEvent) {
        updateOutput("onBuffer()")
    }

    override fun onCaptionsList(captionsListEvent: CaptionsListEvent) {
        updateOutput("onCaptionsList(List<Caption>)")
    }

    override fun onComplete(completeEvent: CompleteEvent) {
        updateOutput("onComplete()")
    }

    override fun onFullscreen(fullscreen: FullscreenEvent) {
        updateOutput("onFullscreen(" + fullscreen.fullscreen + ")")
    }

    override fun onIdle(idleEvent: IdleEvent) {
        updateOutput("onIdle()")
    }

    override fun onMeta(metaEvent: MetaEvent) {
        updateOutput("onMeta()")
    }

    override fun onPause(pauseEvent: PauseEvent) {
        updateOutput("onPause()")
    }

    override fun onPlay(playEvent: PlayEvent) {
        updateOutput("onPlay()")
    }

    override fun onPlaylistComplete(playlistCompleteEvent: PlaylistCompleteEvent) {
        updateOutput("onPlaylistComplete()")
    }

    override fun onPlaylistItem(playlistItemEvent: PlaylistItemEvent) {
        updateOutput("onPlaylistItem()")
    }

    override fun onPlaylist(playlistEvent: PlaylistEvent) {
        updateOutput("onPlaylist()")
    }

    override fun onSetupError(setupErrorEvent: SetupErrorEvent) {
        updateOutput("onSetupError(\"" + setupErrorEvent.message + "\")")
    }

    override fun onTime(timeEvent: TimeEvent) {
        Log.d(TAG, timeEvent.duration.toString() + "  ***  ")
        if (timeEvent.position >= 20.0) {
            jwPlayerView.pause()
        }
    }

    override fun onError(errorEvent: ErrorEvent) {
        updateOutput("onError(\"" + errorEvent.message + "\")")
    }

    override fun onCaptionsChanged(list: CaptionsChangedEvent) {
        updateOutput("onCaptionsChanged(" + list.currentTrack + ", List<Caption>)")
    }

    override fun onControls(controlsEvent: ControlsEvent) {
        updateOutput("onControls(\"" + controlsEvent.controls + "\")")
    }

    override fun onDisplayClick(displayClickEvent: DisplayClickEvent) {
        updateOutput("onDisplayClick()")
    }

    override fun onMute(muteEvent: MuteEvent) {
        updateOutput("onMute()")
    }

    override fun onFirstFrame(firstFrameEvent: FirstFrameEvent) {
        updateOutput("onFirstFrame()")
    }

    override fun onSeek(seekEvent: SeekEvent?) {
        updateOutput("onSeek()")
    }

    override fun onAudioTracks(audioTracksEvent: AudioTracksEvent?) {
        updateOutput("onAudioTracks()")
    }

    override fun onAudioTrackChanged(audioTrackChangedEvent: AudioTrackChangedEvent?) {
        updateOutput("onAudioTrackChanged()")
    }

    override fun onLevelsChanged(levelsChangedEvent: LevelsChangedEvent?) {
        updateOutput("onLevelsChanged()")
    }

    override fun onLevels(levelsEvent: LevelsEvent?) {
        updateOutput("onLevels()")
    }

    override fun onSeeked(seekedEvent: SeekedEvent?) {
        updateOutput("onSeeked()")
    }

    override fun onVisualQuality(visualQualityEvent: VisualQualityEvent?) {
        updateOutput("onVisualQuality()")
    }

    override fun onAdClick(adClickEvent: AdClickEvent?) {
        updateOutput("onAdClick()")
    }

    override fun onAdComplete(adCompleteEvent: AdCompleteEvent?) {
        updateOutput("onAdComplete()")
    }

    override fun onAdSkipped(adSkippedEvent: AdSkippedEvent?) {
        updateOutput("onAdSkipped()")
    }

    override fun onAdError(adErrorEvent: AdErrorEvent?) {
        updateOutput("onAdError()")
    }

    override fun onAdImpression(adImpressionEvent: AdImpressionEvent?) {
        updateOutput("onAdImpression()")
    }

    override fun onAdTime(adTimeEvent: AdTimeEvent?) {
        updateOutput("onAdTime()")
    }

    override fun onAdPause(adPauseEvent: AdPauseEvent?) {
        updateOutput("onAdPause()")
    }

    override fun onAdPlay(adPlayEvent: AdPlayEvent?) {
        updateOutput("onAdPlay()")
    }

    override fun onAdSchedule(adScheduleEvent: AdScheduleEvent?) {
        updateOutput("onAdSchedule()")
        adScheduleEvent?.let {
            val tag = it.tag
            updateOutput("onAdSchedule() >>>> " + tag)
        }
    }

    override fun onBeforePlay(beforePlayEvent: BeforePlayEvent?) {
        updateOutput("onBeforePlay()")
    }

    override fun onBeforeComplete(beforeCompleteEvent: BeforeCompleteEvent?) {
        updateOutput("onBeforeComplete()")
    }
}