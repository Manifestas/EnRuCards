package dev.manifest.en_rucards.word_detail;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import dev.manifest.en_rucards.R;
import dev.manifest.en_rucards.common.SingleFragmentActivity;

public class WordDetailActivity extends SingleFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail);
    }

    @Override
    protected Fragment createFragment() {
        return null;
    }
}
