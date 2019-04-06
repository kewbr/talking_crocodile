package by.fabric.kewbr.talking_crocodile.ViewModel;

import java.util.ArrayList;
import java.util.List;

import by.fabric.kewbr.talking_crocodile.Model.PlayingTeamsModel;
import by.fabric.kewbr.talking_crocodile.Model.ProgressModel;
import by.fabric.kewbr.talking_crocodile.Model.TeamNamesModel;
import io.realm.Realm;
import io.realm.RealmResults;

public class TeamsViewModel {

    private Realm databaseInstance;
    private List<TeamNamesModel> availibleTeams = new ArrayList<>();

    public TeamsViewModel() {

        this.databaseInstance = Realm.getDefaultInstance();

        RealmResults<TeamNamesModel> result = databaseInstance
                .where(TeamNamesModel.class)
                .findAll();
        availibleTeams.addAll(databaseInstance.copyFromRealm(result));
    }

    public List<TeamNamesModel> getTeams() {
        return this.availibleTeams;
    }

    public void writeTeamsToDatabase(List<PlayingTeamsModel> teams) {

        databaseInstance.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<PlayingTeamsModel> result = realm
                        .where(PlayingTeamsModel.class)
                        .findAll();
                result.deleteAllFromRealm();
            }
        });
        databaseInstance.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<ProgressModel> result = realm
                        .where(ProgressModel.class)
                        .findAll();
                result.deleteAllFromRealm();
            }
        });

        List<ProgressModel> progresses = new ArrayList<>();
        for (int index = 0; index < teams.size(); index++) {
            ProgressModel model = new ProgressModel();
            model.setGuessedCount(Long.valueOf(0));
            model.setTeamName(teams.get(index).getTeamName());

            progresses.add(model);
        }

        this.databaseInstance.beginTransaction();

        this.databaseInstance.insert(teams);
        this.databaseInstance.insert(progresses);

        this.databaseInstance.commitTransaction();
    }

}
