package com.soyvictorherrera.nosedive.di

import com.soyvictorherrera.nosedive.BaseApplication
import com.soyvictorherrera.nosedive.util.FileUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UtilModule {

    @Singleton
    @Provides
    fun provideFileUtil(app: BaseApplication): FileUtil {
        return FileUtil(app)
    }

}
