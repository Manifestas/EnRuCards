package dev.manifest.en_rucards.cards;

import android.content.Intent;

import java.util.List;

import dev.manifest.en_rucards.common.BasePresenter;
import dev.manifest.en_rucards.data.model.Card;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface CardsContract {

    interface View {

        void showCards(List<Card> words);

        void showAddCard();

        void showSnackbarMessage(int messageId);

        void playSound(String soundPath);
    }

    interface Presenter extends BasePresenter<View> {

        void loadCards();

        void openCardDetail(Card requestedCard);

        void removeCard(Card requestedCard);

        void addNewCard();

        void result(int requestCode, int resultCode, Intent data);

        void playSound(Card card);
    }
}
