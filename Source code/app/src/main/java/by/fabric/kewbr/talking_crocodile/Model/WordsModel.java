package by.fabric.kewbr.talking_crocodile.Model;

import io.realm.RealmObject;

public class WordsModel extends RealmObject {

    private Long id;
    private String word;
    private String topic;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return this.word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}

