package by.fabric.kewbr.talking_crocodile.Model;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class CurrentStateModel extends RealmObject {

    private long time;

    @Required
    private String teamName;
    private long roundNumber;

    public long getTime() {

        return time;
    }

    public void setTime(long time) {

        this.time = time;
    }


    public String getTeamName() {

        return teamName;
    }

    public void setTeamName(String teamName) {

        this.teamName = teamName;
    }


    public long getRoundNumber() {

        return roundNumber;
    }

    public void setRoundNumber(long roundNumber) {

        this.roundNumber = roundNumber;
    }





}
