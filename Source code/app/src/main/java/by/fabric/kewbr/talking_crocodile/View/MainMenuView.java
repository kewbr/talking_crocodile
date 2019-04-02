package by.fabric.kewbr.talking_crocodile.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import by.fabric.kewbr.talking_crocodile.R;

//MARK: Subsystems
import io.fabric.sdk.android.Fabric;
import com.crashlytics.android.Crashlytics;
import io.realm.Realm;

//MARK: Helpers
import by.fabric.kewbr.talking_crocodile.Database.DatabaseConfigurator;


public class MainMenuView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.configure();
    }

    private void configure() {

        this.configureSubsystems();
        this.configureViews();
    }

    private void configureSubsystems() {

        Fabric.with(this, new Crashlytics());

        Realm.init(this);
        DatabaseConfigurator.configure();
    }

    private void configureViews() {

        setContentView(R.layout.activity_main);

        Button newGameButton = findViewById(R.id.newGameButton);
        newGameButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openNewGameActivity();
            }
        });

        Button continueButton = findViewById(R.id.continueButton);

        Button rulesButton = findViewById(R.id.rulesButton);
        rulesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openRulesActivity();
            }
        });
        continueButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                test();
            }
        });
    }

    private void openRulesActivity() {

        Intent intent = new Intent(this, RulesView.class);
        startActivity(intent);
    }

    private void openNewGameActivity() {

        Intent intent = new Intent(this, SettingsView.class);
        startActivity(intent);
    }

    private void test() {

        Intent intent = new Intent(this, TeamsView.class);
        startActivity(intent);
    }
}
