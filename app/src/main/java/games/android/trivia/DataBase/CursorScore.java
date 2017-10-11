package games.android.trivia.DataBase;

import android.database.Cursor;
import android.database.CursorWrapper;

/**
 * Created by LENOVO on 18/02/2016.
 */
public class CursorScore extends CursorWrapper
{
    private Cursor cursor;

    public CursorScore(Cursor cursor)
    {
        super(cursor);
        this.cursor = cursor;
    }

    public int getScore()
    {
        return cursor.getInt(cursor.getColumnIndexOrThrow(DBConsts.FeedEntry.SCORES_COLUMN_SCORE));
    }

    public String getName()
    {
        return cursor.getString(cursor.getColumnIndexOrThrow(DBConsts.FeedEntry.SCORES_COLUMN_NAME));
    }

    public int getID()
    {
        return cursor.getInt(cursor.getColumnIndexOrThrow(DBConsts.FeedEntry._ID));
    }
}
