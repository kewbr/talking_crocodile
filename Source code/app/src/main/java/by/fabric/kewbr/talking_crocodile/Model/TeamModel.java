package by.fabric.kewbr.talking_crocodile.Model;

public class TeamModel {
    public String teamName;
    private int rating;
    public TeamModel(){
        rating = 0;
        teamName = "team";
    }
    public TeamModel(String name){
        rating = 0;
        teamName = name;
    }
    public int getRating(){
        return rating;
    }
    public void increaseRating(){
        rating++;
    }

    public void decreaseRating(){
        rating--;
    }

    public boolean isWinner(int winRating){
        return rating >= winRating;
    }
}
