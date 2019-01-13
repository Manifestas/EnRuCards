package dev.manifest.en_rucards.di.module;

import javax.inject.Singleton;

import dagger.Provides;
import dev.manifest.en_rucards.words.WordsPresenter;

public class PresenterModule {

    @Provides
    @Singleton
    WordsPresenter provideWordsPresenter() {
        return new WordsPresenter();
    }
}
