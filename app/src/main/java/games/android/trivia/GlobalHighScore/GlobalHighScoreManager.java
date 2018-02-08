package games.android.trivia.GlobalHighScore;

import java.util.ArrayList;

import games.android.trivia.App;
import games.android.trivia.HighScores.WinnerData;

/**
 * Created by Erez on 11/10/2017.
 */

public class GlobalHighScoreManager implements FirebaseManager.HighScoreListener, FirebaseManager.MonthlyHighScoreListener
{
    private FirebaseManager firebaseManager = null;
    private String lastName = null;
    private int lastScore = 0;
    private int minWinnersToSaved = 30;
    private int minMontlyWinnersToSaved = 10;

    public GlobalHighScoreManager() {
        firebaseManager = App.getFirebaseManager();
        firebaseManager.setHighScoreListener(this);
        firebaseManager.setMonthlyHighScoreListener(this);
    }

    public void tryAddWinner(String name, int score){
        this.lastName = name;
        this.lastScore = score;
        firebaseManager.requestHighScores();
        firebaseManager.requestMonthlyHighScores();
    }

    @Override
    public void onHighScoresReady(ArrayList<FirebaseWinnerWrapper> winnerWrappers) {
        if(minWinnersToSaved > winnerWrappers.size()){
            this.firebaseManager.addWinner(new WinnerData(lastScore, lastName));
            return;
        }
        int minScore = 1000000000;
        String  keyOfMinScore = "";
        for(int i = 0 ; i < winnerWrappers.size() ; ++i){
            if(winnerWrappers.get(i).getFirebaseWinnerData().getScore() < minScore){
                minScore = winnerWrappers.get(i).getFirebaseWinnerData().getScore();
                keyOfMinScore = winnerWrappers.get(i).getFirebaseKey();
            }
        }
        if(minScore < lastScore){
            firebaseManager.removeWinner(keyOfMinScore);
            firebaseManager.addWinner(new WinnerData(lastScore, lastName));
        }
    }

    @Override
    public void onMonthlyHighScoresReady(ArrayList<FirebaseWinnerWrapper> winnerWrappers) {
        if(minMontlyWinnersToSaved > winnerWrappers.size()){
            this.firebaseManager.addMonthlyWinner(new WinnerData(lastScore, lastName));
            return;
        }
        int minScore = 1000000000;
        String  keyOfMinScore = "";
        for(int i = 0 ; i < winnerWrappers.size() ; ++i){
            if(winnerWrappers.get(i).getFirebaseWinnerData().getScore() < minScore){
                minScore = winnerWrappers.get(i).getFirebaseWinnerData().getScore();
                keyOfMinScore = winnerWrappers.get(i).getFirebaseKey();
            }
        }
        if(minScore < lastScore){
            firebaseManager.removeMonthlyWinner(keyOfMinScore);
            firebaseManager.addMonthlyWinner(new WinnerData(lastScore, lastName));
        }
    }
}
