package by.fabric.kewbr.talking_crocodile.Model;

import io.realm.RealmObject;

public class WordStatusModel {

    private String teamName;
    private String word;
    private boolean guessed;


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
}
