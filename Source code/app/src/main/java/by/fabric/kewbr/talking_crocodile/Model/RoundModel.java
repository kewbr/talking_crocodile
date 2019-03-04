package by.fabric.kewbr.talking_crocodile.Model;
import java.util.LinkedList;
import java.util.List;

public class RoundModel {
    public int currentTeamIndex = 1; //счет команд начинаем с 1 , сначала команда играет, потом увеличиваем индекс
    private LinkedList<TeamModel> teams = new LinkedList<TeamModel>();
    public RoundModel(){

    }
    public RoundModel(List<String> mteams){
        for (String name: mteams ) {
            teams.add(new TeamModel(name));
        }
    }

    public boolean isFinished (){
        return teams.size() == currentTeamIndex;
    }

    public void restart(){
        currentTeamIndex = 1;
    }

    public Team getCurrentTeam(){
        return teams.get(currentTeamIndex-1);
    }
}
