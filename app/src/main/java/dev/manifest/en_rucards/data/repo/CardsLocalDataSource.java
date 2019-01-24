package dev.manifest.en_rucards.data.repo;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dev.manifest.en_rucards.data.db.CardDao;
import dev.manifest.en_rucards.data.model.Card;
import dev.manifest.en_rucards.data.storage.FileStorage;
import dev.manifest.en_rucards.util.AppExecutors;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Singleton
public class CardsLocalDataSource implements CardsDataSource {

    private CardDao dao;
    private AppExecutors executors;
    private FileStorage fileStorage;

    @Inject
    public CardsLocalDataSource(CardDao dao, AppExecutors executors, FileStorage fileStorage) {
        this.dao = dao;
        this.executors = executors;
        this.fileStorage = fileStorage;
    }

    @Override
    public Flowable<List<Card>> getCards() {
        return dao.getAllCards();
    }

    @Override
    public Flowable<Card> getCard(@NonNull String cardId) {
        return dao.getCardById(Long.valueOf(cardId));
    }


    @Override
    public Flowable<Card> getCardByOriginalWord(@NonNull String word) {
        return dao.getCardByOriginalWord(word);
    }

    @Override
    public void saveCard(@NonNull Card card) {
        executors.diskIO().execute(() -> {
            dao.insertCard(card);
        });
    }

    @Override
    public void deleteCard(@NonNull String cardId) {

    }

    @Override
    public Single<String> getFile(@NonNull Card card) {
        String soundFile = fileStorage.getFilePath(card.getSoundName());
        return Single.just(soundFile);
    }
}
