package com.example.serti.ui.detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.serti.R
import com.example.serti.databinding.ActivityDetailBinding
import com.example.serti.model.UserModel
import com.example.serti.presenter.detail.DetailContract
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : AppCompatActivity(), DetailContract.View {
    @Inject lateinit var presenter: DetailContract.Presenter
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.details)

        presenter.attach(this)
        val id = intent.getIntExtra("USER_ID", 1)
        presenter.loadDetail(id)
    }

    override fun showLoading() {
        binding.progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progress.visibility = View.GONE
    }

    override fun showUser(user: UserModel) {
        // Completa los campos y oculta el ProgressBar
        binding.tvId.setText(user.id.toString())
        binding.tvEmail.setText(user.email)
        binding.tvFirstName.setText(user.firstName)
        binding.tvLastName.setText(user.lastName)
        binding.progress.visibility = View.GONE

        Glide.with(this)
            .load(user.avatar)
            .placeholder(R.drawable.ic_user_placeholder)
            .into(binding.imgAvatar)
    }

    override fun showError(msg: String) {
        binding.progress.visibility = View.GONE
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        if (item.itemId == android.R.id.home) { finish(); true }
        else super.onOptionsItemSelected(item)
}
