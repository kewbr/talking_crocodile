package by.fabric.kewbr.talking_crocodile.Adapter;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import by.fabric.kewbr.talking_crocodile.Model.WordStatusModel;
import by.fabric.kewbr.talking_crocodile.R;
import by.fabric.kewbr.talking_crocodile.ViewModel.RoundViewModel;

public class RoundWordsAdapter extends
        RecyclerView.Adapter<RoundWordsAdapter.RoundWordsViewHolder> {

    private int countOfWordsItems;
    private List<WordStatusModel> words;

    public RoundWordsAdapter (List<WordStatusModel> words, Context context) {

        this.countOfWordsItems = words.size();
        this.words = words;
    }

    @NonNull
    @Override
    public RoundWordsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int idLayoutWordCheckCell = R.layout.word_check_cell;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(idLayoutWordCheckCell, viewGroup, false);

        return new RoundWordsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoundWordsViewHolder roundWordsViewHolder, int i) {
        //MARK – Here update element of recyclerView
        roundWordsViewHolder.bind(this.words.get(i).getWord(), this.words.get(i).getGuessed(), i);
    }

    @Override
    public int getItemCount() {
        return countOfWordsItems;
    }

    class RoundWordsViewHolder extends RecyclerView.ViewHolder {

        TextView wordTextView;
        CheckBox wordCheckBox;
        WordStatusModel word;
        private int position;

        public RoundWordsViewHolder(View itemView) {
            super(itemView);

            wordTextView = itemView.findViewById(R.id.word);
            wordCheckBox = itemView.findViewById(R.id.check_box_word);
            wordCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    words.get(position).setGuessed(isChecked);
                }
            });

        }

        void bind(String currentWord, boolean checked, int position) {

            this.position = position;

            word = new WordStatusModel();
            word.setWord(currentWord);
            word.setGuessed(checked);
            wordTextView.setText(currentWord);
            wordCheckBox.setChecked(checked);
        }

    }
}
