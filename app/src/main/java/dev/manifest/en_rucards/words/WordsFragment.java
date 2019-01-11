package dev.manifest.en_rucards.words;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dev.manifest.en_rucards.App;
import dev.manifest.en_rucards.R;
import dev.manifest.en_rucards.data.model.Minicard;
import dev.manifest.en_rucards.network.LingvoApi;
import dev.manifest.en_rucards.network.NetworkService;
import dev.manifest.en_rucards.data.model.Word;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WordsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WordsFragment extends Fragment {

    private static final String TAG = WordsFragment.class.getSimpleName();
    @Inject
    Retrofit retrofit;

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
        ((App)(getContext().getApplicationContext())).getAppComponent().injectInto(this);
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

        /*
        NetworkService.getInstance()
                .getJSONApi()
                .getAuthToken()
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        System.out.println(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
        */

        retrofit.create(LingvoApi.class).getTranslation("четыре").enqueue(new Callback<Minicard>() {
            @Override
            public void onResponse(Call<Minicard> call, Response<Minicard> response) {
                Minicard body = response.body();
                Log.d(TAG, body.getHeading());
                Log.d(TAG, body.getTranslation().getTranslation());


            }

            @Override
            public void onFailure(Call<Minicard> call, Throwable t) {

            }
        });

        return root;
    }
}
