package com.example.serti.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.serti.databinding.ActivityLoginBinding
import com.example.serti.presenter.login.LoginContract
import com.example.serti.ui.register.RegisterActivity
import com.example.serti.ui.users.UsersActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), LoginContract.View {
    @Inject
    lateinit var presenter: LoginContract.Presenter
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(saved: Bundle?) {
        super.onCreate(saved)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.attach(this)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val pass = binding.etPassword.text.toString()
            presenter.doLogin(email, pass)
        }
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun showLoading() {
        binding.progress.visibility = View.VISIBLE
    }
    override fun hideLoading() {
        binding.progress.visibility = View.GONE
    }
    override fun onLoginSuccess() {
        startActivity(Intent(this, UsersActivity::class.java))
        finish()
    }
    override fun onLoginError(msg: String) {
        AlertDialog.Builder(this)
            .setMessage(msg)
            .setPositiveButton("OK", null)
            .show()
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }
}
