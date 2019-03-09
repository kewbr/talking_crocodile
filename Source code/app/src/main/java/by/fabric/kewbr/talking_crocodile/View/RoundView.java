package by.fabric.kewbr.talking_crocodile.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
        TextView teamName = findViewById(R.id.comandNameTextView);
        TextView currentRating = findViewById(R.id.currentCountTextView);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        wordsList.setLayoutManager(layoutManager);
        wordsList.setHasFixedSize(true) ;

        int wordsCount;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                wordsCount= 0;
                currentRating.setText("+" + 0);
                teamName.setText("Стандартная тима");
            } else {
                wordsCount = extras.getInt("Words Count");
                currentRating.setText("" + extras.getInt("Current Rating"));
                teamName.setText(extras.getString("Team Name"));
            }
        } else {
            wordsCount = (int) savedInstanceState.getSerializable("Words Count");
            currentRating.setText("" + (int) savedInstanceState.getSerializable("Current Rating"));
            teamName.setText((String) savedInstanceState.getSerializable("Team Name"));
        }
        Log.i("Words count", "" + wordsCount );
        wordsAdapter = new RoundWordsAdapter(wordsCount, this);
        wordsList.setAdapter(wordsAdapter);

    }

    public void returnToGame(View view) {
        switch (view.getId()) {
            case R.id.continueButton:
                finish();
                break;
            default:
                break;
        }
    }
}
