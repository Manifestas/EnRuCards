package dev.manifest.en_rucards.words;

import android.app.Activity;
import android.content.Intent;

import javax.inject.Inject;

import dev.manifest.en_rucards.data.model.Card;
import dev.manifest.en_rucards.data.repo.CardsRepository;

public class WordsPresenter implements CardsContract.Presenter {

    @Inject
    CardsRepository repository;
    private CardsContract.View wordsView;

    @Override
    public void loadCards() {

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
            data.getStringExtra(AddWordDialogFragment.EXTRA_WORD);
            if (wordsView != null) {
                wordsView.showSuccessfullyAddedCard();
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
