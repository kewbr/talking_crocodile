package by.fabric.kewbr.talking_crocodile.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import by.fabric.kewbr.talking_crocodile.R;

public class FinishView extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
    }
    public void goToMenu(View view) {
        switch (view.getId()) {
            case R.id.goToMenu:
                finish();
                break;
            default:
                break;
        }
    }
}
