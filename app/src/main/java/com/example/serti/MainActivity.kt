package com.example.serti

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.serti.data.local.SessionDao
import com.example.serti.ui.login.LoginActivity
import com.example.serti.ui.users.UsersActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sessionDao: SessionDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch(Dispatchers.IO) {
            val session = sessionDao.getSession()
            launch(Dispatchers.Main) {
                if (session != null) {
                    startActivity(Intent(this@MainActivity, UsersActivity::class.java))
                } else {
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                }
                finish()
            }
        }
    }
}
