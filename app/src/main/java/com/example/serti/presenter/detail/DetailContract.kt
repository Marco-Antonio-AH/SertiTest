package com.example.serti.presenter.detail

import com.example.serti.model.UserModel

interface DetailContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showUser(user: UserModel)
        fun showError(msg: String)
    }
    interface Presenter {
        fun attach(view: View)
        fun detach()
        fun loadDetail(id: Int)
    }
}
