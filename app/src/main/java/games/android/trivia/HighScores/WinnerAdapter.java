package games.android.trivia.HighScores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import games.android.trivia.App;
import games.android.trivia.R;
import games.android.trivia.Utility;


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
        private TextView score;
        private TextView name;
        private TextView place;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.winner, parent, false);
            ViewHolder vh = new ViewHolder();
            vh.score = (TextView)convertView.findViewById(R.id.scoreWinner);
            vh.name = (TextView)convertView.findViewById(R.id.nameWinner);
            vh.place = (TextView)convertView.findViewById(R.id.winner_table_place);
            convertView.setTag(vh);
        }
        ViewHolder vh = (ViewHolder)convertView.getTag();
        vh.score.setTypeface(App.getResourcesManager().getNumbersFont());
        vh.name.setTypeface(App.getResourcesManager().getNumbersFont());
        vh.place.setTypeface(App.getResourcesManager().getNumbersFont());
        vh.score.setText(Utility.getNumberFormat(winners.get(position).getScore()));
        String nameOneLine = winners.get(position).getName().split("\n")[0];
        vh.name.setText(nameOneLine);
        vh.place.setText((String.valueOf(position + 1)));
        return convertView;
    }

    @Override
    public void add(Object object)
    {
        winners.add((WinnerData)object);
        Collections.sort(winners);

    }
}
