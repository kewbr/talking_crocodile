package by.fabric.kewbr.talking_crocodile.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import by.fabric.kewbr.talking_crocodile.Adapter.RoundWordsAdapter;
import by.fabric.kewbr.talking_crocodile.Adapter.StartRoundAdapter;
import by.fabric.kewbr.talking_crocodile.R;
import by.fabric.kewbr.talking_crocodile.ViewModel.FinishViewModel;
import by.fabric.kewbr.talking_crocodile.ViewModel.RoundViewModel;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class FinishView extends AppCompatActivity {

    FinishViewModel vm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        vm = new FinishViewModel();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
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
        RecyclerView startRoundRecyclerView = findViewById(R.id.finish_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        startRoundRecyclerView.setLayoutManager(layoutManager);
        startRoundRecyclerView.setHasFixedSize(true) ;

        StartRoundAdapter adapter = new StartRoundAdapter(this.vm.getTeamsAndPoints());
        startRoundRecyclerView.setAdapter(adapter);
        TextView text1 = findViewById(R.id.finish_teamName);
        text1.setText(vm.winnerTeamName);


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
