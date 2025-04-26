package com.example.serti.presenter.users

import com.example.serti.data.local.KeywordDao
import com.example.serti.data.local.KeywordEntity
import com.example.serti.data.remote.ReqResApi
import com.example.serti.data.remote.mapper.toModel
import com.example.serti.model.UserModel
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.*
import javax.inject.Inject

@ActivityScoped
class UsersPresenter @Inject constructor(
    private val api: ReqResApi,
    private val keywordDao: KeywordDao
) : UsersContract.Presenter {
    private var view: UsersContract.View? = null
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private var allUsers = emptyList<UserModel>()


    override fun attach(view: UsersContract.View) {
        this.view = view
        scope.launch {
            keywordDao.getAll().collect { entities ->
                val history = entities.map(KeywordEntity::keyword)
                withContext(Dispatchers.Main) {
                    view.showHistory(history)
                }
            }
        }
    }

    override fun detach() {
        scope.cancel()
        view = null
    }

    override fun loadUsers(page: Int) {
        scope.launch(Dispatchers.Main) {
            view?.showLoading()
        }
        scope.launch {
            try {
                val resp = api.listUsers(page)
                allUsers = resp.data.map { it.toModel() }
                withContext(Dispatchers.Main) {
                    view?.hideLoading()
                    view?.showUsers(allUsers)
                    view?.updatePagination(resp.page, resp.total_pages)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view?.hideLoading()
                    view?.showError("Error al cargar usuarios")
                }
            }
        }
    }


    override fun onSearch(query: String) {
        scope.launch(Dispatchers.Main) {
            if (query.isBlank()) {
                view?.showUsers(allUsers)
            } else {
                val filtered = allUsers.filter {
                    it.id.toString().contains(query, true) ||
                            it.email.contains(query, true) ||
                            it.firstName.contains(query, true) ||
                            it.lastName.contains(query, true)
                }
                view?.showUsers(filtered)

                if (filtered.isNotEmpty()) {
                    launch(Dispatchers.IO) {
                        keywordDao.insert(KeywordEntity(query))
                    }
                }
            }
        }
    }


    override fun resetSearch() {
        scope.launch(Dispatchers.Main) {
            view?.showUsers(allUsers)
        }
    }

    override fun selectHistory(query: String) {
        onSearch(query)
    }


    override fun deleteHistory(keyword: String) {
        scope.launch { keywordDao.delete(keyword) }
    }

}
