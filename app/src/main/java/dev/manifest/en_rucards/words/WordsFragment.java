package dev.manifest.en_rucards.words;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dev.manifest.en_rucards.App;
import dev.manifest.en_rucards.R;
import dev.manifest.en_rucards.data.model.Minicard;
import dev.manifest.en_rucards.data.model.Word;
import dev.manifest.en_rucards.network.LingvoApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WordsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WordsFragment extends Fragment implements WordsContract.View {

    public static final int REQUEST_NEW_WORD = 0;
    private static final String TAG = WordsFragment.class.getSimpleName();
    private static final String DIALOG_NEW_WORD = "DialogNewWord";
    @Inject
    @Named("auth")
    Retrofit retrofit;
    @Inject
    private WordsContract.Presenter presenter;

    private RecyclerView recyclerView;
    private WordsAdapter wordsAdapter;

    public WordsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment WordsFragment.
     */
    public static WordsFragment newInstance() {
        return new WordsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        App.getAppComponent().injectInto(this);
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_words, container, false);
        recyclerView = root.findViewById(R.id.rv_words);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        wordsAdapter = new WordsAdapter();

        List<Word> words = new ArrayList<>();
        Word word1 = new Word();
        word1.setEnWord("truncated");
        word1.setRuWord("усеченный");
        words.add(word1);
        Word word2 = new Word();
        word2.setEnWord("commit");
        word2.setRuWord("совершить");
        words.add(word2);
        wordsAdapter.setData(words);
        recyclerView.setAdapter(wordsAdapter);

        retrofit.create(LingvoApi.class).getTranslation("четыре").enqueue(new Callback<Minicard>() {
            @Override
            public void onResponse(Call<Minicard> call, Response<Minicard> response) {
                Minicard body = response.body();
                if (body != null) {
                    Log.d(TAG, body.getHeading());
                    Log.d(TAG, body.getTranslation().getTranslation());
                }
            }

            @Override
            public void onFailure(Call<Minicard> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t);
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.addNewWord();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dropView(); //prevent leaking activity in
        // case presenter is orchestrating a long running task
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.result(requestCode, resultCode, data);
    }

    @Override
    public void showWords(List<Word> words) {

    }

    @Override
    public void showAddWord() {
        FragmentManager manager = getFragmentManager();
        AddWordDialogFragment dialog = AddWordDialogFragment.newInstance();
        dialog.setTargetFragment(this, REQUEST_NEW_WORD);
        dialog.show(manager, DIALOG_NEW_WORD);
    }

    @Override
    public void showSuccessfullyAddedWord() {
        showMessage(getString(R.string.word_added));
    }

    public void showMessage(String message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
    }
}
