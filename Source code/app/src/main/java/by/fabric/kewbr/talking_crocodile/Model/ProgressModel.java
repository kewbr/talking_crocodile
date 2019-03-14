package by.fabric.kewbr.talking_crocodile.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class ProgressModel extends RealmObject {

    @PrimaryKey
    @Required
    private String teamName;
    private Long guessedCount;

    public String getTeamName() {

        return teamName;
    }

    public void setTeamName(String teamName) {

        this.teamName = teamName;
    }


    public Long getGuessedCount() {

        return guessedCount;
    }

    public void setGuessedCount(Long guessedCount) {

        this.guessedCount = guessedCount;
    }
}
