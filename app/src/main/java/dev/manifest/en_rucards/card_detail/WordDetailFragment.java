package dev.manifest.en_rucards.card_detail;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import dev.manifest.en_rucards.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WordDetailFragment extends Fragment {


    public WordDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_word_detail, container, false);
    }

}
