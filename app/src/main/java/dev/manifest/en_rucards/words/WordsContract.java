package dev.manifest.en_rucards.words;

import android.content.Intent;

import java.util.List;

import dev.manifest.en_rucards.common.BasePresenter;
import dev.manifest.en_rucards.data.model.Word;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface WordsContract {

    interface WordsView {

        void showWords(List<Word> words);

        void showAddWord();

        void showSnackbar(String message);
    }

    interface WordsPresenter extends BasePresenter<WordsView> {

        void loadWords();

        void openWordDetail(Word requestedWord);

        void removeWord(Word requestedWord);

        void addNewWord();

        void result(int requestCode, int resultCode, Intent data);
    }
}
