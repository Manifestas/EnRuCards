package dev.manifest.en_rucards.cards;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import dev.manifest.en_rucards.R;
import dev.manifest.en_rucards.data.model.Card;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardsViewHolder> {

    private final CardsAdapterOnPlayClickHandler onPlayClickHandler;
    private List<Card> cards = new ArrayList<>();

    @Inject
    public CardsAdapter(CardsAdapterOnPlayClickHandler handler){
        onPlayClickHandler = handler;
    }

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
        Card currentCard = cards.get(position);
        holder.bind(currentCard);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public void setData(List<Card> cards) {
        this.cards = cards;
        notifyDataSetChanged();
    }


    public class CardsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView ruWordTextView;
        private final TextView enWordTextView;

        public CardsViewHolder(@NonNull View itemView) {
            super(itemView);
            ruWordTextView = itemView.findViewById(R.id.tv_en_word);
            enWordTextView = itemView.findViewById(R.id.tv_ru_word);
            ImageButton playButton = itemView.findViewById(R.id.btn_play);
            playButton.setOnClickListener(this);

        }

        public void bind(Card card) {
            ruWordTextView.setText(card.getOriginalWord());
            enWordTextView.setText(card.getTranslate());
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Card pressedCard = cards.get(adapterPosition);
            onPlayClickHandler.onPlayClick(pressedCard);
        }
    }

    interface CardsAdapterOnPlayClickHandler {
        void onPlayClick(Card clickedCard);
    }
}
