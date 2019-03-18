package by.fabric.kewbr.talking_crocodile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class MainMenu extends AppCompatActivity {

    private Button rulesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        rulesButton = (Button) findViewById(R.id.rulesButton);
        rulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRulesActivity();
            }
        });

    }

    public void openRulesActivity() {
        Intent intent = new Intent(this, Rules.class);
        startActivity(intent);
    }

    public void startNewGame(View view) {
        switch (view.getId()) {
            case R.id.newGameButton:
                Intent intent = new Intent(this, GameView.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
