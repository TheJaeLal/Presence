package com.mjjam.attendanceapp.di;


import com.mjjam.attendanceapp.data.repository.UserRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    UserRepository userRepository();


}
