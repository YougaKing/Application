package youga.com.application;

import java.util.Timer;
import java.util.TimerTask;

public class Chronometer {

    private static final int PERIOD = 1000;
    private TimerTask mTimerTask;
    private Timer mTimer = new Timer();
    private long mDuration;
    private OnChronometerTickListener mOnChronometerTickListener;
    private boolean mRunning;

    public void start() {
        mRunning = true;
        mTimerTask = new TimerTask() {

            @Override
            public void run() {
                if (!mRunning) return;
                mDuration += PERIOD;
                if (mOnChronometerTickListener != null)
                    mOnChronometerTickListener.onChronometerTick(mDuration);
            }
        };
        mTimer.schedule(mTimerTask, 0, PERIOD);
    }

    public void stop() {
        mRunning = false;
        mTimerTask.cancel();
        mTimerTask = null;
        if (mOnChronometerTickListener != null)
            mOnChronometerTickListener.onChronometerTick(mDuration);
    }

    public long getDuration() {
        return mDuration;
    }

    public boolean isRunning() {
        return mRunning;
    }

    public void destory() {
        stop();
        mTimer.cancel();
        mTimerTask = null;
        mTimer = null;
    }

    public void setOnChronometerTickListener(OnChronometerTickListener onChronometerTickListener) {
        mOnChronometerTickListener = onChronometerTickListener;
    }

    public interface OnChronometerTickListener {
        void onChronometerTick(long duration);
    }
}
