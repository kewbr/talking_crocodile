package by.fabric.kewbr.talking_crocodile.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


import by.fabric.kewbr.talking_crocodile.Adapter.TeamsAdapter;
import by.fabric.kewbr.talking_crocodile.R;

public class TeamsView extends AppCompatActivity {

    private RecyclerView teamsList;
    private TeamsAdapter teamsAdapter;
   // private RoundViewModel roundViewModel;

    private Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.teams);
       // Bundle extras = getIntent().getExtras();
      //  this.roundViewModel = new RoundViewModel(extras.getInt("roundNumber")-1);    ////КАКОГО ТО ХУЯ ТУТ +1

        teamsList = findViewById(R.id.wordsRecyclerView);
        TextView teamName = findViewById(R.id.comandNameTextView);
        TextView currentRating = findViewById(R.id.currentCountTextView);
        addButton = findViewById(R.id.addButton);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        teamsList.setLayoutManager(layoutManager);
        teamsList.setHasFixedSize(true) ;

        List<String> a = new ArrayList<String>();
        a.add("team 1");
        a.add("team 2");
        a.add("team 3");
        teamsAdapter = new TeamsAdapter(a, this);
        teamsList.setAdapter(teamsAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addTeam();
            }
        });

    }

    private void addTeam() {
        teamsAdapter.addItem("new team");
    }
}

