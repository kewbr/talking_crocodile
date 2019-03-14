package by.fabric.kewbr.talking_crocodile.Model;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class SettingsModel extends RealmObject {

    private long wordsForWinCount;
    private long durationOfRound;
    private boolean chargeForPassing;
    private boolean inAppSound;

    @Required
    private String topic;


    public long getWordsForWinCount() {

        return wordsForWinCount;
    }

    public void setWordsForWinCount(long wordsForWinCount) {

        this.wordsForWinCount = wordsForWinCount;
    }


    public long getDurationOfRound() {

        return durationOfRound;
    }

    public void setDurationOfRound(Long durationOfRound) {

        this.durationOfRound = durationOfRound;
    }


    public boolean isChargeForPassing() {

        return chargeForPassing;
    }

    public void setChargeForPassing(boolean chargeForPassing) {

        this.chargeForPassing = chargeForPassing;
    }


    public boolean isInAppSound() {

        return inAppSound;
    }

    public void setInAppSound(boolean inAppSound) {

        this.inAppSound = inAppSound;
    }


    public String getTopic() {

        return topic;
    }

    public void setTopic(String topic) {

        this.topic = topic;
    }


}
