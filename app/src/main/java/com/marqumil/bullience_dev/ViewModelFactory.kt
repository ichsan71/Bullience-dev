package com.marqumil.bullience_dev

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marqumil.bullience_dev.data.NewsRepository
import com.marqumil.bullience_dev.ui.screen.detail.detailNews.DetailNewsViewModel
import com.marqumil.bullience_dev.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: NewsRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailNewsViewModel::class.java)) {
            return DetailNewsViewModel(repository) as T
        }
//        else if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
//            return CartViewModel(repository) as T
//        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}