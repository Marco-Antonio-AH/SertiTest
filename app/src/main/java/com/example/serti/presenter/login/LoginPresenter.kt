package com.example.serti.presenter.login

import com.example.serti.data.local.SessionDao
import com.example.serti.data.local.SessionEntity
import com.example.serti.data.remote.ReqResApi
import com.example.serti.data.remote.dto.request.LoginRequest
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.*
import javax.inject.Inject

@ActivityScoped
class LoginPresenter @Inject constructor(
    private val api: ReqResApi,
    private val sessionDao: SessionDao
) : LoginContract.Presenter {
    private var view: LoginContract.View? = null
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun attach(view: LoginContract.View) {
        this.view = view
    }

    override fun detach() {
        scope.cancel()
        view = null
    }

    override fun doLogin(email: String, password: String) {
        view?.showLoading()
        scope.launch {
            try {
                val resp = api.login(LoginRequest(email, password))
                sessionDao.saveSession(SessionEntity(token = resp.token))
                withContext(Dispatchers.Main) {
                    view?.hideLoading()
                    view?.onLoginSuccess()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view?.hideLoading()
                    view?.onLoginError("Usuario/contraseña incorrectos")
                }
            }
        }
    }
}
