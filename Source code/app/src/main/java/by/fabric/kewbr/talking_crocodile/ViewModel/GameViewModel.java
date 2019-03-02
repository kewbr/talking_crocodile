package by.fabric.kewbr.talking_crocodile.ViewModel;
import by.fabric.kewbr.talking_crocodile.Model.Round;
import by.fabric.kewbr.talking_crocodile.Model.Team;
import by.fabric.kewbr.talking_crocodile.View.GameView;


public class GameViewModel {

    private Round round;
    private long roundTimer;
    public Team myTeam = new Team("DreamTeam");

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
