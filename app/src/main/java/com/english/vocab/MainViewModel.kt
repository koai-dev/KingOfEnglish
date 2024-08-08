package com.english.vocab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.english.vocab.domain.usecase.GetCountryCodeUseCase
import com.english.vocab.utils.AppConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val getCountryCodeUseCase: GetCountryCodeUseCase) : ViewModel() {
    fun checkLocale() {
        viewModelScope.launch(Dispatchers.IO) {
            val country = getCountryCodeUseCase.execute()
            AppConfig.locale = country.countryCode ?: "vn"
        }
    }
}
