package games.android.trivia.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LENOVO on 18/02/2016.
 */
public class MyDBHelper extends SQLiteOpenHelper
{

    public MyDBHelper(Context context)
    {
        super(context, DBConsts.DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(DBConsts.SQL_CREATE_SCORES_TB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(DBConsts.SQL_DELETE_SCORES_TB);
        onCreate(db);
    }

    public CursorScore getAll()
    {
        SQLiteDatabase db = getReadableDatabase();
        return new CursorScore(db.rawQuery(DBConsts.SQL_ALL,null));
    }
}
