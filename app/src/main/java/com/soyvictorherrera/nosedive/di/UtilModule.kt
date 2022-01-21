package com.soyvictorherrera.nosedive.di

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.fredporciuncula.flow.preferences.FlowSharedPreferences
import com.soyvictorherrera.nosedive.BaseApplication
import com.soyvictorherrera.nosedive.util.FileUtil
import com.soyvictorherrera.nosedive.util.NotificationUtil
import com.soyvictorherrera.nosedive.util.PreferenceUtil
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

    @Singleton
    @Provides
    fun provideSharedPreferences(app: BaseApplication): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(app)
    }

    @Provides
    @Singleton
    fun provideFlowSharedPreferences(preferences: SharedPreferences): FlowSharedPreferences {
        return FlowSharedPreferences(preferences)
    }

    @Provides
    @Singleton
    fun providePreferenceUtil(
        preferences: SharedPreferences,
        flowPreferences: FlowSharedPreferences
    ): PreferenceUtil {
        return PreferenceUtil(
            sharedPreferences = preferences,
            flowSharedPreferences = flowPreferences
        )
    }

    @Provides
    @Singleton
    fun provideNotificationUtil(
        app: BaseApplication
    ): NotificationUtil {
        return NotificationUtil(
            application = app
        )
    }

}
