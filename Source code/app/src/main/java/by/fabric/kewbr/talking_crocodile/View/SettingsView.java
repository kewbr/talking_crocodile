package by.fabric.kewbr.talking_crocodile.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import by.fabric.kewbr.talking_crocodile.R;

public class SettingsView extends AppCompatActivity {

    private SeekBar wordCount;
    private TextView wordCountTextView;

    private SeekBar timeOfRound;
    private TextView timeOfRoundTextView;

    private Switch inAppSound;
    private Switch surcharge;

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
}
