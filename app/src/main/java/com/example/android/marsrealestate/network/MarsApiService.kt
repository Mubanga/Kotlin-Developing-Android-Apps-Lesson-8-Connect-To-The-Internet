/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://mars.udacity.com/"

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

/**
 *  This Retrofit object will be responsible for taking our Web Service and generating understandable/parsed
 *  objects for the rest of our
 *
 */

private val retrofit = Retrofit.Builder()
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

interface MarsApiService {
    /**
     *  Thanks To Moshi Retrofit Will Now Parse The Returned Json String From The Server And Construct/Build
     *  Nice Kotlin Data class Objects For Us Hence....MarsProperty.
     *  The Use Of Deffered Is So Retrofit Replaces The Default Callback With A Coroutine
     */
    @GET("realestate")
    fun getProperties():
            Deferred<List<MarsProperty>>

}

/**
 *  The MarsApi is a public object so that the entire application has access to it. This will also
 *  probably be a singleton because creating more than one instance of it is rather expensive and time consuming
 */
object MarsApi {
    val retrofitService : MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}
