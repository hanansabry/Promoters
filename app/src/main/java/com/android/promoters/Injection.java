package com.android.promoters;


import com.android.promoters.backend.authentication.AuthenticationRepository;
import com.android.promoters.backend.authentication.AuthenticationRepositoryImpl;
import com.android.promoters.usecase.AuthenticationUseCaseHandler;

public class Injection {

    public static AuthenticationRepository provideAuthenticationRepository() {
        return new AuthenticationRepositoryImpl();
    }

    public static AuthenticationUseCaseHandler provideAuthenticationUseCaseHandler() {
        return new AuthenticationUseCaseHandler(provideAuthenticationRepository());
    }
//
//
//    public static GcUseCaseHandler provideGcUseCaseHandler() {
//        return new GcUseCaseHandler(provideFieldHeaderRepository(), provideGcRepository());
//    }
//
//    public static FieldHeaderRepository provideFieldHeaderRepository() {
//        return new FieldHeaderRepositoryImpl();
//    }
//
//    public static GcRepository provideGcRepository() {
//        return new GcRepositoryImpl();
//    }
//
//    public static WellsRepository provideWellsRepository() {
//        return new WellsRepositoryImpl();
//    }
//
//    public static WellDetailsUseCaseHandler provideWellDetailsUseCaseHandler() {
//        return new WellDetailsUseCaseHandler(provideWellsRepository(), provideUsersRepository());
//    }
//
//    private static UsersRepository provideUsersRepository() {
//        return new UsersRepositoryImpl();
//    }
//
//    public static WellsDailyDataRepository provideWellsDataRepository() {
//        return new WellsDailyDataRepositoryImpl();
//    }
}
