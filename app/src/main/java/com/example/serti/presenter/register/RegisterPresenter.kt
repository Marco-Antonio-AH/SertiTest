package com.example.serti.presenter.register

import com.example.serti.data.local.SessionDao
import com.example.serti.data.local.SessionEntity
import com.example.serti.data.remote.ReqResApi
import com.example.serti.data.remote.dto.request.RegisterRequest
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.*
import javax.inject.Inject

@ActivityScoped
class RegisterPresenter @Inject constructor(
    private val api: ReqResApi,
    private val sessionDao: SessionDao
) : RegisterContract.Presenter {
    private var view: RegisterContract.View? = null
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun attach(view: RegisterContract.View) {
        this.view = view
    }

    override fun detach() {
        scope.cancel()
        view = null
    }

    override fun doRegister(email: String, password: String) {
        view?.showLoading()
        scope.launch {
            try {
                val resp = api.register(RegisterRequest(email, password))
                sessionDao.saveSession(SessionEntity(token = resp.token))
                withContext(Dispatchers.Main) {
                    view?.hideLoading()
                    view?.onRegisterSuccess()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view?.hideLoading()
                    view?.onRegisterError("Registro fallido")
                }
            }
        }
    }
}
