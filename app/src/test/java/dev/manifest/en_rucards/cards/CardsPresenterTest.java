package dev.manifest.en_rucards.cards;

import android.app.Activity;
import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dev.manifest.en_rucards.data.model.Card;
import dev.manifest.en_rucards.data.repo.CardsRepository;
import dev.manifest.en_rucards.util.schedulers.BaseSchedulerProvider;
import dev.manifest.en_rucards.util.schedulers.ImmediateSchedulerProvider;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class CardsPresenterTest {

    private static List<Card> CARDS;
    @Mock
    private CardsContract.View cardsView;
    @Mock
    private CardsRepository repository;
    @Mock
    private Intent intent;

    private BaseSchedulerProvider schedulerProvider;
    private CardsPresenter cardsPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        schedulerProvider = new ImmediateSchedulerProvider();
        cardsPresenter = new CardsPresenter(repository, schedulerProvider);

        cardsPresenter.takeView(cardsView);
        CARDS = new ArrayList<>(3);
        CARDS.add(new Card("ok", "хорошо", "en-ru", "ok.wav"));
        CARDS.add(new Card("no", "нет", "en-ru", "no.wav"));
    }

    @Test
    public void loadCards_ShowsLoadingIndicator() {
        when(repository.getCards()).thenReturn(Flowable.just(CARDS));
        cardsPresenter.loadCards();
        verify(cardsView).showLoadingIndicator(true);
        verify(cardsView).showLoadingIndicator(false);
    }

    @Test
    public void loadCards_LoadsCardsOnView() {
        when(repository.getCards()).thenReturn(Flowable.just(CARDS));
        cardsPresenter.loadCards();
        verify(cardsView).showCards(CARDS);
    }

    @Test
    public void loadCards_ifError_ShowsErrorOnView() {
        when(repository.getCards()).thenReturn(Flowable.error(new Exception()));
        cardsPresenter.loadCards();
        verify(cardsView).showSnackbarMessage(anyInt());
    }

    @Test
    public void result_ifResultOK_RepoSavesNewCard() {
        when(intent.getStringExtra(AddCardDialogFragment.EXTRA_WORD)).thenReturn("string");
        cardsPresenter.result(CardsFragment.REQUEST_NEW_WORD, Activity.RESULT_OK, intent);
        verify(repository).saveCard(any());
    }

    @Test
    public void result_ifResultCanceled_RepoDontSavesNewCard() {
        when(intent.getStringExtra(AddCardDialogFragment.EXTRA_WORD)).thenReturn("string");
        cardsPresenter.result(CardsFragment.REQUEST_NEW_WORD, Activity.RESULT_CANCELED, intent);
        verifyZeroInteractions(repository);
    }

    @Test
    public void playSound_ifNoError_PlaysSoundOnView() {
        when(repository.getFile(any())).thenReturn(Maybe.just("path"));
        cardsPresenter.playSound(any());
        verify(cardsView).playSound("path");
    }

    @Test
    public void playSound_ifError_ShowsMessageOnView() {
        when(repository.getFile(any())).thenReturn(Maybe.error(IOException::new));
        cardsPresenter.playSound(any());
        verify(cardsView).showSnackbarMessage(anyInt());
    }

    @Test
    public void clickOnFab_ShowsAddCardDialog() {
        cardsPresenter.addNewCard();
        verify(cardsView).showAddCard();
    }
}