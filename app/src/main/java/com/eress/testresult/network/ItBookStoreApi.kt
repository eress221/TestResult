package com.eress.testresult.network

import com.eress.testresult.BuildConfig
import com.eress.testresult.common.Common
import com.eress.testresult.model.Book
import com.eress.testresult.model.New
import com.eress.testresult.model.Search
import com.eress.testresult.util.LogUtil
import io.reactivex.Observable
import io.reactivex.internal.util.HalfSerializer.onNext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import okhttp3.CipherSuite
import okhttp3.TlsVersion
import okhttp3.ConnectionSpec
import java.util.*

interface ItBookStoreApi {

    // Search books
    @GET("search/{query}/{page}")
    fun search(@Path("query") query: String,
               @Path("page") page: Int): Observable<Search>

    // New books
    @GET("new")
    fun new(): Observable<New>

    // Detail books by isbn13
    @GET("books/{isbn13}")
    fun detail(@Path("isbn13") isbn13: String): Observable<Book>

    companion object {
        fun create(): ItBookStoreApi {
            val retrofitBuilder = createRetrofitBuilder()

            if (BuildConfig.DEBUG) {
                val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                    LogUtil.d(it)
                })
                logger.level = HttpLoggingInterceptor.Level.BODY
                val httpClient = OkHttpClient
                        .Builder()
                        .addInterceptor(logger)
                        .build()

                retrofitBuilder.client(httpClient)
            }

            // kitkat 4.4.4 이하 HTTPS(SSL/TLS) 지원
            val spec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
                .cipherSuites(
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256,
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA)
                .build()

            val okClient = OkHttpClient.Builder()
                .connectionSpecs(Collections.singletonList(spec))
                .build()

            return retrofitBuilder.client(okClient).build()
                    .create(ItBookStoreApi::class.java)
        }

        private fun createRetrofitBuilder(): Retrofit.Builder {
            return Retrofit.Builder()
                    .baseUrl(Common.IT_BOOKSTORE_API)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
        }
    }
}