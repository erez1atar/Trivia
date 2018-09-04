package games.android.trivia.GlobalHighScore;

import java.util.ArrayList;

import games.android.trivia.App;
import games.android.trivia.HighScores.WinnerData;

/**
 * Created by Erez on 11/10/2017.
 */

public class GlobalHighScoreManager implements FirebaseManager.HighScoreListener, FirebaseManager.MonthlyHighScoreListener
{
    private enum EReqState {
        DATA_REQUEST,
        ADD_WINNER
    }

    private enum ETableType {
        MONTH,
        ALL_TIMES
    }

    public interface TablesDataRequester {
        void onTablesDataReady(TablesData tablesData);
    }

    public class TablesData {
        public int monthlyMinScore = -1;
        public int monthlyMaxScore = -1;
        public int minScore = -1;
        public int maxScore = -1;
    }

    private static GlobalHighScoreManager instance = null;

    private FirebaseManager firebaseManager = null;
    private TablesDataRequester tablesDataRequester = null;
    private TablesData lastTableData = null;
    private EReqState currentState = null;
    private String lastName = null;
    private int lastScore = 0;
    private int minWinnersToSaved = 30;
    private int minMontlyWinnersToSaved = 10;

    public static GlobalHighScoreManager getInstance() {
        if(instance == null) {
            instance = new GlobalHighScoreManager();
        }
        return instance;
    }
    private GlobalHighScoreManager() {
        firebaseManager = App.getFirebaseManager();
        firebaseManager.setHighScoreListener(this);
        firebaseManager.setMonthlyHighScoreListener(this);
    }

    public void tryAddWinner(String name, int score){
        currentState = EReqState.ADD_WINNER;
        this.lastName = name;
        this.lastScore = score;
        firebaseManager.setHighScoreListener(this);
        firebaseManager.setMonthlyHighScoreListener(this);
        firebaseManager.requestHighScores();
        firebaseManager.requestMonthlyHighScores();
    }

    public void requestTablesData(TablesDataRequester tablesDataRequester) {
        lastTableData = null;
        currentState = EReqState.DATA_REQUEST;
        this.tablesDataRequester = tablesDataRequester;
        firebaseManager.setHighScoreListener(this);
        firebaseManager.setMonthlyHighScoreListener(this);
        firebaseManager.requestHighScores();
        firebaseManager.requestMonthlyHighScores();
    }

    private void doAddWinner(ETableType tableType, ArrayList<FirebaseWinnerWrapper> winnerWrappers ) {
        if(tableType == ETableType.ALL_TIMES) {
            if(minWinnersToSaved > winnerWrappers.size()){
                this.firebaseManager.addWinner(new WinnerData(lastScore, lastName));
                return;
            }
        }
        else if(tableType == ETableType.MONTH) {
            if(minMontlyWinnersToSaved > winnerWrappers.size()){
                this.firebaseManager.addMonthlyWinner(new WinnerData(lastScore, lastName));
                return;
            }
        }

        int minScore = 1000000000;
        String  keyOfMinScore = "";
        for(int i = 0 ; i < winnerWrappers.size() ; ++i){
            if(winnerWrappers.get(i).getFirebaseWinnerData().getScore() < minScore){
                minScore = winnerWrappers.get(i).getFirebaseWinnerData().getScore();
                keyOfMinScore = winnerWrappers.get(i).getFirebaseKey();
            }
        }
        if(tableType == ETableType.ALL_TIMES) {
            if(minScore < lastScore){
                firebaseManager.removeWinner(keyOfMinScore);
                firebaseManager.addWinner(new WinnerData(lastScore, lastName));
            }
        }
        else if(tableType == ETableType.MONTH) {
            if(minScore < lastScore){
                firebaseManager.removeMonthlyWinner(keyOfMinScore);
                firebaseManager.addMonthlyWinner(new WinnerData(lastScore, lastName));
            }
        }

    }

    private void doProcessDataForRequest(ETableType tableType, ArrayList<FirebaseWinnerWrapper> winnerWrappers ){
        if(lastTableData == null) {
            lastTableData = new TablesData();
        }
        int minScore = 1000000000;
        int maxScore = -1;

        for(int i = 0 ; i < winnerWrappers.size() ; ++i){
            if(winnerWrappers.get(i).getFirebaseWinnerData().getScore() < minScore){
                minScore = winnerWrappers.get(i).getFirebaseWinnerData().getScore();
            }
            if(winnerWrappers.get(i).getFirebaseWinnerData().getScore() > maxScore){
                maxScore = winnerWrappers.get(i).getFirebaseWinnerData().getScore();
            }
        }
        if(tableType == ETableType.ALL_TIMES) {
            lastTableData.maxScore = maxScore;
            lastTableData.minScore = minScore;
        }
        else if(tableType == ETableType.MONTH) {
            lastTableData.monthlyMaxScore = maxScore;
            lastTableData.monthlyMinScore = minScore;
        }
        // check if all parameters ready to send
        if(lastTableData.monthlyMinScore != -1 && lastTableData.monthlyMaxScore != -1 && lastTableData.minScore != -1 && lastTableData.maxScore != -1){
            tablesDataRequester.onTablesDataReady(lastTableData);
        }

    }

    @Override
    public void onHighScoresReady(ArrayList<FirebaseWinnerWrapper> winnerWrappers) {
        if(currentState == EReqState.ADD_WINNER ) {
            doAddWinner(ETableType.ALL_TIMES, winnerWrappers);
        }
        else if(currentState == EReqState.DATA_REQUEST) {
            doProcessDataForRequest(ETableType.ALL_TIMES, winnerWrappers);
        }

    }

    @Override
    public void onMonthlyHighScoresReady(ArrayList<FirebaseWinnerWrapper> winnerWrappers) {
        if(currentState == EReqState.ADD_WINNER ) {
            doAddWinner(ETableType.MONTH, winnerWrappers);
        }
        else if(currentState == EReqState.DATA_REQUEST) {
            doProcessDataForRequest(ETableType.MONTH, winnerWrappers);
        }
    }
}
