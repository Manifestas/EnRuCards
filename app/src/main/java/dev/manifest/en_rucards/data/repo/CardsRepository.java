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
        return null;
    }

    @Override
    public Flowable<Card> getCardByOriginalWord(@NonNull String word) {
        return null;
    }

    @Override
    public void saveCard(@NonNull Card card) {
        final String originalWord = card.getOriginalWord();
        localDataSource.getCardByOriginalWord(originalWord)
                .
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

    }

    @Override
    public Maybe<String> getFile(@NonNull Card card) {
        throw new UnsupportedOperationException();
        /*
        localDataSource.getFile(card, new GetFileCallback() {
            @Override
            public void onFileLoaded(String path) {
                if (path != null && !path.isEmpty()) {
                    callback.onFileLoaded(path);
                } else {
                    // request from internet
                    remoteDataSource.getFile(card, new GetFileCallback() {
                        @Override
                        public void onFileLoaded(String path) {
                            callback.onFileLoaded(path);
                        }

                        @Override
                        public void onFileNotAvailable() {
                            callback.onFileNotAvailable();
                        }
                    });
                }
            }

            @Override
            public void onFileNotAvailable() {
                // request from internet
                remoteDataSource.getFile(card, new GetFileCallback() {
                    @Override
                    public void onFileLoaded(String path) {
                        callback.onFileLoaded(path);
                    }

                    @Override
                    public void onFileNotAvailable() {
                        callback.onFileNotAvailable();
                    }
                });
            }
        });
        */
    }
}
