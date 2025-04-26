package com.example.serti.presenter.users

import com.example.serti.model.UserModel


interface UsersContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showUsers(list: List<UserModel>)
        fun showError(msg: String)
        fun showHistory(history: List<String>)
        fun updatePagination(current: Int, total: Int)
    }
    interface Presenter {
        fun attach(view: View)
        fun detach()
        fun loadUsers(page: Int = 1)
        fun onSearch(query: String)
        fun selectHistory(query: String)
        fun resetSearch()
        fun deleteHistory(keyword: String)
    }
}
