package by.fabric.kewbr.talking_crocodile.ViewModel;

public class FinishViewModel {
    public String winnerTeamName;
    public int winnerRating;

    public FinishViewModel(){

    }
    public FinishViewModel(String mTeamName, int mRating){
        winnerTeamName = mTeamName;
        winnerRating = mRating;
    }
    public void clearGameInfo() {
        //Do smg for deleting the GameSetting and GameLogs

    }

}
