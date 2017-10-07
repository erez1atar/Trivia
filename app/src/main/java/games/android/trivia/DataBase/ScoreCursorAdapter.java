package games.android.trivia.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import games.android.trivia.App;
import games.android.trivia.R;


/**
 * Created by LENOVO on 18/02/2016.
 */
public class ScoreCursorAdapter extends CursorAdapter
{
    public ScoreCursorAdapter(Context context, CursorScore cursor, int flags)
    {
        super(context, cursor, flags);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.winner, parent, false);
        ViewHolder vh = new ViewHolder();
        vh.date = (TextView) view.findViewById(R.id.dateWinner);
        vh.name = (TextView) view.findViewById(R.id.nameWinner);
        vh.score = (TextView) view.findViewById(R.id.scoreWinner);
        vh.date.setTypeface(App.getResourcesManager().getNumbersFont());
        vh.name.setTypeface(App.getResourcesManager().getNumbersFont());
        vh.score.setTypeface(App.getResourcesManager().getNumbersFont());
        view.setTag(vh);
        return view;
    }

    private class ViewHolder
    {
        TextView date;
        TextView name;
        TextView score;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        ViewHolder vh = (ViewHolder)view.getTag();
        CursorScore cursorScore = (CursorScore)cursor;

        String dateVal = cursorScore.getDate();
        String nameVal = cursorScore.getName();
        int scoreVal = cursorScore.getScore();

        vh.date.setText(dateVal);
        vh.name.setText(nameVal);
        vh.score.setText(String.valueOf(scoreVal));

    }
}
