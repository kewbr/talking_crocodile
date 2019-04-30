package by.fabric.kewbr.talking_crocodile.View;


import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import by.fabric.kewbr.talking_crocodile.Model.SettingsModel;
import by.fabric.kewbr.talking_crocodile.Model.TeamNamesModel;
import by.fabric.kewbr.talking_crocodile.R;
import by.fabric.kewbr.talking_crocodile.ViewModel.GameSettingsViewModel;

public class SettingsView extends AppCompatActivity {

    private GameSettingsViewModel gameSettingsViewModel;

    private SeekBar wordCount;
    private TextView wordCountTextView;

    private SeekBar timeOfRound;
    private TextView timeOfRoundTextView;

    private Switch inAppSound;
    private Switch surcharge;

    private Button continueButton;

    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private List<String> complexityForUser = new ArrayList<String>();
    private List<String> complexityForDB = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_view);

        this.gameSettingsViewModel = new GameSettingsViewModel();
        complexityForUser.add("Легко");
        complexityForUser.add("Трудно");
        complexityForDB.add("easy");
        complexityForDB.add("medium");
        wordCount = (SeekBar) findViewById(R.id.wordSeekBar);
        wordCount.setProgress((int)this.gameSettingsViewModel.settings.getWordsForWinCount());
        wordCountTextView = (TextView) findViewById(R.id.countOfWords);
        wordCountTextView.setText(String.valueOf(this.gameSettingsViewModel.settings.getWordsForWinCount()));

        timeOfRound = (SeekBar) findViewById(R.id.timeSeekBar);
        timeOfRound.setProgress((int)this.gameSettingsViewModel.settings.getDurationOfRound());
        timeOfRoundTextView = (TextView) findViewById(R.id.timeOfRound);
        timeOfRoundTextView.setText(String.valueOf(this.gameSettingsViewModel.settings.getDurationOfRound()));

        inAppSound = (Switch) findViewById(R.id.soundInAppSwitch);
        inAppSound.setChecked(this.gameSettingsViewModel.settings.isInAppSound());
        surcharge = (Switch) findViewById(R.id.surcharge);
        surcharge.setChecked(this.gameSettingsViewModel.settings.isChargeForPassing());

        continueButton = (Button) findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueButtonTapped();
            }
        });


        spinner = (Spinner)findViewById(R.id.spinner);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, complexityForUser);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //MARK – Setup listeners for seekBar

        wordCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                wordCountTextView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        timeOfRound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                timeOfRoundTextView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    public void continueButtonTapped() {

        SettingsModel settingsForGame = new SettingsModel();

        settingsForGame.setWordsForWinCount(new Long(wordCount.getProgress()));
        settingsForGame.setChargeForPassing(surcharge.isChecked());
        settingsForGame.setTopic(complexityForDB.get(spinner.getSelectedItemPosition()));                                       //HARDCODE!!!!!
        settingsForGame.setInAppSound(inAppSound.isChecked());
        settingsForGame.setDurationOfRound(new Long(timeOfRound.getProgress()));

        gameSettingsViewModel.manageSettings(settingsForGame);
        Intent intent = new Intent(this, GameView.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
