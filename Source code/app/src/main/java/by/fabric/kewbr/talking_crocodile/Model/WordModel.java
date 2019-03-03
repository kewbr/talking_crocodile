package by.fabric.kewbr.talking_crocodile.Model;

public class WordModel {

    private String word;
    private boolean guessed;

    public WordModel(String word, boolean guessed){
        this.word = word;
        this.guessed = guessed;
    }

    String getWord(){
        final String word = this.word;
        return word;
    }
    boolean getGuessed(){
        boolean guessed = this.guessed;
        return guessed;
    }

    void setGuessed(boolean guessed){
        this.guessed = guessed;
    }
}
