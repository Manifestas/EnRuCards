package dev.manifest.en_rucards.words;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import dev.manifest.en_rucards.R;
import dev.manifest.en_rucards.data.model.Word;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardsViewHolder> {

    private List<Word> words = new ArrayList<>();

    /**
     * This gets called when each new ViewHolder is created.
     */
    @NonNull
    @Override
    public CardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_list_item, parent, false);
        return new CardsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardsViewHolder holder, int position) {
        Word currentWord = words.get(position);
        holder.bind(currentWord);
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public void setData(List<Word> words) {
        this.words = words;
        notifyDataSetChanged();
    }


    public class CardsViewHolder extends RecyclerView.ViewHolder {

        private final TextView ruWordTextView;
        private final TextView enWordTextView;

        public CardsViewHolder(@NonNull View itemView) {
            super(itemView);
            ruWordTextView = itemView.findViewById(R.id.tv_en_word);
            enWordTextView = itemView.findViewById(R.id.tv_ru_word);
        }

        public void bind(Word word) {
            ruWordTextView.setText(word.getRuWord());
            enWordTextView.setText(word.getEnWord());
        }
    }
}
