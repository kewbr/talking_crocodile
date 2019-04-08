package by.fabric.kewbr.talking_crocodile.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;


import java.util.ArrayList;
import java.util.List;

import by.fabric.kewbr.talking_crocodile.Adapter.TeamsAdapter;

import by.fabric.kewbr.talking_crocodile.Model.PlayingTeamsModel;
import by.fabric.kewbr.talking_crocodile.R;
import by.fabric.kewbr.talking_crocodile.ViewModel.TeamsViewModel;

public class TeamsView extends AppCompatActivity {

    private RecyclerView teamsList;
    private TeamsAdapter teamsAdapter;
    private TeamsViewModel teamsViewModel;

    private Button addButton;
    private Button continueButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        this.teamsViewModel = new TeamsViewModel();
        setContentView(R.layout.teams);
        teamsList = findViewById(R.id.wordsRecyclerView);
        addButton = findViewById(R.id.addButton);
        continueButton = findViewById(R.id.continueButton);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        teamsList.setLayoutManager(layoutManager);
        teamsList.setHasFixedSize(true) ;

        teamsAdapter = new TeamsAdapter(teamsViewModel.getTeams().subList(0,2), this, teamsViewModel.getTeams());
        teamsList.setAdapter(teamsAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addTeam();
            }
        });
        continueButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openSettingActivity();
            }
        });

    }

    private void addTeam() {

        teamsAdapter.addItem();
    }

    private void openSettingActivity() {

        List<PlayingTeamsModel> playingTeams = new ArrayList<>();

        for (int index = 0; index < teamsAdapter.getTeams().size(); index++) {
            PlayingTeamsModel team = new PlayingTeamsModel();
            team.setId(Long.valueOf(index));
            team.setTeamName(teamsAdapter.getTeams().get(index).getTeamName());

            playingTeams.add(team);
        }

        this.teamsViewModel.writeTeamsToDatabase(playingTeams);

        Intent intent = new Intent(this, SettingsView.class);

        startActivity(intent);
    }
}

