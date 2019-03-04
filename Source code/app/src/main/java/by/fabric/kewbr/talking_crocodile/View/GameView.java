package by.fabric.kewbr.talking_crocodile.View;

import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Build;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import by.fabric.kewbr.talking_crocodile.Database.MainDBHelper;
import by.fabric.kewbr.talking_crocodile.R;
import by.fabric.kewbr.talking_crocodile.ViewModel.GameViewModel;

public class GameView extends AppCompatActivity  implements View.OnTouchListener {

    //private GestureDetector gestureDetector;
    int windowwidth; // Actually the width of the RelativeLayout.
    int windowheight; // Actually the height of the RelativeLayout.
    private ImageView mImageView;
    private ViewGroup mRrootLayout;
    private TextView mTextView;
    private int _yDelta;
    RelativeLayout.LayoutParams lp, lpInit;
    private boolean isEnded;
    private String[] array = {"Кошка", "Собака", "Часы", "Компьютер", "Лес"};

    private TextView guessTextView;
    private TextView passTextView;
    private int passCount;
    private int guessCount;

    private GameViewModel vm = new GameViewModel();

    private MainDBHelper dbHelper = MainDBHelper.getInstance(this);

    AnimatorSet s = new AnimatorSet();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);
        // We are interested when the image view leaves its parent RelativeLayout
        // container and not the screen, so the following code is not needed.
//        DisplayMetrics displaymetrics = new DisplayMetrics();
//        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//        windowwidth = displaymetrics.widthPixels;
//        windowheight = displaymetrics.heightPixels;
        mRrootLayout = (ViewGroup) findViewById(R.id.circle);
        mImageView = (ImageView) mRrootLayout.findViewById(R.id.sun);
        mTextView = (TextView) mRrootLayout.findViewById(R.id.word);
        guessTextView = (TextView) mRrootLayout.findViewById(R.id.guessWordCount);
        passTextView = (TextView) mRrootLayout.findViewById(R.id.passWordCount);
        passTextView.setText("0");
        guessTextView.setText("0");

        mTextView.setText(array[new Random().nextInt(100) % 5]);
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
        //mImageView.setImageAlpha(128);
    }

    // Tracks when we have reported that the image view is out of bounds so we
    // don't over report.
    private boolean isOutReported = false;

    //@Override
    //public boolean dispatchTouchEvent(MotionEvent ev) {
    //    onTouch(mImageView,ev);
    //    return true;
    //    //return super.dispatchTouchEvent(ev);
    //}

    public boolean onTouch(View view, MotionEvent event) {
        final int X = (int) event.getRawX();
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
                        //if(view.getTop() < delta || view.getTop() > (windowheight - delta - view.getHeight())
                        if(lp.topMargin <= k  || lp.topMargin > (windowheight - k - view.getHeight())) {
                            startAnimation(lp.topMargin <= k );
                            isOutReported = true;
                        }
                        else
                        view.setLayoutParams(lp);
                        break;
                }
        }
        // invalidate is redundant if layout params are set or not needed if they are not set.
//        mRrootLayout.invalidate();
        return true;
    }

    private boolean isOut(View view) {
        // Check to see if the view is out of bounds by calculating how many pixels
        // of the view must be out of bounds to and checking that at least that many
        // pixels are out.
//        float percentageOut = 0.50f;
//        int viewPctWidth = (int) (view.getWidth() * percentageOut);
//        int viewPctHeight = (int) (view.getHeight() * percentageOut);
//        return ((-view.getLeft() >= viewPctWidth) ||
//                (view.getRight() - windowwidth) > viewPctWidth ||
//                (-view.getTop() >= viewPctHeight) ||
//                (view.getBottom() - windowheight) > viewPctHeight);
        if (isEnded) {
            isEnded = false;
            return isEnded;
        }
        float delta = 0.1f * windowheight;
        return (view.getTop() < delta || view.getTop() > (windowheight - delta - view.getHeight()));
    }

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

//        } else {
//
//            animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator updatedAnimation) {
//                    // You can use the animated value in a property that uses the
//                    // same type as the animation. In this case, you can use the
//                    // float value in the translationX property.
//                    int animatedValue = (int) updatedAnimation.getAnimatedValue();
//                    lp.topMargin = animatedValue;
//                    mImageView.setLayoutParams(lp);
//                }
//            });
//        }
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
//            animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator updatedAnimation) {
//                    int animatedValue = (int) updatedAnimation.getAnimatedValue();
//                    lp.topMargin = animatedValue;
//                    mImageView.setLayoutParams(lp);
//                }
//            });
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
                mTextView.setText(array[new Random().nextInt(100) % 5]);
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
        //because we have only 1 team, so we don't need to implement a full method to work with teams
        if(vm.myTeam.getRating()>0)
        vm.myTeam.decreaseRating();
        passTextView.setText(" "+ passCount);

    }

    private void increaseGuessWordCount() {
        guessCount++;
        vm.myTeam.increaseRating();
        guessTextView.setText(" "+ guessCount);
    }

    private void setOpacity(int value) {
        mImageView.setImageAlpha(value);
        mTextView.setTextColor(Color.argb(value, 0, 0, 0));
    }

    private static void closeWindow(){}
    public static void showDialogAndClose(String s){
        closeWindow();
    }
}
