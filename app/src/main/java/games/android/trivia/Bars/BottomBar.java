package games.android.trivia.Bars;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import games.android.trivia.App;
import games.android.trivia.R;

public class BottomBar extends Fragment {
    private TextView rangeTxt = null;
    private TextView prize = null;
    private String format = "%d  -  %d";
    private BottomBarListener listener = null;
    private TextView StageIdTxt = null;

    public BottomBar() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_bar, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.rangeTxt = (TextView) getView().findViewById(R.id.bottom_bar_range);
        this.prize = (TextView)getView().findViewById(R.id.bottom_bar_prize) ;
        this.StageIdTxt = (TextView)getView().findViewById(R.id.bottom_bar_stage_id);
        this.prize.setTypeface(App.getResourcesManager().getNumbersFont());
        this.listener.onBottomBarCreatedFinished();
    }

    public void showRange(int min, int max, int id) {
        this.rangeTxt.setText(String.format(this.format,min,max));
        this.StageIdTxt.setText(String.valueOf(id));
    }

    public void setPrize(String prize) {
        this.prize.setText(prize);
    }

    public void setListener(BottomBarListener listener) {
        this.listener = listener;
    }

    public interface BottomBarListener {
        void onBottomBarCreatedFinished();
    }
}
