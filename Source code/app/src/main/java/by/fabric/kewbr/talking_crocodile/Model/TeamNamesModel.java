package by.fabric.kewbr.talking_crocodile.Model;

import io.realm.RealmObject;

public class TeamNamesModel extends RealmObject {

    private Long id;
    private String teamName;


    public String getTeamName() {

        return teamName;
    }

    public void setTeamName(String teamName) {

        this.teamName = teamName;
    }


    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }




}
