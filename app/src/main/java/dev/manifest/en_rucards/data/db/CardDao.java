package dev.manifest.en_rucards.data.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import dev.manifest.en_rucards.data.model.Card;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Dao
public interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCard(Card card);

    @Query("SELECT * FROM card")
    Flowable<List<Card>> getAllCards();

    @Query("SELECT * FROM card WHERE id = :cardId")
    Flowable<Card> getCardById(long cardId);

    @Query("SELECT * FROM card WHERE original_word = :originalWord")
    Maybe<Card> getCardByOriginalWord(String originalWord);

}
