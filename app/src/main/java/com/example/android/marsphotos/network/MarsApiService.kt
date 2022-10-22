package com.example.android.marsphotos.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "https://android-kotlin-fun-mars-server.appspot.com"

/**
 * Crea el objeto Moshi con la fábrica de adaptadores Kotlin que utilizará Retrofit.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * El objeto Retrofit con el convertidor Moshi.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 * Interfaz pública que expone el método [getPhotos]
 */
interface MarsApiService {
    /**
     * Devuelve una [List] de [MarsPhoto] y este método se puede llamar desde Coroutine.
     * La anotación @GET indica que el extremo "fotos" se solicitará con el GET
     * Método HTTP
     */
    @GET("photos")
    suspend fun getPhotos() : List<MarsPhoto>
}

/**
 * Objeto Api público que expone el servicio Retrofit con inicialización diferida
 */
object MarsApi {
    val retrofitService: MarsApiService by lazy { retrofit.create(MarsApiService::class.java) }
}