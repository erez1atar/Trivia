package games.android.trivia.HighScores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import games.android.trivia.R;


/**
 * Created by LENOVO on 15/02/2016.
 */
public class WinnerAdapter extends ArrayAdapter
{
    private final Context ctx;
    private final ArrayList<WinnerData> winners;

    public WinnerAdapter(Context context, int resource, ArrayList<WinnerData> winners)
    {
        super(context, resource);
        this.winners = winners;
        this.ctx = context;
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return winners.size();
    }

    private class ViewHolder
    {
        private TextView date;
        private TextView score;
        private TextView name;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.winner, parent, false);
            ViewHolder vh = new ViewHolder();
            vh.date = (TextView)convertView.findViewById(R.id.dateWinner);
            vh.score = (TextView)convertView.findViewById(R.id.scoreWinner);
            vh.name = (TextView)convertView.findViewById(R.id.nameWinner);
            convertView.setTag(vh);
        }
        ViewHolder vh = (ViewHolder)convertView.getTag();
        vh.date.setText(winners.get(position).getDate());
        vh.score.setText(String.valueOf(winners.get(position).getScore()));
        vh.name.setText(winners.get(position).getName());
        return convertView;
    }

    @Override
    public void add(Object object)
    {
        winners.add((WinnerData)object);
        Collections.sort(winners);

    }
}
