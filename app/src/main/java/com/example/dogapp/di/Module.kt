package com.example.dogapp.di

import androidx.fragment.app.ListFragment
import com.example.dogapp.BuildConfig
import com.example.dogapp.repository.DogRepository
import com.example.dogapp.dogcase.DogUseCase
import com.example.dogapp.network.AuthInterceptor
import com.example.dogapp.network.DogApi
import com.example.dogapp.viewmodel.DetailViewModel
import com.example.dogapp.viewmodel.MainViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Module {
    val moduleDI = module{
        single { DogRepository(context = get(), dogApi = get()) }
        single { DogUseCase(dogRepository = get()) }
        single { ListFragment() }


        viewModel { DetailViewModel(useCase = get()) }
        viewModel { MainViewModel(dogUseCase = get()) }
    }

    val networkModule = module {
        factory { AuthInterceptor() }
        factory { provideOkHttpClient(get()) }
        factory { provideDogApi(get()) }
        single { provideRetrofit(get()) }
    }

    private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(BuildConfig.HOST).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()

    private fun provideOkHttpClient(authInterceptor: AuthInterceptor) =
        OkHttpClient().newBuilder().addInterceptor(authInterceptor).build()


    private fun provideDogApi(retrofit: Retrofit): DogApi = retrofit.create(DogApi::class.java)
}