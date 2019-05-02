package by.fabric.kewbr.talking_crocodile.ViewModel;

import java.util.ArrayList;
import java.util.List;

import by.fabric.kewbr.talking_crocodile.Model.ProgressModel;
import io.realm.Realm;
import io.realm.RealmResults;

public class FinishViewModel {
    public String winnerTeamName;
    public int winnerRating;
    private Realm databaseInstance = Realm.getDefaultInstance();

    public FinishViewModel(){

    }
    public FinishViewModel(String mTeamName, int mRating){
        winnerTeamName = mTeamName;
        winnerRating = mRating;
    }
    public void clearGameInfo() {
        //Do smg for deleting the GameSetting and GameLogs

    }

    public List<ProgressModel> getTeamsAndPoints() {

        List<ProgressModel> teamsAndPoints = new ArrayList<>();

        RealmResults<ProgressModel> wordsFromDatabase = databaseInstance
                .where(ProgressModel.class)
                .findAll();

        teamsAndPoints.addAll(databaseInstance.copyFromRealm(wordsFromDatabase));
        return teamsAndPoints;
    }

}
