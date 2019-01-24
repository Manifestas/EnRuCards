package dev.manifest.en_rucards.data.repo;

import java.util.List;

import androidx.annotation.NonNull;
import dev.manifest.en_rucards.data.model.Card;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

public interface CardsDataSource {

    Flowable<List<Card>> getCards();

    Flowable<Card> getCard(@NonNull String cardId);

    Flowable<Card> getCardByOriginalWord(@NonNull String word);

    void saveCard(@NonNull Card card);

    void deleteCard(@NonNull String cardId);

    Maybe<String> getFile(@NonNull Card card);
}

