package by.fabric.kewbr.talking_crocodile.ViewModel;

import java.util.ArrayList;
import java.util.List;

import by.fabric.kewbr.talking_crocodile.Model.WordStatusModel;
import by.fabric.kewbr.talking_crocodile.Model.ProgressModel;

import io.realm.Realm;
import io.realm.RealmResults;

public class RoundViewModel {

    private Realm databaseInstance;
    private List<WordStatusModel> words = new ArrayList<>();
    private List<WordStatusModel> copyOfWords = new ArrayList<>();

    private List <ProgressModel> teamsPlaying = new ArrayList<>();

    public RoundViewModel(int roundNumber) {

        this.databaseInstance = Realm.getDefaultInstance();

        //хер знает но тут мы должны хэндлить тимы ПОДУМАЕМ ЗАВТРА
        RealmResults<WordStatusModel> result = databaseInstance
                .where(WordStatusModel.class)
                .equalTo("roundNumber", Long.valueOf(roundNumber))
                .findAll();
        words.addAll(databaseInstance.copyFromRealm(result));
        copyOfWords.addAll(databaseInstance.copyFromRealm(result));

        RealmResults<ProgressModel> teams = databaseInstance
                .where(ProgressModel.class)
                .findAll();
        teamsPlaying.addAll(databaseInstance.copyFromRealm(teams));
    }

    public List<ProgressModel> getTeams() {
        return this.teamsPlaying;
    }

    public List<WordStatusModel> getWords() {
        return this.words;
    }


    public void writeBack() {

        int[] delta = new int[this.teamsPlaying.size()];

        for (int i = 0; i < this.teamsPlaying.size(); i++) {
            delta[i] = 0;
        }

        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < this.teamsPlaying.size(); i++) {
            names.add(teamsPlaying.get(i).getTeamName());
        }


        for (int j = 0; j < words.size(); j++)
        {

            int index = names.indexOf(words.get(j).getTeamName());

            if (words.get(j).getGuessed() == false && copyOfWords.get(j).getGuessed() == true) {

                delta[index] += -2; //тут прохэндилить нужно ли отнимать
//это в настройках чекать по всей игре
//ахтунг!!!
            }

            if (words.get(j).getGuessed() == true && copyOfWords.get(j).getGuessed() == false) {

                delta[index] += 2;
            }

        }


        for (int i = 0; i < teamsPlaying.size(); i++) {
            Long oldGuessedCount = teamsPlaying.get(i).getGuessedCount();
            oldGuessedCount += delta[i];
            teamsPlaying.get(i).setGuessedCount(oldGuessedCount);
        }

//ахтунг чек

        databaseInstance.beginTransaction();
        databaseInstance.copyToRealm(teamsPlaying); //ахтунг чек
        databaseInstance.commitTransaction();

        databaseInstance.beginTransaction();
        databaseInstance.copyToRealm(words);
        databaseInstance.commitTransaction();
    }

    public void clearRoundResult(final int roundNumber) {
        databaseInstance.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<WordStatusModel> result = realm
                        .where(WordStatusModel.class)
                        .equalTo("roundNumber", Long.valueOf(roundNumber))
                        .findAll();
                result.deleteAllFromRealm();
            }
        });
    }




}
