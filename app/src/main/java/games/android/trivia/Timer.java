package games.android.trivia;

import android.os.CountDownTimer;
import android.util.Log;

/**
 * Created by Erez on 05/10/2017.
 */

public class Timer
{
    private CountDownTimer timer = null;
    private TimerListener listener = null;
    private long lastMiliSecLeft = 0;

    public Timer(TimerListener listener) {
        this.listener = listener;
    }

    public void start(int seconds) {
        endTimer();
        timer = new CountDownTimer(seconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Timer.this.lastMiliSecLeft = millisUntilFinished;
                listener.onTickUpdated((int)millisUntilFinished / 1000);
                Log.d("onTick", millisUntilFinished / 1000 + "");
            }

            @Override
            public void onFinish() {
                listener.onTimeFinished();
            }
        };
        timer.start();
    }

    public void pause() {
        this.timer.cancel();
    }

    public void resume() {
        this.start((int)this.lastMiliSecLeft / 1000);
    }

    public void endTimer() {
        if(timer != null) {
            timer.cancel();
        }

        timer = null;
    }

    public interface TimerListener {
        void onTickUpdated(int seconds);
        void onTimeFinished();
    }
}
