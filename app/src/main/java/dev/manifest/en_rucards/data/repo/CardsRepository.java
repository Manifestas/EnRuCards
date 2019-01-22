package dev.manifest.en_rucards.data.repo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dev.manifest.en_rucards.data.model.Card;
import dev.manifest.en_rucards.data.storage.FileStorage;

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
    public void getCard(@NonNull String cardId, @NonNull GetCardCallback callback) {

    }

    @Override
    public void getCardByOriginalWord(@NonNull String word, @NonNull GetCardCallback callback) {

    }

    @Override
    public void saveCard(@NonNull Card card) {
        final String originalWord = card.getOriginalWord();
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
    }

    @Override
    public void deleteCard(@NonNull String cardId) {

    }

    @Override
    public void getFile(@NonNull Card card, @NonNull GetFileCallback callback) {
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
    }
}
