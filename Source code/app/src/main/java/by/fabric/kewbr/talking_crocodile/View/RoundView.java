package by.fabric.kewbr.talking_crocodile.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import by.fabric.kewbr.talking_crocodile.Adapter.RoundWordsAdapter;
import by.fabric.kewbr.talking_crocodile.R;

public class RoundView extends AppCompatActivity {

    private RecyclerView wordsList;
    private RoundWordsAdapter wordsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_view);
        wordsList = findViewById(R.id.wordsRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        wordsList.setLayoutManager(layoutManager);
        wordsList.setHasFixedSize(true) ;

        wordsAdapter = new RoundWordsAdapter(100);
        wordsList.setAdapter(wordsAdapter);

    }
}
