package games.android.trivia.GlobalHighScore;

import android.os.Bundle;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import games.android.trivia.App;
import games.android.trivia.HighScores.WinnerData;

/**
 * Created by Erez on 10/10/2017.
 */

public class FirebaseManager {


    public interface HighScoreListener {
        void onHighScoresReady(ArrayList<FirebaseWinnerWrapper> winnerWrappers);
    }

    public interface MonthlyHighScoreListener {
        void onMonthlyHighScoresReady(ArrayList<FirebaseWinnerWrapper> winnerWrappers);
    }

    private Executor executor;
    private Firebase fb;
    private FirebaseStorage storage;
    private static final String HIGH_SCORE_KEY = "high_score";
    private HighScoreListener highScoreListener = null;
    private MonthlyHighScoreListener monthlyHighScoreListener = null;
    private FirebaseAnalytics mFirebaseAnalytics;

    private static FirebaseManager instance =  null;

    public FirebaseManager() {
        Firebase.setAndroidContext(App.getInstance());
        executor = Executors.newFixedThreadPool(5);
        fb = new Firebase("https://trivia-280c2.firebaseio.com");
        storage = FirebaseStorage.getInstance();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(App.getInstance());
    }

    public void setHighScoreListener(HighScoreListener highScoreListener) {
        this.highScoreListener = highScoreListener;
    }

    public void setMonthlyHighScoreListener(MonthlyHighScoreListener highScoreListener) {
        this.monthlyHighScoreListener = highScoreListener;
    }

    public void addWinner(final WinnerData winnerData) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                fb.child(HIGH_SCORE_KEY).push().setValue(winnerData);
            }
        });
    }

    public void addMonthlyWinner(final WinnerData winnerData) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                fb.child(getMontlyTableKey()).push().setValue(winnerData);
            }
        });
    }

    private void doRequestHighScore(final String tableKey) {
        fb.child(tableKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final DataSnapshot dataSnapshotFinal = dataSnapshot;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<FirebaseWinnerWrapper> highScores = new ArrayList<>();

                        for (DataSnapshot postSnapshot : dataSnapshotFinal.getChildren()) {
                            WinnerData winnerData = postSnapshot.getValue(WinnerData.class);
                            highScores.add(new FirebaseWinnerWrapper(winnerData, postSnapshot.getKey()));
                            Log.d("requestHighScores ", winnerData.toString());
                        }
                        if(HIGH_SCORE_KEY.equals(tableKey)) {
                            highScoreListener.onHighScoresReady(highScores);
                        }
                        else {
                            monthlyHighScoreListener.onMonthlyHighScoresReady(highScores);
                        }
                    }
                });

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void requestHighScores() {
        this.doRequestHighScore(HIGH_SCORE_KEY);
    }

    public void requestMonthlyHighScores() {
        this.doRequestHighScore(getMontlyTableKey());
    }


    public void removeWinner(final String id) {
        Log.d("fb", "try to remove " + id);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                fb.child(HIGH_SCORE_KEY).child(id).removeValue();
            }
        });
    }

    public void removeMonthlyWinner(final String id) {
        Log.d("fb", "try to remove " + id);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                fb.child(getMontlyTableKey()).child(id).removeValue();
            }
        });
    }

    private String getMontlyTableKey() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        String key =  String.format("%s_%d_%d",HIGH_SCORE_KEY,calendar.get(Calendar.MONTH),calendar.get(Calendar.YEAR));
        return key;
    }

    public void logEvent(String name, Bundle bundle){
        mFirebaseAnalytics.logEvent(name, bundle);
    }
}
