package dev.manifest.en_rucards.data.repo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dev.manifest.en_rucards.data.model.Card;
import dev.manifest.en_rucards.util.schedulers.SchedulerProvider;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Singleton
public class CardsRepository implements CardsDataSource {

    public static final String TAG = CardsRepository.class.getSimpleName();

    private CardsDataSource localDataSource;
    private CardsDataSource remoteDataSource;
    private SchedulerProvider schedulerProvider;

    @Inject
    public CardsRepository(CardsLocalDataSource localDataSource,
                           CardsRemoteDataSource remoteDataSource,
                           SchedulerProvider schedulerProvider) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
        this.schedulerProvider = schedulerProvider;
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
                .switchIfEmpty(Maybe.defer(() -> remoteDataSource.getCardByOriginalWord(originalWord)
                        .doOnSuccess(localDataSource::saveCard)))
                .subscribeOn(schedulerProvider.io())
                .subscribe();
    }

    @Override
    public void deleteCard(@NonNull String cardId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Maybe<String> getFile(@NonNull Card card) {
        return localDataSource.getFile(card)
                .switchIfEmpty(Maybe.defer(() -> remoteDataSource.getFile(card)));
    }
}
