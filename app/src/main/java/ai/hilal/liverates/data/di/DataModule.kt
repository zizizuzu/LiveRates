package ai.hilal.liverates.data.di

import ai.hilal.liverates.data.local.AppDatabase
import ai.hilal.liverates.data.local.dao.CurrencyDao
import ai.hilal.liverates.data.local.datasource.LocalCurrencyDataSource
import ai.hilal.liverates.data.local.datasource.LocalCurrencyDataSourceImpl
import ai.hilal.liverates.data.local.datastore.SettingsDataStore
import ai.hilal.liverates.data.local.datastore.SettingsDataStoreImpl
import ai.hilal.liverates.data.remote.datasource.CoinGeckoRemoteDataSource
import ai.hilal.liverates.data.remote.datasource.RemoteCurrencyDataSource
import ai.hilal.liverates.data.repository.CurrencyRepositoryImpl
import ai.hilal.liverates.domain.repository.CurrencyRepository
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindsCurrencyRepository(impl: CurrencyRepositoryImpl): CurrencyRepository



    @Binds
    @Singleton
    fun bindsLocalDataSource(impl: LocalCurrencyDataSourceImpl): LocalCurrencyDataSource

    @Binds
    @Singleton
    fun provideRemoteDataSource(impl: CoinGeckoRemoteDataSource): RemoteCurrencyDataSource

}

@Module
@InstallIn(SingletonComponent::class)
object DataProvidesModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideCurrencyDao(database: AppDatabase): CurrencyDao {
        return database.currencyDao()
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): SettingsDataStore{

        return SettingsDataStoreImpl(context)
    }
}