package by.fabric.kewbr.talking_crocodile;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;

import by.fabric.kewbr.talking_crocodile.Model.ProgressModel;

import static org.hamcrest.CoreMatchers.is;

@RunWith(AndroidJUnit4.class)
public class TeamModelTest {


    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void ratingIncreasetest() {
        ProgressModel progressModel = new ProgressModel();
        progressModel.increaseRating();
        collector.checkThat(
                "function increaseRating dont return correct result " ,
                progressModel.getRating().intValue(),
                is(1)
        );
    }

    public void ratingDecreasetest() {
        ProgressModel team = new ProgressModel();
        team.decreaseRating();
        collector.checkThat(
                "function decreaseRating dont return correct result " ,
                team.getRating().intValue(),
                is(-1)
        );
    }

    public void isWinnerTest() {
        ProgressModel team = new ProgressModel();
        team.increaseRating();
        collector.checkThat(
                "function isWinner dont return correct result " ,
                team.isWinner(1),
                is(true)
        );
    }
}
