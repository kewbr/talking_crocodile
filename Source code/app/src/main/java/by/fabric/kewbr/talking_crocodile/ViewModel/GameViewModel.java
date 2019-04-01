package by.fabric.kewbr.talking_crocodile.ViewModel;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import by.fabric.kewbr.talking_crocodile.Database.DatabaseConfigurator;
import by.fabric.kewbr.talking_crocodile.Model.Round;
import by.fabric.kewbr.talking_crocodile.Model.Team;
import by.fabric.kewbr.talking_crocodile.Model.WordsModel;
import by.fabric.kewbr.talking_crocodile.View.GameView;
import io.realm.Realm;
import io.realm.RealmResults;

public class GameViewModel extends Observable {

    private Realm databaseInstance = Realm.getDefaultInstance();

    private Round round;
    public int roundCount = 1;
    public long roundTimer;

    public int counterTemp = 0;

    private CountDownTimer timer;

    public Team myTeam;
    public GameSettingsViewModel gameSettingsViewModel;

    private List<WordsModel> words = new ArrayList<>();


    public GameViewModel(Context context){

        this.gameSettingsViewModel = new GameSettingsViewModel();
        RealmResults<WordsModel> result = databaseInstance
                .where(WordsModel.class)
                .equalTo("topic", "easy")
                .findAll();
        words.addAll(databaseInstance.copyFromRealm(result));
        words = mix(words);
        LinkedList<String> list = new LinkedList<String>();
        list.add("Стандартная тима");
        round = new Round(list);
        myTeam = round.getCurrentTeam();
        Log.i("Settings"," " + gameSettingsViewModel.settings.getWordsForWinCount());
        Log.i("Settings"," " + gameSettingsViewModel.settings.getDurationOfRound());
        roundTimer = this.gameSettingsViewModel.settings.getDurationOfRound()*1000;
        timer = new CountDownTimer(this.gameSettingsViewModel.settings.getDurationOfRound()*1000, 1000) {

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
        timer.cancel();
        roundCount++;
        this.setChanged();
        this.notifyObservers();
    }

    public void stopGame(){
        timer.cancel();
        //roundCount = 0;
        //GameView.showDialogAndClose("Something");
    }


    private List<WordsModel> mix(List<WordsModel> array){
        Random rnd = ThreadLocalRandom.current();
        for (int i = array.size() - 1; i > 0; i--) {
            int tempIndex = rnd.nextInt(i + 1);
            // Simple swap
            WordsModel tempWord = array.get(tempIndex);
            array.set(tempIndex, array.get(i));
            array.set(i, tempWord);
        }
        return array;
    }

    //получаем модель текущую
    public WordsModel getCurrentWord() {
        if (counterTemp >= this.words.size()) {
            WordsModel stopModel = new WordsModel();
            stopModel.setId(Long.getLong("999"));
            stopModel.setWord("Упс.. Слова закончились!");
            stopModel.setTopic("System");
        }
        return this.words.get(counterTemp++);

    }

    //будем записывать в модельку
    public void writeToDatabase(WordsModel model) {

    }

}

