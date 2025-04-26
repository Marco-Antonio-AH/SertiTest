package com.example.serti.di



import com.example.serti.presenter.detail.DetailContract
import com.example.serti.presenter.detail.DetailPresenter
import com.example.serti.presenter.login.LoginContract
import com.example.serti.presenter.login.LoginPresenter
import com.example.serti.presenter.register.RegisterContract
import com.example.serti.presenter.register.RegisterPresenter
import com.example.serti.presenter.users.UsersContract
import com.example.serti.presenter.users.UsersPresenter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class PresenterModule {

    @Binds
    abstract fun bindDetailPresenter(p: DetailPresenter): DetailContract.Presenter

    @Binds
    abstract fun bindLoginPresenter(p: LoginPresenter): LoginContract.Presenter

    @Binds
    abstract fun bindRegisterPresenter(p: RegisterPresenter): RegisterContract.Presenter

    @Binds
    abstract fun bindUsersPresenter(p: UsersPresenter): UsersContract.Presenter
}
