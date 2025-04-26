package com.example.serti.presenter.login

interface LoginContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun onLoginSuccess()
        fun onLoginError(msg: String)
    }

    interface Presenter {
        fun attach(view: View)
        fun detach()
        fun doLogin(email: String, password: String)
    }
}
