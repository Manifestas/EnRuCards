package dev.manifest.en_rucards.data.repo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.annotation.NonNull;
import dev.manifest.en_rucards.data.model.Card;

public class CardsRepository implements CardsDataSource {

    private CardsDataSource localDataSource;
    private CardsDataSource remoteDataSource;

    @Inject
    public CardsRepository(@Named("local") CardsDataSource localDataSource,
                           @Named("remote") CardsDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public void getCards(@NonNull final LoadCardCallback callback) {
        localDataSource.getCards(new LoadCardCallback() {
            @Override
            public void onCardsLoaded(List<Card> cards) {
                callback.onCardsLoaded(cards);
            }

            @Override
            public void onDataNotAvailable() {

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
