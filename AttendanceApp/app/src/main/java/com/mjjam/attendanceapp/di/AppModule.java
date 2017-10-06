package com.mjjam.attendanceapp.di;

import com.mjjam.attendanceapp.common.Config;
import com.mjjam.attendanceapp.data.implementation.UserRepositoryImpl;
import com.mjjam.attendanceapp.data.remote.UserRestService;
import com.mjjam.attendanceapp.data.repository.UserRepository;
import com.mjjam.attendanceapp.network.RxErrorHandlingCallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Archish on 1/10/2017.
 */

@Module
public class AppModule {

    @Provides
    @Singleton
    UserRepository provideUserRepository(UserRestService userRestService) {
        return new UserRepositoryImpl(userRestService);
    }


    @Provides
    @Singleton
    UserRestService provideUserRestService(Retrofit retrofit) {
        return retrofit.create(UserRestService.class);
    }



    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .build();
    }
}
