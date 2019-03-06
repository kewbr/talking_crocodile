package by.fabric.kewbr.talking_crocodile.ViewModel;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;

import java.util.EventObject;
import java.util.LinkedList;
import java.util.Observable;

import by.fabric.kewbr.talking_crocodile.Model.Round;
import by.fabric.kewbr.talking_crocodile.Model.Team;
import by.fabric.kewbr.talking_crocodile.View.GameView;

public class GameViewModel extends Observable {

    private Round round;
    public int roundCount = 1;
    public long roundTimer;

    private CountDownTimer timer;

    public Team myTeam;

    public GameSettings gameSettings;

    public GameViewModel(Context context){

        gameSettings = new GameSettings(context);
        LinkedList<String> list = new LinkedList<String>();
        list.add("Стандартная тима");
        round = new Round(list);
        myTeam = round.getCurrentTeam();
        Log.i("Settings"," " + gameSettings.wordCount);
        Log.i("Settings"," " + gameSettings.timeOfRound);
        timer = new CountDownTimer(gameSettings.timeOfRound*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                roundTimer = millisUntilFinished;
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                roundTimer =0;
                onChanged();
                //startNewRound();
                //mTextField.setText("done!");
            }
        };
    }

    private void collectGamelog(){}

    private void loadGame(){
    }
    public void startTimer()
    {
        if(roundTimer==0)
        timer.start();
        else
        {
            timer.cancel();
            timer.start();
        }
    }

    public void startNewRound() {
        if(round.isFinished())
            round.restart();
        //roundTimer = 5000;
        timer.start();
        Log.i("Done"," ");
    }

    public void onChanged(){
        //if(this.hasChanged())
        roundCount++;
        this.setChanged();
        this.notifyObservers();
    }

    public void stopGame(){
        //GameView.showDialogAndClose("Something");
    }



}

