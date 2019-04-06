package by.fabric.kewbr.talking_crocodile.Model;

public class Team {
    public String teamName;
    private int rating;
    public Team(){
        rating = 0;
        teamName = "team";
    }
    public Team(String name){
        rating = 0;
        teamName = name;
    }
    public Team(String name, Long rate){
        rating = rate.intValue();
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
