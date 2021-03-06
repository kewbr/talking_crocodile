package by.fabric.kewbr.talking_crocodile.Model;
import java.util.LinkedList;
import java.util.List;

public class Round {
    public int currentTeamIndex = 1; //счет команд начинаем с 1 , сначала команда играет, потом увеличиваем индекс
    private LinkedList<ProgressModel> teams = new LinkedList<ProgressModel>();
    public Round(){

    }
    public Round(List<ProgressModel> mteams){
        for (ProgressModel team: mteams ) {
            //Team t = new Team(team.teamName,team.getGuessedCount());
            teams.add(team);
        }
    }

    public boolean isFinished (){
        return teams.size() == currentTeamIndex - 1;
    }

    public void restart(){
        currentTeamIndex = 1;
    }

    public ProgressModel getCurrentTeam(){
        return teams.get(currentTeamIndex-1);
    }

    public int getTeamCount() {
        return teams.size();
    }

    public LinkedList<ProgressModel> getAllTeams() {
        //List<ProgressModel> l = new LinkedList<ProgressModel>();
        //for (int index = 0; index < teams.size(); index++) {
        //    l.add(teams.get(index));
        // }
        return teams;
    }
}
