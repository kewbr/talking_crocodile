package by.fabric.kewbr.talking_crocodile.ViewModel;
import by.fabric.kewbr.talking_crocodile.Model.RoundModel;
import by.fabric.kewbr.talking_crocodile.Model.TeamModel;
import by.fabric.kewbr.talking_crocodile.View.GameView;


public class GameViewModel {

    private RoundModel round;
    private long roundTimer;
    public TeamModel myTeam = new TeamModel("DreamTeam");

    public GameViewModel(){

    }

    private void collectGamelog(){}

    private void loadGame(){
    }

    public void startNewRound()
    {
        if(round.isFinished())
            round.restart();
        roundTimer = 0;
    }

    public void finishGame(){
        GameView.showDialogAndClose("Something");
    }



}
