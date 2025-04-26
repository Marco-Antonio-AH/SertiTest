package com.example.serti.presenter.detail

import com.example.serti.data.remote.ReqResApi
import com.example.serti.data.remote.mapper.toModel
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.*
import javax.inject.Inject

@ActivityScoped
class DetailPresenter @Inject constructor(
    private val api: ReqResApi
) : DetailContract.Presenter {
    private var view: DetailContract.View? = null
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun attach(view: DetailContract.View) {
        this.view = view
    }

    override fun detach() {
        scope.cancel()
        view = null
    }

    override fun loadDetail(id: Int) {
        view?.showLoading()
        scope.launch {
            try {
                val resp = api.getUser(id)
                val model = resp.data.toModel()
                withContext(Dispatchers.Main) {
                    view?.hideLoading()
                    view?.showUser(model)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view?.hideLoading()
                    view?.showError("No se pudo cargar detalle")
                }
            }
        }
    }
}
