package games.android.trivia.DataBase;


import android.provider.BaseColumns;

/**
 * Created by LENOVO on 18/02/2016.
 */
public class DBConsts {
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SKP = ", ";

    public static final String DATABASE_NAME = "scores.db";

    public static final class FeedEntry implements BaseColumns
    {
        public static final String SCORES_TABLE_NAME = "HallOfFame";
        public static final String SCORES_COLUMN_NAME = "name";
        public static final String SCORES_COLUMN_SCORE = "score";
    }

    public static final String SQL_CREATE_SCORES_TB = "CREATE TABLE IF NOT EXISTS " + FeedEntry.SCORES_TABLE_NAME + " ( " + FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL " + COMMA_SKP + FeedEntry.SCORES_COLUMN_NAME + INTEGER_TYPE + COMMA_SKP + FeedEntry.SCORES_COLUMN_SCORE + INTEGER_TYPE  +" );";
    public static final String SQL_DELETE_SCORES_TB = "DROP TABLE IF EXISTS " + FeedEntry.SCORES_TABLE_NAME;
    public static final String SQL_ALL = "SELECT * FROM " + FeedEntry.SCORES_TABLE_NAME + " ORDER BY " + FeedEntry.SCORES_COLUMN_SCORE + " DESC";

    public static final String SQL_UPDATE_STATMENT =
    "UPDATE "+ DBConsts.FeedEntry.SCORES_TABLE_NAME + " SET " + DBConsts.FeedEntry.SCORES_COLUMN_SCORE + "=?" + " , " + DBConsts.FeedEntry.SCORES_COLUMN_NAME + "=?" +"  WHERE " + DBConsts.FeedEntry._ID + "=?";
    public static final int UPDATE_SCORE_IDX = 1;
    public static final int UPDATE_NAME_IDX = 2;
    public static final int UPDATE_ID_IDX = 3;

    public static final String SQL_MIN_SCORE =
            "SELECT * FROM " + FeedEntry.SCORES_TABLE_NAME +  " ORDER BY "  + FeedEntry.SCORES_COLUMN_SCORE + " LIMIT 1;";

    public static final String SQL_MAX_SCORE =
            "SELECT * FROM " + FeedEntry.SCORES_TABLE_NAME +  " ORDER BY "  + FeedEntry.SCORES_COLUMN_SCORE + " DESC " + " LIMIT 1;";

    public static final String SQL_INSERT_STATMENT =
    "INSERT INTO " + FeedEntry.SCORES_TABLE_NAME +  " ( " + FeedEntry.SCORES_COLUMN_SCORE + COMMA_SKP  + FeedEntry.SCORES_COLUMN_NAME + " ) VALUES (?,?)";
    public static final int INSERT_SCORE_IDX = 1;
    public static final int INSERT_NAME_IDX = 2;

}
