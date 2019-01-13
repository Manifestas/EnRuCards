package dev.manifest.en_rucards.words;

import android.app.Activity;
import android.content.Intent;

import dev.manifest.en_rucards.data.model.Word;

public class WordsPresenter implements WordsContract.Presenter {

    private WordsContract.View wordsView;

    @Override
    public void loadWords() {

    }

    @Override
    public void openWordDetail(Word requestedWord) {

    }

    @Override
    public void removeWord(Word requestedWord) {

    }

    @Override
    public void addNewWord() {

    }

    @Override
    public void result(int requestCode, int resultCode, Intent data) {
        // If a word was successfully added, show snackbar
        if (WordsFragment.REQUEST_NEW_WORD == requestCode
                && Activity.RESULT_OK == resultCode) {
            data.getStringExtra(AddWordDialogFragment.EXTRA_WORD);
            if (wordsView != null) {
                wordsView.showSuccessfullyAddedWord();
            }
        }
    }

    @Override
    public void takeView(WordsContract.View view) {
        wordsView = view;
    }

    @Override
    public void dropView() {
        wordsView = null;

    }
}
