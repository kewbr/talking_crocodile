package by.fabric.kewbr.talking_crocodile.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import by.fabric.kewbr.talking_crocodile.Adapter.RoundWordsAdapter;
import by.fabric.kewbr.talking_crocodile.R;
import by.fabric.kewbr.talking_crocodile.ViewModel.RoundViewModel;

public class StartRoundView extends AppCompatActivity {

    public String winnerTeamName;
    public int winnerRating;
    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_team);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                winnerTeamName= null;
            } else {
                winnerTeamName= extras.getString("Team Name");
                winnerRating = extras.getInt("Team Rating");
            }
        } else {
            winnerTeamName = (String) savedInstanceState.getSerializable("Team Rating");
            winnerRating = (int) savedInstanceState.getSerializable("Team Rating");
        }
        TextView text1 = findViewById(R.id.teamName);
        text1.setText(winnerTeamName);
        TextView text2 = findViewById(R.id.rating);
        text2.setText(""+winnerRating);

        continueButton = (Button) findViewById(R.id.returnToGame);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

//    public void returnToGame(View view) {
//        switch (view.getId()) {
//            case R.id.returnToGame:
//                finish();
//                break;
//            default:
//                break;
//        }
//    }
}