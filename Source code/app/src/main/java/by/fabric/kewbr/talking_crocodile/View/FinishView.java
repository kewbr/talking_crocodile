package by.fabric.kewbr.talking_crocodile.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import by.fabric.kewbr.talking_crocodile.R;
import by.fabric.kewbr.talking_crocodile.ViewModel.FinishViewModel;

public class FinishView extends AppCompatActivity {
    FinishViewModel vm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        vm = new FinishViewModel();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
//        intent.putExtra("Team Name",vm.myTeam.teamName);
//        intent.putExtra("Team Rating", vm.myTeam.getRating());
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                vm.winnerTeamName= null;
            } else {
                vm.winnerTeamName= extras.getString("Team Name");
                vm.winnerRating = extras.getInt("Team Rating");
            }
        } else {
            vm.winnerTeamName = (String) savedInstanceState.getSerializable("Team Rating");
            vm.winnerRating = (int) savedInstanceState.getSerializable("Team Rating");
        }
        TextView text1 = findViewById(R.id.finish_teamName);
        text1.setText(vm.winnerTeamName);
        TextView text2 = findViewById(R.id.finish_rating);
        text2.setText(""+vm.winnerRating);

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
