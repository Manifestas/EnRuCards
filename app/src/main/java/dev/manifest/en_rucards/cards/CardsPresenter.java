package dev.manifest.en_rucards.cards;

import android.app.Activity;
import android.content.Intent;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dev.manifest.en_rucards.R;
import dev.manifest.en_rucards.data.model.Card;
import dev.manifest.en_rucards.data.repo.CardsDataSource;
import dev.manifest.en_rucards.data.repo.CardsDataSource.GetFileCallback;
import dev.manifest.en_rucards.data.repo.CardsRepository;
import dev.manifest.en_rucards.util.SchedulerProvider;

@Singleton
public class CardsPresenter implements CardsContract.Presenter {

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
        repository.getCards(new CardsDataSource.LoadCardCallback() {
            @Override
            public void onCardsLoaded(List<Card> cards) {
                if (wordsView != null) {
                    wordsView.showCards(cards);
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (wordsView != null) {
                    wordsView.showSnackbarMessage(R.string.empty_dictionary);
                }
            }
        });
    }

    @Override
    public void openCardDetail(Card requestedCard) {

    }

    @Override
    public void removeCard(Card requestedCard) {

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
        repository.getFile(card, new GetFileCallback() {
            @Override
            public void onFileLoaded(String path) {
                if (wordsView != null) {
                    wordsView.playSound(path);
                }
            }

            @Override
            public void onFileNotAvailable() {
                if (wordsView != null) {
                    wordsView.showSnackbarMessage(R.string.sound_not_available);
                }
            }
        });
    }

    @Override
    public void takeView(CardsContract.View view) {
        if (view != null) {
            wordsView = view;
        }
    }

    @Override
    public void dropView() {
        wordsView = null;

    }
}
