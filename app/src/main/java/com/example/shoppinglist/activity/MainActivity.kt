package com.example.shoppinglist.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceManager
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityMainBinding
import com.example.shoppinglist.dialogs.NewListDialog
import com.example.shoppinglist.fragments.FragmentManager
import com.example.shoppinglist.fragments.NoteFragment
import com.example.shoppinglist.fragments.ShopListNamesFragment
import com.example.shoppinglist.settings.SettingsActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


class MainActivity : AppCompatActivity(), NewListDialog.listener {
    private lateinit var binding: ActivityMainBinding
    private var currentMenuItemId = R.id.shopList
    private lateinit var defPref: SharedPreferences
    private var currentTheme = ""
    private var iAD: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        defPref = PreferenceManager.getDefaultSharedPreferences(this)
        currentTheme = defPref.getString("theme_key", "blue").toString()
        super.onCreate(savedInstanceState)
        setTheme(getSelectedTheme())
        setContentView(binding.root)
        setBottomNavListener()
        loadInterAdd()
    }

    private fun loadInterAdd(){
        val request = AdRequest.Builder().build()
        InterstitialAd.load(this, getString(R.string.inter_ad_id), request,
            object : InterstitialAdLoadCallback(){
                override fun onAdLoaded(ad: InterstitialAd) {
                    iAD = ad
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    iAD = null
                }
            })
    }

    private fun showInterAd(addListener: AdListener) {
        if (iAD != null) {
            iAD?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    iAD = null
                    loadInterAdd()
                    addListener.onFinish()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    iAD = null
                    loadInterAdd()
                }

                override fun onAdShowedFullScreenContent() {
                    iAD = null
                    loadInterAdd()
                }
            }
            iAD?.show(this)
        } else {
            addListener.onFinish()
        }
    }

    private fun setBottomNavListener() {
        binding.bNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.settings -> {
                    showInterAd(object : AdListener{
                        override fun onFinish() {
                            startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
                        }

                    })
                }
                R.id.notes -> {
                    currentMenuItemId = R.id.notes
                    FragmentManager.setFragmnet(NoteFragment.newInstance(), this)
                }
                R.id.shopList -> {
                    currentMenuItemId = R.id.shopList
                    FragmentManager.setFragmnet(ShopListNamesFragment.newInstance(), this)
                }
                R.id.newItem -> {
                    FragmentManager.currentFrag?.onClick()
                }
            }
            true
        }
    }

    private fun getSelectedTheme(): Int {
        return if (defPref.getString("theme_key", "blue") == "blue") {
            R.style.Theme_ShoppingListBlue
        } else {
            R.style.Theme_ShoppingListGreen
        }
    }

    override fun onResume() {
        super.onResume()
        binding.bNav.selectedItemId = currentMenuItemId
        if (defPref.getString("theme_key", "blue") != currentTheme) recreate()
    }

    override fun onClick(name: String) {
        Log.d("mylog", "ggg")
    }

    interface AdListener{
        fun onFinish()
    }


}