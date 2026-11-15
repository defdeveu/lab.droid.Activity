package defdev.labdroid.activity

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import defdev.labdroid.activity.service.DefaultLoginService

class AppContainer {
    private val service = DefaultLoginService()
    val viewModelFactory: ViewModelProvider.Factory = viewModelFactory {
        initializer { LoginViewModel(service) }
    }
}
