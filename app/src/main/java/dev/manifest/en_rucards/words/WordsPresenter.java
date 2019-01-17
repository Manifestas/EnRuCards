package dev.manifest.en_rucards.words;

import android.app.Activity;
import android.content.Intent;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dev.manifest.en_rucards.data.model.Card;
import dev.manifest.en_rucards.data.repo.CardsDataSource;
import dev.manifest.en_rucards.data.repo.CardsRepository;

@Singleton
public class WordsPresenter implements CardsContract.Presenter {

    CardsRepository repository;
    private CardsContract.View wordsView;

    @Inject
    public WordsPresenter(CardsRepository repo) {
        repository = repo;
    }

    @Override
    public void loadCards() {
        repository.getCards(new CardsDataSource.LoadCardCallback() {
            @Override
            public void onCardsLoaded(List<Card> cards) {
                wordsView.showCards(cards);
            }

            @Override
            public void onDataNotAvailable() {

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
        wordsView.showAddCard();
    }

    @Override
    public void result(int requestCode, int resultCode, Intent data) {
        // If a word was successfully added, show snackbar
        if (CardsFragment.REQUEST_NEW_WORD == requestCode
                && Activity.RESULT_OK == resultCode) {
            String ruWord = data.getStringExtra(AddCardDialogFragment.EXTRA_WORD);
            if (wordsView != null) {
                wordsView.showSuccessfullyAddedCard();
                repository.saveCard(new Card(ruWord));
            }
        }
    }

    @Override
    public void takeView(CardsContract.View view) {
        wordsView = view;
    }

    @Override
    public void dropView() {
        wordsView = null;

    }
}
