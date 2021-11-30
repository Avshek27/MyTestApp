package com.test.ui

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.test.BaseActivity
import com.test.R
import com.test.viewmodel.LSViewModel


class MainActivity : BaseActivity() {

    private lateinit var vm: LSViewModel
    var locationManager: LocationManager? = null
    private var latitude: String? = null
    private var longitude: String? = null
    private lateinit var progress: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vm = ViewModelProvider(this).get(LSViewModel::class.java)
        progress = findViewById(R.id.progress)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            setGPSOn()
        } else {
            getLocation()
        }
        val location = vm.getActiveLocation()
        if (location.isNotEmpty())
            addFragmentToActivity(
                WeatherReportFragment.newInstance(location[0].woeid),
                ""
            )
        else
            appHome()
    }

    fun appHome() {
        addFragmentToActivity(
            SearchFragment.newInstance("$latitude,$longitude"),
            ""
        )
    }

    private fun getLocation() {
        if ((ContextCompat.checkSelfPermission(
                this@MainActivity, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED)
        ) {
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (!locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                setGPSOn()
            } else {
                locationManager =
                    applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager
                val providers: List<String> = locationManager!!.getProviders(true)
                for (provider in providers) {
                    val l: Location = locationManager!!.getLastKnownLocation(provider) ?: continue
                    val lat = l.latitude
                    val longi = l.longitude
                    latitude = lat.toString()
                    longitude = longi.toString()
                    Toast.makeText(this, "Location. $latitude, $longitude", Toast.LENGTH_SHORT)
                        .show()

                }
            }
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1
                )
            } else {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1
                )
            }
        }
    }

    private fun setGPSOn() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton(
            "Yes"
        ) { _, _ -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
            .setNegativeButton(
                "No"
            ) { dialog, _ -> dialog.cancel() }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    // create an action bar button
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_history_action, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // handle button activities
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.search_history) {
            addFragmentToActivity(
                SearchHistoryFragment.newInstance(),
                getString(R.string.search_history_fragment)
            )
        }
        return super.onOptionsItemSelected(item)
    }
}