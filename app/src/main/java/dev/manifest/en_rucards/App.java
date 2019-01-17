package dev.manifest.en_rucards;


import android.app.Application;

import androidx.room.Room;
import dev.manifest.en_rucards.data.db.CardDatabase;
import dev.manifest.en_rucards.di.component.AppComponent;
import dev.manifest.en_rucards.di.component.DaggerAppComponent;
import dev.manifest.en_rucards.di.module.AppModule;

public class App extends Application {

    private static AppComponent appComponent;
    CardDatabase cardDatabase;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Dagger%COMPONENT_NAME%
        appComponent = DaggerAppComponent.builder()
                // list of modules that are part of this component need to be created here too
                .appModule(new AppModule(this)) // This also corresponds to the name of your module: %component_name%Module
                .build();

        // If a Dagger 2 component does not have any constructor arguments for any of its modules,
        // then we can use .create() as a shortcut instead:
        //  mAppComponent = com.codepath.dagger.components.DaggerAppComponent.create();
        cardDatabase = Room.databaseBuilder(this, CardDatabase.class, CardDatabase.DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }
}
