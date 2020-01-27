package com.android.promoters;


import com.android.promoters.backend.authentication.AuthenticationRepository;
import com.android.promoters.backend.authentication.AuthenticationRepositoryImpl;
import com.android.promoters.backend.events.EventsRepository;
import com.android.promoters.backend.events.EventsRepositoryImpl;
import com.android.promoters.backend.organizer.OrganizerRepository;
import com.android.promoters.backend.organizer.OrganizerRepositoryImpl;
import com.android.promoters.backend.promoters.PromotersRepository;
import com.android.promoters.backend.promoters.PromotersRepositoryImpl;
import com.android.promoters.backend.users.UsersRepository;
import com.android.promoters.backend.users.UsersRepositoryImpl;
import com.android.promoters.usecase.AuthenticationUseCaseHandler;

public class Injection {

    public static AuthenticationRepository provideAuthenticationRepository() {
        return new AuthenticationRepositoryImpl();
    }

    public static AuthenticationUseCaseHandler provideAuthenticationUseCaseHandler() {
        return new AuthenticationUseCaseHandler(provideAuthenticationRepository(), provideUsersRepository());
    }

    public static UsersRepository provideUsersRepository() {
        return new UsersRepositoryImpl();
    }

    public static OrganizerRepository provideOrganizerRepository() {
        return new OrganizerRepositoryImpl();
    }

    public static EventsRepository provideEventsRepository() {
        return new EventsRepositoryImpl();
    }

    public static PromotersRepository providePromotersRepository() {
        return new PromotersRepositoryImpl();
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
