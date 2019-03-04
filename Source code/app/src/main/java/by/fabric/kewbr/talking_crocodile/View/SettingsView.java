package by.fabric.kewbr.talking_crocodile.View;

import android.os.Bundle;
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
import java.io.FileWriter;
import java.io.Writer;

import by.fabric.kewbr.talking_crocodile.R;

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
        JSONObject settings = new JSONObject();
        try {
            settings.put("wordCountForWin", wordCount.getProgress());
            settings.put("timeOfRound", timeOfRound.getProgress());
            settings.put("inAppSounds", inAppSound.isChecked());
            settings.put("surcharge", surcharge.isChecked());
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        try {
            Writer output = null;
            File file = new File("roundSettings.json");
            output = new BufferedWriter(new FileWriter(file));
            output.write(settings.toString());
            output.close();

        } catch (Exception e) {

        }
    }
}
