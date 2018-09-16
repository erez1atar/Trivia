package games.android.trivia.DataBase;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import games.android.trivia.HighScores.WinnerData;


/**
 * Created by LENOVO on 18/02/2016.
 */
public class DBManager
{
    private MyDBHelper dbHelper;
    private int numOfHighScores;

    public DBManager(Context context)
    {
        dbHelper = new MyDBHelper(context);
        numOfHighScores = 10;
    }

    public void add(WinnerData data)
    {
        CursorScore highScores = getAllHighScores();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(highScores.getCount() >= numOfHighScores)
        {
            update(db, data);
            return;
        }
        SQLiteStatement stmt = db.compileStatement(DBConsts.SQL_INSERT_STATMENT);
        stmt.bindString(DBConsts.INSERT_SCORE_IDX, String.valueOf(data.getScore()));
        stmt.bindString(DBConsts.INSERT_NAME_IDX, data.getName());
        stmt.execute();
    }

    public CursorScore getAllHighScores()
    {
        return dbHelper.getAll();
    }

    public int getLocalHighScore() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        CursorScore cursor = new CursorScore(db.rawQuery(DBConsts.SQL_MAX_SCORE, null));
        if(!cursor.moveToFirst())
        {
            cursor.close();
            return 0;
        }
        return cursor.getScore();

    }

    private void update(SQLiteDatabase db, WinnerData data)
    {
        CursorScore cursor = new CursorScore(db.rawQuery(DBConsts.SQL_MIN_SCORE, null));
        if(!cursor.moveToFirst())
        {
            cursor.close();
            return;
        }
        int min = cursor.getScore();
        if(min < data.getScore())
        {
            int id = cursor.getID();
            SQLiteStatement stmt = db.compileStatement(DBConsts.SQL_UPDATE_STATMENT);
            stmt.bindString(DBConsts.UPDATE_SCORE_IDX, String.valueOf(data.getScore()));
            stmt.bindString(DBConsts.UPDATE_NAME_IDX, data.getName());
            stmt.bindString(DBConsts.UPDATE_ID_IDX, String.valueOf(id));
            stmt.execute();
        }
        cursor.close();
    }

}
