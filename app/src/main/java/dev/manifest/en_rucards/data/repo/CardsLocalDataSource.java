package dev.manifest.en_rucards.data.repo;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import dev.manifest.en_rucards.data.db.CardDao;
import dev.manifest.en_rucards.data.model.Card;

public class CardsLocalDataSource implements CardsDataSource {

    CardDao dao;

    @Inject
    public CardsLocalDataSource(CardDao dao) {
        this.dao = dao;
    }

    @Override
    public void getCards(@NonNull LoadCardCallback callback) {

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
