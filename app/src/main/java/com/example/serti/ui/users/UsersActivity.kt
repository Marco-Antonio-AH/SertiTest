package com.example.serti.ui.users

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.serti.R
import com.example.serti.data.local.SessionDao
import com.example.serti.databinding.ActivityUsersBinding
import com.example.serti.model.UserModel
import com.example.serti.presenter.users.UsersContract
import com.example.serti.ui.detail.DetailActivity
import com.example.serti.ui.login.LoginActivity
import com.example.serti.utils.hide
import com.example.serti.utils.show
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UsersActivity : AppCompatActivity(), UsersContract.View {
    @Inject lateinit var presenter: UsersContract.Presenter
    @Inject lateinit var sessionDao: SessionDao

    private lateinit var binding: ActivityUsersBinding
    private lateinit var usersAdapter: UsersAdapter

    private var lastTag: String? = null
    private var currentPage = 1
    private var totalPages = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.attach(this)
        binding.drawerLayout.setScrimColor(
            ContextCompat.getColor(this, android.R.color.transparent)
        )

        usersAdapter = UsersAdapter { id ->
            startActivity(
                Intent(this, DetailActivity::class.java)
                    .putExtra("USER_ID", id)
            )
        }
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(this@UsersActivity)
            adapter = usersAdapter
        }

        binding.searchView.apply {
            setIconifiedByDefault(true)
            isIconified = true
            setOnClickListener { isIconified = false }
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    presenter.onSearch(query.orEmpty())
                    clearFocus()
                    return true
                }
                override fun onQueryTextChange(newText: String?) = if (newText.isNullOrEmpty()) {
                    presenter.resetSearch(); true
                } else true
            })
            setOnCloseListener { presenter.resetSearch(); false }
        }

        binding.btnPrev.setOnClickListener { loadPage(currentPage - 1) }
        binding.btnNext.setOnClickListener { loadPage(currentPage + 1) }
        updatePageButtons()

        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar,
            R.string.open, R.string.close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener {
            if (it.itemId == R.id.nav_logout) {
                val dialogView = layoutInflater.inflate(R.layout.dialog_logout, null)
                val dialog = MaterialAlertDialogBuilder(this)
                    .setView(dialogView)
                    .create()

                dialogView.findViewById<TextView>(R.id.btnCancel).setOnClickListener {
                    dialog.dismiss()
                }
                dialogView.findViewById<TextView>(R.id.btnAccept).setOnClickListener {
                    dialog.dismiss()
                    presenter.detach()
                    CoroutineScope(Dispatchers.IO).launch { sessionDao.clearSession() }
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }

                dialog.show()
                true
            } else {
                false
            }
        }


        loadPage(1)
    }

    private fun loadPage(page: Int) {
        currentPage = page.coerceIn(1, totalPages)
        updatePageButtons()
        presenter.loadUsers(page)
    }

    private fun updatePageButtons() {
        binding.btnPrev.isEnabled = currentPage > 1
        binding.btnNext.isEnabled = currentPage < totalPages
        binding.tvPage.text = getString(R.string.page_indicator, currentPage, totalPages)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val rect = Rect().also { binding.searchView.getGlobalVisibleRect(it) }
            if (binding.searchView.hasFocus() && !rect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                binding.searchView.clearFocus()
                binding.searchView.isIconified = true
                hideKeyboard()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.windowToken?.let { imm.hideSoftInputFromWindow(it, 0) }
    }

    override fun showLoading() = binding.progress.show()
    override fun hideLoading() = binding.progress.hide()
    override fun showUsers(list: List<UserModel>) = usersAdapter.submitList(list)
    override fun showHistory(history: List<String>) {
        binding.historyContainer.removeAllViews()
        history.forEach { text ->
            val chip = Chip(this).apply {
                this.text = text
                chipIcon = null
                isChipIconVisible = false
                isCloseIconVisible = true
                setOnCloseIconClickListener { presenter.deleteHistory(text) }
                setOnClickListener {
                    if (lastTag == text) {
                        lastTag = null
                        binding.searchView.setQuery("", false)
                        presenter.resetSearch()
                    } else {
                        lastTag = text
                        binding.searchView.setQuery(text, true)
                        presenter.selectHistory(text)
                    }
                }
            }
            binding.historyContainer.addView(chip)
        }
    }

    override fun updatePagination(current: Int, total: Int) {
        currentPage = current
        totalPages = total
        updatePageButtons()
    }

    override fun showError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }
}
