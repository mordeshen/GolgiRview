package com.e.golgirview.api

import androidx.lifecycle.LiveData
import com.e.golgirview.api.response.ItemResponse
import com.e.golgirview.util.GenericApiResponse
import retrofit2.http.GET

interface ApiService {
    @GET("api/random")
    fun getItemFromApi(): LiveData<GenericApiResponse<List<ItemResponse>>>
}