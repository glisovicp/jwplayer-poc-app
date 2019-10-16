package rs.pglisovic.jwplayerdemoandroidapp.handlers

import android.view.Window
import android.view.WindowManager
import com.longtailvideo.jwplayer.JWPlayerView
import com.longtailvideo.jwplayer.events.*
import com.longtailvideo.jwplayer.events.listeners.AdvertisingEvents
import com.longtailvideo.jwplayer.events.listeners.VideoPlayerEvents

/**
 * Sets the FLAG_KEEP_SCREEN_ON flag during playback - disables it when playback is stopped
 */
public class KeepScreenOnHandler(jwPlayerView: JWPlayerView, window: Window) : VideoPlayerEvents.OnPlayListener,
    VideoPlayerEvents.OnPauseListener,
    VideoPlayerEvents.OnCompleteListener,
    VideoPlayerEvents.OnErrorListener,
    AdvertisingEvents.OnAdPlayListener,
    AdvertisingEvents.OnAdPauseListener,
    AdvertisingEvents.OnAdCompleteListener,
    AdvertisingEvents.OnAdSkippedListener,
    AdvertisingEvents.OnAdErrorListener {

    /**
     * The application window
     */
    private lateinit var mWindow: Window


    init {
        jwPlayerView.addOnPlayListener(this)
        jwPlayerView.addOnPauseListener(this)
        jwPlayerView.addOnCompleteListener(this)
        jwPlayerView.addOnErrorListener(this)
        jwPlayerView.addOnAdPlayListener(this)
        jwPlayerView.addOnAdPauseListener(this)
        jwPlayerView.addOnAdCompleteListener(this)
        jwPlayerView.addOnAdSkippedListener(this)
        jwPlayerView.addOnAdErrorListener(this)
        mWindow = window
    }

    private fun updateWakeLock(enable: Boolean) {
        if (enable) {
            mWindow.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            mWindow.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    override fun onError(errorEvent: ErrorEvent) {
        updateWakeLock(false)
    }

    override fun onComplete(completeEvent: CompleteEvent) {
        updateWakeLock(false)
    }

    override fun onPause(pauseEvent: PauseEvent) {
        updateWakeLock(false)

    }

    override fun onPlay(playEvent: PlayEvent) {
        updateWakeLock(true)
    }

    override fun onAdPlay(adPlayEvent: AdPlayEvent?) {
        updateWakeLock(true)
    }

    override fun onAdPause(adPauseEvent: AdPauseEvent?) {
        updateWakeLock(true)
    }

    override fun onAdComplete(adCompleteEvent: AdCompleteEvent?) {
        updateWakeLock(true)
    }

    override fun onAdSkipped(adSkippedEvent: AdSkippedEvent?) {
        updateWakeLock(true)
    }

    override fun onAdError(adErrorEvent: AdErrorEvent?) {
        updateWakeLock(true)
    }
}