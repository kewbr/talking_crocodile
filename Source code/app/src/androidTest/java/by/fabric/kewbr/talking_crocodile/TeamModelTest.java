package by.fabric.kewbr.talking_crocodile;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;

import by.fabric.kewbr.talking_crocodile.Model.Team;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class TeamModelTest {


    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void ratingIncreasetest() {
        Team team = new Team();
        team.increaseRating();
        collector.checkThat(
                "function increaseRating dont return correct result " ,
                team.getRating(),
                is(1)
        );
    }

    public void ratingDecreasetest() {
        Team team = new Team();
        team.decreaseRating();
        collector.checkThat(
                "function decreaseRating dont return correct result " ,
                team.getRating(),
                is(-1)
        );
    }

    public void isWinnerTest() {
        Team team = new Team();
        team.increaseRating();
        collector.checkThat(
                "function isWinner dont return correct result " ,
                team.isWinner(1),
                is(true)
        );
    }
}
