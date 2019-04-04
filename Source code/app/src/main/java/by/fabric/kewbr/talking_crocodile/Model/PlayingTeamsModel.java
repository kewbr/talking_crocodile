package by.fabric.kewbr.talking_crocodile.Model;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class PlayingTeamsModel extends RealmObject {

        @Required
        public String teamName;

        private long id;


        public String getTeamName() {

            return teamName;
        }

        public void setTeamName(String teamName) {

            this.teamName = teamName;
        }


        public long getId() {
            return id;
        }

        public void setId(long id) {

            this.id = id;
        }
}
