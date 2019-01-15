package dev.manifest.en_rucards.data.repo;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.annotation.NonNull;
import dev.manifest.en_rucards.data.model.Card;
import retrofit2.Retrofit;

public class CardsRemoteDataSource implements CardsDataSource {

    private Retrofit retrofit;

    @Inject
    public CardsRemoteDataSource(@Named("auth") Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    public void getCards(@NonNull LoadCardCallback callback) {

    }

    @Override
    public void getCard(@NonNull String cardId, @NonNull GetCardCallback callback) {

    }

    @Override
    public void getCardByOriginalWord(@NonNull String word, @NonNull GetCardCallback callback) {

    }

    @Override
    public void saveCard(@NonNull Card card) {

    }

    @Override
    public void deleteCard(@NonNull String cardId) {

    }
}
