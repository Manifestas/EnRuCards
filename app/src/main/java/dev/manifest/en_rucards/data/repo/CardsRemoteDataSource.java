package dev.manifest.en_rucards.data.repo;

import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dev.manifest.en_rucards.data.model.Card;
import dev.manifest.en_rucards.data.model.Minicard;
import dev.manifest.en_rucards.data.storage.FileStorage;
import dev.manifest.en_rucards.network.LingvoApi;
import io.reactivex.Flowable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

@Singleton
public class CardsRemoteDataSource implements CardsDataSource {

    private Retrofit retrofit;
    private FileStorage fileStorage;

    @Inject
    public CardsRemoteDataSource(@Named("auth") Retrofit retrofit, FileStorage fileStorage) {
        this.retrofit = retrofit;
        this.fileStorage = fileStorage;
    }

    @Override
    public Flowable<List<Card>> getCards() {
        return null;
    }

    @Override
    public Flowable<Card> getCard(@NonNull String cardId) {
        return null;
    }

    @Override
    public Flowable<Card> getCardByOriginalWord(@NonNull String word) {
        return retrofit.create(LingvoApi.class).getTranslation(word)
                .map(minicard -> {
                    String translation = minicard.getTranslation().getTranslation();
                    String dictName = minicard.getTranslation().getDictionaryName();
                    String soundName = minicard.getTranslation().getSoundName();
                    return new Card(word, translation, dictName, soundName);
                });
    }

    @Override
    public void saveCard(@NonNull Card card) {

    }

    @Override
    public void deleteCard(@NonNull String cardId) {

    }

    @Override
    public Single<String> getFile(@NonNull Card card) {
        return retrofit.create(LingvoApi.class).getSoundFIle(card.getDictName(), card.getSoundName())
                .map(responseBody -> {
                    if (responseBody != null) {
                        InputStream inputStream = responseBody.byteStream();
                        String soundName = card.getSoundName();
                        fileStorage.saveFile(soundName, inputStream);
                        return soundName;
                    } else {
                        return null;
                    }
                });
    }
}
