package rs.pglisovic.jwplayerdemoandroidapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.google.android.gms.cast.framework.CastButtonFactory
import com.google.android.gms.cast.framework.CastContext
import com.longtailvideo.jwplayer.JWPlayerSupportFragment
import com.longtailvideo.jwplayer.JWPlayerView
import com.longtailvideo.jwplayer.configuration.*
import com.longtailvideo.jwplayer.media.ads.AdBreak
import com.longtailvideo.jwplayer.media.ads.AdSource
import com.longtailvideo.jwplayer.media.ads.AdType
import com.longtailvideo.jwplayer.media.ads.Advertising
import com.longtailvideo.jwplayer.media.captions.Caption
import com.longtailvideo.jwplayer.media.playlists.PlaylistItem
import org.json.JSONObject
import rs.pglisovic.jwplayerdemoandroidapp.Constants
import rs.pglisovic.jwplayerdemoandroidapp.Feature
import rs.pglisovic.jwplayerdemoandroidapp.R
import rs.pglisovic.jwplayerdemoandroidapp.handlers.JWEventHandler
import rs.pglisovic.jwplayerdemoandroidapp.handlers.KeepScreenOnHandler
import java.io.IOException
import java.util.ArrayList

class VideoActivity : AppCompatActivity() {

    companion object {
        val TAG = VideoActivity::class.java.simpleName
    }

    private var featureToPresent: Feature? = null

    private var mpx: String? = null
    private var configResource: String? = null

    private lateinit var featureTV: TextView
    private lateinit var testabilityTV: TextView

    /**
     * A reference to the [JWPlayerSupportFragment].
     */
    private var playerFragment: JWPlayerSupportFragment? = null

    /**
     * A reference to the [JWPlayerView] used by the JWPlayerSupportFragment.
     */
    private var playerView: JWPlayerView? = null

    /**
     * An instance of our event handling class
     */
    private var eventHandler: JWEventHandler? = null

    /**
     * Reference to the [CastContext]
     */
    private var castContext: CastContext? = null


    private var playerConfig: PlayerConfig? = null
    private var skinConfig: SkinConfig? = null
    private var logoConfig: LogoConfig? = null
    private var sharingConfig: SharingConfig? = null
    private var advertisingConfig: Advertising? = null
    private var captionsConfig: CaptionsConfig? = null

    private val playerConfigBuilder = PlayerConfig.Builder()

    private var playbackRates = floatArrayOf(0.5f, 1f, 2f)

    // Create a list of Caption objects to represent the captions tracks
    private var captionTracks: MutableList<Caption> = ArrayList()

    // Create a list to contain the PlaylistItems
    private var playlist: MutableList<PlaylistItem> = ArrayList()

    private var adSchedule: ArrayList<AdBreak>? = null


    // Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        setupAppBar()

        featureTV = findViewById(R.id.featureTV)
        testabilityTV = findViewById(R.id.testabilityTV)

        featureToPresent = intent.extras.getSerializable(Constants.EXTRA_FEATURE) as Feature?     //intent.extras?.getString(Constants.EXTRA_FEATURE)
        featureToPresent?.let {
            featureTV.text = it.title
        }


        configResource = loadJSONFromAsset("config.json")

        if (!TextUtils.isEmpty(configResource)) {
            val configObj = JSONObject(configResource)
            val skinObj = configObj.getJSONObject("skin")
            val logoObj = configObj.getJSONObject("logo")
            val advertisingObj = configObj.getJSONObject("advertising")
            val metadataObj = configObj.getJSONObject("metadata")

            // prepare list items
            val pi1 = PlaylistItem.Builder()
                .file(Constants.DEMO_URL_HLS)
                .title(getString(R.string.bikerTitle))
                .description(getString(R.string.bikerDescription))
                .build()

            val pi2 = PlaylistItem.Builder()
                .file(Constants.DEMO_URL_HLS2)
                .title(getString(R.string.bipbopTitle))
                .description(getString(R.string.bipbopDescription))
                .build()

            val pi3 = PlaylistItem.Builder()
                .file(Constants.DEMO_URL_MP4)
                .title(getString(R.string.stream3Title))
                .description(getString(R.string.stream3Description))
                .build()

            val pi4 = PlaylistItem.Builder()
                .file(Constants.DEMO_URL_DASH)
                .title(getString(R.string.dashTitle))
                .description(getString(R.string.dashDescription))
                .build()

            val pi5 = PlaylistItem.Builder()
                .file(Constants.DEMO_URL_SS)
                .title(getString(R.string.ssTitle))
                .description(getString(R.string.ssDescription))
                .build()


            // TODO: make methods for every feature setup and then call inside when
            when (featureToPresent) {
                Feature.SUBTITLES -> {
                    // Create a Caption pointing to English subtitles and add it to the list
                    val captionEn = Caption.Builder().file("english.srt").label("English SRT").isdefault(true).build()
                    captionTracks.add(captionEn)

                    // Create a Caption pointing to French subtitles, this time using the Builder
                    val captionFr = Caption.Builder().file("french.srt").label("French SRT").build()
                    captionTracks.add(captionFr)

                    // Create a Caption pointing to English VTT subtitles, this time using the Builder
                    val captionEnVTT = Caption.Builder().file("english.vtt").label("English VTT").build()
                    captionTracks.add(captionEnVTT)

                    // Create a Caption pointing to English SRT subtitles, this time using the Builder
                    val captionRemote = Caption.Builder().file("https://raw.githubusercontent.com/andreyvit/subtitle-tools/master/sample.srt").label("English SRT Remote").build()
                    captionTracks.add(captionRemote)

                    pi1.setCaptions(captionTracks)


                    testabilityTV.text = getString(R.string.subtitlesDescription)
                }
                Feature.TAPKEYTOSCRUP -> {
                    testabilityTV.text = getString(R.string.tapToScrupDescription)
                }
                Feature.PLAYLIST -> {
                    // Add a PlaylistItem into playlist
                    playlist.add(pi1)
                    playlist.add(pi2)
                    playlist.add(pi3)
                    playlist.add(pi4)
                    playlist.add(pi5)
                }
                Feature.FULLSCREEN -> {

                }
                Feature.STARTVIDEOATSECOND -> {
                    playerConfigBuilder.autostart(true)
                }
                Feature.SOCIALSHARINGCUSTOMURL -> {
                    sharingConfig = SharingConfig.Builder()
                        .heading("Sth to share on social networks")
                        .code("Code")
                        .link("https://www.google.com")
                        .build()

                    playerConfigBuilder.sharingConfig(sharingConfig)
                }
                Feature.AUTOPLAY -> {
                    playerConfigBuilder.autostart(true)
                        .mute(true)
                }
                Feature.LOOPVIDEO -> {
                    playerConfigBuilder.repeat(true)
                }
                Feature.CONTROLVISIBILITYOFCONTROLS -> {
                    playerConfigBuilder.autostart(true)
                        .controls(false)
                }
                Feature.DRMSUPPORT -> {
                    testabilityTV.text = getString(R.string.drmSupportDescription)
                }
                Feature.PLAYBACKRATES -> {
                    playerConfigBuilder.playbackRates(PlaybackRateConfig.Factory.createPlaybackRateConfig(playbackRates))
                }
                Feature.PLAYBACKQUALITY -> {
                    testabilityTV.text = getString(R.string.playbackQualityDescription)
                }
                Feature.FREEWHEELADS -> {
                    testabilityTV.text = getString(R.string.freewheelAdsDescription)
                }
                Feature.ADS -> {
                    val adBreakPre = AdBreak.Builder()
                        .tag(Constants.DEMO_URL_AD_PREROLL2)
                        .offset("pre")
                        .build()

                    val adBreakMid1 = AdBreak.Builder()
                        .tag(Constants.DEMO_URL_AD_MIDROLL1)
                        .offset("25%")
                        .build()

                    val adBreakMid2 = AdBreak.Builder()
                        .tag(Constants.DEMO_URL_AD_MIDROLL2)
                        .offset("50%")
                        .build()

                    val adBreakMid3 = AdBreak.Builder()
                        .tag(Constants.DEMO_URL_AD_MIDROLL3)
                        .offset("75%")
                        .build()

                    val adBreakPost = AdBreak.Builder()
                        .tag(Constants.DEMO_URL_AD_POSTROLL)
                        .offset("post")
                        .build()

                    adSchedule = arrayListOf(adBreakPre, adBreakMid1, adBreakMid2, adBreakMid3, adBreakPost)

                    advertisingConfig = Advertising(AdSource.VAST, adSchedule)
                    playerConfigBuilder.advertising(advertisingConfig!!)
                        .autostart(true)

                    playlist.add(pi3)

                    testabilityTV.text = getString(R.string.adsDescription)
                }
                Feature.OVERLAYADS -> {
                    // Set the ad break offset
                    val adBreakNonLinear1 = AdBreak("25%", AdSource.VAST, Constants.DEMO_URL_AD_OVERLAY)
                    val adBreakNonLinear2 = AdBreak("75%", AdSource.VAST, Constants.DEMO_URL_AD_OVERLAY)

                    // Specify the AdType as NONLINEAR
                    adBreakNonLinear1.adType = AdType.NONLINEAR
                    adBreakNonLinear2.adType = AdType.NONLINEAR

                    adSchedule = arrayListOf(adBreakNonLinear1, adBreakNonLinear2)

                    // Set the ad schedule to your video
                    pi1.adSchedule = adSchedule

                    playerConfigBuilder.autostart(true)

                    playlist.add(pi1)

                }
                else -> {
                    Log.d(TAG, "Don't know how it ends here")
                }
            }

            playerConfig = playerConfigBuilder.build()

            // Construct a new JWPlayerSupportFragment (since we're using AppCompatActivity)
            playerFragment = JWPlayerSupportFragment.newInstance(playerConfig)

            playerFragment?.let {
                // Attach the Fragment to our layout
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, it)
                    .commit()

                // Make sure all the pending fragment transactions have been completed, otherwise
                // mPlayerFragment.getPlayer() may return null
                supportFragmentManager.executePendingTransactions()

                // Get a reference to the JWPlayerView from the fragment
                playerView = it.player

                playerView?.let { view ->

                    // load videos
                    when (featureToPresent) {
                        Feature.PLAYLIST, Feature.ADS -> view.load(playlist)
                        Feature.PLAYBACKQUALITY -> view.load(pi5)
                        Feature.STARTVIDEOATSECOND -> {
                            view.load(pi1)
                            view.seek(10.0)
                        }
                        else -> view.load(pi1)
                    }

                    // Keep the screen on during playback
                    KeepScreenOnHandler(view, window)

                    // Instantiate the JW Player event handler class
                    eventHandler = JWEventHandler(this@VideoActivity, view, featureTV)

                    // Get a reference to the CastContext
                    castContext = CastContext.getSharedInstance(applicationContext)

                    playerView
                }
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_videoactivity, menu)

        // Register the MediaRouterButton
        CastButtonFactory.setUpMediaRouteButton(
            applicationContext, menu,
            R.id.media_route_menu_item
        )

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Exit fullscreen when the user pressed the Back button
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            playerView?.let {
                if (it.fullscreen) {
                    it.setFullscreen(false, true)
                    return false
                }
            }
        }

        return super.onKeyDown(keyCode, event)
    }

    // Helper methods

    fun loadJSONFromAsset(jsonFile: String): String? {
        var json: String? = null
        try {
            val inputStream = assets.open(jsonFile)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }

        return json
    }

    private fun setupAppBar() {
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setLogo(R.mipmap.ic_launcher)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }
}
