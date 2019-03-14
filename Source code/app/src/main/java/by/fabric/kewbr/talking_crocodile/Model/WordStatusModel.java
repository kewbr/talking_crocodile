package by.fabric.kewbr.talking_crocodile.Model;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class WordStatusModel extends RealmObject {

    private String teamName;
    @Required
    private String word;
    private boolean guessed;
    private Long roundNumber;

    public String getTeamName() {

        return teamName;
    }

    public void setTeamName(String teamName) {

        this.teamName = teamName;
    }


    public String getWord(){

        return this.word;
    }

    public void setWord(String word) {

        this.word = word;
    }


    public boolean getGuessed(){

        return this.guessed;
    }

    public void setGuessed(boolean guessed){

        this.guessed = guessed;
    }


    public Long getRoundNumber() {

        return roundNumber;
    }

    public void setRoundNumber(Long roundNumber) {

        this.roundNumber = roundNumber;
    }
}
