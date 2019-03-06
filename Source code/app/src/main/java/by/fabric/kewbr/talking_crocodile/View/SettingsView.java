package by.fabric.kewbr.talking_crocodile.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Writer;

import by.fabric.kewbr.talking_crocodile.R;
import by.fabric.kewbr.talking_crocodile.ViewModel.GameSettings;

public class SettingsView extends AppCompatActivity {

    private SeekBar wordCount;
    private TextView wordCountTextView;

    private SeekBar timeOfRound;
    private TextView timeOfRoundTextView;

    private Switch inAppSound;
    private Switch surcharge;

    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_view);

        wordCount = (SeekBar) findViewById(R.id.wordSeekBar);
        wordCountTextView = (TextView) findViewById(R.id.countOfWords);

        timeOfRound = (SeekBar) findViewById(R.id.timeSeekBar);
        timeOfRoundTextView = (TextView) findViewById(R.id.timeOfRound);

        inAppSound = (Switch) findViewById(R.id.soundInAppSwitch);
        surcharge = (Switch) findViewById(R.id.surcharge);

        continueButton = (Button) findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueButtonTapped();
            }
        });


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
        GameSettings tempSettings = new GameSettings(wordCount.getProgress(),
                                                    timeOfRound.getProgress(),
                                                    inAppSound.isChecked(),
                                                    surcharge.isChecked());
        tempSettings.saveToJSON(this.getApplicationContext());
        Intent intent = new Intent(this, GameView.class);
        startActivity(intent);
    }
}
