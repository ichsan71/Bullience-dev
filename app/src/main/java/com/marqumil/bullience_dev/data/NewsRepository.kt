package com.marqumil.bullience_dev.data


import com.marqumil.bullience_dev.model.FakeNewsDataSource
import com.marqumil.bullience_dev.model.News
import com.marqumil.bullience_dev.model.TotalNews
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class NewsRepository {

    private val totalNewses = mutableListOf<TotalNews>()

    init {
        if (totalNewses.isEmpty()) {
            FakeNewsDataSource.dummyCourse.forEach {
                totalNewses.add(TotalNews(it, 0))
            }
        }
    }

    fun getAllNews(): Flow<List<TotalNews>> {
        return flowOf(totalNewses)
    }

    fun getTotalNewsById(courseId: Long): TotalNews {
        return totalNewses.first {
            it.news.id == courseId
        }
    }

    fun updateNews(courseId: Long, newCountValue: Int): Flow<Boolean> {
        val index = totalNewses.indexOfFirst { it.news.id == courseId }
        val result = if (index >= 0) {
            val totalNews = totalNewses[index]
            totalNewses[index] =
                totalNews.copy(news = totalNews.news, count = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedNews(): Flow<List<TotalNews>> {
        return getAllNews()
            .map { orderCourses ->
                orderCourses.filter { orderCourse ->
                    orderCourse.count != 0
                }
            }
    }

    companion object {
        @Volatile
        private var instance: NewsRepository? = null

        fun getInstance(): NewsRepository =
            instance ?: synchronized(this) {
                NewsRepository().apply {
                    instance = this
                }
            }
    }
}