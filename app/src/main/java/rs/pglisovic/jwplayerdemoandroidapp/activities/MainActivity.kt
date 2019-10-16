package rs.pglisovic.jwplayerdemoandroidapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import rs.pglisovic.jwplayerdemoandroidapp.Constants
import rs.pglisovic.jwplayerdemoandroidapp.Feature
import rs.pglisovic.jwplayerdemoandroidapp.R
import rs.pglisovic.jwplayerdemoandroidapp.adapters.DataAdapter
import rs.pglisovic.jwplayerdemoandroidapp.extensions.showAlert

class MainActivity : AppCompatActivity(), DataAdapter.DataAdapterListener {

    private lateinit var recyclerView: RecyclerView

    // Initializing an empty ArrayList to be filled with features
    val features = listOf(Feature.SUBTITLES,
        Feature.SCRUBBINGWITHPREVIEW, Feature.TAPKEYTOSCRUP, Feature.PLAYLIST,
        Feature.FULLSCREEN, Feature.STARTVIDEOATSECOND,
        Feature.SOCIALSHARINGCUSTOMURL, Feature.INPLAYERNOTIFICATIONS, Feature.AUTOPLAY,
        Feature.LOOPVIDEO, Feature.CONTROLVISIBILITYOFCONTROLS, Feature.DRMSUPPORT,
        Feature.PLAYBACKRATES, Feature.PLAYBACKQUALITY, Feature.FREEWHEELADS,
        Feature.ADS, Feature.OVERLAYADS, Feature.CASTING,
        Feature.CASTINGADS, Feature.EVENTHEARTBEAT, Feature.DOWNLOADVIDEO/**s,
        Feature.SKINNING*/)

    private lateinit var dataAdapter: DataAdapter

    private var alertDialog: AlertDialog? = null

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    // Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupAppBar()

        recyclerView = findViewById(R.id.recyclerView)

        dataAdapter = DataAdapter(this, features, this)

        recyclerView.apply {
            setHasFixedSize(true)                                                               // items are static and will not change for significantly smoother scrolling
            layoutManager = LinearLayoutManager(this@MainActivity)                   // Creates a vertical Layout Manager
            adapter = dataAdapter
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Listener methods

    override fun onItemClicked(item: Feature) {
        Log.d(TAG, "Item: $item")

        if (!item.available) {
            showAlertDialog(item.title)
        } else {
            val intent = Intent(this@MainActivity, VideoActivity::class.java)
            intent.putExtra(Constants.EXTRA_FEATURE, item)
            startActivity(intent)
        }
    }

    // Helper methods

    private fun showAlertDialog(title: String) {
        showAlert(title, getString(R.string.feature_not_supported))
    }

    private fun setupAppBar() {
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setLogo(R.mipmap.ic_launcher)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }
}
