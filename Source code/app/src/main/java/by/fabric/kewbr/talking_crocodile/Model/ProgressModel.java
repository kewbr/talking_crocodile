package by.fabric.kewbr.talking_crocodile.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class ProgressModel extends RealmObject {

    @PrimaryKey
    @Required
    public String teamName;
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

    public Long getRating(){
        return guessedCount;
    }

    public void increaseRating(){
        guessedCount++;
    }

    public void decreaseRating(){
        guessedCount--;
    }

    public boolean isWinner(int winRating){
        return guessedCount >= winRating;
    }
}
