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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import games.android.trivia.App;
import games.android.trivia.HighScores.WinnerData;

/**
 * Created by Erez on 10/10/2017.
 */

public class FirebaseManager {

    private Executor executor;
    private Firebase fb;
    private FirebaseStorage storage;
    private static final String HIGH_SCORE_KEY = "high_score";
    private HighScoreListener highScoreListener = null;
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

    public void addWinner(final WinnerData winnerData) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                fb.child(HIGH_SCORE_KEY).push().setValue(winnerData);
            }
        });
    }

    public void requestHighScores() {

        fb.child(HIGH_SCORE_KEY).addListenerForSingleValueEvent(new ValueEventListener() {
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
                        if(highScoreListener != null) {
                            highScoreListener.onHighScoresReady(highScores);
                        }
                    }
                });

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public interface HighScoreListener {
        void onHighScoresReady(ArrayList<FirebaseWinnerWrapper> winnerWrappers);
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

    public void logEvent(String name, Bundle bundle){
        mFirebaseAnalytics.logEvent(name, bundle);
    }
}
