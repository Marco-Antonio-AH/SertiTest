package com.example.serti.presenter.register

interface RegisterContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun onRegisterSuccess()
        fun onRegisterError(msg: String)
    }
    interface Presenter {
        fun attach(view: View)
        fun detach()
        fun doRegister(email: String, password: String)
    }
}
