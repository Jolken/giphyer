package dev.jolken.giphyer.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import dev.jolken.giphyer.R
import dev.jolken.giphyer.databinding.MainActivityBinding
import dev.jolken.giphyer.fragments.MainFragment
import dev.jolken.giphyer.models.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out,android.R.anim.fade_in,android.R.anim.fade_out)
                .replace(R.id.container, MainFragment())
                .commitNow()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}