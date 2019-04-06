package by.fabric.kewbr.talking_crocodile.ViewModel;

import java.util.ArrayList;
import java.util.List;

import by.fabric.kewbr.talking_crocodile.Model.WordStatusModel;
import io.realm.Realm;
import io.realm.RealmResults;

public class RoundViewModel {

    private Realm databaseInstance;
    private List<WordStatusModel> words = new ArrayList<>();

    public RoundViewModel(int roundNumber) {

        this.databaseInstance = Realm.getDefaultInstance();

        //хер знает но тут мы должны хэндлить тимы ПОДУМАЕМ ЗАВТРА
        RealmResults<WordStatusModel> result = databaseInstance
                .where(WordStatusModel.class)
                .equalTo("roundNumber", Long.valueOf(roundNumber))
                .findAll();
        words.addAll(databaseInstance.copyFromRealm(result));
    }

    public List<WordStatusModel> getWords() {
        return this.words;
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
