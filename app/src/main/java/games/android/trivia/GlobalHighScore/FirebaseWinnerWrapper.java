package games.android.trivia.GlobalHighScore;

import games.android.trivia.HighScores.WinnerData;

/**
 * Created by Erez on 11/10/2017.
 */

public class FirebaseWinnerWrapper {
    private WinnerData firebaseWinnerData = null;
    private String firebaseKey = null;

    public FirebaseWinnerWrapper(WinnerData firebaseWinnerData, String firebaseKey){
        this.firebaseKey = firebaseKey;
        this.firebaseWinnerData = firebaseWinnerData;
    }

    public WinnerData getFirebaseWinnerData() {
        return firebaseWinnerData;
    }

    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }

    public void setFirebaseWinnerData(WinnerData firebaseWinnerData) {
        this.firebaseWinnerData = firebaseWinnerData;
    }
}

