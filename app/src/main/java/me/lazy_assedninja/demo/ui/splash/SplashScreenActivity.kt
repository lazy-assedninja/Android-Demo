package me.lazy_assedninja.demo.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import me.lazy_assedninja.demo.library.ui.BaseActivity
import me.lazy_assedninja.demo.ui.index.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}