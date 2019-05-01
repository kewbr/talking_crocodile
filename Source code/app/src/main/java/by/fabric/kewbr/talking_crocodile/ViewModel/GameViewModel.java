package by.fabric.kewbr.talking_crocodile.ViewModel;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import by.fabric.kewbr.talking_crocodile.Model.ProgressModel;
import by.fabric.kewbr.talking_crocodile.Model.Round;
import by.fabric.kewbr.talking_crocodile.Model.WordStatusModel;
import by.fabric.kewbr.talking_crocodile.Model.WordsModel;
import io.realm.Realm;
import io.realm.RealmResults;

public class GameViewModel extends Observable {

    private Realm databaseInstance = Realm.getDefaultInstance();

    public Round round;
    public int roundCount = 1;
    public long roundTimer;
    public boolean roundEnd = false;
    public boolean roundStart = false;
    public int counterTemp = 0;

    private CountDownTimer timer;

    public ProgressModel myTeam;
    public GameSettingsViewModel gameSettingsViewModel;

    private List<WordsModel> words = new ArrayList<>();


    public GameViewModel(Context context){

        this.gameSettingsViewModel = new GameSettingsViewModel();
        RealmResults<WordsModel> wordsFromDatabase = databaseInstance
                .where(WordsModel.class)
                .equalTo("topic", gameSettingsViewModel.settings.getTopic())
                .findAll();
        words.addAll(databaseInstance.copyFromRealm(wordsFromDatabase));
        words = mix(words);

        this.clearLastGameResult();

        List<ProgressModel> list = new ArrayList<>();

        RealmResults<ProgressModel> playingTeams = databaseInstance
                .where(ProgressModel.class)
                .findAll();
        for (int index = 0; index < playingTeams.size(); index++) {
            list.add(playingTeams.get(index));
        }

        round = new Round(list);
        myTeam = round.getCurrentTeam();
        //counterTemp = round.getTeamCount();
        Log.i("Settings"," " + gameSettingsViewModel.settings.getWordsForWinCount());
        Log.i("Settings"," " + gameSettingsViewModel.settings.getDurationOfRound());
        roundTimer = this.gameSettingsViewModel.settings.getDurationOfRound()*1000;
        timer = new CountDownTimer((this.gameSettingsViewModel.settings.getDurationOfRound()*1000)/round.getTeamCount(),  1000) {

            public void onTick(long millisUntilFinished) {
                roundTimer = millisUntilFinished;
                //onChanged();
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

    private void clearLastGameResult() {
        databaseInstance.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<WordStatusModel> result = realm.where(WordStatusModel.class).findAll();
                result.deleteAllFromRealm();
            }
        });
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
        roundStart = true;
        Log.i("Done"," ");
    }

    public void onChanged(){
        //if(this.hasChanged())
        timer.cancel();
        setTeamsAndPoints(round.getAllTeams());
        round.currentTeamIndex++;
        this.setChanged();
        if(round.isFinished()) {
            roundCount++;
            roundEnd = true;
            roundStart = false;
        }
        this.notifyObservers();
    }

    public void stopGame(){
        timer.cancel();
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
    public void writeToDatabase(WordStatusModel model) {
        databaseInstance.beginTransaction();

        WordStatusModel result = databaseInstance.copyToRealm(model);

        databaseInstance.commitTransaction();
    }

    public List<ProgressModel> getTeamsAndPoints() {

        List<ProgressModel> teamsAndPoints = new ArrayList<>();

        RealmResults<ProgressModel> wordsFromDatabase = databaseInstance
                .where(ProgressModel.class)
                .findAll();

        teamsAndPoints.addAll(databaseInstance.copyFromRealm(wordsFromDatabase));
        return teamsAndPoints;
    }

    public void setTeamsAndPoints(List<ProgressModel> teamsAndPoints) {

        databaseInstance.beginTransaction();

        databaseInstance.copyToRealm(teamsAndPoints);

        databaseInstance.commitTransaction();
    }

    public void resume() {
        timer = new CountDownTimer(roundTimer,  1000) {

            public void onTick(long millisUntilFinished) {
                roundTimer = millisUntilFinished;
            }

            public void onFinish() {
                roundTimer =0;
                onChanged();
            }
        };
        timer.start();
    }

    public void increaseCurrentTeamRating() {
        databaseInstance.beginTransaction();
        this.round.getCurrentTeam().increaseRating();
        databaseInstance.commitTransaction();
    }

    public void decreaseCurrentTeamRating() {
        databaseInstance.beginTransaction();
        this.round.getCurrentTeam().decreaseRating();
        databaseInstance.commitTransaction();
    }
}

