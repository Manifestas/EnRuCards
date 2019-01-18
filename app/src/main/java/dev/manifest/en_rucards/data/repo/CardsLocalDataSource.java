package dev.manifest.en_rucards.data.repo;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dev.manifest.en_rucards.data.db.CardDao;
import dev.manifest.en_rucards.data.model.Card;
import dev.manifest.en_rucards.util.AppExecutors;

@Singleton
public class CardsLocalDataSource implements CardsDataSource {

    private CardDao dao;
    private AppExecutors executors;

    @Inject
    public CardsLocalDataSource(CardDao dao, AppExecutors executors) {
        this.dao = dao;
        this.executors = executors;
    }

    @Override
    public void getCards(@NonNull final LoadCardCallback callback) {
        executors.diskIO().execute(() -> {
            final List<Card> cards = dao.getAllCards();

            executors.mainThread().execute(() -> {
                if (cards.isEmpty()) {
                    callback.onDataNotAvailable();
                } else {
                    callback.onCardsLoaded(cards);
                }
            });
        });
    }

    @Override
    public void getCard(@NonNull String cardId, @NonNull GetCardCallback callback) {

    }

    @Override
    public void getCardByOriginalWord(@NonNull final String word, @NonNull GetCardCallback callback) {
        executors.diskIO().execute(() -> {
            Card searchedCard = dao.getCardByOriginalWord(word);

            executors.mainThread().execute(() -> {
                if (searchedCard != null) {
                    callback.onCardLoaded(searchedCard);
                } else {
                    callback.onDataNotAvailable();
                }
            });
        });

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
}
