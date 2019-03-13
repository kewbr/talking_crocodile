package by.fabric.kewbr.talking_crocodile.View;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.content.Intent;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import by.fabric.kewbr.talking_crocodile.Model.WordsModel;
import by.fabric.kewbr.talking_crocodile.R;
import by.fabric.kewbr.talking_crocodile.ViewModel.GameViewModel;
import io.realm.Realm;

public class GameView extends AppCompatActivity  implements View.OnTouchListener, Observer {

    int windowwidth; // Actually the width of the RelativeLayout.
    int windowheight; // Actually the height of the RelativeLayout.
    private ImageView mImageView;
    private ViewGroup mRrootLayout;
    private TextView mTextView;
    private int _yDelta;
    RelativeLayout.LayoutParams lp, lpInit;
    private boolean isEnded;
    private String[] dataFromDB;

    private TextView guessTextView;
    private TextView passTextView;
    private int passCount;
    private int guessCount;
    public int allCount;

    private GameViewModel vm;

    AnimatorSet s = new AnimatorSet();
    private CountDownTimer timer;

    private boolean flag;
    private TextView timerTextView;
    private long timerTimeConstant;
    private long timerTime;
    private long previosTime;

    private Handler mHandler = new Handler();

    // Описание Runnable-объекта
    private Runnable timeUpdaterRunnable;

    // Tracks when we have reported that the image view is out of bounds so we
    // don't over report.
    private boolean isOutReported = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        vm = new GameViewModel(this.getApplicationContext());
        timerTimeConstant = vm.roundTimer;
        vm.addObserver(this);
        startRoundScreen();

        Realm realm = Realm.getDefaultInstance();

        WordsModel word = realm.where(WordsModel.class).findFirst();

    }


    private void startGame(){
        vm.startNewRound();
        // We are interested when the image view leaves its parent RelativeLayout
        // container and not the screen, so the following code is not needed.

        mRrootLayout = (ViewGroup) findViewById(R.id.circle);
        mImageView = (ImageView) mRrootLayout.findViewById(R.id.sun);
        mTextView = (TextView) mRrootLayout.findViewById(R.id.word);

        guessTextView = (TextView) mRrootLayout.findViewById(R.id.guessWordCount);
        passTextView = (TextView) mRrootLayout.findViewById(R.id.passWordCount);
        passTextView.setText("0");
        guessTextView.setText("0");

        timerTextView = (TextView) mRrootLayout.findViewById(R.id.timerTextView);

        timerTime = timerTimeConstant;
        timeUpdaterRunnable = new Runnable() {
            public void run() {
                // вычисляем время
                timerTime = timerTime - (SystemClock.uptimeMillis() - previosTime);
                previosTime = SystemClock.uptimeMillis();
                int second = (int) (timerTime / 1000);
                int min = second / 60;
                second = second % 60;
                // выводим время
                timerTextView.setText("" + String.format("%02d", min) + ":" + String.format("%02d", second));
                // повторяем через каждые 200 миллисекунд
                if (timerTime < 1000) {
                    onPause();
                     mHandler.removeCallbacks(timeUpdaterRunnable);
                    return;
                }
                mHandler.postDelayed(this, 1000);
            }
        };

        mTextView.setText("АЛЛО БАЗА НА РЕКОНСТРУКЦИИ ОТЪЕБИСЬ!");
                //array[new Random().nextInt(100) % 5]);
        // These these following 2 lines that address layoutparams set the width
        // and height of the ImageView to 150 pixels and, as a side effect, clear any
        // params that will interfere with movement of the ImageView.
        // We will rely on the XML to define the size and avoid anything that will
        // interfere, so we will comment these lines out. (You can test out how a layout parameter
        // can interfere by setting android:layout_centerInParent="true" in the ImageView.
        mImageView.setOnTouchListener(this);

        RelativeLayout.LayoutParams temp = (RelativeLayout.LayoutParams) mImageView
                .getLayoutParams();
        lpInit = new RelativeLayout.LayoutParams(temp);

        lp = lpInit;

        // Capture the width of the RelativeLayout once it is laid out.
        mRrootLayout.post(new Runnable() {
            @Override
            public void run() {
                windowwidth = mRrootLayout.getWidth();
                windowheight = mRrootLayout.getHeight();
            }
        });
        int top = windowheight / 2;
        isEnded = false;

        previosTime = SystemClock.uptimeMillis();
        // Добавляем Runnable-объект timeUpdaterRunnable в очередь
        // сообщений, объект должен быть запущен после задержки в 100 мс
        mHandler.postDelayed(timeUpdaterRunnable, 100);

    }

    public boolean onTouch(View view, MotionEvent event) {
        final int Y = (int) event.getRawY();

        // Check if the image view is out of the parent view and report it if it is.
        // Only report once the image goes out and don't stack toasts.
        if (view.getId() == R.id.sun) {
            if (!isOutReported )
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        s.cancel();
                        _yDelta = Y - view.getTop();
                        break;
                    case MotionEvent.ACTION_UP: {
                        returnAnimation(view.getTop() < windowheight / 2, mImageView.getImageAlpha());
                        break;
                    }
                    case MotionEvent.ACTION_POINTER_DOWN:
                    case MotionEvent.ACTION_POINTER_UP:
                        // Do nothing
                        break;
                    case MotionEvent.ACTION_MOVE:
                        lp = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();
                        // Image is centered to start, but we need to unhitch it to move it around.
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            lp.removeRule(RelativeLayout.CENTER_HORIZONTAL);
                            lp.removeRule(RelativeLayout.CENTER_VERTICAL);
                        } else {
                            lp.addRule(RelativeLayout.CENTER_HORIZONTAL, 0);
                            lp.addRule(RelativeLayout.CENTER_VERTICAL, 0);
                        }
                        lp.topMargin = Y - _yDelta;
                        lp.leftMargin = view.getLeft();
                        // Negative margins here ensure that we can move off the screen to the right
                        // and on the bottom. Comment these lines out and you will see that
                        // the image will be hemmed in on the right and bottom and will actually shrink.
                        lp.rightMargin = view.getWidth() - lp.leftMargin - windowwidth;
                        lp.bottomMargin = view.getHeight() - lp.topMargin - windowheight;
                        float height = windowheight;
                        float delta = 0.5f * height - (float) view.getHeight() / 2;
                        float viewCenter = (float) lp.topMargin + view.getHeight() / 2;
                        float temp = Math.abs(viewCenter - height / 2);
                        float tempr = 1 - (temp / delta);
                        float opacity = tempr * 255;

                        setOpacity((int) opacity);
                        float k = 0.1f * windowheight;
                        if(lp.topMargin <= k  || lp.topMargin > (windowheight - k - view.getHeight())) {
                            startAnimation(lp.topMargin <= k );
                            isOutReported = true;
                        }
                        else
                        view.setLayoutParams(lp);
                        break;
                }
        }
        return true;
    }

//    private boolean isOut(View view) {
//
//        if (isEnded) {
//            isEnded = false;
//            return isEnded;
//        }
//        float delta = 0.1f * windowheight;
//        return (view.getTop() < delta || view.getTop() > (windowheight - delta - view.getHeight()));
//    }

    private void returnAnimation(boolean isPullUp, int opacity) {
        ValueAnimator animation;
        ObjectAnimator obj = ObjectAnimator.ofInt(mImageView, "ImageAlpha", opacity, 255);
        obj.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int animatedValue = (int) valueAnimator.getAnimatedValue();
                mTextView.setTextColor(Color.argb(animatedValue, 0, 0, 0));
            }
        });
        animation = ValueAnimator.ofInt(lp.topMargin, (windowheight - mImageView.getHeight()) / 2);
        //if (isPullUp) {

            animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator updatedAnimation) {
                    // You can use the animated value in a property that uses the
                    // same type as the animation. In this case, you can use the
                    // float value in the translationX property.
                    int animatedValue = (int) updatedAnimation.getAnimatedValue();
                    lp.topMargin = animatedValue;
                    mImageView.setLayoutParams(lp);
                }

            });

        animation.setDuration(1000);
        obj.setDuration(1000);
        //AnimatorSet s = new AnimatorSet();
        s.playTogether(animation, obj);
        s.start();
    }

    private void startAnimation(boolean isPullUp) {

        ValueAnimator animation;
        ObjectAnimator obj = ObjectAnimator.ofInt(mImageView, "ImageAlpha", 0, 255);
        obj.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int animatedValue = (int) valueAnimator.getAnimatedValue();
                mTextView.setTextColor(Color.argb(animatedValue, 0, 0, 0));
            }
        });
        animation = ValueAnimator.ofInt(lp.topMargin, (windowheight - mImageView.getHeight()) / 2);
        if (isPullUp) {
            increaseGuessWordCount();


        } else {
            increasePassWordCount();
        }
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator updatedAnimation) {
                int animatedValue = (int) updatedAnimation.getAnimatedValue();
                lp.topMargin = animatedValue;
                mImageView.setLayoutParams(lp);
            }

        });
        animation.setDuration(1000);
        obj.setDuration(1000);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animation, obj);
        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mTextView.setText("Алло БАЗА НА РЕКОНСТРУКЦИИ ОТЪЕБИСЬ!");

        }

            @Override
            public void onAnimationEnd(Animator animator) {
                isOutReported = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        };
        set.addListener(animatorListener);
        set.start();
    }

    private void increasePassWordCount() {
        passCount++;
        allCount++;
        //because we have only 1 team, so we don't need to implement a full method to work with teams
        if(vm.myTeam.getRating()>0)
        vm.myTeam.decreaseRating();
        passTextView.setText(" "+ passCount);

    }

    private void increaseGuessWordCount() {
        guessCount++;
        allCount++;
        //change this when we have more than 1 team to " vm.inspectTeamsRating()"
        vm.myTeam.increaseRating();
        guessTextView.setText(" "+ guessCount);
        if(vm.myTeam.isWinner(vm.gameSettings.wordCount)) {
            vm.stopGame();
            vm.deleteObserver(this);
            finish();
            openFinishScreen();
        }
    }

    private void setOpacity(int value) {
        mImageView.setImageAlpha(value);
        mTextView.setTextColor(Color.argb(value, 0, 0, 0));
    }

//    private static void closeWindow(){}
//    public static void showDialogAndClose(String s){
//
//        closeWindow();
//    }

    @Override
    public void update(Observable observable, Object o) {
        //onPause();
        openRoundEndScreen();
    }


    @Override
    protected void onPause() {
        // Удаляем Runnable-объект для прекращения задачи
        mHandler.removeCallbacks(timeUpdaterRunnable);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Добавляем Runnable-объект
        if(vm.roundTimer == 0) {
            guessCount = 0;
            passCount = 0;
            mHandler.removeCallbacks(timeUpdaterRunnable);
            startRoundScreen();
            //startGame();
        }
        else
        {
            //previosTime = SystemClock.uptimeMillis();
            //timerTime = timerTimeConstant;
            // Добавляем Runnable-объект timeUpdaterRunnable в очередь
            // сообщений, объект должен быть запущен после задержки в 100 мс
            mHandler.postDelayed(timeUpdaterRunnable, 100);
        }

    }

    @Override
    protected void onStop() {
        mHandler.removeCallbacks(timeUpdaterRunnable);
        super.onStop();
        vm.stopGame();
        //stop the timers
    }

    private void startRoundScreen(){
        setContentView(R.layout.start_round);
        TextView text = findViewById(R.id.roundName);
        TextView rating = findViewById(R.id.rating);
        text.setText("Раунд " + vm.roundCount);
        rating.setText("" + vm.myTeam.getRating());
        guessCount = 0;
        passCount = 0;
        timer = new CountDownTimer(5000, 1000)
        {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                setContentView(R.layout.activity_game_view);
                startGame();
            }
        }.start();
        Log.i("Im observer"," ");
    }

    private void openFinishScreen()
    {
        Intent intent = new Intent(this, FinishView.class);
        intent.putExtra("Team Name",vm.myTeam.teamName);
        intent.putExtra("Team Rating", vm.myTeam.getRating());
        startActivity(intent);
    }

    private void openRoundEndScreen()
    {
        Intent intent = new Intent(this, RoundView.class);
        intent.putExtra("Words Count", allCount);
        intent.putExtra("Team Name",vm.myTeam.teamName);
        intent.putExtra("Current Rating", guessCount-passCount);
        startActivity(intent);
    }
}
