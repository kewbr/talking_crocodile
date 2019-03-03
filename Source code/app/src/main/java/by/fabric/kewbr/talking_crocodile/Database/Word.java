package by.fabric.kewbr.talking_crocodile.Database;

public class Word {
	String word;	
	Boolean status=false;
	
	Word(String word){
		this.word = word;
	}
	public String getWord() {
		return this.word;
	}
	void changeStatus() {
		this.status = true;
	}
	
	Boolean getStatus() {
		return this.status;
	}
}
