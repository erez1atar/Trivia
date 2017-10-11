package games.android.trivia.HighScores;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import games.android.trivia.R;

/**
 * Created by Erez on 11/10/2017.
 */

public class GlobalScoreAdapter extends ArrayAdapter<WinnerData> {
    private final ArrayList<WinnerData> winnerDatas;
    private final Activity context;

    public GlobalScoreAdapter(Activity context, int resource, ArrayList<WinnerData> winnerDatas) {
        super(context, resource);
        this.context = context;
        this.winnerDatas = winnerDatas;
    }

    @Override
    public WinnerData getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return winnerDatas.size();
    }

    public class ViewHolderWinner {
        TextView name;
        TextView score;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        ViewHolderWinner holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.winner, parent, false);
            holder = new ViewHolderWinner();
            holder.name = (TextView) convertView.findViewById(R.id.nameWinner);
            holder.score = (TextView) convertView.findViewById(R.id.scoreWinner);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderWinner) convertView.getTag();
        }
        Log.d("getView", "position = " + position);

        holder.name.setText(winnerDatas.get(position).getName());

        holder.score.setText(String.valueOf(winnerDatas.get(position).getScore()));

        return convertView;
    }
}
