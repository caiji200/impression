package com.me.impression.repository

import com.me.impression.net.ApiFactory
import com.me.impression.service.ApiService
import com.me.impression.service.ApiSource
/**
 * @author: Jie Cai
 * @date: 2021-03-14
 * @desc:
 * */
object RepositoryManager {

    val apiService: ApiSource
        get() = ApiRepository.instance(ApiFactory.instance().create(ApiService::class.java))

    val db:DatabaseRepository  = DatabaseRepository
}