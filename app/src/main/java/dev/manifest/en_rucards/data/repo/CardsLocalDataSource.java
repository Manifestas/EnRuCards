package dev.manifest.en_rucards.data.repo;


import android.os.Handler;
import android.os.Looper;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import dev.manifest.en_rucards.data.db.CardDao;
import dev.manifest.en_rucards.data.model.Card;

public class CardsLocalDataSource implements CardsDataSource {

    private CardDao dao;
    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private Handler backgroundHandler = new Handler();

    @Inject
    public CardsLocalDataSource(CardDao dao) {
        this.dao = dao;
    }

    @Override
    public void getCards(@NonNull final LoadCardCallback callback) {
        backgroundHandler.post(new Runnable() {
            @Override
            public void run() {
                final List<Card> cards = dao.getAllCards();
                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (cards.isEmpty()) {
                            callback.onDataNotAvailable();
                        } else {
                            callback.onCardsLoaded(cards);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void getCard(@NonNull String taskId, @NonNull GetCardCallback callback) {

    }

    @Override
    public void saveCard(@NonNull Card task) {

    }

    @Override
    public void deleteCard(@NonNull String cardId) {

    }
}
