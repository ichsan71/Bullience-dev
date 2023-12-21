package com.marqumil.bullience_dev.data.di

import com.marqumil.bullience_dev.data.NewsRepository

object Injection {
    fun provideRepository(): NewsRepository {
        return NewsRepository.getInstance()
    }
}