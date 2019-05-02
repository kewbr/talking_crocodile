package by.fabric.kewbr.talking_crocodile.ViewModel;

import java.util.ArrayList;
import java.util.List;

import by.fabric.kewbr.talking_crocodile.Model.WordStatusModel;
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

        RealmResults<ProgressModel> teams = databaseInstace
                .where(ProgressModel.class)
                .findAll();
        teamsPlaying.addAll(databaseInstance.copyFromRealm(teams));
    }

    public List<WordStatusModel> getWords() {
        return this.words;
    }

    public void writeBack() {

        //ахтунг чек

        int[] delta = new int[this.teamsPlaying.size()];

        for (int i = 0; i < this.teamsPlaying.size(); i++) {
            delta[i] = 0;
        }

        for (int i = 0; i < teamsPlaying.size(); i++) {
            for (int j = 0; j < words.size(); j++) {

                if (words.get(j).getGuessed() == false && copyOfWords.get(j).getGuessed() == true) {

                    delta[i] += -1;             //тут прохэндилить нужно ли отнимать
                                                //это в настройках чекать по всей игре
                                                //ахтунг!!!
                }

                if (words.get(j).getGuessed() == true && copyOfWords.get(j).getGuessed() == false) {

                    delta[i] += 1;
                }

            }
        }

        for (int i = 0; i < teamsPlaying.size(); i++) {
            int oldGuessedCount = teamsPlaying.get(i).getGuessedCount();
            oldGuessedCount += delta[i];
        }

        //ахтунг чек

        databaseInstance.beginTransaction();

        databaseInstance.copyToRealm(words);
        databaseInstance.copyToRealm(teamsPlaying);     //ахтунг чек

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
