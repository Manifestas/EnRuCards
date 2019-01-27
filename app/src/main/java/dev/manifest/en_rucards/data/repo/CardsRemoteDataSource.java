package dev.manifest.en_rucards.data.repo;

import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dev.manifest.en_rucards.data.model.Card;
import dev.manifest.en_rucards.data.storage.FileStorage;
import dev.manifest.en_rucards.network.LingvoApi;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
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
        throw new UnsupportedOperationException();
    }

    @Override
    public Flowable<Card> getCard(@NonNull String cardId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Maybe<Card> getCardByOriginalWord(@NonNull String word) {
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
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteCard(@NonNull String cardId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Maybe<String> getFile(@NonNull Card card) {
        return retrofit.create(LingvoApi.class).getSoundFile(card.getDictName(), card.getSoundName())
                .flatMapMaybe(responseBody -> {
                    if (responseBody.body() != null) {
                        InputStream inputStream = responseBody.body().byteStream();
                        String soundName = card.getSoundName();
                        if (fileStorage.saveFile(soundName, inputStream)) {
                            return Maybe.just(fileStorage.getFilePath(soundName));
                        } else {
                            return Maybe.empty();
                        }
                    } else {
                        return Maybe.empty();
                    }
                });
    }
}
