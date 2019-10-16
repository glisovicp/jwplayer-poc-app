package rs.pglisovic.jwplayerdemoandroidapp

import android.content.Context
import com.google.android.gms.cast.CastMediaControlIntent
import com.google.android.gms.cast.framework.CastOptions
import com.google.android.gms.cast.framework.OptionsProvider
import com.google.android.gms.cast.framework.SessionProvider
import com.google.android.gms.cast.framework.media.CastMediaOptions
import com.google.android.gms.cast.framework.media.MediaIntentReceiver
import com.google.android.gms.cast.framework.media.NotificationOptions
import rs.pglisovic.jwplayerdemoandroidapp.activities.VideoActivity
import java.util.*

class CastOptionsProvider : OptionsProvider {
    override fun getAdditionalSessionProviders(context: Context?): MutableList<SessionProvider> {
        return mutableListOf()
    }

    /**
     * The Application Id to use, currently the Default Media Receiver.
     */
    private val DEFAULT_APPLICATION_ID =
        CastMediaControlIntent.DEFAULT_MEDIA_RECEIVER_APPLICATION_ID

    override fun getCastOptions(context: Context?): CastOptions {
        val notificationOptions = NotificationOptions.Builder()
            .setActions(
                Arrays.asList(
                    MediaIntentReceiver.ACTION_SKIP_NEXT,
                    MediaIntentReceiver.ACTION_TOGGLE_PLAYBACK,
                    MediaIntentReceiver.ACTION_STOP_CASTING
                ), intArrayOf(1, 2)
            )
            .setTargetActivityClassName(VideoActivity::class.java!!.getName())
            .build()

        val mediaOptions = CastMediaOptions.Builder()
            .setNotificationOptions(notificationOptions)
            .build()

        return CastOptions.Builder()
            .setReceiverApplicationId(DEFAULT_APPLICATION_ID)
            .setCastMediaOptions(mediaOptions)
            .build()
    }
}