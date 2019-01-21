package dev.manifest.en_rucards.data.repo;

import java.io.InputStream;
import java.util.List;

import androidx.annotation.NonNull;
import dev.manifest.en_rucards.data.model.Card;

public interface CardsDataSource {

    void getCards(@NonNull LoadCardCallback callback);

    void getCard(@NonNull String cardId, @NonNull GetCardCallback callback);

    void getCardByOriginalWord(@NonNull String word, @NonNull GetCardCallback callback);

    void saveCard(@NonNull Card card);

    void deleteCard(@NonNull String cardId);

    void getFile(@NonNull String fileName, @NonNull GetFileCallback callback);


    interface LoadCardCallback {

        void onCardsLoaded(List<Card> cards);

        void onDataNotAvailable();
    }


    interface GetCardCallback {

        void onCardLoaded(Card card);

        void onDataNotAvailable();
    }

    interface GetFileCallback {

        void onFileLoaded(InputStream inputStream);

        void onFileNotAvailable();
    }
}
