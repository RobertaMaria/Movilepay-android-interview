package com.roberta.movilepay_android_interview.di

import com.roberta.movilepay_android_interview.adapter.CardsAdapter
import com.roberta.movilepay_android_interview.repository.CardsRepository
import com.roberta.movilepay_android_interview.repository.CardsRepositoryImp
import com.roberta.movilepay_android_interview.repository.remote.CardsRemoteDataSource
import com.roberta.movilepay_android_interview.repository.remote.CardsRemoteDataSourceImp
import com.roberta.movilepay_android_interview.repository.webClient.CardsService
import com.roberta.movilepay_android_interview.ui.viewmodel.DetailsAccountViewModel
import com.roberta.movilepay_android_interview.ui.viewmodel.DetailsCardViewModel
import com.roberta.movilepay_android_interview.ui.viewmodel.ListCardsViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val URL_BASE = "https://mpay-android-interview.herokuapp.com/android/interview/"

val retrofitModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(URL_BASE)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<CardsService> { get<Retrofit>().create(CardsService::class.java) }

    single<OkHttpClient> {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }
}

val repositoryModule = module {
    single<CardsRepository> { CardsRepositoryImp(get()) }
}

val remoteModule = module {
    single<CardsRemoteDataSource> { CardsRemoteDataSourceImp(get()) }
}

val viewModel = module {
    viewModel { ListCardsViewModel(get()) }
    viewModel { DetailsCardViewModel() }
    viewModel {DetailsAccountViewModel()}
}

val adapterModule = module {
factory<CardsAdapter> { CardsAdapter() }
}

val appModules = listOf(
    retrofitModule,
    repositoryModule,
    remoteModule,
    viewModel,
    adapterModule

)