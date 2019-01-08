package dev.manifest.en_rucards.words;

import java.util.List;

import dev.manifest.en_rucards.data.model.Word;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface WordsContract {

    interface WordsView {

        void showWords(List<Word> words);

        void showSnackbar(String message);
    }

    interface WordsPresenter {

        void loadWords();

        void openWordDetail(Word requestedWord);

        void removeWord(Word requestedWord);

        void addNewWord();
    }
}
