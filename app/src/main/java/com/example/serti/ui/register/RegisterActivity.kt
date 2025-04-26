package com.example.serti.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.serti.databinding.ActivityRegisterBinding
import com.example.serti.presenter.register.RegisterContract
import com.example.serti.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity(), RegisterContract.View {
    @Inject
    lateinit var presenter: RegisterContract.Presenter
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.attach(this)

        binding.btnRegister.setOnClickListener {
            presenter.doRegister(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }
    }

    override fun showLoading() {
        binding.progress.visibility = View.VISIBLE
    }
    override fun hideLoading() {
        binding.progress.visibility = View.GONE
    }
    override fun onRegisterSuccess() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
    override fun onRegisterError(msg: String) {
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
