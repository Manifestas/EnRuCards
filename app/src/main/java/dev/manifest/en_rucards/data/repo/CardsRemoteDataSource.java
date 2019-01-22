package dev.manifest.en_rucards.data.repo;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dev.manifest.en_rucards.data.model.Card;
import dev.manifest.en_rucards.data.model.Minicard;
import dev.manifest.en_rucards.network.LingvoApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

@Singleton
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
        retrofit.create(LingvoApi.class).getTranslation(word).enqueue(new Callback<Minicard>() {
            @Override
            public void onResponse(Call<Minicard> call, Response<Minicard> response) {
                Minicard minicard = response.body();
                if (minicard != null) {
                    String translation = minicard.getTranslation().getTranslation();
                    String dictName = minicard.getTranslation().getDictionaryName();
                    String soundName = minicard.getTranslation().getSoundName();
                    Card card = new Card(word, translation, dictName, soundName);
                    callback.onCardLoaded(card);
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<Minicard> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });

    }

    @Override
    public void saveCard(@NonNull Card card) {

    }

    @Override
    public void deleteCard(@NonNull String cardId) {

    }

    @Override
    public void getFile(@NonNull Card card, @NonNull GetFileCallback callback) {

    }
}
