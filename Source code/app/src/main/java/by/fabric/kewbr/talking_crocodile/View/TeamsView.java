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
import by.fabric.kewbr.talking_crocodile.Model.TeamNamesModel;
import by.fabric.kewbr.talking_crocodile.R;
import by.fabric.kewbr.talking_crocodile.ViewModel.TeamsViewModel;

public class TeamsView extends AppCompatActivity {

    private RecyclerView teamsList;
    private TeamsAdapter teamsAdapter;
    private TeamsViewModel teamsViewModel;

    private Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        this.teamsViewModel = new TeamsViewModel();
        setContentView(R.layout.teams);
        teamsList = findViewById(R.id.wordsRecyclerView);
        TextView teamName = findViewById(R.id.comandNameTextView);
        TextView currentRating = findViewById(R.id.currentCountTextView);
        addButton = findViewById(R.id.addButton);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        teamsList.setLayoutManager(layoutManager);
        teamsList.setHasFixedSize(true) ;

        teamsAdapter = new TeamsAdapter(teamsViewModel.getTeams(), this);
        teamsList.setAdapter(teamsAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addTeam();
            }
        });

    }

    private void addTeam() {
        TeamNamesModel team = new TeamNamesModel();
        team.setId(Long.valueOf(123));
        team.setTeamName("ХУЙ");
        teamsAdapter.addItem(team);
    }
}

