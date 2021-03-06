package dev.manifest.en_rucards.di.module;

import android.app.Application;

import javax.inject.Named;
import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import dev.manifest.en_rucards.data.db.CardDao;
import dev.manifest.en_rucards.data.db.CardDatabase;
import dev.manifest.en_rucards.data.repo.CardsDataSource;
import dev.manifest.en_rucards.data.repo.CardsLocalDataSource;
import dev.manifest.en_rucards.data.repo.CardsRemoteDataSource;
import dev.manifest.en_rucards.data.storage.FileStorage;
import retrofit2.Retrofit;

@Module
public class CardsModule {

    @Provides
    @Singleton
    @Named("local")
    CardsDataSource provideCardsLocalDataSource(CardDao dao, FileStorage fileStorage) {
        return new CardsLocalDataSource(dao, fileStorage);
    }

    @Provides
    @Singleton
    @Named("remote")
    CardsDataSource provideCardsRemoteDataSource(Retrofit retrofit, FileStorage fileStorage) {
        return new CardsRemoteDataSource(retrofit, fileStorage);
    }


    @Provides
    @Singleton
    CardDatabase provideCardDatabase(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), CardDatabase.class, CardDatabase.DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    CardDao provideCardDao(CardDatabase db) {
        return db.cardDao();
    }
}
