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
import games.android.trivia.Utility;

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
        TextView place;
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
            holder.place = (TextView) convertView.findViewById(R.id.winner_table_place);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderWinner) convertView.getTag();
        }
        Log.d("getView", "position = " + position);

        String nameOneLine = winnerDatas.get(position).getName().split("\n")[0];
        holder.name.setText(nameOneLine);

        holder.score.setText(Utility.getNumberFormat(winnerDatas.get(position).getScore()));

        holder.place.setText(String.valueOf(position + 1));

        if(position == 0){
            convertView.setBackgroundResource(R.drawable.hall_of_fame_item_no_1_background);
        }
        else if(position == 1){
            convertView.setBackgroundResource(R.drawable.hall_of_fame_item_background);
        }
        else if(position == 2){
            convertView.setBackgroundResource(R.drawable.hall_of_fame_item_background);
        }
        else {
            convertView.setBackgroundResource(R.drawable.hall_of_fame_item_background);
        }

        return convertView;
    }
}
