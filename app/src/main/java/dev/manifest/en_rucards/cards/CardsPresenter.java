package dev.manifest.en_rucards.cards;

import android.app.Activity;
import android.content.Intent;

import javax.inject.Inject;
import javax.inject.Singleton;

import dev.manifest.en_rucards.R;
import dev.manifest.en_rucards.data.model.Card;
import dev.manifest.en_rucards.data.repo.CardsRepository;
import dev.manifest.en_rucards.util.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;

@Singleton
public class CardsPresenter implements CardsContract.Presenter {

    private CompositeDisposable subscriptions = new CompositeDisposable();
    private CardsRepository repository;
    private CardsContract.View wordsView;
    private SchedulerProvider schedulerProvider;

    @Inject
    public CardsPresenter(CardsRepository repository, SchedulerProvider schedulerProvider) {
        this.repository = repository;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public void loadCards() {
        if (wordsView != null) {
            subscriptions.add(repository.getCards()
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .doOnSubscribe(subscription -> wordsView.showLoadingIndicator(true))
                    .doFinally(() -> wordsView.showLoadingIndicator(false))
                    .subscribe(
                            cards -> wordsView.showCards(cards),
                            error -> wordsView.showSnackbarMessage(R.string.empty_dictionary)
                    ));
        }
    }

    @Override
    public void openCardDetail(Card requestedCard) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeCard(Card requestedCard) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addNewCard() {
        if (wordsView != null) {
            wordsView.showAddCard();
        }
    }

    @Override
    public void result(int requestCode, int resultCode, Intent data) {
        // If a word was successfully added, show snackbar
        if (CardsFragment.REQUEST_NEW_WORD == requestCode
                && Activity.RESULT_OK == resultCode) {
            String ruWord = data.getStringExtra(AddCardDialogFragment.EXTRA_WORD);
            if (wordsView != null) {
                wordsView.showSnackbarMessage(R.string.word_added);
                repository.saveCard(new Card(ruWord));
            }
        }
    }

    @Override
    public void playSound(Card card) {
        subscriptions.add(repository.getFile(card)
                .subscribeOn(schedulerProvider.io())
                .subscribe(
                        path -> {
                            if (wordsView != null) {
                                wordsView.playSound(path);
                            }
                        },
                        throwable -> {
                            if (wordsView != null) {
                                wordsView.showSnackbarMessage(R.string.sound_not_available);
                            }
                        }
                ));
    }

    @Override
    public void takeView(CardsContract.View view) {
        if (view != null) {
            wordsView = view;
        }
    }

    @Override
    public void dropView() {
        subscriptions.clear();
        wordsView = null;
    }
}
