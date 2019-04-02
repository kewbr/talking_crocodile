package by.fabric.kewbr.talking_crocodile.ViewModel;

import java.util.ArrayList;
import java.util.List;

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

}
