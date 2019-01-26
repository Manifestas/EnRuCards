package dev.manifest.en_rucards.data.repo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dev.manifest.en_rucards.data.model.Card;
import dev.manifest.en_rucards.data.storage.FileStorage;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Singleton
public class CardsRepository implements CardsDataSource {

    private CardsDataSource localDataSource;
    private CardsDataSource remoteDataSource;
    private FileStorage fileStorage;

    @Inject
    public CardsRepository(CardsLocalDataSource localDataSource,
                           CardsRemoteDataSource remoteDataSource,
                           FileStorage fileStorage) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
        this.fileStorage = fileStorage;
    }

    @Override
    public Flowable<List<Card>> getCards() {
        return localDataSource.getCards();
    }

    @Override
    public Flowable<Card> getCard(@NonNull String cardId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Maybe<Card> getCardByOriginalWord(@NonNull String word) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveCard(@NonNull Card card) {
        final String originalWord = card.getOriginalWord();
        localDataSource.getCardByOriginalWord(originalWord)
                .switchIfEmpty(remoteDataSource.getCardByOriginalWord(originalWord));
                /*
        localDataSource.getCardByOriginalWord(originalWord,
                new GetCardCallback() {
                    @Override
                    public void onCardLoaded(Card card) {
                        // card already in db
                    }

                    @Override
                    public void onDataNotAvailable() {
                        remoteDataSource.getCardByOriginalWord(originalWord,
                                new GetCardCallback() {
                                    @Override
                                    public void onCardLoaded(Card card) {
                                        localDataSource.saveCard(card);
                                    }

                                    @Override
                                    public void onDataNotAvailable() {

                                    }
                                });
                    }
                });
        */
    }

    @Override
    public void deleteCard(@NonNull String cardId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Maybe<String> getFile(@NonNull Card card) {
        return localDataSource.getFile(card)
                .switchIfEmpty(remoteDataSource.getFile(card));
    }
}
